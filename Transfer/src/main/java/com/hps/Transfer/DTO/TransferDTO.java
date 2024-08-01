package com.hps.Transfer.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDTO {
    private Long id;
    private BigDecimal amount;
    private String description;
}
