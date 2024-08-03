package com.entrevistador.orquestador.application.service;

import com.entrevistador.orquestador.dominio.model.HojaDeVidaModel;
import com.entrevistador.orquestador.dominio.model.Perfil;
import com.entrevistador.orquestador.dominio.port.HojaDeVidaDao;
import com.entrevistador.orquestador.dominio.port.client.AnalizadorClient;
import com.entrevistador.orquestador.dominio.port.client.NotificacionesClient;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import com.entrevistador.orquestador.utils.CustomFilePart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HojaDeVidaServiceTest {
    @InjectMocks
    private HojaDeVidaService hojaDeVidaService;
    @Mock
    private ValidadorPdfService validadorPdfService;
    @Mock
    private HojaDeVidaDao hojaDeVidaDao;
    @Mock
    private NotificacionesClient notificacionesClient;
    @Mock
    private AnalizadorClient analizadorClient;

    @Test
    void testGenerarSolicitudHojaDeVida() {
        HojaDeVidaModel hojaDeVidaModel = HojaDeVidaModel.builder().build();
        byte[] bytes = "%PDF-1.4".getBytes();
        DefaultDataBuffer defaultDataBuffer = new DefaultDataBufferFactory().wrap(bytes);
        FilePart filePart = new CustomFilePart("file", defaultDataBuffer);

        when(this.validadorPdfService.ejecutar(any())).thenReturn(Mono.just(new byte[0]));
        when(this.analizadorClient.enviar(any())).thenReturn(Mono.just(hojaDeVidaModel));
        when(this.hojaDeVidaDao.guardarHojaDeVida(any())).thenReturn(Mono.empty());

        Mono<HojaDeVidaModel> publisher = this.hojaDeVidaService.generarSolicitudHojaDeVida(Mono.just(filePart), "any");

        StepVerifier
                .create(publisher)
                .expectNext(hojaDeVidaModel)
                .verifyComplete();

        verify(this.validadorPdfService, times(1)).ejecutar(any());
        verify(this.analizadorClient, times(1)).enviar(any());
        verify(this.hojaDeVidaDao, times(1)).guardarHojaDeVida(any());
    }

    @Test
    void testObtenerHojaDeVida() {
        HojaDeVidaModel hojaDeVidaModel = HojaDeVidaModel.builder().build();
        when(this.hojaDeVidaDao.obtenerHojaDeVidaPorNombreUsuario("any")).thenReturn(Mono.just(hojaDeVidaModel));

        Mono<HojaDeVidaModel> publisher = this.hojaDeVidaService.obtenerHojaDeVida("any");

        StepVerifier
                .create(publisher)
                .expectNext(hojaDeVidaModel)
                .verifyComplete();

        verify(this.hojaDeVidaDao, times(1)).obtenerHojaDeVidaPorNombreUsuario(anyString());
    }

    @Test
    void testGuardarHojaDeVida() {
        HojaDeVidaModel hojaDeVidaModel = HojaDeVidaModel.builder().username("any").build();
        when(this.hojaDeVidaDao.guardarHojaDeVida(any())).thenReturn(Mono.just(hojaDeVidaModel));
        when(this.notificacionesClient.enviar(anyString(), any())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.hojaDeVidaService.guardarHojaDeVida(hojaDeVidaModel);

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.hojaDeVidaDao, times(1)).guardarHojaDeVida(any());
        verify(this.notificacionesClient, times(1)).enviar(anyString(), any());
    }

    @Test
    void testActualizarDatosPerfil() {
        HojaDeVidaModel hojaDeVidaModel = HojaDeVidaModel.builder().build();
        when(this.hojaDeVidaDao.actualizarDatosPerfil(anyString(), any())).thenReturn(Mono.just(hojaDeVidaModel));

        Mono<HojaDeVidaModel> publisher = this.hojaDeVidaService.actualizarDatosPerfil("any", Perfil.builder().build());

        StepVerifier
                .create(publisher)
                .expectNext(hojaDeVidaModel)
                .verifyComplete();

        verify(this.hojaDeVidaDao, times(1)).actualizarDatosPerfil(anyString(), any());
    }
}