package ma.tna.ebanking.userservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Device;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerMinifiedDto {
    public CustomerMinifiedDto(Customer customer){
        this(
                customer.getId(),customer.getPhone(),customer.getEmail(),customer.isActive(),
                customer.getDisponibilityStart(),customer.getDisponibilityEnd(),customer.isAllowEmails(),new LanguageDto(customer.getLanguage())
        );
    }

    private Integer id;
    private String phone;
    private String email;
    private boolean active = true;
    private String disponibilityStart = "08:00";
    private String  disponibilityEnd = "23:00";
    private boolean allowEmails= true;
    private LanguageDto language = new LanguageDto("fr",null);
}
