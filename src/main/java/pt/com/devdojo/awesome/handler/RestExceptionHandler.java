package pt.com.devdojo.awesome.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.com.devdojo.awesome.error.ErrorDetail;
import pt.com.devdojo.awesome.error.ResourceNotFoundDetails;
import pt.com.devdojo.awesome.error.ResourceNotFoundException;
import pt.com.devdojo.awesome.error.ValidationErrorDetails;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException (ResourceNotFoundException rfnException){
        ResourceNotFoundDetails rfnDetails =  ResourceNotFoundDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("ResourceNotFound")
                .detail(rfnException.getMessage())
                .developerMessage(rfnException.getClass().getName())
                .build();
        return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);
    }



    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvException, HttpHeaders headers, HttpStatus status, WebRequest request) {


       List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
       String field = fieldErrors.stream().map( FieldError::getField ).collect(Collectors.joining(","));
       String fieldMessage = fieldErrors.stream().map( FieldError::getDefaultMessage ).collect(Collectors.joining(","));

        ValidationErrorDetails rfnDetails =  ValidationErrorDetails.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Field Validation Error")
                .detail("Field Validation Error")
                .developerMessage(manvException.getClass().getName())
                .field(field)
                .fieldMessage(fieldMessage)
                .build();
        return new ResponseEntity<>(rfnDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
/*    protected  ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ErrorDetail erroDetails =  ErrorDetail.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("ResourceNotFound")
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

       return new ResponseEntity<>(erroDetails, HttpStatus.BAD_REQUEST);
    }*/
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetail erroDetails =  ErrorDetail.Builder
                .newBuilder()
                .timestamp(new Date().getTime())
                .status(status.value())
                .title("Internal Exception")
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();

        return new ResponseEntity<>(erroDetails, headers, status);
    }

}
