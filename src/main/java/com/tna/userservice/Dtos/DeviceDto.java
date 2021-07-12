package com.tna.userservice.Dtos;


import com.tna.userservice.model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    public DeviceDto(Device device){
        this(device.getId(),device.getName(),device.isFingerprintActivated());
    }
    private int id;
    private String name;
    private boolean fingerprintActivated;
    public Device asDevice(){
        return new Device(id,name,fingerprintActivated,null);
    }
}
