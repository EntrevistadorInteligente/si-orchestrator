package com.entrevistador.orquestador.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "const.orquestadorwebflux")
public class WebFluxProperties {
    private Map<String, WebfluxComponent> webFluxRoutes;
    @Getter
    @Setter
    public static class WebfluxComponent {
        private String urlBase;
        private Map<String, String> endpoints;
    }
}

