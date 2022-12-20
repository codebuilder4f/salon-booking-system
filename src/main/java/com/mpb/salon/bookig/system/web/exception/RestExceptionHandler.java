package com.mpb.salon.bookig.system.web.exception;


import com.mpb.salon.bookig.system.web.apierror.ApiError;
import com.mpb.salon.bookig.system.web.res.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handles ExceptionWithMessage. Created to encapsulate errors.
     *
     * @param ex the ExceptionWithMessage
     * @return the ApiError object
     */
    @ExceptionHandler(ExceptionWithMessage.class)
    protected ResponseEntity<Object> handleExceptionWithMessage(ExceptionWithMessage ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * @param ex JwtTokenMalformedException
     * @return the ApiError object
     */
    @ExceptionHandler(JwtTokenMalformedException.class)
    protected ResponseEntity<Object> handleJwtTokenMalformedException(JwtTokenMalformedException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles BadRequestException. Created to encapsulate errors with more detail
     *
     * @param ex the BadRequestException
     * @return the ApiError object
     */
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    /**
     * Handles InvalidCredentialException. Created to encapsulate errors with more detail
     *
     * @param ex InvalidCredentialException
     * @return the ApiError object
     */
    @ExceptionHandler(InvalidCredentialException.class)
    protected ResponseEntity<Object> handleInvalideCredentialException(InvalidCredentialException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }



    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        ApiResponse apiResponse = ApiResponse
                .builder()
                .status(false)
                .body(apiError)
                .message(apiError.getMessage())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<>(apiResponse, apiError.getStatus());
    }

}
