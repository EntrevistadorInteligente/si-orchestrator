package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.PdfException;

public class ValidadorPdfService {

    public void ejecutar(byte[] sdf){

        if(sdf.length == 0){
            throw new PdfException();
        }

    }

}
