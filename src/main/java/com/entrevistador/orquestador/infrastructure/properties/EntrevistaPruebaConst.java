package com.entrevistador.orquestador.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "const.entrevistaprueba")
public class EntrevistaPruebaConst {
    private int limitperfiles;
}
