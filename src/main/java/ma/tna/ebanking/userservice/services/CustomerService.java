package ma.tna.ebanking.userservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.api.CustomerInfo;
import ma.tna.ebanking.userservice.dtos.CustomerDto;
import ma.tna.ebanking.userservice.dtos.CustomerInfoDto;
import ma.tna.ebanking.userservice.model.Image;
import ma.tna.ebanking.userservice.repositories.CustomerRepo;
import ma.tna.ebanking.userservice.repositories.DeviceRepo;
import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Device;
import ma.tna.ebanking.userservice.model.Language;
import ma.tna.ebanking.userservice.tools.Constantes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@Service
public class CustomerService {
    private final CustomerInfo customerInfo;
    private final CustomerRepo customerRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DeviceRepo deviceRepo;
    public CustomerService(CustomerInfo customerInfo, CustomerRepo customerRepo, BCryptPasswordEncoder passwordEncoder, DeviceRepo deviceRepo) {
        this.customerInfo = customerInfo;
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
        if(customerOp.isPresent()){
            Customer customer = customerOp.get();
            return new CustomerDto(getCustomerInfo(customer));
        }
        throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
    }

    /**
     * this methode is responsible for loading user's extra information from the core banking service
     * @param customer the suggested customer
     * @return customer with extra data
     */

    @HystrixCommand(fallbackMethod = "defaultGetCustomer", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public Customer getCustomerInfo(Customer customer){
        Map<String, CustomerInfoDto> customerMap = new HashMap<>();
        customerMap.put(Constantes.getCUSTOMER(),new CustomerInfoDto(customer.getId()));
        CustomerInfoDto customerInfoResponse = customerInfo.getCustomerInfo(customerMap).get(Constantes.getCUSTOMER());
        if(customerInfoResponse != null){
            customer.setFullName(customerInfoResponse.getFullName());
            customer.setShortName(customerInfoResponse.getShortName());
            customer.setNationality(customerInfoResponse.getNationality());
            customer.setAddress(customerInfoResponse.getAdress());
            customer.setTown(customerInfoResponse.getTown());
            customer.setPostCode(customerInfoResponse.getPostCode());
            customer.setRestriction(customerInfoResponse.getRestriction());
            customer.setResidence(customerInfoResponse.getResidence());
            customer.setGender(customerInfoResponse.getGender());
            customer.setTitle(customerInfoResponse.getTitle());
            customer.setAgency(customerInfoResponse.getAgency());
            customer.setRestrictionValue(customerInfoResponse.getRestrictionValue());
        }
        return customer;
    }

    public CustomerDto defaultGetCustomer(int customerId,Throwable throwable) {
        log.info(throwable.getMessage());
        Customer customer = customerRepo.findById(customerId).orElse(null);
        log.info(customer);
        if(customer == null ) throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
        return new CustomerDto(customer);
    }

    /**
     * this method is responsible for creating new customers
     * @param customer : the customer to be created
     * @return Customer : customer data after creation
     */
    public Customer createCustomer(Customer customer){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        log.info(customer.getLanguage());
        customer = customerRepo.save(customer);
        return customer;
    }

    /**
     * This methode is responsible for customer update, it receives multiple parameters and updates the valid ones
     * @param id user's id
     * @param active user activity status
     * @param disponibilityStart user disponibility start time
     * @param disponibilityEnd user disponibility end time
     * @param allowEmails describes if users allows us to send him emails
     * @param lang user's language
     * @return Customer : new Customer data
     */
    public Customer updateCustomer(int id, Boolean active,String disponibilityStart, String disponibilityEnd, Boolean allowEmails,String lang){
            Optional<Customer> customerOptional = customerRepo.findById(id);
            if(customerOptional.isPresent()){
                Customer customer = customerOptional.get();
                customer.setActive((active==null)?customer.isActive():active);
                customer.setDisponibilityStart(("".equals(disponibilityStart)?customer.getDisponibilityStart():disponibilityStart));
                customer.setDisponibilityEnd(("".equals(disponibilityEnd)?customer.getDisponibilityEnd():disponibilityEnd));
                customer.setAllowEmails(allowEmails==null?customer.isAllowEmails():allowEmails);
                customer.setLanguage("".equals(lang)?customer.getLanguage():new Language(lang,null));
                customer = customerRepo.save(customer);
                return customer;
            }
            throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());

    }

