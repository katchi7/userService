package com.tna.userservice.Services;

import com.tna.userservice.Dtos.CustomerDto;
import com.tna.userservice.Repositories.CustomerRepo;
import com.tna.userservice.Repositories.DeviceRepo;
import com.tna.userservice.model.Customer;
import com.tna.userservice.model.Device;
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
    private final DeviceRepo deviceRepo;
    public CustomerService(CustomerRepo customerRepo, BCryptPasswordEncoder passwordEncoder, DeviceRepo deviceRepo) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
        this.deviceRepo = deviceRepo;
    }

    /**
     * @return List<Customer> a list of all subscribed customers
     */
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }


    /**
     * @param id the requested customer's id
     * @return CustomerDto : an object containing user data
     */
    public CustomerDto getCustomerById(int id){
        Optional<Customer> customerOp = customerRepo.findById(id);
        return customerOp.map(CustomerDto::new).orElse(null);
    }

    /**
     * this method is responsible for creating new customers
     * @param customer : the customer to be created
     * @return Customer : customer data after creation
     */
    public Customer createCustomer(Customer customer){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer = customerRepo.save(customer);
        return customer;
    }

    /**
     * This methode is responsible for customer update, it receives multiple parameters and updates the valid ones
     * @param id
     * @param active
     * @param disponibilityStart
     * @param disponibilityEnd
     * @param image
     * @param allowEmails
     * @param lang
     * @return Customer : new Customer data
     */
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

    /**
     * This methode receives customer id, old password and new password, and updates customer's password if the old password is valid
     * @param id
     * @param oldPassword
     * @param newPassword
     */
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

    /**
     * This function is responsible for device creation
     * receives user id and device data
     * throws InvalidParameterException if device is already existing
     * returns device after creation data
     * @param userId
     * @param device
     * @return Device : device data after creation
     * @throws InvalidParameterException if the device is already existing
     */
    public Device createDevice(int userId,Device device) {

        List<Device> devices = deviceRepo.findDeviceByName(device.getName());
        if(devices.isEmpty()){
            device.setCustomer(customerRepo.findById(userId).get());
            device = deviceRepo.save(device);
            return device;
        }
        throw new InvalidParameterException("Device already existing");

    }

    /**
     * This methode is responsible for updating device fingerprint status
     * receives user'id, device id and fingerprint status
     * @param userId
     * @param deviceId
     * @param fingerprintActivated
     * @return Device : device data after updating
     * @throws InvalidParameterException if the device is not corresponding to he given user
     */
    public Device updateFingerprint(int userId, int deviceId, Boolean fingerprintActivated){
        try{
        Customer customer = customerRepo.findById(userId).get();
        Device device = deviceRepo.findDeviceByIdAndAndCustomer(deviceId,customer);
        if(device == null) throw new InvalidParameterException("Device id: "+deviceId+" is not corresponding to the given user: "+userId);
        device.setFingerprintActivated(fingerprintActivated);
        device = deviceRepo.save(device);
        return device;
        }catch (NoSuchElementException e ){
            throw new NoSuchElementException("Cannot find user with id : "+userId);
        }
    }

}
