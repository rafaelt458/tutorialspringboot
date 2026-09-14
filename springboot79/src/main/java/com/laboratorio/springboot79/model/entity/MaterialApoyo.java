package com.laboratorio.springboot79.model.entity;

import com.laboratorio.springboot79.model.dto.MaterialApoyoDto;
import com.laboratorio.springboot79.model.type.MaterialApoyoEnum;
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