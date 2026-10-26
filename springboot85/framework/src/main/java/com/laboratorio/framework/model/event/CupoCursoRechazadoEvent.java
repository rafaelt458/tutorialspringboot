package com.laboratorio.framework.model.event;

import java.time.LocalDateTime;

public record CupoCursoRechazadoEvent(
        Integer matriculaId,
        String transaccionId,
        LocalDateTime horaEvento,
        String motivo
) {
}