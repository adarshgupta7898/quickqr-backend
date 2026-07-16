package com.adarsh.quickqr.mapper;

import com.adarsh.quickqr.dto.GenerateQRResponse;
import com.adarsh.quickqr.dto.QRCodeResponse;
import com.adarsh.quickqr.entity.QRCode;
import org.springframework.stereotype.Component;

/**
 * Maps QRCode entities to response DTOs.
 */
@Component
public class QRCodeMapper {

    /**
     * Maps a QRCode entity to a GenerateQRResponse DTO.
     *
     * @param qrCode   the entity
     * @param imageUrl the publicly accessible image URL
     * @return populated GenerateQRResponse
     */
    public GenerateQRResponse toGenerateResponse(QRCode qrCode, String imageUrl) {
        return GenerateQRResponse.builder()
                .id(qrCode.getId())
                .title(qrCode.getTitle())
                .type(qrCode.getType())
                .content(qrCode.getContent())
                .imageUrl(imageUrl)
                .createdAt(qrCode.getCreatedAt())
                .build();
    }

    /**
     * Maps a QRCode entity to a QRCodeResponse DTO.
     *
     * @param qrCode   the entity
     * @param imageUrl the publicly accessible image URL
     * @return populated QRCodeResponse
     */
    public QRCodeResponse toQRCodeResponse(QRCode qrCode, String imageUrl) {
        return QRCodeResponse.builder()
                .id(qrCode.getId())
                .title(qrCode.getTitle())
                .type(qrCode.getType())
                .content(qrCode.getContent())
                .imageUrl(imageUrl)
                .createdAt(qrCode.getCreatedAt())
                .updatedAt(qrCode.getUpdatedAt())
                .build();
    }
}
