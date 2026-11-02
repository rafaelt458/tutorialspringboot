package com.laboratorio.curso_service.service.impl;

import com.laboratorio.framework.model.event.CupoCursoRechazadoEvent;
import com.laboratorio.framework.model.event.CupoCursoReservadoEvent;
import com.laboratorio.framework.model.event.MatriculaCreadaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Slf4j
public class CursoEventPublisher {
    private static final String CUPO_CURSO_RESERVADO_EXCHANGE = "cupo-curso-reservado-exchange";
    private static final String CUPO_CURSO_RECHAZADO_EXCHANGE = "cupo-curso-rechazado-exchange";

    private final StreamBridge streamBridge;

    public void publishCupoCursoReservadoEvent(MatriculaCreadaEvent event) {
        CupoCursoReservadoEvent cupoCursoReservadoEvent = new CupoCursoReservadoEvent(
                event.matriculaId(),
                event.transaccionId(),
                LocalDateTime.now(ZoneId.systemDefault())
        );
        this.streamBridge.send(CUPO_CURSO_RESERVADO_EXCHANGE, cupoCursoReservadoEvent);
        log.info("Evento de reserva de cupo de curso publicado: {}", cupoCursoReservadoEvent);
    }

    public void publishCupoCursoRechazadoEvent(MatriculaCreadaEvent event, String motivo) {
        CupoCursoRechazadoEvent cupoCursoRechazadoEvent = new CupoCursoRechazadoEvent(
                event.matriculaId(),
                event.transaccionId(),
                LocalDateTime.now(ZoneId.systemDefault()),
                motivo
        );
        this.streamBridge.send(CUPO_CURSO_RECHAZADO_EXCHANGE, cupoCursoRechazadoEvent);
        log.info("Evento de rechazo de cupo de curso publicado: {}", cupoCursoRechazadoEvent);
    }
}