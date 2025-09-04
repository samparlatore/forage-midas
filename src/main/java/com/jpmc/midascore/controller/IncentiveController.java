package com.jpmc.midascore.controller;

import com.jpmc.midascore.dto.IncentiveRequest;
import com.jpmc.midascore.entity.IncentiveRecord;
import com.jpmc.midascore.service.IncentiveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incentive")
public class IncentiveController {

    private final IncentiveService incentiveService;

    public IncentiveController(IncentiveService incentiveService) {
        this.incentiveService = incentiveService;
    }

    @PostMapping
    public ResponseEntity<IncentiveRecord> createIncentive(@RequestBody IncentiveRequest request) {
        IncentiveRecord record = incentiveService.createIncentive(request);
        return ResponseEntity.ok(record);
    }
}