    /**
     * This methode receives customer id, old password and new password, and updates customer's password if the old password is valid
     * @param id user's id
     * @param oldPassword user's old password
     * @param newPassword user's new password
     */
    public void updatePassword(int id, String oldPassword,String newPassword) {

            Optional<Customer> customerOptional = customerRepo.findById(id);
            if(customerOptional.isPresent()){
                Customer customer = customerOptional.get();
                if (passwordEncoder.matches(oldPassword, customer.getPassword())) {
                    customer.setPassword(passwordEncoder.encode(newPassword));
                    customerRepo.save(customer);
                    return;
                }
                throw new InvalidParameterException("Password is not matching");
            }
            throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());

    }

    /**
     * This function is responsible for device creation
     * receives user id and device data
     * throws InvalidParameterException if device is already existing
     * returns device after creation data
     * @param userId user's id
     * @param device device data
     * @return Device : device data after creation
     * @throws InvalidParameterException if the device is already existing
     */
    public Device createDevice(int userId,Device device) {
        Device device1 = deviceRepo.findDeviceByKeyAndCustomerId(device.getKey(),userId);
        if(device1 != null){
            log.info(device1);
            device1.setManufacturer(device.getManufacturer());
            device1.setModel(device.getModel());
            device1.setName(device.getName());
            device1.setRef(device.getRef());
            device1.setFingerprintActivated(device.isFingerprintActivated());
            device1.setOs(device.getOs());
            device1.setLastConnection(LocalDateTime.now());
            deviceRepo.save(device1);
            return device1;
        }
        device.setLastConnection(LocalDateTime.now());
        return  deviceRepo.save(device);
    }

    /**
     * This methode is responsible for updating device fingerprint status
     * receives user'id, device id and fingerprint status
     * @param userId user's id
     * @param deviceKey device key
     * @param fingerprintActivated fingerprint status
     * @return Device : device data after updating
     * @throws InvalidParameterException if the device is not corresponding to he given user
     */
    public Device updateFingerprint(int userId, String deviceKey, Boolean fingerprintActivated){
        Device device = deviceRepo.findDeviceByKeyAndCustomerId(deviceKey,userId);
        if(device != null){
            device.setFingerprintActivated(fingerprintActivated);
            return deviceRepo.save(device);
        }
        throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
    }

    /**
     * this function receives a user's id and returns a list of the user's devices
     * @param userId user's id
     * @return devices :  List of user's Devices
     * @throws NoSuchElementException if the user does not exit
     */
    public List<Device> userDevices(int userId){
        Optional<Customer> customerOptional = customerRepo.findById(userId);
        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            return deviceRepo.getDeviceByCustomer(customer);
        }
        throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());

    }

    /**
     * this methode is responsible for updating a customer's image
     * @param image User's image in a Base64 format
     * @param userId User's id
     * @return The modified customer
     * @throws NoSuchElementException if the customer does not exist
     */
    public Customer updateUserImage(String image,int userId) {
        Optional<Customer> customerOptional = customerRepo.findById(userId);
        if(customerOptional.isPresent()){
            customerRepo.updateCustomerImage(image,userId);
            return customerOptional.get();
        }
        throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());

    }

    /**
     * this methode is responsible for loading customer's image
     * @param userId customer id
     * @return customer image in a Base64 format
     */
    public Image getUserImage(int userId){
        return customerRepo.findCustomerImage(userId);
    }

    public void deleteDevice(int deviceId){
        deviceRepo.deleteById(deviceId);
    }

    public Customer validateCustomer(Integer userName,String psw){
        Customer customer = customerRepo.findById(userName).orElse(null);
        if(customer == null) throw new NoSuchElementException(Constantes.getUSER_NOT_FOUND());
        if(passwordEncoder.matches(psw,customer.getPassword())) {

            return getCustomerInfo(customer);
        }
        throw new InvalidParameterException("Password not matching");
    }

    public Customer validateCustomerWithDevice(Integer username,String psw,Device device){
        Customer customer = validateCustomer(username,psw);
        device.setCustomer(customer);
        createDevice(customer.getId(),device);
        return customer;
    }

}
