package ma.tna.ebanking.userservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

// Customer entity : Stores all costumer data
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private int id;
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
    @Column(name = "CUSTOMER_IMAGE")
    private String image;
    @Column(name = "CUSTOMER_ALLOW_EMAILS")
    private boolean allowEmails;
    @ManyToOne(targetEntity = Language.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_LANGUAGE_ISO_639_1")
    private Language language;
    @OneToMany(targetEntity = Device.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "customer",orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private List<Device> devices;
}
