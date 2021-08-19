package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class T24AgencyResponse {
    private List<AgencyInfo> agence;
    public boolean emptyAgency(){
        if(agence!=null && !agence.isEmpty()){
            for (AgencyInfo agencyInfo : agence) {
                if(!"".equals(agencyInfo.getiD())) return false;
            }
        }
        return true;
    }
}
