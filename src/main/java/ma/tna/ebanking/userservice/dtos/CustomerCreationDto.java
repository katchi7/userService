package ma.tna.ebanking.userservice.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Customer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CustomerCreationDto extends CustomerDto{
    @NotNull(message = "Password should not be null")
    @Size(min = 5,message = "Password length should be > 5")
    private String password;

    @Override
    public Customer asCustomer() {
        Customer customer = super.asCustomer();
        customer.setPassword(password);
        return customer;
    }
}
