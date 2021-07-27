package ma.tna.ebanking.userservice.dtos;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class DeviceLoginDto extends LoginDto {

    public DeviceLoginDto(){super();}
    @NotNull
    @Valid
    private DeviceDto device;

    @Override
    public String toString() {
        return "DeviceLoginDto{" +
                "deviceDto=" + device +
                "} " + super.toString();
    }
}
