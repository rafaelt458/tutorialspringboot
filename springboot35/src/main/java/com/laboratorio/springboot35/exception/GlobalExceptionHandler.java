package com.laboratorio.springboot35.exception;

import com.laboratorio.springboot35.dto.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({DatabaseException.class})
    public ResponseEntity<ErrorInfo> technicalError(Exception e) {
        ErrorInfo error = new ErrorInfo(1, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({InvalidOperationException.class, ParameterException.class})
    public ResponseEntity<ErrorInfo> badRequest(Exception e) {
        ErrorInfo error = new ErrorInfo(
                e instanceof InvalidOperationException ? 2 : 3,
                e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorInfo> nofFound(Exception e) {
        ErrorInfo error = new ErrorInfo(4, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}