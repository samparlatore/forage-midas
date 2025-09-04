package com.jpmc.midascore.dto;

import java.math.BigDecimal;

public class IncentiveRequest {
    private Long transaction_id;
    private BigDecimal amount;

    public Long getTransaction_id() { return transaction_id; }

    public void setTransaction_id(Long transaction_id) { this.transaction_id = transaction_id; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }


}
