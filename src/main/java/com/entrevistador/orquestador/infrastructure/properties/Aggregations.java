package com.entrevistador.orquestador.infrastructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "aggregations")
public class Aggregations {

    private Map<String, AggregationComponent> components;

    public Map<String, AggregationComponent> getComponents() {
        return components;
    }

    public void setComponents(Map<String, AggregationComponent> components) {
        this.components = components;
    }

    public static class AggregationComponent {
        private String group;
        private String sort;
        private String match;
        private String limit;
        private String skip;
        private String unwind;
        private String lookup;
        private String project;

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getMatch() {
            return match;
        }

        public void setMatch(String match) {
            this.match = match;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getSkip() {
            return skip;
        }

        public void setSkip(String skip) {
            this.skip = skip;
        }

        public String getUnwind() {
            return unwind;
        }

        public void setUnwind(String unwind) {
            this.unwind = unwind;
        }

        public String getLookup() {
            return lookup;
        }

        public void setLookup(String lookup) {
            this.lookup = lookup;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }
    }
}
