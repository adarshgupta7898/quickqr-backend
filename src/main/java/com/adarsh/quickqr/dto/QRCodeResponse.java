package com.adarsh.quickqr.dto;

import com.adarsh.quickqr.enums.QRCodeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * General-purpose response DTO for QR Code records.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "QR Code record response")
public class QRCodeResponse {

    @Schema(description = "QR Code ID")
    private Long id;

    @Schema(description = "Title")
    private String title;

    @Schema(description = "QR Code type")
    private QRCodeType type;

    @Schema(description = "Encoded content")
    private String content;

    @Schema(description = "URL to access the QR Code image")
    private String imageUrl;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;
}
