package ma.tna.ebanking.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.audit.model.Auditable;
import org.hibernate.envers.Audited;

import javax.persistence.*;

//Benef entity: stores costumer beneficiary data
@Data
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Entity
@Table(name = "benef")
public class Benef extends Auditable<Benef> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BENEF_ID")
    private Long id;
    @Column(name = "BENEF_FIRST_NAME")
    private String firstName;
    @Column(name = "BENEF_LAST_NAME")
    private String lastName;
    @Column(name = "BENEF_RIB")
    private String rib;
    @Column(name = "BENEF_PHONE")
    private String phone;
    @Column(name = "BENEF_EMAIL")
    private String email;
    @Column(name = "BENEF_PROFILE_ID")
    private String profileId;
    @Column(name = "BENEF_CUSTOMER_ID")
    private String customerId;

    private String category; // catégorie du comptre si il est interne
    private String currency; // devise du comptre si il est interne
    private Boolean internal; // true si compte inetrene, false si rib confrére valide

}
