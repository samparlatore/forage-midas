package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.IncentiveRecord;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.IncentiveRecordRepository;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final UserRecordRepository userRecordRepository;
    private final TransactionRecordRepository transactionRecordRepository;
    private final IncentiveRecordRepository incentiveRecordRepository;
    private final IncentiveService incentiveService;

    public TransactionService(UserRecordRepository userRecordRepository, TransactionRecordRepository transactionRecordRepository, IncentiveRecordRepository incentiveRecordRepository, IncentiveService incentiveService) {
        this.userRecordRepository = userRecordRepository;
        this.transactionRecordRepository = transactionRecordRepository;
        this.incentiveRecordRepository = incentiveRecordRepository;
        this.incentiveService = incentiveService;
    }

    @Transactional
    public void processTransaction(Transaction transaction) {
        UserRecord sender = userRecordRepository.findById(transaction.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
        UserRecord recipient = userRecordRepository.findById(transaction.getRecipientId()).orElseThrow(() -> new RuntimeException("Recipient not found"));

        if (sender.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // Step 1: Create and persist transaction without incentive
        TransactionRecord record = new TransactionRecord(transaction.getAmount(), sender, recipient, LocalDateTime.now(), null);
        transactionRecordRepository.save(record);

        // Step 2: Request the Incentive and create an incentiveRecord and link it
        IncentiveRecord incentive = incentiveService.applyIncentive(record);
        record.setIncentive(incentive);
        incentiveRecordRepository.save(incentive);

        // Step 3: Update transaction to link incentive
        record.setIncentive(incentive);
        transactionRecordRepository.save(record);

        //Step 4: Update the sender and recipient records
        sender.setBalance(sender.getBalance().subtract(transaction.getAmount()));
        userRecordRepository.save(sender);
        recipient.setBalance(recipient.getBalance().add(transaction.getAmount()).add(incentive.getAmount()) ); //add the transaction and the incentive.
        userRecordRepository.save(recipient);

    }
}