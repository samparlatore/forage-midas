package com.jpmc.midascore.controller;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRecordRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final UserRecordRepository userRecordRepository;

    public BalanceController(UserRecordRepository userRecordRepository) {
        this.userRecordRepository = userRecordRepository;
    }

    @GetMapping
    public Balance getBalance(@RequestParam Long userId) {
        return userRecordRepository.findById(userId).map(user -> new Balance(user.getBalance())).orElse(new Balance(BigDecimal.ZERO));
    }
}
