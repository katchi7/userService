package ma.tna.ebanking.userservice.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    public CustomerDto(Customer customer){
        this(
                customer.getId(),customer.getPassword(),customer.getPhone(),customer.getEmail(),customer.isActive(),
                customer.getDisponibilityStart(),customer.getDisponibilityEnd(),customer.isAllowEmails(),new LanguageDto(customer.getLanguage()),null,
                customer.getFullName(),customer.getShortName(),customer.getAddress(),customer.getTown(),customer.getPostCode(),customer.getNationality()
        );
        if(customer.getDevices()!=null){
            List<DeviceDto> deviceDtos = new ArrayList<>();
            for (Device device : customer.getDevices()) {
                deviceDtos.add(new DeviceDto(device));
            }

            this.devices = deviceDtos;
        }

    }

    @NotNull
    @Min(1)
    private Integer id;

    @NotNull(message = "Password should not be null")
    @Size(min = 5,message = "Password length should be > 5")
    @JsonBackReference
    private String password;
    @NotNull
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$",message = "Rejected phone value")
    private String phone;
    @NotNull
    @Pattern(regexp = "(?!.*\\.\\.)(^[^\\.][^@\\s]+@[^@\\s]+\\.[^@\\s\\.]+$)",message = "Rejected email value")
    private String email;
    private boolean active = true;
    private String disponibilityStart = "08:00";
    private String  disponibilityEnd = "23:00";
    private boolean allowEmails= true;
    private LanguageDto language = new LanguageDto("fr",null);
    private List<DeviceDto> devices;

    private String fullName;

    private String shortName;

    private String address;

    private String town;

    private String postCode;

    private String nationality;
    public Customer asCustomer(){
        ArrayList<Device> devices1 = null;
        if(devices != null){
            devices1 = new ArrayList<>();
            for (DeviceDto device : devices) {
                devices1.add(device.asDevice());
            }
        }
        return new Customer(id,password,phone,email,active,disponibilityStart,disponibilityEnd,allowEmails,language.asLanguage(),devices1,fullName,shortName,address,town,postCode,nationality);
    }
}
