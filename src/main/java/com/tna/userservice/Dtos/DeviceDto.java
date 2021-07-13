package com.tna.userservice.Dtos;


import com.tna.userservice.model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    public DeviceDto(Device device){
        this(device.getId(),device.getName(),device.isFingerprintActivated());
    }
    private int id;
    @NotNull(message = "name should not be null")
    @Size(min = 4,message = "name size must be bigger than 4")
    private String name;
    @NotNull(message = "fingerprintActivated should not be null")
    private Boolean fingerprintActivated;
    public Device asDevice(){
        return new Device(id,name,fingerprintActivated,null);
    }
}
