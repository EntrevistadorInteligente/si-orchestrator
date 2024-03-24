package com.entrevistador.orquestador.dominio.service;


import com.entrevistador.orquestador.dominio.model.Entrevista;

public class ValidadorEventosSimultaneosService {

    public boolean ejecutar(String idEntrevista, String campoDeEventoAValidar, String seActualizo){
        var ambosEventosHanSidoCompletados = false;
        if (seActualizo.equals("hojadevida")){
            if(campoDeEventoAValidar.equals(null)){
                ambosEventosHanSidoCompletados = true;
            }
        }
        if (seActualizo.equals("infoempresa")){
            if(campoDeEventoAValidar.equals(null)){
                ambosEventosHanSidoCompletados = true;
            }
        }
        return ambosEventosHanSidoCompletados;

    }

}
