package ma.tna.ebanking.userservice.dtos;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyInfo {
    private String iD;
    private String nomAgence;
    private String ville;
    private String adresse;
    private String codeLocalite;
    private String nTelephone;

    public String getiD() {
        return iD;
    }

    public String getnTelephone() {
        return nTelephone;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public void setnTelephone(String nTelephone) {
        this.nTelephone = nTelephone;
    }

    @JsonGetter("id")
    public String idGetter(){
        return getiD();
    }
}
