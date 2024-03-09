package com.entrevistador.orquestador.infrastructure.beanconfiguration;


import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.dominio.service.*;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesBeanConfiguration {

    @Bean
    public ActualizarEstadoProvesoEntrevistaService actualizarEstadoProvesoEntrevistaService(ProcesoEntrevistaRepository procesoEntrevistaRepository) {
        return new ActualizarEstadoProvesoEntrevistaService(procesoEntrevistaRepository);
    }

    @Bean
    public ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService() {
        return new ActualizarInformacionEntrevistaService();
    }

    @Bean
    public CrearEntrevistaAlternativaService crearEntrevistaAlternativaService(NotificarFrontEntrevistaFallidaService notificarFrontEntrevistaFallidaService,
                                                                               ProcesoEntrevistaRepository procesoEntrevistaRepository) {
        return new CrearEntrevistaAlternativaService(notificarFrontEntrevistaFallidaService, procesoEntrevistaRepository);
    }

    @Bean
    public CrearEntrevistaService crearEntrevistaService(EntrevistaDao entrevistaDao) {
        return new CrearEntrevistaService(entrevistaDao);
    }

    @Bean
    public NotificarFrontEntrevistaFallidaService notificarFrontEntrevistaFallidaService() {
        return new NotificarFrontEntrevistaFallidaService();
    }

    @Bean
    public SolicitudPreparacionEntrevistaService solicitudPreparacionEntrevistaService() {
        return new SolicitudPreparacionEntrevistaService();
    }

    @Bean
    public ValidadorEventosSimultaneosService validadorEventosSimultaneosService() {
        return new ValidadorEventosSimultaneosService();
    }

    @Bean
    public ValidadorPdfService validadorPdfService() {
        return new ValidadorPdfService();
    }


}
