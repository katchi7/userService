package ma.tna.ebanking.userservice.controllers;


import ma.tna.ebanking.userservice.dtos.OtpDto;
import ma.tna.ebanking.userservice.services.OtpService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customer")
public class OtpController {
    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    /**
     * This methode is responsible for receiving request to generate otp
     * @param userId the user's id
     * @return HttpResponseEntity containing a message
     */
    @PostMapping("/{user_id}/otp")
    public HttpEntity<Map<String,String>> createOtp(@PathVariable("user_id") Integer userId){
        otpService.createOtp(userId);
        HashMap<String,String> message = new HashMap<>();
        message.put("message","Otp created");
        return ResponseEntity.ok(message);
    }

    /**
     * This methode receives request to validate otp
     * @param userId user's id
     * @param otp the otp in a string format
     * @return HttpResponseEntity containing otp data
     */
    @PutMapping("/{user_id}/otp")
    public HttpEntity<OtpDto> validateOtp(@PathVariable("user_id") Integer userId,@RequestParam("otp") String otp){
        return ResponseEntity.ok(new OtpDto(otpService.validateOtp(userId,otp)));
    }

    @GetMapping("/{user_id}/otp")
    public HttpEntity<OtpDto> getCustomerOtp(@PathVariable("user_id") int userId){
        return ResponseEntity.ok(new OtpDto(otpService.getUserOtp(userId)));
    }

}
