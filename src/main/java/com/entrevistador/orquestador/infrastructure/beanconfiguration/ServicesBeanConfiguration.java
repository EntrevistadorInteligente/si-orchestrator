package com.entrevistador.orquestador.infrastructure.beanconfiguration;


import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProvesoEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ActualizarInformacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaAlternativaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.NotificarFrontEntrevistaFallidaService;
import com.entrevistador.orquestador.dominio.service.SolicitudPreparacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServicesBeanConfiguration {

    private final ProcesoEntrevistaRepository procesoEntrevistaRepository;

    @Bean
    public ActualizarEstadoProvesoEntrevistaService actualizarEstadoProvesoEntrevistaService(){
        return new ActualizarEstadoProvesoEntrevistaService(procesoEntrevistaRepository);
    }
    @Bean
    public ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService(){
        return new ActualizarInformacionEntrevistaService();
    }

    @Bean
    public CrearEntrevistaAlternativaService crearEntrevistaAlternativaService(NotificarFrontEntrevistaFallidaService notificarFrontEntrevistaFallidaService){
        return new CrearEntrevistaAlternativaService(notificarFrontEntrevistaFallidaService, procesoEntrevistaRepository);
    }

    @Bean
    public CrearEntrevistaService crearEntrevistaService(){
        return new CrearEntrevistaService();
    }

    @Bean
    public NotificarFrontEntrevistaFallidaService notificarFrontEntrevistaFallidaService(){
        return new NotificarFrontEntrevistaFallidaService();
    }

    @Bean
    public SolicitudPreparacionEntrevistaService solicitudPreparacionEntrevistaService(){
        return new SolicitudPreparacionEntrevistaService();
    }

    @Bean
    public ValidadorEventosSimultaneosService validadorEventosSimultaneosService(){
        return new ValidadorEventosSimultaneosService();
    }

    @Bean
    public ValidadorPdfService validadorPdfService(){
        return new ValidadorPdfService();
    }

}
