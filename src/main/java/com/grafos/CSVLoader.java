package com.grafos;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVLoader {
    public static void carregarGrafoDeCSV(Grafo grafo, String caminhoCSV) {
        try {
            InputStream is = CSVLoader.class.getClassLoader().getResourceAsStream(caminhoCSV);
            if (is == null) {
                System.err.println("Arquivo CSV não encontrado: " + caminhoCSV);
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("Origem")) continue; // Pula cabeçalho
                String[] partes = linha.split(",");
                if (partes.length != 3) continue;

                String origem = partes[0].trim();
                String destino = partes[1].trim();
                int distancia = Integer.parseInt(partes[2].trim());

                grafo.adicionarAresta(origem, destino, distancia);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
