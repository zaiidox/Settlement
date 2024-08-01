package com.hps.Transfer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Entity
@Data
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transferId;  // Identifiant du transfert (par exemple, TR1)
    private Integer amount;     // Montant du transfert
    private String settlementAccount;  // Compte de règlement (par exemple, CA1)
    private String merchant;    // Commerçant (par exemple, M1)
    private String description;

}
