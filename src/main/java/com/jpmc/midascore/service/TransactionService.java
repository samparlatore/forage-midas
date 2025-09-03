package com.jpmc.midascore.service;

import com.jpmc.midascore.component.KafkaConsumer;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public TransactionService(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Transactional
    public void processTransaction(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
        UserRecord recipient = userRepository.findById(transaction.getRecipientId()).orElseThrow(() -> new RuntimeException("Recipient not found"));

        if (sender.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        sender.setBalance(sender.getBalance().subtract(transaction.getAmount()));
        recipient.setBalance(recipient.getBalance().add(transaction.getAmount()));

        userRepository.save(sender);
        userRepository.save(recipient);

        TransactionRecord record = new TransactionRecord( transaction.getAmount(), sender, recipient, LocalDateTime.now() );
        transactionRecordRepository.save(record);

    }
}