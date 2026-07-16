package com.adarsh.quickqr.controller;

import com.adarsh.quickqr.dto.GenerateQRRequest;
import com.adarsh.quickqr.dto.GenerateQRResponse;
import com.adarsh.quickqr.dto.QRCodeResponse;
import com.adarsh.quickqr.service.QRCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * REST controller exposing all QR Code endpoints.
 */
@Slf4j
@RestController
@RequestMapping("/api/qr")
@Tag(name = "QR Code API", description = "Endpoints for generating, retrieving, downloading, and deleting QR Codes")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @Operation(summary = "Generate a new QR Code")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "QR Code generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    @PostMapping("/generate")
    public ResponseEntity<GenerateQRResponse> generate(@Valid @RequestBody GenerateQRRequest request,
                                                       HttpServletRequest httpRequest) throws IOException {
        String baseUrl = getBaseUrl(httpRequest);
        GenerateQRResponse response = qrCodeService.generateQRCode(request, baseUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all QR Codes sorted by newest first")
    @ApiResponse(responseCode = "200", description = "List of QR Codes returned")
    @GetMapping
    public ResponseEntity<List<QRCodeResponse>> getAll(HttpServletRequest httpRequest) {
        String baseUrl = getBaseUrl(httpRequest);
        return ResponseEntity.ok(qrCodeService.getAllQRCodes(baseUrl));
    }

    @Operation(summary = "Get a single QR Code by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QR Code found"),
            @ApiResponse(responseCode = "404", description = "QR Code not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<QRCodeResponse> getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        String baseUrl = getBaseUrl(httpRequest);
        return ResponseEntity.ok(qrCodeService.getQRCodeById(id, baseUrl));
    }

    @Operation(summary = "Download QR Code image as PNG")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "File downloaded"),
            @ApiResponse(responseCode = "404", description = "QR Code not found")
    })
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        Path imagePath = qrCodeService.downloadQRCode(id);
        Resource resource = new PathResource(imagePath);
        String filename = imagePath.getFileName().toString();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @Operation(summary = "Delete a QR Code by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "QR Code deleted"),
            @ApiResponse(responseCode = "404", description = "QR Code not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) throws IOException {
        qrCodeService.deleteQRCode(id);
        return ResponseEntity.ok(Map.of("message", "QR Code deleted successfully"));
    }

    private String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
