package com.adarsh.quickqr.exception;

/**
 * Thrown when a QR Code record cannot be found by the given identifier.
 */
public class QRCodeNotFoundException extends RuntimeException {

    public QRCodeNotFoundException(Long id) {
        super("QR Code not found with id: " + id);
    }
}
