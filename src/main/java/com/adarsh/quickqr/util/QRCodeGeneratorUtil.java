package com.adarsh.quickqr.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Utility component responsible for QR Code image generation and file management.
 */
@Component
public class QRCodeGeneratorUtil {

    private static final String UPLOADS_DIR = "uploads";
    private static final int QR_SIZE = 350;
    private static final String FORMAT = "PNG";

    /**
     * Generates a QR Code PNG image from the given content and saves it to the uploads directory.
     *
     * @param content the text/data to encode
     * @return the relative file path of the saved image
     * @throws IOException     if the file cannot be written
     * @throws WriterException if the QR Code cannot be generated
     */
    public String generateAndSave(String content) throws IOException, WriterException {
        Path uploadsPath = Paths.get(UPLOADS_DIR);
        if (!Files.exists(uploadsPath)) {
            Files.createDirectories(uploadsPath);
        }

        String filename = "qr_" + System.currentTimeMillis() + ".png";
        Path filePath = uploadsPath.resolve(filename);

        Map<EncodeHintType, Object> hints = Map.of(
                EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H,
                EncodeHintType.CHARACTER_SET, "UTF-8",
                EncodeHintType.MARGIN, 1
        );

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE, hints);
        MatrixToImageWriter.writeToPath(bitMatrix, FORMAT, filePath);

        return filePath.toString();
    }

    /**
     * Deletes the QR Code image file at the given path.
     *
     * @param imagePath the file path to delete
     * @throws IOException if deletion fails
     */
    public void deleteImage(String imagePath) throws IOException {
        if (imagePath != null) {
            Path path = Paths.get(imagePath);
            Files.deleteIfExists(path);
        }
    }

    /**
     * Resolves the absolute Path for a stored image path string.
     *
     * @param imagePath the stored image path
     * @return resolved Path
     */
    public Path resolveImagePath(String imagePath) {
        return Paths.get(imagePath).toAbsolutePath();
    }
}
