package com.entrevistador.orquestador.dominio.model.dto;

public class SoloPerfilImp implements SoloPerfilDto{
    private String perfilEmpresa;

    public SoloPerfilImp(String perfilEmpresa) {
        this.perfilEmpresa = perfilEmpresa;
    }

    public SoloPerfilImp() {

    }

    @Override
    public String getPerfilEmpresa() {
        return this.perfilEmpresa;
    }
}
