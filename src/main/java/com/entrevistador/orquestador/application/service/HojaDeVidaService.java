package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.Perfil;
import com.entrevistador.orquestador.dominio.model.SolicitudHojaDeVida;
import com.entrevistador.orquestador.dominio.port.HojaDeVidaDao;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
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

    private final JmsPublisherClient jmsPublisherClient;
    private final ValidadorPdfService validadorPdfService;
    private final HojaDeVidaDao hojaDeVidaDao;

    @Override
    public Mono<Void> generarSolicitudHojaDeVida(Mono<FilePart> file, String username) {
        return file.flatMap(this.validadorPdfService::ejecutar)
                .flatMap(bytes -> this.procesarHojaDeVida(bytes, username));
    }

    private Mono<Void> procesarHojaDeVida(byte[] hojaDeVidaBytes, String username) {
        return this.jmsPublisherClient.enviarHojaDeVida(
                SolicitudHojaDeVida.builder()
                        .username(username)
                        .hojaDeVida(hojaDeVidaBytes)
                        .build());
    }

    @Override
    public Mono<HojaDeVidaModel> obtenerHojaDeVida(String username) {
        return hojaDeVidaDao.obtenerHojaDeVidaPorNombreUsuario(username);
    }

    @Override
    public Mono<Void> guardarHojaDeVida(HojaDeVidaModel hojaDeVidaModel) {
        log.info("Recibiendo hoja de vida");
        return this.hojaDeVidaDao.guardarHojaDeVida(hojaDeVidaModel);
    }

    @Override
    public Mono<Void> actualizarDatosPerfil(String uuid, Perfil perfil) {
        return this.hojaDeVidaDao.actualizarDatosPerfil(uuid, perfil);
    }
}

