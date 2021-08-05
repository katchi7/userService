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
    private String type;
    @Transient
    private String name;
    @Transient
    private String description;
    @Transient
    private String address;
    @Transient
    private String agencyCode;
    @Transient
    private String phone;
    @Transient
    private String email;
    @Transient
    private String days;
    @Transient
    private String hours;

}
