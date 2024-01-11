package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.InterviewPreparation;
import com.entrevistador.orquestador.domain.port.InterviewDao;
import com.entrevistador.orquestador.domain.port.InterviewProccesDao;
import com.entrevistador.orquestador.domain.service.CreateInterviewService;
import com.entrevistador.orquestador.domain.service.CreatePreparationRequestService;
import com.entrevistador.orquestador.domain.service.SendAlternativeInterviewService;
import com.entrevistador.orquestador.infrastructure.adapter.client.AnalizadorClient;

public class InterviewPreparationService implements InterviewPreparation {

    private final InterviewDao interviewDao;
    private final InterviewProccesDao interviewProccesDao;
    private final AnalizadorClient analizadorClient;
    private final CreatePreparationRequestService createPreparationRequestService;
    private final CreateInterviewService createInterviewService;
    private final SendAlternativeInterviewService sendAlternativeInterviewService;

    public InterviewPreparationService(InterviewDao interviewDao, InterviewProccesDao interviewProccesDao, AnalizadorClient analizadorClient, CreatePreparationRequestService createPreparationRequestService, CreateInterviewService createInterviewService, SendAlternativeInterviewService sendAlternativeInterviewService) {
        this.interviewDao = interviewDao;
        this.interviewProccesDao = interviewProccesDao;
        this.analizadorClient = analizadorClient;
        this.createPreparationRequestService = createPreparationRequestService;
        this.createInterviewService = createInterviewService;
        this.sendAlternativeInterviewService = sendAlternativeInterviewService;
    }

}

