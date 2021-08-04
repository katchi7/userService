package ma.tna.ebanking.userservice.dtos;

import lombok.Data;
import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Device;

import javax.validation.constraints.NotNull;

@Data
public class DeviceCreationDto extends DeviceDto {
    @NotNull
    private String customerId;

    public DeviceCreationDto(){
        super();
    }


    @Override
    public Device asDevice() {
        Device device = super.asDevice();
        Customer customer = new Customer();
        customer.setId(customerId);
        device.setCustomer(customer);
        return device;
    }

    @Override
    public String toString() {
        return "DeviceCreationDto{" +
                "customerId=" + customerId +
                "} " + super.toString();
    }

}
