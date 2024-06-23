package com.entrevistador.orquestador.dominio.model;

import com.entrevistador.orquestador.dominio.model.enums.TipoNotificacionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notificacion {
    private TipoNotificacionEnum tipo;
    private String mensaje;

}
