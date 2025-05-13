package com.grafos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MenorCaminhoCapitais {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = new Grafo();

        CSVLoader.carregarGrafoDeCSV(grafo, "conexoes_capitais.csv");

        boolean continuar = true;

        while (continuar) {
            System.out.println("Digite a capital de origem (nome ou sigla):");
            String origemStr = CapitalMapper.capitalPadronizada(scanner.nextLine());
            System.out.println("Digite a capital de destino (nome ou sigla):");
            String destinoStr = CapitalMapper.capitalPadronizada(scanner.nextLine());

            Vertice origem = new Vertice(origemStr);
            Vertice destino = new Vertice(destinoStr);

            if (!grafo.getAdjacencias().containsKey(origem)) {
                System.out.println("Capital de origem inválida ou não cadastrada no grafo: " + origemStr);
                continue;
            }
            if (!grafo.getAdjacencias().containsKey(destino)) {
                System.out.println("Capital de destino inválida ou não cadastrada no grafo: " + destinoStr);
                continue;
            }

            Map<Vertice, Vertice> predecessores = new HashMap<>();
            Map<Vertice, Integer> distancias = Dijkstra.calcularDistancias(grafo, origem, predecessores);

            if (!distancias.containsKey(destino) || distancias.get(destino) == Integer.MAX_VALUE) {
                System.out.println("Não foi possível encontrar um caminho entre as capitais informadas.");
            } else {
                List<Vertice> caminho = Dijkstra.reconstruirCaminho(destino, predecessores);
                System.out.println("Menor caminho: " + caminho);
                System.out.println("Distância total: " + distancias.get(destino) + " km");
            }

            System.out.println("Deseja calcular outra rota? (Sim/Não):");
            String resposta = scanner.nextLine();
            continuar = CapitalMapper.desejaContinuar(resposta);
        }

        System.out.println("Sistema encerrado.");
    }
}