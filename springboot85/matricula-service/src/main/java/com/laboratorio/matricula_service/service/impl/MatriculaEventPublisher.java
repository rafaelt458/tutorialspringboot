package com.laboratorio.matricula_service.service.impl;

import com.laboratorio.framework.model.event.MatriculaCreadaEvent;
import com.laboratorio.matricula_service.model.entity.Matricula;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatriculaEventPublisher {
    private static final String MATRICULA_CREADA_EXCHANGE = "matricula-creada-exchange";

    private final StreamBridge streamBridge;

    public void publishMatriculaCreadaEvent(Matricula matricula){
        MatriculaCreadaEvent event = new MatriculaCreadaEvent(
                matricula.getId(),
                matricula.getEstudianteId(),
                matricula.getCursoId(),
                matricula.getTransaccionId()
        );
        this.streamBridge.send(MATRICULA_CREADA_EXCHANGE, event);
        log.info("Evento de matrícula creada publicado: {}", event);
    }
}