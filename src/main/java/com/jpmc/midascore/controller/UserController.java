package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRecordRepository userRepository;

    public UserController(UserRecordRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRecord> getUser(@PathVariable long id) {
        Optional<UserRecord> user = userRepository.findById(id);
        logger.info( "UserController.getUser(" + id + ")");
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserRecord> createUser(@RequestBody UserRecord userRecord) {
        UserRecord saved = userRepository.save(userRecord);
        logger.info( "UserController.createUser(" + userRecord + ")");
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}/balance")
    public ResponseEntity<UserRecord> updateBalance(@PathVariable long id, @RequestBody BigDecimal newBalance) {
        Optional<UserRecord> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) return ResponseEntity.notFound().build();
        BigDecimal oldBalance = userOpt.get().getBalance();
        UserRecord user = userOpt.get();
        user.setBalance(newBalance);
        userRepository.save(user);
        logger.info( "UserController.updateBalance(" + id + " -> " + oldBalance + ", " + newBalance + ")");
        return ResponseEntity.ok(user);
    }
}
