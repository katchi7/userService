package ma.tna.ebanking.userservice.exceptions;

import com.netflix.hystrix.exception.HystrixBadRequestException;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class AgencyServiceException extends HystrixBadRequestException {
    @Getter
    private final HttpStatus status;

    public AgencyServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
