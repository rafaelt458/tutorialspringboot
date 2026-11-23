package com.laboratorio.curso_service.listener;

import com.laboratorio.curso_service.service.CursoService;
import com.laboratorio.curso_service.service.impl.CursoEventPublisher;
import com.laboratorio.framework.model.event.MatriculaCreadaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatriculaCreadaListener {
    private final CursoService cursoService;
    private final CursoEventPublisher cursoEventPublisher;

    @Bean
    public Consumer<MatriculaCreadaEvent> matriculaCreadaEventConsumer() {
        return event -> {
            log.info("Se ha recibido un evento de matrícula creada: {}", event);
            this.handleMatriculaCreadaEvent(event);
        };
    }

    public void handleMatriculaCreadaEvent(MatriculaCreadaEvent event) {
        try {
            this.cursoService.disminuirCuposCurso(event.cursoId());
            this.cursoEventPublisher.publishCupoCursoReservadoEvent(event);
        } catch (Exception e) {
            log.error("Ha habido un problema reservando el asiento para el curso {}: {}", event.cursoId(), e.getMessage());
            this.cursoEventPublisher.publishCupoCursoRechazadoEvent(event, e.getMessage());
        }
    }
}