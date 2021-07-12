package com.tna.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

//Transaction treatment entity: stores information about transaction treatment
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_treatment")
@IdClass(TreatmentCompositeId.class)
public class TransactionTreatment {
    @Id
    @Column(name = "TRANSACTION_TREATMENT_TRANSACTION_ID")
    private int transactionId;
    @Id
    @Column(name = "TRANSACTION_TREATMENT_CUSTOMER_ID")
    private int customerId;
    @Column(name = "TRANSACTION_TREATMENT_STATUS")
    private String status;
    @Column(name = "TRANSACTION_TREATMENT_DATE")
    private Date date;
}
