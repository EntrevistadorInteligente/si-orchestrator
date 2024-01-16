package com.entrevistador.orquestador.dominio.service;

import com.entrevistador.orquestador.dominio.excepciones.PdfException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ValidadorPdfService {

    public byte[] ejecutar(MultipartFile file) {
        byte[] bytes;

        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new PdfException();
        }

        if (bytes.length == 0) {
            throw new PdfException();
        }

        return bytes;

    }

}
