package ma.tna.ebanking.userservice.dtos;


import ma.tna.ebanking.userservice.model.Device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDto {
    public DeviceDto(Device device){
        this(device.getId(),device.getKey(),device.getName(),device.getModel(),device.isFingerprintActivated(),device.getManufacturer(),device.getOs());
    }
    private int id;
    @NotNull
    private String key;
    @NotNull(message = "name should not be null")
    @Size(min = 4,message = "name size must be bigger than 4")
    private String name;
    private String model;
    private Boolean fingerprintActivated = false;
    private String manufacturer;
    private String os;
    public Device asDevice(){
        return new Device(id,key,name,model,fingerprintActivated,manufacturer,os,null);
    }
}
