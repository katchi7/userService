package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Benef;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefUpdateDto {
    public BenefUpdateDto(Benef benef){
        this(benef.getId(),benef.getFirstName(),benef.getLastName(),benef.getRib(),benef.getPhone(),benef.getEmail());
    }

    private Integer id;
    @NotNull
    @Size(min = 4)
    private String firstName;
    @NotNull
    @Size(min = 4)
    private String lastName;
    @NotNull
    @Size(min = 25,max = 40)
    private String rib;
    @NotNull
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$",message = "Rejected phone value")
    private String phone;
    @NotNull
    @Pattern(regexp = "(?!.*\\.\\.)(^[^\\.][^@\\s]+@[^@\\s]+\\.[^@\\s\\.]+$)",message = "Rejected email value")
    private String email;
    public Benef asBenef(){
        return new Benef(id,firstName,lastName,rib,phone,email,0,0);
    }
    public void validate(Errors errors){
        firstName = errors.hasFieldErrors("firstName")?"":firstName;
        lastName = errors.hasFieldErrors("lastName") ? "":lastName;
        rib = errors.hasFieldErrors("rib")?"":rib;
        phone = errors.hasFieldErrors("phone")?"":phone;
        email = errors.hasFieldErrors("email")?"":email;
    }
}
