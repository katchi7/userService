package com.tna.userservice.Services;

import com.tna.userservice.Dtos.CustomerDto;
import com.tna.userservice.Repositories.CustomerRepo;
import com.tna.userservice.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }
    public CustomerDto getCustomerById(int id){
        Optional<Customer> customerOp = customerRepo.findById(id);
        return customerOp.isPresent()?new CustomerDto(customerOp.get()):null;
    }
}
