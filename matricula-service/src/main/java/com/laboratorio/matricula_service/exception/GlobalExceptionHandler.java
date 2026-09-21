package com.laboratorio.matricula_service.exception;

import com.laboratorio.matricula_service.model.dto.MatriculaError;
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
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MatriculaError> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                          HttpServletRequest request) {
        MatriculaError error = new MatriculaError(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<MatriculaError> handleIllegalStateException(IllegalStateException ex,
                                                                       HttpServletRequest request) {
        log.error("Error por conflicto: {}", ex.getMessage());
        MatriculaError error = new MatriculaError(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MatriculaError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                 HttpServletRequest request) {
        StringBuilder sb = new StringBuilder("Errores de validación:\n");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                sb.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("\n")
        );
        log.error("Errores de validación: {}", sb);
        MatriculaError error = new MatriculaError(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.BAD_REQUEST.value(),
                sb.toString(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MatriculaError> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("Argumento inválido: {}", ex.getMessage());
        MatriculaError error = new MatriculaError(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MatriculaError> handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("Error inesperado: {}", ex.getMessage());
        MatriculaError error = new MatriculaError(
                LocalDateTime.now(ZoneId.systemDefault()),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}