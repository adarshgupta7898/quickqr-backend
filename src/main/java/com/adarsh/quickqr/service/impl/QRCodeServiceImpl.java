package com.adarsh.quickqr.service.impl;

import com.adarsh.quickqr.dto.GenerateQRRequest;
import com.adarsh.quickqr.dto.GenerateQRResponse;
import com.adarsh.quickqr.dto.QRCodeResponse;
import com.adarsh.quickqr.entity.QRCode;
import com.adarsh.quickqr.exception.QRCodeNotFoundException;
import com.adarsh.quickqr.mapper.QRCodeMapper;
import com.adarsh.quickqr.repository.QRCodeRepository;
import com.adarsh.quickqr.service.QRCodeService;
import com.adarsh.quickqr.util.QRCodeGeneratorUtil;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Implementation of QRCodeService containing all business logic.
 */
@Slf4j
@Service
public class QRCodeServiceImpl implements QRCodeService {

    private final QRCodeRepository qrCodeRepository;
    private final QRCodeGeneratorUtil qrCodeGeneratorUtil;
    private final QRCodeMapper qrCodeMapper;

    public QRCodeServiceImpl(QRCodeRepository qrCodeRepository,
                             QRCodeGeneratorUtil qrCodeGeneratorUtil,
                             QRCodeMapper qrCodeMapper) {
        this.qrCodeRepository = qrCodeRepository;
        this.qrCodeGeneratorUtil = qrCodeGeneratorUtil;
        this.qrCodeMapper = qrCodeMapper;
    }

    @Override
    @Transactional
    public GenerateQRResponse generateQRCode(GenerateQRRequest request, String baseUrl) throws IOException {
        String imagePath;
        try {
            imagePath = qrCodeGeneratorUtil.generateAndSave(request.getContent());
        } catch (WriterException e) {
            log.error("Failed to generate QR Code for content: {}", request.getContent(), e);
            throw new IOException("QR Code generation failed: " + e.getMessage(), e);
        }

        QRCode qrCode = QRCode.builder()
                .title(request.getTitle())
                .type(request.getType())
                .content(request.getContent())
                .imagePath(imagePath)
                .build();

        QRCode saved = qrCodeRepository.save(qrCode);
        log.info("QR Code generated - id: {}, type: {}", saved.getId(), saved.getType());

        String imageUrl = buildImageUrl(baseUrl, imagePath);
        return qrCodeMapper.toGenerateResponse(saved, imageUrl);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QRCodeResponse> getAllQRCodes(String baseUrl) {
        return qrCodeRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(qr -> qrCodeMapper.toQRCodeResponse(qr, buildImageUrl(baseUrl, qr.getImagePath())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public QRCodeResponse getQRCodeById(Long id, String baseUrl) {
        QRCode qrCode = findById(id);
        return qrCodeMapper.toQRCodeResponse(qrCode, buildImageUrl(baseUrl, qrCode.getImagePath()));
    }

    @Override
    @Transactional(readOnly = true)
    public Path downloadQRCode(Long id) {
        QRCode qrCode = findById(id);
        log.info("Download request for QR Code id: {}", id);
        return qrCodeGeneratorUtil.resolveImagePath(qrCode.getImagePath());
    }

    @Override
    @Transactional
    public void deleteQRCode(Long id) throws IOException {
        QRCode qrCode = findById(id);
        qrCodeGeneratorUtil.deleteImage(qrCode.getImagePath());
        qrCodeRepository.delete(qrCode);
        log.info("QR Code deleted - id: {}", id);
    }

    private QRCode findById(Long id) {
        return qrCodeRepository.findById(id)
                .orElseThrow(() -> new QRCodeNotFoundException(id));
    }

    private String buildImageUrl(String baseUrl, String imagePath) {
        if (imagePath == null) return null;
        String filename = Paths.get(imagePath).getFileName().toString();
        return baseUrl + "/uploads/" + filename;
    }
}
