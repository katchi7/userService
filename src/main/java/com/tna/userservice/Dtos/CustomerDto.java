package com.tna.userservice.Dtos;

import com.tna.userservice.model.Customer;
import com.tna.userservice.model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    public CustomerDto(Customer customer){
        this(customer.getId(),customer.getPhone(),customer.getEmail(),customer.isActive(),customer.getDisponibilityStart(),customer.getDisponibilityEnd(),customer.getImage(),customer.isAllowEmails(),new LanguageDto(customer.getLanguage()),null);
        List<DeviceDto> deviceDtos = new ArrayList<>();
        for (Device device : customer.getDevices()) {
            deviceDtos.add(new DeviceDto(device));
        }
        this.devices = deviceDtos;
    }
    private int id;
    private String phone;
    private String email;
    private boolean active;
    private String disponibilityStart;
    private String  disponibilityEnd;
    private String image;
    private boolean allowEmails;
    private LanguageDto language;
    private List<DeviceDto> devices;
}
