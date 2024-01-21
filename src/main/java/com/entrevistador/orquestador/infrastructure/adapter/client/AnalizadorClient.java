package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${feign.name.analizador}", url = "${feign.url.analizador}")
public interface AnalizadorClient {

    @PostMapping(value = "")
    void enviarHojaDeVida(PreparacionEntrevistaDto preparacionEntrevistaDto);

}
