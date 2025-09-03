package com.jpmc.midascore.component;

import com.jpmc.midascore.config.KafkaConsumerConfig;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.time.LocalDateTime;

@Component
public class DatabaseConduit {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConduit.class);
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public DatabaseConduit(UserRepository userRepository,TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
        logger.info( "DatabaseConduit constructed.");
    }

    @Transactional
    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
        logger.info( "UserRecord saved. (" +  userRecord + ").");
    }

    @Transactional
    public void handle(Transaction transaction) {
        logger.info( "DatabaseConduit.handle(Transaction) --> " + transaction);
    }

}
