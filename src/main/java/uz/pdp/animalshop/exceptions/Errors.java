package uz.pdp.animalshop.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("uz.pdp.animalshop")
public class Errors {

    @ExceptionHandler({RuntimeException.class, InternalAuthenticationServiceException.class, Exception.class, NullPointerException.class})
    public ResponseEntity<?> handleNotFound(Exception e, HttpServletRequest request) {

        CurrentException currentException = new CurrentException();
        currentException.setMessage(e.getMessage());
        currentException.setStatus(HttpStatus.NOT_FOUND.value());
        currentException.setUrl(request.getAuthType());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(currentException);

    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<?> handleExecutionException(ChangeSetPersister.NotFoundException e, HttpServletRequest request) {
        CurrentException currentException = new CurrentException();
        currentException.setMessage(e.getMessage());
        currentException.setStatus(HttpStatus.NOT_FOUND.value());
        currentException.setUrl(request.getAuthType());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(currentException);
    }
}
