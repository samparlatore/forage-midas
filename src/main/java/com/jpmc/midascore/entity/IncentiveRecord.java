package com.jpmc.midascore.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class IncentiveRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incentive_id;
    private BigDecimal amount;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", nullable = false)
    private TransactionRecord transaction;
    private LocalDateTime createdAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public IncentiveRecord() {}
    public IncentiveRecord(BigDecimal amount, TransactionRecord transaction) {
        this.amount = amount;
        this.transaction = transaction;
    }

    public Long getIncentive_id() { return incentive_id;  }
    public void setIncentive_id(Long id) { this.incentive_id = id; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public TransactionRecord getTransactionRecord() { return transaction; }
    public void setTransactionRecord(TransactionRecord transaction) { this.transaction = transaction; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}