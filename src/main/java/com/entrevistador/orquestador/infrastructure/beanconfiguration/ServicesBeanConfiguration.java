package com.entrevistador.orquestador.infrastructure.beanconfiguration;


import com.entrevistador.orquestador.application.service.HojaDeVidaService;
import com.entrevistador.orquestador.dominio.port.HojaDeVidaDao;
import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProcesoEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ActualizarInformacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaAlternativaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaService;
import com.entrevistador.orquestador.dominio.service.NotificarFrontEntrevistaFallidaService;
import com.entrevistador.orquestador.dominio.service.SolicitudPreparacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import com.entrevistador.orquestador.dominio.service.ValidadorPdfService;
import com.entrevistador.orquestador.dominio.port.EntrevistaDao;
import com.entrevistador.orquestador.infrastructure.adapter.repository.ProcesoEntrevistaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesBeanConfiguration {

    @Bean
    public ActualizarEstadoProcesoEntrevistaService actualizarEstadoProcesoEntrevistaService(ProcesoEntrevistaDao procesoEntrevistaDao) {
        return new ActualizarEstadoProcesoEntrevistaService(procesoEntrevistaDao);
    }

    @Bean
    public ActualizarInformacionEntrevistaService actualizarInformacionEntrevistaService(EntrevistaDao entrevistaDao) {
        return new ActualizarInformacionEntrevistaService(entrevistaDao);
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
    public ValidadorEventosSimultaneosService validadorEventosSimultaneosService(EntrevistaDao entrevistaDao) {
        return new ValidadorEventosSimultaneosService(entrevistaDao);
    }

    @Bean
    public ValidadorPdfService validadorPdfService() {
        return new ValidadorPdfService();
    }


}
