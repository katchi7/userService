package ma.tna.ebanking.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.audit.model.Auditable;
import ma.tna.ebanking.userservice.dtos.ConseilleInfo;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.List;
// Customer entity : Stores all costumer data
@Data
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Entity
@Table(name = "customer")
public class Customer extends Auditable<Customer> {
    @Id
    @Column(name = "CUSTOMER_ID")
    private String id;
    @Column(name = "CUSTOMER_PASSWORD")
    private String password;
    @Column(name = "CUSTOMER_PHONE")
    private String phone;
    @Column(name = "CUSTOMER_EMAIL")
    private String email;
    @Column(name = "CUSTOMER_ACTIVE")
    private boolean active;
    @Column(name = "CUSTOMER_DISPONIBILITY_START")
    private String disponibilityStart;
    @Column(name = "CUSTOMER_DISPONIBILITY_END")
    private String  disponibilityEnd;
    @Column(name = "CUSTOMER_ALLOW_EMAILS")
    private boolean allowEmails;
    @ManyToOne(targetEntity = Language.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_LANGUAGE_ISO_639_1")
    @NotAudited
    private Language language;
    @OneToMany(targetEntity = Device.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "customer",orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotAudited
    private List<Device> devices;
    @Transient
    private String mnemonic;
    @Transient
    private String fullName;
    @Transient
    private String shortName;
    @Transient
    private String address;
    @Transient
    private String adress2;
    @Transient
    private String town;
    @Transient
    private String postCode;
    @Transient
    private String nationality;
    @Transient
    private String restriction;
    @Transient
    private String title;
    @Transient
    private String gender;
    @Transient
    private String agency;
    @Transient
    private String agencyName;
    @Transient
    private String residence;
    @Transient
    private String restrictionValue;
    @Transient
    private String primaryProfil;
    @Transient
    private List<Profile> profiles;
    @Transient
    private List<Account> primaryProfileAccounts;
    @Transient
    private ConseilleInfo conseille;

}
