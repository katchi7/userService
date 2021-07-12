package com.tna.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

//Transaction entity: stores ebanking transactions
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private int id;
    @Column(name = "TRANSACTION_AMOUNT")
    private double amount;
    @Column(name = "TRANSACTION_LIB")
    private String lib;
    @Column(name = "TRANSACTION_DATE")
    private Date date;
    @Column(name = "TRANSACTION_ACCOUNT")
    private String account;
    @Column(name = "TRANSACTION_IS_FAV")
    private boolean isFav;
    @ManyToOne(targetEntity = Benef.class)
    @JoinColumn(name = "TRANSACTION_BENEF_ID")
    private Benef benef;
}
