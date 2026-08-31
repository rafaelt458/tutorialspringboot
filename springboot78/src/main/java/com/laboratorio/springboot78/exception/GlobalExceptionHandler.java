package com.laboratorio.springboot78.exception;

import com.laboratorio.springboot78.model.dto.EstudianteError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<EstudianteError> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        EstudianteError error = new EstudianteError(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<EstudianteError> handleIllegalStateException(IllegalStateException ex,
                                                                    HttpServletRequest request) {
        log.error("Ha ocurrido el siguiente conflicto: {}", ex.getMessage());
        EstudianteError error = new EstudianteError(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EstudianteError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                HttpServletRequest request) {
        StringBuilder sb = new StringBuilder("Errores de validación:\n");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                sb.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("\n")
        );
        log.error("Han ocurrido los siguientes errores de validación: {}", sb);
        EstudianteError error = new EstudianteError(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.BAD_REQUEST.value(),
                sb.toString(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EstudianteError> handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("Error inesperado: {}", ex.getMessage());
        EstudianteError error = new EstudianteError(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}