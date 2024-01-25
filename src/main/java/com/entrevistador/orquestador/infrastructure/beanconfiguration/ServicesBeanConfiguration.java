package com.entrevistador.orquestador.infrastructure.beanconfiguration;


import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProcesoEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ActualizarInformacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaAlternativaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.NotificarFrontEntrevistaFallidaService;
import com.entrevistador.orquestador.dominio.service.SolicitudPreparacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesBeanConfiguration {

    @Bean
    public ActualizarEstadoProcesoEntrevistaService actualizarEstadoProvesoEntrevistaService(){
        return new ActualizarEstadoProcesoEntrevistaService();
    }
    @Bean
    public ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService(){
        return new ActualizarInformacionEntrevistaService();
    }

    @Bean
    public CrearEntrevistaAlternativaService crearEntrevistaAlternativaService(NotificarFrontEntrevistaFallidaService notificarFrontEntrevistaFallidaService){
        return new CrearEntrevistaAlternativaService(notificarFrontEntrevistaFallidaService);
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
