package in.stackroute.userprofileservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class APIExceptionHandller {

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<?> handleUserExistsExcpetion(UserExistsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }



}
