package com.laboratorio.SpringBoot02.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoController {
    @GetMapping(value = "saludar/{nombre}/{apellido}")
    public String saludar(@PathVariable String nombre, @PathVariable String apellido) {
        return "Hola " + nombre + " " + apellido + "!";
    }
}