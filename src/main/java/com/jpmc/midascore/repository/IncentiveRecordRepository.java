package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.IncentiveRecord;
import com.jpmc.midascore.entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncentiveRecordRepository extends JpaRepository<IncentiveRecord, Long> {
    Optional<IncentiveRecord> findByTransaction(TransactionRecord transaction);
}