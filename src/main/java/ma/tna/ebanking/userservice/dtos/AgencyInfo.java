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

    public String getiD() {
        return iD;
    }

    @JsonGetter("id")
    public String idGetter(){
        return getiD();
    }
}
