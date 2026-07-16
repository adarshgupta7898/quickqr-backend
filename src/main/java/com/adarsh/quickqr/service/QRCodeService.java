package com.adarsh.quickqr.service;

import com.adarsh.quickqr.dto.GenerateQRRequest;
import com.adarsh.quickqr.dto.GenerateQRResponse;
import com.adarsh.quickqr.dto.QRCodeResponse;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Service interface defining all QR Code business operations.
 */
public interface QRCodeService {

    /**
     * Generates a new QR Code, persists the record, and saves the image.
     *
     * @param request the generation request
     * @param baseUrl the base URL of the server (used to build the image URL)
     * @return the generated QR Code response
     */
    GenerateQRResponse generateQRCode(GenerateQRRequest request, String baseUrl) throws IOException;

    /**
     * Returns all QR Codes sorted by newest first.
     *
     * @param baseUrl the base URL of the server
     * @return list of QR Code responses
     */
    List<QRCodeResponse> getAllQRCodes(String baseUrl);

    /**
     * Returns a single QR Code by its ID.
     *
     * @param id      the QR Code ID
     * @param baseUrl the base URL of the server
     * @return the QR Code response
     */
    QRCodeResponse getQRCodeById(Long id, String baseUrl);

    /**
     * Resolves the file system path of the QR Code image for download.
     *
     * @param id the QR Code ID
     * @return the absolute Path to the image file
     */
    Path downloadQRCode(Long id);

    /**
     * Deletes the QR Code record and its associated image file.
     *
     * @param id the QR Code ID
     */
    void deleteQRCode(Long id) throws IOException;
}
