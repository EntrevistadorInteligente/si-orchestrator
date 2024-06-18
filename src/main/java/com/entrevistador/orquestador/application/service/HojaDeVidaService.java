package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.Perfil;
import com.entrevistador.orquestador.dominio.model.SolicitudHojaDeVida;
import com.entrevistador.orquestador.dominio.model.dto.NotifiacionDto;
import com.entrevistador.orquestador.dominio.model.enums.TipoNotificacionEnum;
import com.entrevistador.orquestador.dominio.port.HojaDeVidaDao;
import com.entrevistador.orquestador.dominio.port.client.NotificacionesClient;
import com.entrevistador.orquestador.dominio.port.jms.JmsPublisherClient;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final NotificacionesClient notificacionesClient;

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
        log.info("Obtener hoja de Vida");
        return hojaDeVidaDao.obtenerHojaDeVidaPorNombreUsuario(username);
    }

    @Override
    public Mono<Void> guardarHojaDeVida(HojaDeVidaModel hojaDeVidaModel) {
        log.info("Recibiendo hoja de vida");
        return this.hojaDeVidaDao.guardarHojaDeVida(hojaDeVidaModel)
                 .flatMap(hojadevida-> generarNotificacion(
                        hojadevida.getUsername(),
                        TipoNotificacionEnum.HG,
                        "OK"
                ));
    }

    @Override
    public Mono<Void> actualizarDatosPerfil(String uuid, Perfil perfil) {
        return this.hojaDeVidaDao.actualizarDatosPerfil(uuid, perfil);
    }

    private Mono<Void> generarNotificacion(String userId,
                                           TipoNotificacionEnum notificacion,
                                           Object object) {
        return
                Mono.fromCallable(() -> new ObjectMapper().writeValueAsString(object))
                        .flatMap(jsonData ->
                                this.notificacionesClient.enviar(userId, NotifiacionDto.builder()
                                        .tipo(notificacion)
                                        .mensaje(jsonData)
                                        .build())
                                        .onErrorMap(JsonProcessingException.class, e -> {
                                            log.error("Error processing JSON", e);
                                            return new Exception("Error processing JSON");
                                        }));
    }
}

