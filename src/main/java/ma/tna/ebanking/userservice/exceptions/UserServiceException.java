package ma.tna.ebanking.userservice.exceptions;

import lombok.Data;

@Data
public class UserServiceException extends RuntimeException {
    public UserServiceException(String message,int status){
        super(message);
        this.message = message;
        this.status = status;
    }
    private final String message;
    private final int status;
}