package com.grafos.service;

import com.grafos.dto.Coordenada;
import com.grafos.dto.RotaRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RotaService {

    public List<Coordenada> calcular(RotaRequest request) {
        // Aqui você pode integrar com MenorCaminhoCapitais e sua lógica de grafo
        // Por enquanto, simulamos um caminho direto com origem, percurso e destino

        List<Coordenada> caminho = new ArrayList<>();
        caminho.add(request.getOrigem());
        if (request.getPercurso() != null) {
            caminho.addAll(request.getPercurso());
        }
        caminho.add(request.getDestino());

        return caminho;
    }
}
