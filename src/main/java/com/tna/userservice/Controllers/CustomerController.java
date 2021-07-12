package com.tna.userservice.Controllers;

import com.tna.userservice.Dtos.CustomerDto;
import com.tna.userservice.Dtos.LanguageDto;
import com.tna.userservice.Dtos.PasswordDto;
import com.tna.userservice.Services.CustomerService;
import com.tna.userservice.model.Customer;
import com.tna.userservice.model.Language;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/customer")
@Log4j2
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public HttpEntity<List<CustomerDto>> getAllUsers(){

        List<CustomerDto> customerDtos = new ArrayList<>();
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            customerDtos.add(new CustomerDto(customer));
        }
        return ResponseEntity.ok(customerDtos);
    }
    @GetMapping("/{id}")
    public HttpEntity<CustomerDto> getUserById(@PathVariable(value = "id",required = true) int id ){
        CustomerDto dto = customerService.getCustomerById(id);
        if(dto !=null){
            return ResponseEntity.ok(dto);
        }
        throw new InvalidParameterException("Cannot find customer with id "+ id);
    }

    @PostMapping(value = "",consumes = {"application/json"})
    public HttpEntity<CustomerDto> createCustomer(@RequestBody @Valid CustomerDto customerDto, Errors errors) throws InvalidObjectException {
        log.info(errors);
        log.info(customerDto);
        if(errors.hasErrors()){
            List<ObjectError> errorsobjs = errors.getAllErrors();
            String message = "";
            for (ObjectError errorsobj : errorsobjs) {
                message += "\n "+errorsobj.getObjectName()+" : "+ errorsobj.getDefaultMessage();

            }
            throw new InvalidObjectException("Invalid request body :"+message);
        }

        customerDto = new CustomerDto(customerService.createCustomer(customerDto.asCustomer()));

        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("/{id}")
    public HttpEntity<CustomerDto> updateCustomer(@PathVariable(value = "id") int customerId,
                                                  @RequestParam(value = "active",required = false) Boolean active,
                                                  @RequestParam(value = "disponibilityStart",required = false,defaultValue = "") String disponibilityStart,
                                                  @RequestParam(value = "disponibilityEnd",required = false,defaultValue = "") String disponibilityEnd,
                                                  @RequestParam(value = "image",required = false,defaultValue = "") String image,
                                                  @RequestParam(value = "allowEmails",required = false) Boolean allowEmails,
                                                  @RequestParam(value = "language",required = false,defaultValue = "") String language){

        return ResponseEntity.ok(new CustomerDto(customerService.updateCustomer(customerId,active,disponibilityStart,disponibilityEnd,image,allowEmails,language)));

    }

    @PostMapping(value = "/{id}/password",consumes = {"application/json"})
    public HttpEntity<String> updatePassword(@PathVariable("id") int user_id,@RequestBody @Valid PasswordDto password,Errors errors){
        if(errors.hasErrors()){
            List<ObjectError> errorsobjs = errors.getAllErrors();
            String message = "";
            for (ObjectError errorsobj : errorsobjs) {
                message += "\n "+errorsobj.getObjectName()+" : "+ errorsobj.getDefaultMessage();

            }
            throw new InvalidParameterException("Request fields are not valid :  "+message);
        }
        customerService.updatePassword(user_id,password.getOldPassword(),password.getNewPassword());
        return ResponseEntity.ok("Password Updated");
    }



    @ExceptionHandler(InvalidParameterException.class)
    public HttpEntity<String> InvalidParameterExceptionHandler(InvalidParameterException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(InvalidObjectException.class)
    public HttpEntity<String> InvalidObjectExceptionHandler(InvalidObjectException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(NoSuchElementException.class)
    public HttpEntity<String>  NoSuchElementExceptionHandler(NoSuchElementException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public HttpEntity<String> MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
