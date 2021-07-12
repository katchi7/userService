package com.tna.userservice.Services;

import com.tna.userservice.Dtos.CustomerDto;
import com.tna.userservice.Repositories.CustomerRepo;
import com.tna.userservice.model.Customer;
import com.tna.userservice.model.Language;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    public CustomerService(CustomerRepo customerRepo, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
    }
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }
    public CustomerDto getCustomerById(int id){
        Optional<Customer> customerOp = customerRepo.findById(id);
        return customerOp.map(CustomerDto::new).orElse(null);
    }
    public Customer createCustomer(Customer customer){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer = customerRepo.save(customer);
        return customer;
    }

    public Customer updateCustomer(int id, Boolean active,String disponibilityStart, String disponibilityEnd,String image, Boolean allowEmails,String lang){
        try {
            Customer customer = customerRepo.findById(id).get();
            customer.setActive((active==null)?customer.isActive():active);
            customer.setDisponibilityStart(("".equals(disponibilityStart)?customer.getDisponibilityStart():disponibilityStart));
            customer.setDisponibilityEnd(("".equals(disponibilityEnd)?customer.getDisponibilityEnd():disponibilityEnd));
            customer.setImage("".equals(image)?customer.getImage():image);
            customer.setAllowEmails(allowEmails==null?customer.isAllowEmails():allowEmails);
            customer.setLanguage("".equals(lang)?customer.getLanguage():new Language(lang,null));
            customer = customerRepo.save(customer);
            return customer;
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("User does not exist");
        }
    }
    public void updatePassword(int id, String oldPassword,String newPassword) {
        try {

            Customer customer = customerRepo.findById(id).get();
            if (passwordEncoder.matches(oldPassword, customer.getPassword())) {
                customer.setPassword(passwordEncoder.encode(newPassword));
                customerRepo.save(customer);
                return;
            }
            throw new InvalidParameterException("Password is not matching");
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("User does not exist");
        }
    }

}
