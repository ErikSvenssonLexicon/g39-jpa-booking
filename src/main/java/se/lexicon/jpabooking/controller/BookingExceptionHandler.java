package se.lexicon.jpabooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import se.lexicon.jpabooking.exception.AppResourceNotFoundException;
import se.lexicon.jpabooking.model.dto.view.ExceptionResponseDTO;
import se.lexicon.jpabooking.model.dto.view.ValidationErrorResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class BookingExceptionHandler {

    public static final String VALIDATIONS_FAILED = "One or several validations failed";

    public ExceptionResponseDTO build(String message, HttpStatus status, WebRequest request){
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO();
        responseDTO.setError(status.name());
        responseDTO.setStatus(status.value());
        responseDTO.setTimeStamp(LocalDateTime.now());
        responseDTO.setPath(request.getDescription(false));
        responseDTO.setMessage(message);
        return responseDTO;
    }

    @ExceptionHandler(AppResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleAppResourceNotFoundException(AppResourceNotFoundException ex, WebRequest request){
        return ResponseEntity.status(404).body(
                build(ex.getMessage(), HttpStatus.NOT_FOUND, request)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        return ResponseEntity.badRequest()
                .body(build(ex.getMessage(), HttpStatus.BAD_REQUEST, request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ){
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.setTimeStamp(LocalDateTime.now());
        response.setError(HttpStatus.BAD_REQUEST.name());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setPath(request.getDescription(false));
        response.setMessage(VALIDATIONS_FAILED);

        List<String> fields = ex.getBindingResult().getFieldErrors().stream()
                .distinct()
                .map(FieldError::getField)
                .collect(Collectors.toList());

        Map<String, List<String>> errors = new HashMap<>();

        for(String field : fields){
            List<String> list = new ArrayList<>();
            for(FieldError fieldError : ex.getFieldErrors(field)){
                list.add(fieldError.getDefaultMessage());
            }
            errors.put(field, list);
        }

        response.setErrors(errors);

        return ResponseEntity.badRequest().body(response);
    }


}
