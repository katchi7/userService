package ma.tna.ebanking.userservice.api;

import ma.tna.ebanking.userservice.dtos.CustomerAccountRequest;
import ma.tna.ebanking.userservice.dtos.CustomerAccountServiceResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "EBANKING-ACCOUNT")
public interface AccountService {
    @PostMapping("/ebanking/eAccount/Accounts")
    @ResponseBody CustomerAccountServiceResp getCustomerAccounts(@RequestBody CustomerAccountRequest body);
}
