package ma.tna.ebanking.userservice.dtos;

import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.tna.ebanking.userservice.model.Profile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    public CustomerDto(Customer customer){
        this(
                customer.getId(),customer.getPhone(),customer.getEmail(),customer.isActive(),
                customer.getDisponibilityStart(),customer.getDisponibilityEnd(),customer.isAllowEmails(),new LanguageDto(customer.getLanguage()),null,
                customer.getFullName(),customer.getShortName(),customer.getAddress(),customer.getTown(),customer.getPostCode(),customer.getNationality(),
                customer.getRestriction(),customer.getTitle(),customer.getGender(),customer.getAgency(),
                customer.getResidence(),customer.getRestrictionValue(),
                customer.getMnemonic(),customer.getAdress2(),customer.getPrimaryProfil(),customer.getProfiles()
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
    private String id;

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
    private String restriction;
    private String title;
    private String gender;
    private String agency;
    private String residence;
    private String restrictionValue;
    private String mnemonic;
    private String adress2;
    private String primaryProfil;
    private List<Profile> profiles;
    public Customer asCustomer(){
        List<Device> devices1 = (devices!=null)?(devices.stream().map(DeviceDto::asDevice).collect(Collectors.toList())):null;
        return new Customer(id,null,phone,email,active,disponibilityStart,disponibilityEnd,allowEmails,language.asLanguage(),devices1,mnemonic,fullName,shortName,address,adress2,town,postCode,nationality,restriction,title
        ,gender,agency,residence,restrictionValue,primaryProfil,profiles);
    }
}
