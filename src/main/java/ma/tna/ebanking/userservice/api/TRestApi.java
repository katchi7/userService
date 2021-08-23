package ma.tna.ebanking.userservice.api;

import feign.Headers;
import ma.tna.ebanking.userservice.dtos.CustomerInfoDto;
import ma.tna.ebanking.userservice.dtos.T24AgencyResponse;
import ma.tna.ebanking.userservice.dtos.T24BenefBody;
import ma.tna.ebanking.userservice.dtos.T24CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
@FeignClient(value = "T-REST")
public interface TRestApi {
    @PostMapping(value = "/RestEndPoint/ebanking/customer/getCustomerInfo",produces = "application/json")
    @Headers("Content-Type : application/json")
    @ResponseBody T24CustomerResponse getCustomerInfo(@RequestBody Map<String, CustomerInfoDto> body);

    @PostMapping(value = "/RestEndPoint/ebanking/agence/getListeAgence",produces = "application/json")
    @Headers("Content-Type : application/json")
    @ResponseBody
    T24AgencyResponse getAgencyInfo(@RequestBody Map<String,Object> requestBody);

    @PostMapping(value = "/RestEndPoint/ebanking/virement/checkBenefeciary",produces = "application/json")
    @ResponseBody
    Map<String, T24BenefBody> validateBenef(@RequestBody Map<String,Object> body);
}
