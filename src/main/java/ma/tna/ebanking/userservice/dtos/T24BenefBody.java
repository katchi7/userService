package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

@Data
 @AllArgsConstructor
@NoArgsConstructor
public class T24BenefBody {
    private List<BenefBody> retourList;
    public boolean notAnEmptyBody(){
        return retourList!=null && !retourList.isEmpty();
    }
    public boolean validResponse(){
        return retourList.stream().map(BenefBody::valid).allMatch(Predicate.isEqual(true));
    }
}
