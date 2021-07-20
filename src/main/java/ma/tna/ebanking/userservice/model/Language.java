package ma.tna.ebanking.userservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import java.io.Serializable;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;


//Language entity : Stores system laguages
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "language")
@Audited(targetAuditMode = NOT_AUDITED)
public class Language implements Serializable {
    @Id
    @Column(name = "LANGUAGE_ISO_639_1")
    private String languageIso;
    @JsonBackReference
    @Column(name = "LANGUAGE_NATIVE_NAME")
    private String languageNativeName;
}
