package ma.tna.ebanking.userservice.controllers;

import ma.tna.ebanking.userservice.dtos.OtpDto;
import ma.tna.ebanking.userservice.model.Otp;
import ma.tna.ebanking.userservice.services.OtpService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
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
     * @return HttpResponseEntity containing otp data
     */
    @PostMapping("/{user_id}/otp")
    public HttpEntity<OtpDto> createOtp(@PathVariable("user_id") Integer userId){
        Otp otp = otpService.createOtp(userId);
        return ResponseEntity.ok(new OtpDto(otp));
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

    //TODO remove this methode after implementing an sms sender service
    @GetMapping("/{user_id}/otp")
    public HttpEntity<OtpDto> getCustomerOtp(@PathVariable("user_id") int userId){
        return ResponseEntity.ok(new OtpDto(otpService.getUserOtp(userId)));
    }

    @ExceptionHandler(InvalidParameterException.class)
    public HttpEntity<String>InvalidParameterExceptionHandler(InvalidParameterException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public HttpEntity<String> NoSuchElementExceptionHandler(NoSuchElementException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
