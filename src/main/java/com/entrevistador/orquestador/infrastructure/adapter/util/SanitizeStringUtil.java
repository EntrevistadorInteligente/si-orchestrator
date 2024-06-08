package com.entrevistador.orquestador.infrastructure.adapter.util;

import com.entrevistador.orquestador.dominio.excepciones.SanitizeStringUtilException;
import org.owasp.encoder.Encode;

public final class SanitizeStringUtil {

    private SanitizeStringUtil() {
    }

    public static String sanitize(final String str) {
        try {
            return Encode.forJava(str);
        } catch (Exception e) {
            throw new SanitizeStringUtilException("Error al sanitizar el string: " + e.getMessage());
        }
    }
}
