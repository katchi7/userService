package ma.tna.ebanking.userservice.controllers;

import ma.tna.ebanking.userservice.dtos.*;
import ma.tna.ebanking.userservice.model.Image;
import ma.tna.ebanking.userservice.services.CustomerService;
import ma.tna.ebanking.userservice.model.Customer;
import ma.tna.ebanking.userservice.model.Device;
import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.tools.Constantes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

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
    public HttpEntity<List<CustomerMinifiedDto>> getAllUsers(){
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers.stream().map(CustomerMinifiedDto::new).collect(Collectors.toList()));
    }

    /**
     * @param id User's id
     * @return ResponseEntity containing user's data
     * @throws InvalidParameterException if th user doesn't exist
     */
    @GetMapping("/{id}")
    public HttpEntity<CustomerDto> getUserById(@PathVariable(value = "id") int id ){
        Customer customer = customerService.getCustomerById(id);
        CustomerDto dto = new CustomerDto(customerService.getCustomerInfo(customer));
        return ResponseEntity.ok(dto);
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
     * @param allowEmails defines if customze allows us to send emials
     * @param language customer's language
     * @return HttpResponseEntity containing new Data
     */
    @PutMapping("/{id}")
    public HttpEntity<CustomerDto> updateCustomer(@PathVariable(value = "id") int customerId,
                                                  @RequestParam(value = "active",required = false) Boolean active,
                                                  @RequestParam(value = "disponibilityStart",required = false,defaultValue = "") String disponibilityStart,
                                                  @RequestParam(value = "disponibilityEnd",required = false,defaultValue = "") String disponibilityEnd,
                                                  @RequestParam(value = "allowEmails",required = false) Boolean allowEmails,
                                                  @RequestParam(value = "language",required = false,defaultValue = "") String language){

        return ResponseEntity.ok(new CustomerDto(customerService.updateCustomer(customerId,active,disponibilityStart,disponibilityEnd,allowEmails,language)));

    }
    /**
     * This methode receives a post http request containing user's id in path variables and user's old and new password in the body
     * and returns a ResponseEntity with the status OK if the password was update, throws an InvalidParameter if the parameters are invalid

     * @param password user password
     * @param errors object that contains bean validation errors
     * @return an HttpResponseEntity with the OK status if the password was updated
     */
    @PostMapping(value = "/password",consumes = {"application/json"})
    public HttpEntity<String> updatePassword(@RequestBody @Valid PasswordDto password,Errors errors){
        throwErrors(errors);
        customerService.updatePassword(password.getId(),password.getOldPassword(),password.getNewPassword());
        return ResponseEntity.ok("Password Updated");
    }
    @PostMapping(value = "/validatePassword")
    public HttpEntity<OperationResponse> validatePassword(@RequestBody @Valid PasswordDto passwordDto,Errors errors,HttpServletRequest request){

        try {
            throwErrors(errors);
            customerService.validatePassword(passwordDto.getId(),passwordDto.getOldPassword(),passwordDto.getNewPassword());
            return ResponseEntity.ok(new OperationResponse(HttpStatus.OK.value(), null,"Password is valid",request.getServletPath()));
        }catch (NoSuchElementException e){
            return ResponseEntity.status(Constantes.getUSER_NOT_FOUND_STATUS()).body(new OperationResponse(Constantes.getUSER_NOT_FOUND_STATUS().value(),e.getClass().getSimpleName(),e.getMessage(),request.getServletPath()));
        }
        catch (InvalidParameterException e){
            return ResponseEntity.status(Constantes.getUSER_PASSWORD_DOES_NOT_MATCH()).body(new OperationResponse(Constantes.getUSER_PASSWORD_DOES_NOT_MATCH().value(),e.getClass().getSimpleName(),e.getMessage(),request.getServletPath()));
        }
        catch (InvalidObjectException e){
            return ResponseEntity.status(Constantes.getUSER_NEW_MATCH_OLD()).body(new OperationResponse(Constantes.getUSER_NEW_MATCH_OLD().value(),e.getClass().getSimpleName(),e.getMessage(),request.getServletPath()));
        }
        catch (ValidationException e){
            return ResponseEntity.status(Constantes.getUSER_PASSWORD_VALIDATION_STATUS()).body(new OperationResponse(Constantes.getUSER_PASSWORD_VALIDATION_STATUS().value(),e.getClass().getSimpleName(),e.getMessage(),request.getServletPath()));
        }


    }

    private void throwErrors(Errors errors) {
        if(errors.hasErrors()){
            List<ObjectError> errorsobjs = errors.getAllErrors();
            StringBuilder message = new StringBuilder();
            for (ObjectError errorsobj : errorsobjs) {
                message.append(", ").append(errorsobj.getObjectName()).append(" : ").append(errorsobj.getDefaultMessage());
            }
            throw new ValidationException("Request fields are not valid :  "+message);
        }
    }


    /**
     * This methode receives an image in a Base64 format from the user and passes it to customerService
     * @param requestBody JSon object containing the image in a Base64 format
     * @return HttpResponseEntity containing customer data
     */
    @PostMapping("/image")
    public HttpEntity<OperationResponse> updateUserImage( @RequestBody Map<String,String> requestBody,HttpServletRequest request){
        String image = requestBody.get(Constantes.getIMAGE_FIELD());
        try {
            int userId = Integer.parseInt(requestBody.get(Constantes.getID()));
            customerService.updateUserImage(image,userId);
            OperationResponse response = new OperationResponse(HttpStatus.OK.value(), null,"Image updated",request.getServletPath());
            return ResponseEntity.ok(response);
        }catch (NumberFormatException e){
            throw new InvalidParameterException("customer id is not valid");
        }
    }

    /**
     * this methode is responsible for gtting the user's image
     * @param userId the user's id
     * @return ResponseEntity containing the image in a Base64 format
     */
    @GetMapping("/image")
    public HttpEntity<Image> getCustomerImage(@RequestParam("customerId") int userId){
        return ResponseEntity.ok(customerService.getUserImage(userId));
    }

    /**
     * this methode is responsible for the creation of user's devices
     * receive a POST http request containing device data in a JSON body, and user's id as a path variable
     * @param errors bean validation errors
     * @param device device data
     * @return HttpResponseEntity containing new device data
     * @throws InvalidParameterException if the data sent by the user is not valid or the device is already existing
     */
    @PostMapping(value = "/device",consumes = {"application/json"})
    public HttpEntity<DeviceDto> createDevice(@RequestBody @Valid DeviceCreationDto device,Errors errors) {
        log.info(device);
        if(errors.hasErrors()){
            List<ObjectError> errorsobjs = errors.getAllErrors();
            StringBuilder message = new StringBuilder();
            for (ObjectError errorsobj : errorsobjs) {
                message.append(", ").append(errorsobj.getObjectName()).append(" : ").append(errorsobj.getDefaultMessage());

            }
            throw  new InvalidParameterException("Invalid device data "+message);
        }
        DeviceDto device1 = new DeviceDto(customerService.createDevice(device.getCustomerId(),device.asDevice()));
        return ResponseEntity.ok(device1);
    }

    /**
     * this methode is responsible for updating device fingerprint status
     * @param deviceDto device data
     * @return HttpResponseEntity containing new device data
     */
    @PutMapping(value = "/device")
    public  HttpEntity<OperationResponse> changeDeviceFingerprint( @RequestBody @Valid DeviceCreationDto deviceDto,Errors errors,HttpServletRequest request){
        if(errors.hasFieldErrors(Constantes.getKEY())) throw new InvalidParameterException("Body is not valid : device key must be provided");
        customerService.updateFingerprint(deviceDto.getCustomerId(),deviceDto.getKey(),deviceDto.getFingerprintActivated());
        return ResponseEntity.ok(new OperationResponse(HttpStatus.OK.value(), null,"fingerprint updated to "+deviceDto.getFingerprintActivated(),request.getServletPath()));
    }

    /**
     * This function is responsible for listing all user's devices
     * @param userId user's id
     * @return HttpResponseEntity containing a list of devices
     */
    @GetMapping("/device")
    public HttpEntity<List<DeviceDto>> getUserDevices(@RequestParam("customerId") int userId){
        List<Device> devices = customerService.userDevices(userId);

        return ResponseEntity.ok(devices.stream().map(DeviceDto::new).collect(Collectors.toList()));
    }

    /**
     * This function is resposible for deleting a user's device
     * @param deviceId device id
     * @param httpServletRequest the sent request
     * @return Operation Response contains operation status
     */
    @DeleteMapping("/device/{device_id}")
    public HttpEntity<OperationResponse> deleteUserDevice( @PathVariable("device_id") Integer deviceId, HttpServletRequest httpServletRequest){
        customerService.deleteDevice(deviceId);
        return ResponseEntity.ok(new OperationResponse(HttpStatus.OK.value(), null,"device deleted",httpServletRequest.getServletPath()));
    }

    @PostMapping(value = "/login",consumes = {"application/json"})
    public HttpEntity<CustomerDto> validateCustomer(@RequestBody @Valid LoginDto loginDto,Errors errors){
        if(errors.hasErrors()) throw new InvalidParameterException("Invalid username and password");
        return ResponseEntity.ok(new CustomerDto(customerService.getCustomerInfo(customerService.validateCustomer(loginDto.getUserName(),loginDto.getPassword()))));
    }
    @PostMapping(value = "/deviceLogin",consumes = {"application/json"})
    public HttpEntity<CustomerDto> validateCustomerWithDevice(@RequestBody @Valid DeviceLoginDto loginDto,Errors errors){
        if(errors.hasErrors()) throw new InvalidParameterException("Invalid data");
        return ResponseEntity.ok(new CustomerDto(customerService.getCustomerInfo(customerService.validateCustomerWithDevice(loginDto.getUserName(),loginDto.getPassword(),loginDto.getDevice().asDevice()))));
    }


}
