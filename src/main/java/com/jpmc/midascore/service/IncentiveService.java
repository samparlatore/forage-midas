package com.jpmc.midascore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.midascore.dto.IncentiveRequest;
import com.jpmc.midascore.entity.IncentiveRecord;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.IncentiveRecordRepository;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class IncentiveService {
    private static final Logger logger = LoggerFactory.getLogger(IncentiveService.class);
    private final RestTemplate restTemplate;
    private final String host;
    private final int port;
    private final String path;



    @Autowired
    private IncentiveRecordRepository incentiveRecordRepository;

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    public IncentiveService(RestTemplateBuilder builder, @Value("${incentive.service.host}") String host, @Value("${incentive.service.port}") int port, @Value("${incentive.service.path}") String path ) {
        this.restTemplate = builder.build();
        this.host = host;
        this.port = port;
        this.path = path;
    }

    public String buildUrl() {
        return "http://" + host + ":" + port + path;
    }

//    public Incentive fetchIncentive(Transaction transaction) {
//        try {
//            ResponseEntity<Incentive> response = restTemplate.postForEntity( incentiveApiUrl, transaction,  Incentive.class );
//            return response.getBody();
//        } catch (RestClientException e) {
//            System.err.println("Failed to fetch incentive: " + e.getMessage());
//            return new Incentive(0);
//        }
//    }

    public IncentiveRecord createIncentive(IncentiveRequest request) {
        TransactionRecord txn = transactionRecordRepository.findById(request.getTransaction_id()).orElseThrow(() -> new RuntimeException("Transaction not found"));

        IncentiveRecord incentive = new IncentiveRecord();
        incentive.setTransactionRecord(txn);
        incentive.setAmount(request.getAmount());

        return incentiveRecordRepository.save(incentive);
    }




    public IncentiveRecord applyIncentive(TransactionRecord record) {
        IncentiveRequest request = new IncentiveRequest();
        request.setTransaction_id(record.getTransaction_id());
        request.setAmount(record.getAmount());

        ResponseEntity<String> response = restTemplate.postForEntity(buildUrl(), request, String.class);

        JsonNode json;
        try {
            json = new ObjectMapper().readTree(response.getBody());
//            logger.info(json.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON response", e);
        }
        BigDecimal incentiveAmount = Optional.ofNullable(json.get("amount")).map(JsonNode::decimalValue).orElseThrow(() -> new IllegalArgumentException("Missing 'amount' field in response"));

        if (incentiveAmount == null) {
            throw new IllegalStateException("Incentive service returned null");
        }

        IncentiveRecord incentive = new IncentiveRecord();
        incentive.setTransactionRecord(record);
        incentive.setAmount(incentiveAmount);

        return incentiveRecordRepository.save(incentive);
    }

}