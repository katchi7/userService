package ma.tna.ebanking.userservice.controllers;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import ma.tna.ebanking.userservice.dtos.OperationResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@EnableWebMvc
public class ExceptionController {
    @ExceptionHandler(value = {NoSuchElementException.class, InvalidParameterException.class, InvalidObjectException.class, HystrixBadRequestException.class})
    public HttpEntity<OperationResponse> globalErrorHandler(Exception e, HttpServletRequest servletRequest){
        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(new OperationResponse(HttpStatus.BAD_REQUEST.value(),e.getClass().getSimpleName(),e.getMessage(), servletRequest.getServletPath()));
    }
}
