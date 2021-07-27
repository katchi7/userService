package ma.tna.ebanking.userservice.dtos;

import lombok.Data;

@Data
public class DeviceLoginDto extends LoginDto {

    public DeviceLoginDto(){super();}
    private DeviceDto device;

    @Override
    public String toString() {
        return "DeviceLoginDto{" +
                "deviceDto=" + device +
                "} " + super.toString();
    }
}
