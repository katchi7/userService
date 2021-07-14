package ma.tna.ebanking.userservice.services;

import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.dtos.CustomerDto;
import ma.tna.ebanking.userservice.repositories.CustomerRepo;
import ma.tna.ebanking.userservice.repositories.DeviceRepo;
import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Device;
import ma.tna.ebanking.userservice.model.Language;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
public class CustomerService {
    private static final String USER_NOT_FOUND = "User does not exist";
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
     * @param image user image
     * @param allowEmails describes if users allows us to send him emails
     * @param lang user's language
     * @return Customer : new Customer data
     */
    public Customer updateCustomer(int id, Boolean active,String disponibilityStart, String disponibilityEnd,String image, Boolean allowEmails,String lang){
            Optional<Customer> customerOptional = customerRepo.findById(id);
            if(customerOptional.isPresent()){
                Customer customer = customerOptional.get();
                customer.setActive((active==null)?customer.isActive():active);
                customer.setDisponibilityStart(("".equals(disponibilityStart)?customer.getDisponibilityStart():disponibilityStart));
                customer.setDisponibilityEnd(("".equals(disponibilityEnd)?customer.getDisponibilityEnd():disponibilityEnd));
                customer.setImage("".equals(image)?customer.getImage():image);
                customer.setAllowEmails(allowEmails==null?customer.isAllowEmails():allowEmails);
                customer.setLanguage("".equals(lang)?customer.getLanguage():new Language(lang,null));
                customer = customerRepo.save(customer);
                return customer;
            }
            throw new NoSuchElementException(USER_NOT_FOUND);

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
            throw new NoSuchElementException(USER_NOT_FOUND);

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

        List<Device> devices = deviceRepo.findDeviceByName(device.getName());
        if(devices.isEmpty()){
            Optional<Customer> customerOptional = customerRepo.findById(userId);
            if(customerOptional.isPresent()){
                device.setCustomer(customerOptional.get());
                device = deviceRepo.save(device);
                return device;
            }
            else {
                throw new NoSuchElementException(USER_NOT_FOUND);
            }
        }
        throw new InvalidParameterException("Device already existing");

    }

    /**
     * This methode is responsible for updating device fingerprint status
     * receives user'id, device id and fingerprint status
     * @param userId user's id
     * @param deviceId device id
     * @param fingerprintActivated fingerprint status
     * @return Device : device data after updating
     * @throws InvalidParameterException if the device is not corresponding to he given user
     */
    public Device updateFingerprint(int userId, int deviceId, Boolean fingerprintActivated){
        Optional<Customer> customerOptional = customerRepo.findById(userId);
        if(customerOptional.isPresent()){
            Customer customer =customerOptional.get();
            Device device = deviceRepo.findDeviceByIdAndAndCustomer(deviceId,customer);
            if(device == null) throw new InvalidParameterException("Device id: "+deviceId+" is not corresponding to the given user: "+userId);
            device.setFingerprintActivated(fingerprintActivated);
            device = deviceRepo.save(device);
            return device;
        }

        throw new NoSuchElementException(USER_NOT_FOUND);

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
        throw new NoSuchElementException(USER_NOT_FOUND);

    }

}
