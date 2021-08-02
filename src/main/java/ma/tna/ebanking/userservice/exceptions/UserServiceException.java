package ma.tna.ebanking.userservice.exceptions;

import lombok.Data;

@Data
public class UserServiceException extends Exception {
    public UserServiceException(String message,int status){
        super(message);
        this.message = message;
        this.status = status;
    }
    private String message;
    private int status;
}
