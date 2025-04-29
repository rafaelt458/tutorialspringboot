package com.laboratorio.springboot31.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ManejoErrores {
    private ManejoErrores() {
    }

    public static Map<String, String> procesar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error: result.getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return errores;
    }
}