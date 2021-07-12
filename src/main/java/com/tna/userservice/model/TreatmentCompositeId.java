package com.tna.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentCompositeId implements Serializable {
    private int transactionId;
    private int customerId;
}
