package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.TransactionService;
import com.jpmc.midascore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final DatabaseConduit databaseConduit;
    private final TransactionService transactionService;
    private final UserService userService;

    public KafkaConsumer(DatabaseConduit databaseConduit, TransactionService transactionService, UserService userService) {
        this.databaseConduit = databaseConduit;
        this.transactionService = transactionService;
        this.userService = userService;
        logger.info("KafkaConsumer constructed.");
    }

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-core-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(Transaction transaction) {
        try {
            transactionService.processTransaction(transaction);
        } catch (Exception e) {
            logger.warn("Transaction failed: {}", e.getMessage());
        }
    }

}