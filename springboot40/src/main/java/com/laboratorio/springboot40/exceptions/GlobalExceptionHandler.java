package com.laboratorio.springboot40.exceptions;

import com.laboratorio.dto.Error;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.*;

@ControllerAdvice
@RequiredArgsConstructor @Slf4j
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler({DatabaseException.class})
    public ResponseEntity<Error> technicalError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error(1, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> validationError(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder errores = new StringBuilder();

        for (FieldError error : result.getFieldErrors()) {
            String errorMessage = error.getDefaultMessage();

            String[] codes = error.getCodes();
            if (codes != null && codes.length > 0) {
                log.info("Claves buscadas para {}: {}", error.getField(), Arrays.toString(codes));
                try {
                    errorMessage = messageSource.getMessage(codes[0], error.getArguments(), LocaleContextHolder.getLocale());
                } catch (Exception ex) {
                    log.warn("No se pudo recuperar el mensaje personalizado para la clave: {}", codes[0]);
                    errorMessage = error.getDefaultMessage();
                }
            }

            if (!errores.toString().isEmpty()) {
                errores.append(" - ");
            }
            errores.append(error.getField());
            errores.append(": ");
            errores.append(errorMessage);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(2, errores.toString()));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Error> datetimeParseError(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(3, "El formato de la fecha suministrada es incorrecto"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> missingParemeterError(MissingServletRequestParameterException e) {
        String message = "El parámetro " + e.getParameterName() + " es obligatorio";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(4, message));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> parameterConstraintError(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder errores = new StringBuilder();

        for (ConstraintViolation<?> violation : violations) {
            if (!errores.toString().isEmpty()) {
                errores.append(" - ");
            }

            String propertyPath = violation.getPropertyPath().toString();
            log.info("Property path: {}", propertyPath);
            String constraintName = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
            String messageKey = constraintName + "." + propertyPath;

            String errorMessage;
            try {
                String messageTemplate = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
                Map<String, Object> attrs = new HashMap<>(violation.getConstraintDescriptor().getAttributes());

                for (Map.Entry<String, Object> entry : attrs.entrySet()) {
                    String placeholder = "{" + entry.getKey() + "}";
                    messageTemplate = messageTemplate.replace(placeholder, String.valueOf(entry.getValue()));
                }
                errorMessage = messageTemplate;
            } catch (Exception ex) {
                log.warn("No se pudo recuperar el mensaje personalizado para la clave: {}", messageKey);
                errorMessage = violation.getMessage();
            }
            errores.append(errorMessage);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(5, errores.toString()));
    }
}