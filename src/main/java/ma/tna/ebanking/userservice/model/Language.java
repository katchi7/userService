package ma.tna.ebanking.userservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Language entity : Stores system laguages
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "language")
public class Language {
    @Id
    @Column(name = "LANGUAGE_ISO_639_1")
    private String languageIso;
    @JsonBackReference
    @Column(name = "LANGUAGE_NATIVE_NAME")
    private String languageNativeName;
}
