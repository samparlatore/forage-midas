package com.jpmc.midascore.controller;

import com.jpmc.midascore.config.KafkaConsumerConfig;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.component.DatabaseConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final DatabaseConduit databaseConduit;

    public TransactionController(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @PostMapping
    public ResponseEntity<String> processTransaction(@RequestBody Transaction transaction) {
        logger.info("TransactionController Received transaction: " + transaction);
        try {
            databaseConduit.handle(transaction);
            return ResponseEntity.ok("Transaction processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Transaction failed: " + e.getMessage());
        }
    }
}