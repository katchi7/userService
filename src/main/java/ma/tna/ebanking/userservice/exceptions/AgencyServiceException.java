package ma.tna.ebanking.userservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyServiceException extends RuntimeException {
    private String message;
    private HttpStatus status;
}
