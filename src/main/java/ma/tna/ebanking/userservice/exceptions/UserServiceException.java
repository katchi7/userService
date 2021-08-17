package ma.tna.ebanking.userservice.exceptions;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServiceException extends HystrixBadRequestException {
    public UserServiceException(String message,int status){
        super(message);
        this.status = status;
    }
    private final int status;
}