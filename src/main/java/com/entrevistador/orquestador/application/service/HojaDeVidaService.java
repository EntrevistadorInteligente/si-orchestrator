package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudHojaDeVidaDto;
import com.entrevistador.orquestador.dominio.port.HojaDeVidaDao;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class HojaDeVidaService implements HojaDeVida {

    private final AnalizadorClient analizadorClient;
    private final ValidadorPdfService validadorPdfService;
    private final HojaDeVidaDao hojaDeVidaDao;

    @Override
    public Mono<Void> generarSolicitudHojaDeVida(Mono<FilePart> file, String username) {
        return file.flatMap(this.validadorPdfService::ejecutar)
                .flatMap(bytes -> this.procesarHojaDeVida(bytes, username));
    }

    private Mono<Void> procesarHojaDeVida(byte[] hojaDeVidaBytes, String username) {
        return this.analizadorClient.enviarHojaDeVida(
                SolicitudHojaDeVidaDto.builder()
                        .username(username)
                        .hojaDeVida(hojaDeVidaBytes)
                        .build());
    }

    @Override
    public Mono<HojaDeVidaDto> obtenerHojaDeVida(String username) {
        return hojaDeVidaDao.obtenerHojaDeVida(username);
    }

    @Override
    public Mono<Void> guardarHojaDeVida(HojaDeVidaDto hojaDeVidaDto) {
        return hojaDeVidaDao.guardarHojaDeVida(hojaDeVidaDto);
    }

}

