package com.laboratorio.framework.model.event;

import java.time.LocalDateTime;

public record CupoCursoReservadoEvent(
        Integer matriculaId,
        String transaccionId,
        LocalDateTime horaEvento
) {
}