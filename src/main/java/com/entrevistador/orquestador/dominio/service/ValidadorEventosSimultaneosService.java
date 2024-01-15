package com.entrevistador.orquestador.dominio.service;


public class ValidadorEventosSimultaneosService {



    public boolean ejecutar(String idEntrevista){

        var ambosEventosHanSidoCompletados = true;
        if(ambosEventosHanSidoCompletados){

            return true;
        }

        return false;
    }

}
