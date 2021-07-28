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
    private int id;
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
    @Column(name = "BENEF_CUSTOMER_ID")
    private int customerId;

}
