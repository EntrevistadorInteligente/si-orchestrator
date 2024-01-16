package com.entrevistador.orquestador.application.usescases;

import org.springframework.web.multipart.MultipartFile;

public interface SolicitudEntrevista {

    void generarSolicitudEntrevista(MultipartFile file);

    void generarOfertaLaboral(MultipartFile file);

}
