package com.entrevistador.orquestador.infrastructure.adapter.client;

public interface AnalizadorClient {

    void enviarHojaDeVida(String idEntrevista, String eventoEntrevistaId, byte[] hojadevida);
}
