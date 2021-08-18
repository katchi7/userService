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
                agency.getLongitude()),agency.getName(),
                agency.getVille(),agency.getAddress(),agency.getDescription(),
                agency.getPhone(),new Schedule(agency.getDays(),agency.getHours())
        );
    }
    @NotNull
    private String id;
    @NotNull
    @Valid
    private Position position;
    private String name;
    private String ville;
    private String address;
    private String description;
    private String phone;
    private Schedule schedule;


    public Agency asAgency(){
        return new Agency(id,position.getLatitude(),position.getLongitude(),name,ville,address,description,phone,schedule==null?null:schedule.getDays(),schedule==null?null:schedule.getHours());
    }
}
