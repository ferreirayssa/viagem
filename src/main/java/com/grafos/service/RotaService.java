package com.grafos.service;

import com.grafos.*;
import java.util.*;
import java.util.stream.Collectors;

public class RotaService {

    private Grafo grafo;

    // Mapa de capital para coordenadas (latitude, longitude)
    private static final Map<String, double[]> coordenadasCapitais = Map.ofEntries(
        Map.entry("Rio Branco", new double[]{-9.97499, -67.8243}),
        Map.entry("Maceió", new double[]{-9.66599, -35.735}),
        Map.entry("Macapá", new double[]{0.034934, -51.0694}),
        Map.entry("Manaus", new double[]{-3.10194, -60.025}),
        Map.entry("Salvador", new double[]{-12.9718, -38.5011}),
        Map.entry("Fortaleza", new double[]{-3.71722, -38.5434}),
        Map.entry("Brasília", new double[]{-15.7797, -47.9297}),
        Map.entry("Vitória", new double[]{-20.3155, -40.3128}),
        Map.entry("Goiânia", new double[]{-16.6864, -49.2643}),
        Map.entry("São Luís", new double[]{-2.52972, -44.3028}),
        Map.entry("Cuiabá", new double[]{-15.601, -56.0975}),
        Map.entry("Campo Grande", new double[]{-20.4428, -54.6469}),
        Map.entry("Belo Horizonte", new double[]{-19.9167, -43.9345}),
        Map.entry("Belém", new double[]{-1.455, -48.502}),
        Map.entry("João Pessoa", new double[]{-7.115, -34.8631}),
        Map.entry("Curitiba", new double[]{-25.4278, -49.2731}),
        Map.entry("Recife", new double[]{-8.05389, -34.8811}),
        Map.entry("Teresina", new double[]{-5.08917, -42.8019}),
        Map.entry("Rio de Janeiro", new double[]{-22.9068, -43.1729}),
        Map.entry("Natal", new double[]{-5.7945, -35.211}),
        Map.entry("Porto Alegre", new double[]{-30.0277, -51.2287}),
        Map.entry("Porto Velho", new double[]{-8.7612, -63.9005}),
        Map.entry("Boa Vista", new double[]{2.8196, -60.6733}),
        Map.entry("Florianópolis", new double[]{-27.5954, -48.548}),
        Map.entry("São Paulo", new double[]{-23.5505, -46.6333}),
        Map.entry("Aracaju", new double[]{-10.9472, -37.0731}),
        Map.entry("Palmas", new double[]{-10.2491, -48.3242})
    );

    public RotaService() {
        grafo = new Grafo();
        CSVLoader.carregarGrafoDeCSV(grafo, "conexoes_capitais.csv");
    }

    /**
     * Calcula o menor caminho entre origem e destino (nomes das capitais).
     * Retorna um Resultado com lista do caminho e distância total.
     */
    public Resultado calcularRota(String origemStr, String destinoStr) {
        String origem = CapitalMapper.capitalPadronizada(origemStr);
        String destino = CapitalMapper.capitalPadronizada(destinoStr);

        Vertice vOrigem = new Vertice(origem);
        Vertice vDestino = new Vertice(destino);

        if (!grafo.getAdjacencias().containsKey(vOrigem)) {
            throw new IllegalArgumentException("Capital de origem inválida: " + origem);
        }
        if (!grafo.getAdjacencias().containsKey(vDestino)) {
            throw new IllegalArgumentException("Capital de destino inválida: " + destino);
        }

        Map<Vertice, Vertice> predecessores = new HashMap<>();
        Map<Vertice, Integer> distancias = Dijkstra.calcularDistancias(grafo, vOrigem, predecessores);
        List<Vertice> caminho = Dijkstra.reconstruirCaminho(vDestino, predecessores);

        int distanciaTotal = distancias.getOrDefault(vDestino, Integer.MAX_VALUE);
        if (distanciaTotal == Integer.MAX_VALUE || caminho.isEmpty()) {
            return new Resultado(Collections.emptyList(), -1);
        }

        return new Resultado(caminho, distanciaTotal);
    }

    public double obterLatitude(String capital) {
        double[] coord = coordenadasCapitais.get(capital);
        if (coord != null) {
            return coord[0];
        }
        return 0;
    }

    public double obterLongitude(String capital) {
        double[] coord = coordenadasCapitais.get(capital);
        if (coord != null) {
            return coord[1];
        }
        return 0;
    }

    public List<String> getCapitaisOrdenadas() {
        return grafo.getAdjacencias().keySet().stream()
            .map(Vertice::getNome)
            .sorted()
            .collect(Collectors.toList());
    }

    public static class Resultado {
        private final List<Vertice> caminho;
        private final int distanciaTotal;

        public Resultado(List<Vertice> caminho, int distanciaTotal) {
            this.caminho = caminho;
            this.distanciaTotal = distanciaTotal;
        }

        public List<Vertice> getCaminho() {
            return caminho;
        }

        public int getDistanciaTotal() {
            return distanciaTotal;
        }
    }
}
