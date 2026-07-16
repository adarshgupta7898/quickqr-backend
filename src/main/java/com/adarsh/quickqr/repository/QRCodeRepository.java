package com.adarsh.quickqr.repository;

import com.adarsh.quickqr.entity.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for QRCode entity persistence operations.
 */
@Repository
public interface QRCodeRepository extends JpaRepository<QRCode, Long> {

    /**
     * Returns all QR Codes ordered by creation date descending (newest first).
     */
    List<QRCode> findAllByOrderByCreatedAtDesc();
}
