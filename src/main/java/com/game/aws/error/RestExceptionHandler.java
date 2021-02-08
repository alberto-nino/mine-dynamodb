package com.game.aws.error;


import com.game.aws.dto.ApplicationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

   @ExceptionHandler(MineException.class)
    protected ResponseEntity<ApplicationError> handleSolicitudesApiException(final MineException ex) {
        log.error("An error has occurred with the following message " +  ex.getMessage());
        final ApplicationError apiError = ApplicationError.builder().errorMessage(ex.getMessage()).build();
        return new ResponseEntity<ApplicationError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApplicationError> handleException(final Exception ex) {
        log.error("An error has occurred with the following message " + ex.getMessage());
        final ApplicationError apiError = ApplicationError.builder().errorMessage("Unexpected Error").build();
        return new ResponseEntity<ApplicationError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
