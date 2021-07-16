package ma.tna.ebanking.userservice.controllers;

import io.swagger.v3.oas.models.responses.ApiResponse;
import ma.tna.ebanking.userservice.dtos.ErrorResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@EnableWebMvc
public class ExceptionController {
    @ExceptionHandler(value = {NoSuchElementException.class, InvalidParameterException.class})
    public HttpEntity<ErrorResponse> ErrorHandler(Exception e){
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),e.getClass().getName(),e.getMessage(),""));
    }
}
