package com.entrevistador.orquestador.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "aggregations")
@Getter
@Setter
public class Aggregations {

    private Map<String, AggregationComponent> components;

    @Getter
    @Setter
    public static class AggregationComponent {
        private String group;
        private String sort;
        private String match;
        private String limit;
        private String skip;
        private String unwind;
        private String lookup;
        private String project;
    }
}
