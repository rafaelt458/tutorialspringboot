package com.laboratorio.springboot34.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

public class ManejoErrores {
    private ManejoErrores() {
    }

    public static Map<String, String> procesar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        /* for (FieldError error: result.getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        } */

        for (ObjectError error : result.getAllErrors()) {
            if (error instanceof FieldError) {
                errores.put(((FieldError)error).getField(), error.getDefaultMessage());
            } else {
                errores.put("Error en varios campos", error.getDefaultMessage());
            }
        }

        return errores;
    }
}