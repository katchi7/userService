package ma.tna.ebanking.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String compte;
    private String category;
    private String openDate;
    private String titre;
    private String rib;
    private String canDebit;
    private String canCredit;
    private String availableAmount;
    private String devise;
}