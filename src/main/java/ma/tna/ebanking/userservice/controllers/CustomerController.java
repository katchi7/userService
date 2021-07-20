package ma.tna.ebanking.userservice.controllers;

import ma.tna.ebanking.userservice.dtos.*;
import ma.tna.ebanking.userservice.model.Image;
import ma.tna.ebanking.userservice.services.CustomerService;
import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Device;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.*;

@RestController
@RequestMapping("/customer")
@Log4j2
public class CustomerController {
    private final CustomerService customerService;
    private static final String IMAGE_FIELD = "image";
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    /**
     * This method send a Json List of all subscribed users
     * @return ResponseEntity containing all the subscribed users
     */
    @GetMapping("")
    public HttpEntity<List<CustomerDto>> getAllUsers(){
        List<CustomerDto> customerDtos = new ArrayList<>();
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            customerDtos.add(new CustomerDto(customer));
        }
        return ResponseEntity.ok(customerDtos);
    }

    /**
     * @param id User's id
     * @return ResponseEntity containing user's data
     * @throws InvalidParameterException if th user doesn't exist
     */
    @GetMapping("/{id}")
    public HttpEntity<CustomerDto> getUserById(@PathVariable(value = "id") int id ){
        CustomerDto dto = customerService.getCustomerById(id);
        if(dto !=null){
            return ResponseEntity.ok(dto);
        }
        throw new InvalidParameterException("Cannot find customer with id "+ id);
    }

    /**
     * this method receives an http Post request containing new user's data in the JSON body
     * validates the given data
     * stores the user or throws an InvalidObjectException if the data is not valid
     * @param customerDto object containing received data
     * @param errors object containing bean validation errors
     * @return HttpResponseEntity that contains created customer data
     * @throws InvalidObjectException if the request body is invalid
     */
    @PostMapping(value = "",consumes = {"application/json"})
    public HttpEntity<CustomerDto> createCustomer(@RequestBody @Valid CustomerCreationDto customerDto, Errors errors) throws InvalidObjectException {
        log.info(errors);
        log.info(customerDto);
        if(errors.hasErrors()){
            List<ObjectError> errorsobjs = errors.getAllErrors();
            StringBuilder message = new StringBuilder();
            for (ObjectError errorsobj : errorsobjs) {
                message.append(", ").append(errorsobj.getObjectName()).append(" : ").append(errorsobj.getDefaultMessage());

            }
            throw new InvalidObjectException("Invalid request body :"+message);
        }

        CustomerDto customerDto1 = new CustomerDto(customerService.createCustomer(customerDto.asCustomer()));

        return ResponseEntity.ok(customerDto1);
    }

    /**
     * This methode receives an http Put request containing various parameters
     * it updates the given data and sends the new user data back to the user
     * @param customerId customer's id
     * @param active customer's activity status
     * @param disponibilityStart customer's disponibility start time
     * @param disponibilityEnd customer's disponibility end time
     * @param image customer's image
     * @param allowEmails defines if customze allows us to send emials
     * @param language customer's language
     * @return HttpResponseEntity containing new Data
     */
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
    /**
     * This methode receives a post http request containing user's id in path variables and user's old and new password in the body
     * and returns a ResponseEntity with the status OK if the password was update, throws an InvalidParameter if the parameters are invalid
     * @param userId user's id
     * @param password user password
     * @param errors object that contains bean validation errors
     * @return an HttpResponseEntity with the OK status if the password was updated
     */
    @PostMapping(value = "/{id}/password",consumes = {"application/json"})
    public HttpEntity<String> updatePassword(@PathVariable("id") int userId,@RequestBody @Valid PasswordDto password,Errors errors){
        if(errors.hasErrors()){
            List<ObjectError> errorsobjs = errors.getAllErrors();
            StringBuilder message = new StringBuilder();
            for (ObjectError errorsobj : errorsobjs) {
                message.append(", ").append(errorsobj.getObjectName()).append(" : ").append(errorsobj.getDefaultMessage());

            }
            throw new InvalidParameterException("Request fields are not valid :  "+message);
        }
        customerService.updatePassword(userId,password.getOldPassword(),password.getNewPassword());
        return ResponseEntity.ok("Password Updated");
    }


    /**
     * This methode receives an image in a Base64 format from the user and passes it to customerService
     * @param userId user's id
     * @param requestBody JSon object containing the image in a Base64 format
     * @return HttpResponseEntity containing customer data
     */
    @PostMapping("/{id}/image")
    public HttpEntity<CustomerDto> updateUserImage(@PathVariable("id") Integer userId, @RequestBody Map<String,String> requestBody){
        String image = requestBody.get(IMAGE_FIELD);
        return ResponseEntity.ok(new CustomerDto(customerService.updateUserImage(image,userId)));
    }

    /**
     * this methode is responsible for gtting the user's image
     * @param userId the user's id
     * @return ResponseEntity containing the image in a Base64 format
     */
    @GetMapping("/{id}/image")
    public HttpEntity<Image> getCustomerImage(@PathVariable("id") int userId){
        return ResponseEntity.ok(customerService.getUserImage(userId));
    }

    /**
     * this methode is responsible for the creation of user's devices
     * receive a POST http request containing device data in a JSON body, and user's id as a path variable
     * @param userId user's id
     * @param errors bean validation errors
     * @param device device data
     * @return HttpResponseEntity containing new device data
     * @throws InvalidParameterException if the data sent by the user is not valid or the device is already existing
     */
    @PostMapping(value = "/{id}/device",consumes = {"application/json"})
    public HttpEntity<DeviceDto> createDevice(@PathVariable("id") int userId,@RequestBody @Valid DeviceDto device,Errors errors) {
        if(errors.hasErrors()){
            List<ObjectError> errorsobjs = errors.getAllErrors();
            StringBuilder message = new StringBuilder();
            for (ObjectError errorsobj : errorsobjs) {
                message.append(", ").append(errorsobj.getObjectName()).append(" : ").append(errorsobj.getDefaultMessage());

            }
            throw  new InvalidParameterException("Invalid device data "+message);
        }
        log.info(device);
        device = new DeviceDto(customerService.createDevice(userId,device.asDevice()));
        return ResponseEntity.ok(device);
    }

    /**
     * this methode is responsible for updating device fingerprint status
     * @param userId user's id
     * @param deviceId device id
     * @param fingerprintActivated device's fingerprint is activated
     * @return HttpResponseEntity containing new device data
     */
    @PutMapping(value = "/{user_id}/device/{device_id}")
    public  HttpEntity<DeviceDto> changeDeviceFingerprint(@PathVariable("user_id") int userId, @PathVariable("device_id") int deviceId,
                                                          @RequestParam("fingerprintActivated")Boolean fingerprintActivated){
        DeviceDto device = new DeviceDto(customerService.updateFingerprint(userId,deviceId,fingerprintActivated));
        return ResponseEntity.ok(device);
    }

    /**
     * This function is responsible for listing all user's devices
     * @param userId user's id
     * @return HttpResponseEntity containing a list of devices
     */
    @GetMapping("/{user_id}/device")
    public HttpEntity<List<DeviceDto>> getUserDevices(@PathVariable("user_id") int userId){
        List<Device> devices = customerService.userDevices(userId);
        List<DeviceDto> devices1 = new ArrayList<>();
        for (Device device : devices) {
            devices1.add(new DeviceDto(device));
        }
        return ResponseEntity.ok(devices1);
    }

    /**
     * This function is resposible for deleting a user's device
     * @param userId  user id
     * @param deviceId device id
     * @param httpServletRequest the sent request
     * @return Operation Response contains operation status
     */
    @DeleteMapping("/{user_id}/device/{device_id}")
    public HttpEntity<OperationResponse> deleteUserDevice(@PathVariable("user_id") Integer userId, @PathVariable("device_id") Integer deviceId, HttpServletRequest httpServletRequest){
        customerService.deleteDevice(userId,deviceId);
        return ResponseEntity.ok(new OperationResponse(HttpStatus.OK.value(), null,"device deleted",httpServletRequest.getServletPath()));
    }

    @PostMapping(value = "/login",consumes = {"application/json"})
    public HttpEntity<CustomerDto> validateCustomer(@RequestBody @Valid LoginDto loginDto,Errors errors){
        if(errors.hasErrors()) throw new InvalidParameterException("Invalid username and password");
        return ResponseEntity.ok(new CustomerDto(customerService.validateCustomer(loginDto.getUserName(),loginDto.getPassword())));
    }
}
