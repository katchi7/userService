package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Benef;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BenefDto {
    public BenefDto(Benef benef){
        this(benef.getId(),benef.getFirstName(),benef.getLastName(),benef.getRib(),benef.getPhone(),benef.getEmail(),benef.getCustomerId());
    }
    private int id;
    @NotNull(message = "firstName must not be null")
    @Size(min = 4)
    private String firstName;
    @NotNull(message = "lastName must not be null")
    @Size(min = 4)
    private String lastName;
    @NotNull(message = "rib must not be null")
    @Size(min = 25,max = 40)
    private String rib;
    @NotNull(message = "phone must not be null")
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$",message = "Rejected phone value")
    private String phone;
    @NotNull(message = "email must not be null")
    @Pattern(regexp = "(?!.*\\.\\.)(^[^\\.][^@\\s]+@[^@\\s]+\\.[^@\\s\\.]+$)",message = "Rejected email value")
    private String email;
    @NotNull(message = "customerId must not be null")
    private Integer customerId;

    public Benef asBenef(){
        return new Benef(id,firstName,lastName,rib,phone,email,customerId);
    }
}
