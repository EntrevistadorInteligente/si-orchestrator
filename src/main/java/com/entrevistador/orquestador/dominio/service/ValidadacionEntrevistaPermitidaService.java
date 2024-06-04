package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.model.Entrevista;
import com.entrevistador.orquestador.infrastructure.adapter.dto.EstadoEntrevistaDto;
import com.entrevistador.orquestador.dominio.model.enums.CodigoErrorEnum;
import com.entrevistador.orquestador.dominio.model.enums.EstadoEntrevistaEnum;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.exceptions.EntrevistaProcesoException;
import com.entrevistador.orquestador.infrastructure.adapter.exceptions.LimiteEntrevistaException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RequiredArgsConstructor
public class ValidadacionEntrevistaPermitidaService {
    public static final int HORAS_INHABILITADAS = 24;
    private static final int CANTIDAD_ENTREVISTAS = 3;
    private final EntrevistaDao entrevistaDao;

    public Mono<Void> ejecutar(String username) {
        return verificarEstadoEntrevistaEnProceso(username)
                .then(entrevistaDao.consultarUltimasEntrevistas(username)
                        .collectList()
                        .flatMap(entrevistas -> {
                            if (entrevistas.size() < CANTIDAD_ENTREVISTAS) {
                                return Mono.empty();
                            }
                            Duration diferencia = obtenerDiferenciaPrimerEntrevista(entrevistas);
                            if (diferencia.toHours() >= HORAS_INHABILITADAS) {
                                return Mono.empty();
                            }
                            return notificarEntrevistaInvalida(diferencia);
                        }));
    }

    private Duration obtenerDiferenciaPrimerEntrevista(List<Entrevista> entrevistas) {
        LocalDateTime fechaMasAntigua = entrevistas.get(CANTIDAD_ENTREVISTAS - 1).getFechaCreacion()
                .atOffset(ZoneOffset.UTC).toLocalDateTime();

        LocalDateTime ahora = LocalDateTime.now(ZoneOffset.UTC);
        return Duration.between(fechaMasAntigua, ahora);
    }

    private Mono<Void> notificarEntrevistaInvalida(Duration diferencia) {
        long horasRestantes = HORAS_INHABILITADAS - diferencia.toHours();
        long minutosRestantes = 60L - diferencia.toMinutesPart();
        long segundosRestantes = 60L - diferencia.toSecondsPart();

        String mensaje = String.format(
                " %02d horas, %02d minutos y %02d segundos",
                horasRestantes, minutosRestantes, segundosRestantes
        );

        return Mono.error(new LimiteEntrevistaException(CodigoErrorEnum.ENTREVISTA_LIMITE_EXCEDIDO, mensaje));
    }

    private Mono<Void> verificarEstadoEntrevistaEnProceso(String username) {
        return obtenerEstadoEntrevistaPorUsuario(username)
                .flatMap(estadoEntrevistaDto -> {
                    if (!EstadoEntrevistaEnum.FN.name().equals(estadoEntrevistaDto.getEstadoEntrevista())) {
                        return Mono.error(new EntrevistaProcesoException(CodigoErrorEnum.ENTREVISTA_EN_PROCESO));
                    }
                    return Mono.empty();
                });
    }

    private Mono<EstadoEntrevistaDto> obtenerEstadoEntrevistaPorUsuario(String username) {
        return entrevistaDao.obtenerEstadoEntrevistaPorUsuario(username)
                .map(entrevista -> new EstadoEntrevistaDto(entrevista.getIdEntrevista(), entrevista.getEstadoEntrevista()))
                .switchIfEmpty(Mono.empty());
    }
}