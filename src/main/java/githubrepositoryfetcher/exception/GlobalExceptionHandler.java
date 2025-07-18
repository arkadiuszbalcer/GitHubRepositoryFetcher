package githubrepositoryfetcher.exception;

import githubrepositoryfetcher.model.InternalServerException;
import githubrepositoryfetcher.model.RepositoryNotFoundException;
import githubrepositoryfetcher.model.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import githubrepositoryfetcher.model.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RepositoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(RepositoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage()));
    }

    @ExceptionHandler(RepositoryNotFoundException.class)
    ResponseEntity<ErrorResponse> handleRepositoryNotFoundException(UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage()));
    }

    @ExceptionHandler(InternalServerException.class)
    ResponseEntity<ErrorResponse> handleInternalServerError(InternalServerException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, ex.getMessage()));
    }
}
