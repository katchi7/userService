package ma.tna.ebanking.userservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class AgencyServiceException extends RuntimeException {
    private final String message;
    private final HttpStatus status;
}
