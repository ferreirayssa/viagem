package com.grafos.ui;

import com.grafos.Vertice;
import com.grafos.service.RotaService;
import com.grafos.service.RotaService.Resultado;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RotaAppSwing extends JFrame {

    private JComboBox<String> origemCombo;
    private JComboBox<String> destinoCombo;
    private JTextArea resultadoArea;
    private MapaPanel mapaPanel;

    private RotaService rotaService;

    public RotaAppSwing() {
        super("Calculadora de Rotas");

        rotaService = new RotaService();

        List<String> capitais = rotaService.getCapitaisOrdenadas();

        origemCombo = new JComboBox<>(capitais.toArray(new String[0]));
        destinoCombo = new JComboBox<>(capitais.toArray(new String[0]));

        JButton calcularBtn = new JButton("Calcular Rota");
        calcularBtn.addActionListener(this::calcularRota);

        resultadoArea = new JTextArea();
        resultadoArea.setFont(new Font("SansSerif", Font.BOLD, 20));
        resultadoArea.setMargin(new Insets(10, 10, 10, 10));
        resultadoArea.setBackground(new Color(250, 250, 250));
        resultadoArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);

        // Painel dos controles (lado esquerdo) com largura maior
        JPanel controlesPanel = new JPanel();
        controlesPanel.setLayout(new GridBagLayout());
        controlesPanel.setPreferredSize(new Dimension(400, 700)); // largura maior para evitar sobra
        controlesPanel.setBackground(Color.WHITE); // Fundo branco no painel de controles

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        controlesPanel.add(new JLabel("Origem:"), gbc);
        gbc.gridx = 1;
        controlesPanel.add(origemCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        controlesPanel.add(new JLabel("Destino:"), gbc);
        gbc.gridx = 1;
        controlesPanel.add(destinoCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlesPanel.add(calcularBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        JScrollPane scroll = new JScrollPane(resultadoArea);
        scroll.setPreferredSize(new Dimension(380, 250));
        controlesPanel.add(scroll, gbc);

        // Define fundo branco para o conteúdo da janela
        getContentPane().setBackground(Color.WHITE);

        // Carrega a imagem do mapa do Brasil (coloque brasil.jpg em src/main/resources)
        BufferedImage mapaImg = null;
        try {
            mapaImg = ImageIO.read(getClass().getResource("/brasil.jpg"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagem do mapa: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }

        mapaPanel = new MapaPanel(mapaImg);
        mapaPanel.setPreferredSize(new Dimension(700, 700));
        mapaPanel.setBackground(Color.WHITE); // Fundo branco no painel do mapa

        // Layout principal da janela
        setLayout(new BorderLayout(10, 10));
        add(controlesPanel, BorderLayout.WEST);
        add(mapaPanel, BorderLayout.CENTER);
        
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void calcularRota(ActionEvent e) {
        String origem = (String) origemCombo.getSelectedItem();
        String destino = (String) destinoCombo.getSelectedItem();

        if (origem == null || destino == null) {
            JOptionPane.showMessageDialog(this, "Selecione origem e destino.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (origem.equals(destino)) {
            JOptionPane.showMessageDialog(this, "Origem e destino devem ser diferentes.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Resultado resultado = rotaService.calcularRota(origem, destino);

            if (resultado.getDistanciaTotal() < 0) {
                resultadoArea.setText("Não foi possível encontrar uma rota entre essas capitais.");
                mapaPanel.setPontos(new ArrayList<>());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Distância total: ").append(resultado.getDistanciaTotal()).append(" km\n");
                sb.append("Caminho:\n");

                List<MapaPanel.PontoMapa> pontos = new ArrayList<>();
                List<Vertice> caminho = resultado.getCaminho();

                for (int i = 0; i < caminho.size(); i++) {
                    Vertice v = caminho.get(i);
                    sb.append(" - ").append(v.getNome()).append("\n");

                    MapaPanel.PontoMapa.Tipo tipo;
                    if (i == 0) tipo = MapaPanel.PontoMapa.Tipo.ORIGEM;
                    else if (i == caminho.size() - 1) tipo = MapaPanel.PontoMapa.Tipo.DESTINO;
                    else tipo = MapaPanel.PontoMapa.Tipo.PERCURSO;

                    double lat = rotaService.obterLatitude(v.getNome());
                    double lon = rotaService.obterLongitude(v.getNome());

                    pontos.add(new MapaPanel.PontoMapa(v.getNome(), lat, lon, tipo));
                }

                resultadoArea.setText(sb.toString());
                mapaPanel.setPontos(pontos);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RotaAppSwing app = new RotaAppSwing();
            app.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abrir maximizado (tela cheia)
            app.setVisible(true);
        });
    }
}
