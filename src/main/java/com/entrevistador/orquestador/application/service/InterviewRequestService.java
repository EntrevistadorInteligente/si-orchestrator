package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.InterviewRequest;
import com.entrevistador.orquestador.infrastructure.adapter.client.AnalizadorClient;
import com.entrevistador.orquestador.domain.port.JmsPublisherAdapter;
import com.entrevistador.orquestador.infrastructure.adapter.repository.InterviewProccesRepository;

public class InterviewRequestService implements InterviewRequest {

    private final JmsPublisherAdapter jmsPublisherAdapter;
    private final InterviewProccesRepository interviewProccesRepository;
    private final AnalizadorClient analizadorClient;

    public InterviewRequestService(JmsPublisherAdapter jmsPublisherAdapter, InterviewProccesRepository interviewProccesRepository, AnalizadorClient analizadorClient) {
        this.jmsPublisherAdapter = jmsPublisherAdapter;
        this.interviewProccesRepository = interviewProccesRepository;
        this.analizadorClient = analizadorClient;
    }

}

