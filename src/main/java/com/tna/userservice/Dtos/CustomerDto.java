package com.tna.userservice.Dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tna.userservice.model.Customer;
import com.tna.userservice.model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

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
        this(customer.getId(),customer.getPassword(),customer.getPhone(),customer.getEmail(),customer.isActive(),customer.getDisponibilityStart(),customer.getDisponibilityEnd(),customer.getImage(),customer.isAllowEmails(),new LanguageDto(customer.getLanguage()),null);
        if(customer.getDevices()!=null){
            List<DeviceDto> deviceDtos = new ArrayList<>();
            for (Device device : customer.getDevices()) {
                deviceDtos.add(new DeviceDto(device));
            }

            this.devices = deviceDtos;
        }

    }
    private int id;

    @NotNull(message = "Password should not be null")
    @Size(min = 5,message = "Password length should be > 5")
    @JsonBackReference
    private String password;
    @NotNull
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$",message = "Rejected phone value")
    private String phone;
    @NotNull
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",message = "Rejected email value")
    private String email;
    private boolean active = true;
    private String disponibilityStart = "08:00";
    private String  disponibilityEnd = "23:00";
    private String image;
    private boolean allowEmails= true;
    private LanguageDto language;
    private List<DeviceDto> devices;
    public Customer asCustomer(){
        ArrayList<Device> devices_ = null;
        if(devices != null){
            devices_ = new ArrayList<>();
            for (DeviceDto device : devices) {
                devices_.add(device.asDevice());
            }
        }
        return new Customer(id,password,phone,email,active,disponibilityStart,disponibilityEnd,image,allowEmails,language.asLanguage(),devices_);
    }
}
