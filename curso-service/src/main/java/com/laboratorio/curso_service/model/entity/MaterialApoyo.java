package com.laboratorio.curso_service.model.entity;

import com.laboratorio.curso_service.model.dto.MaterialApoyoDto;
import com.laboratorio.curso_service.model.type.MaterialApoyoEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MaterialApoyo {
    private MaterialApoyoEnum tipo;
    private String titulo;
    private String descripcion;
    private String urlRecurso;

    public MaterialApoyo(MaterialApoyoDto dto) {
        this.tipo = MaterialApoyoEnum.from(dto.getTipo());
        this.titulo = dto.getTitulo();
        this.descripcion = dto.getDescripcion();
        this.urlRecurso = dto.getUrlRecurso();
    }
}