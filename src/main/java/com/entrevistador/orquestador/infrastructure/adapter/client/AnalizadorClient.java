package com.entrevistador.orquestador.infrastructure.adapter.client;

import com.entrevistador.orquestador.dominio.model.dto.PreparacionEntrevistaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${feign.name.analizador}", url = "${feign.url.analizador}")
public interface AnalizadorClient {

    @RequestMapping(method = RequestMethod.POST, value = "")
    void enviarHojaDeVida(PreparacionEntrevistaDto preparacionEntrevistaDto);
}
