package com.grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Grafo {
    Map<Vertice, List<Aresta>> adjacencias = new HashMap<>();

    public void adicionarVertice(Vertice v) {
        adjacencias.putIfAbsent(v, new ArrayList<>());
    }

    public void adicionarAresta(String origem, String destino, int distancia) {
        Vertice vOrigem = new Vertice(origem);
        Vertice vDestino = new Vertice(destino);
        adicionarVertice(vOrigem);
        adicionarVertice(vDestino);
        adjacencias.get(vOrigem).add(new Aresta(vDestino, distancia));
        adjacencias.get(vDestino).add(new Aresta(vOrigem, distancia));
    }

    public Map<Vertice, List<Aresta>> getAdjacencias() {
        return adjacencias;
    }
}
