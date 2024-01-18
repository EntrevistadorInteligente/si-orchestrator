package com.entrevistador.orquestador.application.usescases;

import com.entrevistador.orquestador.dominio.model.dto.FormularioDto;

public interface SolicitudEntrevista {

    void generarSolicitudEntrevista(byte[] hojaDeVida, FormularioDto formulario);

}
