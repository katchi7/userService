package ma.tna.ebanking.userservice.api;

import feign.Headers;
import ma.tna.ebanking.userservice.dtos.CustomerInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@FeignClient(value = "T-REST",url = "${ebanking.customerInfo.url:}")
public interface CustomerInfo {
    @PostMapping(value = "/RestEndPoint/ebanking/customer/getCustomerInfo",produces = "application/json")
    @Headers("Content-Type : application/json")
    @ResponseBody Map<String,CustomerInfoDto> getCustomerInfo(@RequestBody Map<String, CustomerInfoDto> body);
}
