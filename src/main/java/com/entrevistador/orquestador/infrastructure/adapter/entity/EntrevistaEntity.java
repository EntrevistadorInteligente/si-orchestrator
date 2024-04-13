package com.entrevistador.orquestador.infrastructure.adapter.entity;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Getter
@Builder
@Document(collection = "entrevista")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EntrevistaEntity {
    @Id
    private String uuid;
    private String idHojaDeVidaRag;
    private String idInformacionEmpresaRag;
    private String empresa;
    private String perfilEmpresa;
    private String seniorityEmpresa;
    private String pais;
    private List<String> preguntas;
    @Setter
    private boolean isHojaDeVidaValida;

}
