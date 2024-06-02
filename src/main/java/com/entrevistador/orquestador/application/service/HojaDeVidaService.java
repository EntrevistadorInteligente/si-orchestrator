package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.application.usescases.HojaDeVida;
import com.entrevistador.orquestador.dominio.model.dto.HojaDeVidaDto;
import com.entrevistador.orquestador.dominio.model.dto.PerfilDto;
import com.entrevistador.orquestador.dominio.model.dto.SolicitudHojaDeVidaDto;
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
                SolicitudHojaDeVidaDto.builder()
                        .username(username)
                        .hojaDeVida(hojaDeVidaBytes)
                        .build());
    }

    @Override
    public Mono<PerfilDto> obtenerHojaDeVida(String username) {

        return hojaDeVidaDao.obtenerHojaDeVidaPorNombreUsuario(username)
                .map(hojaDeVidaDto -> PerfilDto.builder()
                        .uuid(hojaDeVidaDto.getUuid())
                        .nombre(hojaDeVidaDto.getNombre())
                        .perfil(hojaDeVidaDto.getPerfil())
                        .seniority(hojaDeVidaDto.getSeniority())
                        .tecnologiasPrincipales(hojaDeVidaDto.getTecnologiasPrincipales())
                        .experienciasLaborales(hojaDeVidaDto.getExperienciasLaborales())
                        .habilidadesTecnicas(hojaDeVidaDto.getHabilidadesTecnicas())
                        .certificaciones(hojaDeVidaDto.getCertificaciones())
                        .proyectos(hojaDeVidaDto.getProyectos())
                        .nivelIngles(hojaDeVidaDto.getNivelIngles())
                        .otrasHabilidades(hojaDeVidaDto.getOtrasHabilidades())
                        .build());
    }

    @Override
    public Mono<Void> guardarHojaDeVida(HojaDeVidaDto hojaDeVidaDto) {
        log.info("Recibiendo hoja de vida");
        return this.hojaDeVidaDao.guardarHojaDeVida(hojaDeVidaDto);
    }

    @Override
    public Mono<Void> actualizarDatosPerfil(String uuid, PerfilDto perfilDto) {
        return this.hojaDeVidaDao.actualizarDatosPerfil(uuid, perfilDto);
    }

}

