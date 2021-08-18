package ma.tna.ebanking.userservice.dtos;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CustomerInfoDto extends  CustomerDto {
    private List<T24ProfileDto> profils;
    public CustomerInfoDto(String id){
        super.setId(id);
    }
    @JsonGetter("adress")
    public String getAdress(){
        return super.getAddress();
    }


    @JsonSetter("adress")
    public void setAdress(String address) {
        super.setAddress(address);
    }

    @JsonSetter("coneille")
    public void setConeille(ConseilleInfo conseille) {
        super.setConseille(conseille);
    }

    @JsonGetter("coneille")
    public ConseilleInfo getConeille() {
        return super.getConseille();
    }
}