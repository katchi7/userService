package com.tna.userservice.Controllers;

import com.tna.userservice.Dtos.CustomerDto;
import com.tna.userservice.Services.CustomerService;
import com.tna.userservice.model.Customer;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/customer")
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
    public HttpEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto){
        return ResponseEntity.ok().build();
    }



    @ExceptionHandler(InvalidParameterException.class)
    public HttpEntity<String> InvalidParameterExceptionHandler(InvalidParameterException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
