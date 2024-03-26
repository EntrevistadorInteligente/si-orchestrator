package com.entrevistador.orquestador.infrastructure.beanconfiguration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class ReactiveMongoConfiguration {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://root:secret@localhost:27017");
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
        return new ReactiveMongoTemplate(mongoClient, "orquestador");
    }

}
