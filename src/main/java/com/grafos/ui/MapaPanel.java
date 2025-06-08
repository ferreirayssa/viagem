package com.grafos.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class MapaPanel extends JPanel {

    private BufferedImage mapaImagem;
    private List<PontoMapa> pontos;

    // Limites geográficos do mapa (exemplo aproximado para Brasil)
    private final double latMax = 5.3;     // Norte
    private final double latMin = -33.7;   // Sul
    private final double lonMin = -73.9;   // Oeste
    private final double lonMax = -34.8;   // Leste

    public MapaPanel(BufferedImage mapaImagem) {
        this.mapaImagem = mapaImagem;
        this.pontos = List.of();
    }

    public void setPontos(List<PontoMapa> pontos) {
        this.pontos = pontos;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("SansSerif", Font.BOLD, 14));

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        double imageAspect = (double) mapaImagem.getWidth() / mapaImagem.getHeight();
        double panelAspect = (double) panelWidth / panelHeight;

        int drawWidth, drawHeight;
        int offsetX = 0, offsetY = 0;

        if (panelAspect > imageAspect) {
            drawHeight = panelHeight;
            drawWidth = (int) (drawHeight * imageAspect);
            offsetX = (panelWidth - drawWidth) / 2;
        } else {
            drawWidth = panelWidth;
            drawHeight = (int) (drawWidth / imageAspect);
            offsetY = (panelHeight - drawHeight) / 2;
        }

        g.drawImage(mapaImagem, offsetX, offsetY, drawWidth, drawHeight, this);

        for (PontoMapa p : pontos) {
            Point pixel = geoToPixel(p.latitude, p.longitude, drawWidth, drawHeight);
            int x = pixel.x + offsetX;
            int y = pixel.y + offsetY;

            switch (p.tipo) {
                case ORIGEM:
                    g.setColor(Color.GREEN);
                    break;
                case DESTINO:
                    g.setColor(Color.RED);
                    break;
                case PERCURSO:
                    g.setColor(Color.BLUE);
                    break;
            }

            g.fillOval(x - 28, y + 3, 12, 12);     // mais à esquerda e abaixo
            g.setColor(Color.BLACK);
            g.drawString(p.nome, x + 8, y + 16); // nome mais abaixo do ponto
        }
    }


    private Point geoToPixel(double lat, double lon, int largura, int altura) {
        int x = (int) ((lon - lonMin) / (lonMax - lonMin) * largura);
        int y = (int) ((latMax - lat) / (latMax - latMin) * altura);
        return new Point(x, y);
    }

    public static class PontoMapa {
        public enum Tipo { ORIGEM, DESTINO, PERCURSO }
        public final String nome;
        public final double latitude;
        public final double longitude;
        public final Tipo tipo;

        public PontoMapa(String nome, double latitude, double longitude, Tipo tipo) {
            this.nome = nome;
            this.latitude = latitude;
            this.longitude = longitude;
            this.tipo = tipo;
        }
    }
}

