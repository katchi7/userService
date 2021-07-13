package ma.tna.ebanking.userservice.controllers;

import ma.tna.ebanking.userservice.dtos.CustomerDto;
import ma.tna.ebanking.userservice.dtos.DeviceDto;
import ma.tna.ebanking.userservice.dtos.PasswordDto;
import ma.tna.ebanking.userservice.services.CustomerService;
import ma.tna.ebanking.userservice.model.Customer;;
import ma.tna.ebanking.userservice.model.Device;
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
    public HttpEntity<CustomerDto> getUserById(@PathVariable(value = "id",required = true) int id ){
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
     * @return
     * @throws InvalidObjectException
     */
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

    /**
     * This methode receives an http Put request containing various parameters
     * it updates the given data and sends the new user data back to the user
     * @param customerId
     * @param active
     * @param disponibilityStart
     * @param disponibilityEnd
     * @param image
     * @param allowEmails
     * @param language
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
     * @param user_id
     * @param password
     * @param errors
     * @return an HttpResponseEntity with the OK status if the password was updated
     */
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
            String message = "";
            for (ObjectError errorsobj : errorsobjs) {
                message += "\n "+errorsobj.getObjectName()+" : "+ errorsobj.getDefaultMessage();

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
        List<DeviceDto> devices_ = new ArrayList<>();
        for (Device device : devices) {
            devices_.add(new DeviceDto(device));
        }
        return ResponseEntity.ok(devices_);
    }

    /**
     * This methode is responsible for handling InvalidParameterExceptions
     * @param e thrown exception
     * @return HttpResponseEntity containing exception message
     */
    @ExceptionHandler(InvalidParameterException.class)
    public HttpEntity<String> InvalidParameterExceptionHandler(InvalidParameterException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * This methode is responsible for handling InvalidObjectException
     * @param e thrown exception
     * @return HttpResponseEntity containing exception message
     */
    @ExceptionHandler(InvalidObjectException.class)
    public HttpEntity<String> InvalidObjectExceptionHandler(InvalidObjectException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * This methode is responsible for handling NoSuchElementException
     * @param e thrown exception
     * @return HttpResponseEntity containing exception message
     */
    @ExceptionHandler(NoSuchElementException.class)
    public HttpEntity<String>  NoSuchElementExceptionHandler(NoSuchElementException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * This methode is responsible for handling MethodArgumentTypeMismatchException
     * @param e thrown exception
     * @return HttpResponseEntity containing exception message
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public HttpEntity<String> MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }


}
