package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDto {
    public CustomerInfoDto(String id){
        this.id = id;
    }
    private String id;
    private String fullName;

    private String shortName;

    private String adress;
    private String town;
    private String postCode;
    private String nationality;
    private String restriction;
    private String title;
    private String gender;
    private String agency;
    private String residence;
    private String restrictionValue;
    private List<T24ProfileDto> profils;
}
