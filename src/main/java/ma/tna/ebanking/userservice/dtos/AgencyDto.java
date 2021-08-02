package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Agency;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyDto {
    public AgencyDto(Agency agency) {
        this(
                agency.getId(),agency.getLatitude(),
                agency.getLongitude(),agency.getType(),agency.getName(),
                agency.getDescription(),agency.getAddress(),agency.getAgencyCode(),
                agency.getPhone(),agency.getEmail(),agency.getDays(),agency.getHours()
        );
    }
    @NotNull
    private String id;
    @NotNull
    private String latitude;
    @NotNull
    private String longitude;
    private String type;
    private String name;
    private String description;
    private String address;
    private String agencyCode;
    private String phone;
    private String email;
    private String days;
    private String hours;

    public Agency asAgency(){
        return new Agency(id,latitude,longitude,type,name,description,address,agencyCode,phone,email,days,hours);
    }
}
