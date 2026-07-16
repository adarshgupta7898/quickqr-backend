package com.adarsh.quickqr.dto;

import com.adarsh.quickqr.enums.QRCodeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for generating a new QR Code.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for generating a QR Code")
public class GenerateQRRequest {

    @Size(max = 100, message = "Title must not exceed 100 characters")
    @Schema(description = "Optional title for the QR Code", example = "My Website")
    private String title;

    @NotNull(message = "QR Code type is required")
    @Schema(description = "Type of QR Code", example = "URL")
    private QRCodeType type;

    @NotBlank(message = "Content cannot be blank")
    @Schema(description = "Content to encode in the QR Code", example = "https://example.com")
    private String content;
}
