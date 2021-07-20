package ma.tna.ebanking.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

//Device entity : Stores user's devices
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@Table(name = "device")
public class Device extends Auditable<Device> {

    @Id
    @Column(name = "DEVICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "device_key")
    private String key;
    @Column(name = "DEVICE_NAME")
    private String name;
    @Column(name = "device_model")
    private String model;
    @Column(name = "DEVICE_FINGERPRINT_ACTIVATED")
    private boolean fingerprintActivated;
    @Column(name = "device_manufacturer")
    private String manufacturer;
    @Column(name = "device_os")
    private String os;
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "DEVICE_CUSTOMER_ID")
    @NotAudited
    private Customer customer;
}
