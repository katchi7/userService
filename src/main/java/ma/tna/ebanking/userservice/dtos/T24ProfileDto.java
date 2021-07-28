package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Profile;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class T24ProfileDto {
    private String id;
    private String name;
    public Profile asProfile(){
        return new Profile(id,name);
    }
}
