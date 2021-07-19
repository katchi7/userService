package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDto {
    public CustomerInfoDto(Integer id){
        this.id = id;
    }
    private Integer id;
    private String fullName;

    private String shortName;

    private String adress;

    private String town;

    private String postCode;
    private String nationality;
}
