package ma.tna.ebanking.userservice.controllers;

import ma.tna.ebanking.userservice.dtos.OperationResponse;
import ma.tna.ebanking.userservice.dtos.OtpDto;
import ma.tna.ebanking.userservice.services.OtpService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.InvalidParameterException;

@RestController
@RequestMapping("/customer")
public class OtpController {
    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }
    /**
     * This methode is responsible for receiving request to generate otp
     * @param otpDto request Body Containing user id
     * @param request http servlet request
     * @return HttpResponseEntity containing a message
     */
    @PostMapping("/otp")
    public HttpEntity<OperationResponse> createOtp(@RequestBody @Valid OtpDto otpDto, Errors errors,HttpServletRequest request) {
        if(errors.hasFieldErrors("id")) throw new InvalidParameterException("Id is not valid");
        int userId = otpDto.getId();
        otpService.createOtp(userId);
        return ResponseEntity.ok(new OperationResponse(HttpStatus.OK.value(), null, "Otp created", request.getServletPath()));
    }
    /**
     * This methode receives request to validate otp
     * @return HttpResponseEntity containing otp data
     */
    @PutMapping("/otp")
    public HttpEntity<OtpDto> validateOtp(@RequestBody @Valid OtpDto otpDto,Errors errors){
        if(errors.hasFieldErrors("id")||errors.hasFieldErrors("otp")) throw new InvalidParameterException("Id or otp are not valid");
        return ResponseEntity.ok(new OtpDto(otpService.validateOtp(otpDto.getId(),otpDto.getOtp())));
    }
    @GetMapping("/otp")
    public HttpEntity<OtpDto> getCustomerOtp(@RequestParam("user_id") int userId){
        return ResponseEntity.ok(new OtpDto(otpService.getUserOtp(userId)));
    }

}
