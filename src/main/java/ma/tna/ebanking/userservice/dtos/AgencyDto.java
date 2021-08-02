package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Agency;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyDto {
    public AgencyDto(Agency agency) {
        this(
                agency.getId(),new Position(agency.getLatitude(),
                agency.getLongitude()),agency.getType(),agency.getName(),
                agency.getDescription(),agency.getAddress(),agency.getAgencyCode(),
                agency.getPhone(),agency.getEmail(),new Schedule(agency.getDays(),agency.getHours())
        );
    }
    @NotNull
    private String id;
    @NotNull
    @Valid
    private Position position;
    private String type;
    private String name;
    private String description;
    private String address;
    private String agencyCode;
    private String phone;
    private String email;
    private Schedule schedule;


    public Agency asAgency(){
        return new Agency(id,position.getLatitude(),position.getLongitude(),type,name,description,address,agencyCode,phone,email,schedule==null?null:schedule.getDays(),schedule==null?null:schedule.getHours());
    }
}
