package ma.tna.ebanking.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements Serializable {
    String id;
    String name;
    private String adresse;
    private String pays;
    private String ville;
    private String postCode;
    private String sector;
}
