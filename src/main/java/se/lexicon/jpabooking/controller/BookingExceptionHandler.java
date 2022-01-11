package se.lexicon.jpabooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import se.lexicon.jpabooking.exception.AppResourceNotFoundException;
import se.lexicon.jpabooking.model.dto.view.ExceptionResponseDTO;

import java.time.LocalDateTime;

@ControllerAdvice
public class BookingExceptionHandler {

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

}
