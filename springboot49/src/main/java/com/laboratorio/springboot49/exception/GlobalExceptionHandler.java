package com.laboratorio.springboot49.exception;

import com.laboratorio.springboot49.dto.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorInfo> validationError(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        BindingResult result = e.getBindingResult();

        for (ObjectError error : result.getAllErrors()) {
            if (error instanceof FieldError) {
                errors.put(((FieldError)error).getField(), error.getDefaultMessage());
            } else {
                errors.put("Incoherencia en datos", error.getDefaultMessage());
            }
        }

        ErrorInfo errorInfo = new ErrorInfo(5, errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorInfo);
    }
}