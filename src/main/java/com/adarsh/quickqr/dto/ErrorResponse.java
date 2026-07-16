package com.adarsh.quickqr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Structured error response DTO returned by the global exception handler.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Structured error response")
public class ErrorResponse {

    @Schema(description = "Timestamp of the error")
    private LocalDateTime time;

    @Schema(description = "HTTP status code")
    private int status;

    @Schema(description = "Short error description")
    private String error;

    @Schema(description = "Detailed error message")
    private String message;

    @Schema(description = "Request path that caused the error")
    private String path;
}
