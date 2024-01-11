package com.entrevistador.orquestador.infrastructure.adapter.jms.impl;

import com.entrevistador.orquestador.application.usescases.InterviewPreparation;

public class JmsListenerAdapterImpl {
    private final InterviewPreparation interviewPreparation;

    public JmsListenerAdapterImpl(InterviewPreparation interviewPreparation) {
        this.interviewPreparation = interviewPreparation;
    }

}
