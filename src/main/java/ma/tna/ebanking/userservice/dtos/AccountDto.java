package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    public AccountDto(Account account){
        this(
                account.getCompte(),account.getCategory(),account.getOpenDate(),
                account.getTitre(),account.getRib(),account.getCanDebit(),
                account.getCanCredit(),account.getAvailableAmount(),account.getDevise());
    }
    private String compte;
    private String category;
    private String openDate;
    private String titre;
    private String rib;
    private String canDebit;
    private String canCredit;
    private String availableAmount;
    private String devise;
    public Account asAccount(){
         return new Account(compte,category,openDate,titre,rib,canDebit,canCredit,availableAmount,devise);
    }
}
