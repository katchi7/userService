package ma.tna.ebanking.userservice.controllers;

import ma.tna.ebanking.userservice.dtos.BenefDto;
import ma.tna.ebanking.userservice.dtos.BenefUpdateDto;
import ma.tna.ebanking.userservice.model.Benef;
import ma.tna.ebanking.userservice.services.BenefService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/benef")
public class BenefController {
    private final BenefService benefService;

    public BenefController(BenefService benefService) {
        this.benefService = benefService;
    }

    /**
     * Shows all customer's benefs
     * @param customerId customer's id
     * @return HttpResponseEntity containing benef data
     */
    @GetMapping(value = "")
    public HttpEntity<List<BenefDto>> getCustomerBenef(@RequestParam("customerId") Integer customerId){
        List<Benef> benefs = benefService.getCustomerBenef(customerId);
        return ResponseEntity.ok(benefs.stream().map(BenefDto::new).collect(Collectors.toList()));
    }

    /**
     * Creates a benef for a user
     * @param benefDto benef data
     * @param errors beanValidation errors
     * @return HttpResponseEntity containing the new created benef data
     */
    @PostMapping(value = "")
    public HttpEntity<BenefDto> createBenef(@RequestBody @Valid BenefDto benefDto, Errors errors){
        if(errors.hasErrors()){
            List<String> errorsStr = errors.getAllErrors().stream().map(s -> "\n"+s.getObjectName()+" : " +s.getDefaultMessage()).collect(Collectors.toList());
            String message = "Body is not valid "+errorsStr;
            throw new InvalidParameterException(message);
        }
        benefDto = new BenefDto(benefService.createBenef(benefDto.asBenef()));

        return ResponseEntity.ok(benefDto);
    }

    @PutMapping("/{benef_id}")
    public HttpEntity<BenefDto> updateBenef(@PathVariable("benef_id") Integer benefId,
                                            @RequestBody @Valid BenefUpdateDto benefDto, Errors errors){
        benefDto.validate(errors);
        benefDto.setId(benefId);
        return ResponseEntity.ok(new BenefDto(benefService.updateBenef(benefDto.asBenef())));
    }

    @DeleteMapping("/{benef_id}")
    public HttpEntity<String> deleteBenef(@PathVariable("benef_id") Integer benefId){
        benefService.deleteBenef(benefId);
        return ResponseEntity.ok("Benef deleted");
    }

    /**
     * Handles NoSuchElementExceptions
     * @param e the thrown exception
     * @return HttpResponseEntity containing the error message
     */
    @ExceptionHandler(NoSuchElementException.class)
    private HttpEntity<String> noSuchElementExceptionHandler(NoSuchElementException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles InvalidParameterException
     * @param e the thrown exception
     * @return HttpResponseEntity containing the error message
     */
    @ExceptionHandler(InvalidParameterException.class)
    private HttpEntity<String> invalidParameterExceptionHandler(InvalidParameterException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
