package ma.tna.ebanking.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.audit.model.Auditable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
@Data

@AllArgsConstructor
@NoArgsConstructor
@Audited
@Entity
@Table(name = "AGENCE")
public class Agency extends Auditable<Agency> {
    @Id
    @Column(name = "AGENCE_ID")
    private String id;
    @Column(name = "AGENCE_LATITUDE")
    private String latitude;
    @Column(name = "AGENCE_LONGITUDE")
    private String longitude;
    @Transient
    private String name;
    @Transient
    private String ville;
    @Transient
    private String address;
    @Transient
    private String codeVille;
    @Transient
    private String phone;
    @Column(name = "AGENCY_DAYS")
    private String days;
    @Column(name = "AGENCY_HOURS")
    private String hours;

}
