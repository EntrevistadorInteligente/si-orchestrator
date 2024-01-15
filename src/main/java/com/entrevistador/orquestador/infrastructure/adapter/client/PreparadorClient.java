package com.entrevistador.orquestador.infrastructure.adapter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "${feign.name.preparador}", url = "${feign.url.preparador}")
public interface PreparadorClient {

    @RequestMapping(method = RequestMethod.POST, value = "")
    void generarEntrevista();

}
