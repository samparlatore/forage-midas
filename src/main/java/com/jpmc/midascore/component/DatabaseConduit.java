package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseConduit {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConduit.class);
    private final UserRecordRepository userRecordRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public DatabaseConduit(UserRecordRepository userRecordRepository, TransactionRecordRepository transactionRecordRepository) {
        this.userRecordRepository = userRecordRepository;
        this.transactionRecordRepository = transactionRecordRepository;
        logger.info( "DatabaseConduit constructed.");
    }

    @Transactional
    public void save(UserRecord userRecord) {
        userRecordRepository.save(userRecord);
        logger.info( "UserRecord saved. (" +  userRecord + ").");
    }

    @Transactional
    public void handle(Transaction transaction) {
        logger.info( "DatabaseConduit.handle(Transaction) --> " + transaction);
    }

}
