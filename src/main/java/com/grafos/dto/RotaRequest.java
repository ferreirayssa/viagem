package com.grafos.dto;

import java.util.List;

public class RotaRequest {
    private Coordenada origem;
    private Coordenada destino;
    private List<Coordenada> percurso;

    public Coordenada getOrigem() {
        return origem;
    }

    public void setOrigem(Coordenada origem) {
        this.origem = origem;
    }

    public Coordenada getDestino() {
        return destino;
    }

    public void setDestino(Coordenada destino) {
        this.destino = destino;
    }

    public List<Coordenada> getPercurso() {
        return percurso;
    }

    public void setPercurso(List<Coordenada> percurso) {
        this.percurso = percurso;
    }
}
