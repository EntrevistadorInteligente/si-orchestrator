package com.entrevistador.orquestador.infrastructure.beanconfiguration;

import com.entrevistador.orquestador.dominio.port.ProcesoEntrevistaDao;
import com.entrevistador.orquestador.dominio.service.ActualizarEstadoProcesoEntrevistaService;
import com.entrevistador.orquestador.dominio.service.ActualizarInformacionEntrevistaService;
import com.entrevistador.orquestador.dominio.service.CrearEntrevistaAlternativaService;
import com.entrevistador.orquestador.dominio.service.ValidadorEventosSimultaneosService;
import com.entrevistador.orquestador.dominio.service.ValidadacionEntrevistaPermitidaService;
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
    public CrearEntrevistaAlternativaService crearEntrevistaAlternativaService(ProcesoEntrevistaRepository procesoEntrevistaRepository) {
        return new CrearEntrevistaAlternativaService(procesoEntrevistaRepository);
    }

    @Bean
    public ValidadorEventosSimultaneosService validadorEventosSimultaneosService(EntrevistaDao entrevistaDao) {
        return new ValidadorEventosSimultaneosService(entrevistaDao);
    }

    @Bean
    public ValidadorPdfService validadorPdfService() {
        return new ValidadorPdfService();
    }

    @Bean
    public ValidadacionEntrevistaPermitidaService validadorIntervaloEntrevistaService(EntrevistaDao entrevistaDao) {
        return new ValidadacionEntrevistaPermitidaService(entrevistaDao);
    }


}
