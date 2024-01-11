package com.entrevistador.orquestador.infrastructure.rest;

import com.entrevistador.orquestador.application.usescases.InterviewRequest;

public class InterviewController {

    private final InterviewRequest interviewRequest;

    public InterviewController(InterviewRequest interviewRequest) {
        this.interviewRequest = interviewRequest;
    }
}
