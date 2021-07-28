package ma.tna.ebanking.userservice.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.audit.model.Auditable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Column(name = "DEVICE_REF")
    private String ref;
    @Column(name = "DEVICE_LAST_CONNECTION")
    @JsonIgnore
    private LocalDateTime lastConnection;
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "DEVICE_CUSTOMER_ID")
    @NotAudited
    private Customer customer;

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", fingerprintActivated=" + fingerprintActivated +
                ", manufacturer='" + manufacturer + '\'' +
                ", os='" + os + '\'' +
                ", ref='" + ref + '\'' +
                ", lastConnection=" + lastConnection +
                '}';
    }

    @JsonGetter(value = "lastConnection")
    public  String lastConnectionJsonGetter(){
        return lastConnection!=null?lastConnection.toString():null;
    }
}
