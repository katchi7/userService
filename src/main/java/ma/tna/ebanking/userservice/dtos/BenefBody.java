package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.tools.Constantes;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BenefBody {
    private String account;
    private String codeRetour;
    private String msgRetour;
    private Boolean interne;
    private String currency;
    private String category;
    public boolean valid(){
        return Constantes.getT24_SUCCESS_CODE().equals(codeRetour);
    }
}
