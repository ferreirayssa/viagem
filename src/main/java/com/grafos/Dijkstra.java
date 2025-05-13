package com.grafos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Dijkstra {
    public static Map<Vertice, Integer> calcularDistancias(Grafo grafo, Vertice origem, Map<Vertice, Vertice> predecessores) {
        Map<Vertice, Integer> distancias = new HashMap<>();
        PriorityQueue<Vertice> fila = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

        for (Vertice v : grafo.getAdjacencias().keySet()) {
            distancias.put(v, Integer.MAX_VALUE);
        }
        distancias.put(origem, 0);
        fila.add(origem);

        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();
            List<Aresta> vizinhos = grafo.getAdjacencias().get(atual);
            if (vizinhos == null) continue;
            for (Aresta a : vizinhos) {
                int novaDist = distancias.get(atual) + a.distancia;
                if (novaDist < distancias.get(a.destino)) {
                    distancias.put(a.destino, novaDist);
                    predecessores.put(a.destino, atual);
                    fila.add(a.destino);
                }
            }
        }
        return distancias;
    }

    public static List<Vertice> reconstruirCaminho(Vertice destino, Map<Vertice, Vertice> predecessores) {
        List<Vertice> caminho = new ArrayList<>();
        for (Vertice at = destino; at != null; at = predecessores.get(at)) {
            caminho.add(at);
        }
        Collections.reverse(caminho);
        return caminho;
    }
}
