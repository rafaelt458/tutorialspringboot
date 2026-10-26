package com.laboratorio.matricula_service.listener;

import com.laboratorio.framework.model.event.CupoCursoRechazadoEvent;
import com.laboratorio.framework.model.event.CupoCursoReservadoEvent;
import com.laboratorio.matricula_service.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class CupoCursoListener {
    private final MatriculaService matriculaService;

    @Bean
    public Consumer<CupoCursoReservadoEvent> cupoCursoReservadoEventConsumer() {
        return event -> {
            log.info("Se recibió evento de curso reservado: {}", event);
            this.matriculaService.confirmarMatricula(event);
        };
    }

    @Bean
    public Consumer<CupoCursoRechazadoEvent> cupoCursoRechazadoEventConsumer() {
        return event -> {
            log.info("Se recibió evento de curso rechazado: {}", event);
            this.matriculaService.cancelarMatricula(event);
        };
    }
}