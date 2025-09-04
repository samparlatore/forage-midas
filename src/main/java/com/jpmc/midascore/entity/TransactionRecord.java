package com.jpmc.midascore.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;
    private BigDecimal amount;
    @ManyToOne
    private UserRecord sender;
    @ManyToOne
    private UserRecord recipient;
    private LocalDateTime timestamp;
    @OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY)
    private IncentiveRecord incentive;

    public TransactionRecord(BigDecimal amount, UserRecord sender, UserRecord recipient, LocalDateTime timestamp, IncentiveRecord incentive) {
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.incentive = incentive;
    }

    protected TransactionRecord() {} // JPA needs a no-arg constructor

    public Long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Long id) {
        this.transaction_id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UserRecord getSender() {
        return sender;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public IncentiveRecord getIncentive() { return incentive; }

    public void setIncentive(IncentiveRecord incentive) { this.incentive = incentive; }

}
