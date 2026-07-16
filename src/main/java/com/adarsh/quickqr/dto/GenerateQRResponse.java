package com.adarsh.quickqr.dto;

import com.adarsh.quickqr.enums.QRCodeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO returned after generating a QR Code.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response payload after QR Code generation")
public class GenerateQRResponse {

    @Schema(description = "Generated QR Code ID")
    private Long id;

    @Schema(description = "Title of the QR Code")
    private String title;

    @Schema(description = "Type of QR Code")
    private QRCodeType type;

    @Schema(description = "Encoded content")
    private String content;

    @Schema(description = "URL to access the QR Code image")
    private String imageUrl;

    @Schema(description = "Timestamp when the QR Code was created")
    private LocalDateTime createdAt;
}
