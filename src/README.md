# 🗺️ Calculadora de Rotas entre Capitais Brasileiras

Aplicativo desktop em Java que calcula a rota mais curta entre capitais do Brasil, com visualização geográfica sobre um mapa simples.

---

## 📦 Estrutura do Projeto

```
viagem/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── grafos/
│       │           ├── service/
│       │           │   └── RotaService.java
│       │           ├── ui/
│       │           │   ├── RotaAppSwing.java
│       │           │   └── MapaPanel.java
│       │           ├── Aresta.java
│       │           ├── CapitalMapper.java
│       │           ├── CSVLoader.java
│       │           ├── Dijkstra.java
│       │           ├── Grafo.java
│       │           ├── Vertice.java
│       │           └── MenorCaminhoCapitais.java
│       └── resources/
│           ├── brasil.jpg
│           ├── conexoes_capitais.csv
│           ├── Conexões por região.md
```

---

## 🚀 Funcionalidades

- Seleção da **origem e destino** entre as capitais do Brasil.
- Cálculo do **menor caminho** com base nas conexões reais de um arquivo CSV.
- Exibição da **distância total** em quilômetros.
- Renderização gráfica do percurso sobre um **mapa do Brasil**.
  - 🟢 Origem
  - 🔴 Destino
  - 🔵 Capitais intermediárias

---

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Swing (Java GUI)**
- **Maven** para gerenciamento de dependências
- **Algoritmo de Dijkstra** para cálculo do menor caminho
- Manipulação de arquivos `.csv` para carregar conexões

---

## 📂 Arquivos Relevantes

- `conexoes_capitais.csv`: contém as distâncias entre capitais.
- `brasil.jpg`: imagem do mapa utilizada para visualização.
- `MapaPanel.java`: renderiza o mapa e desenha os pontos da rota.
- `RotaAppSwing.java`: interface gráfica principal do usuário.

---

## 🧪 Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/seuusuario/viagem.git
```

2. Compile com Maven:

```bash
mvn clean install
```

3. Execute a aplicação:

```bash
mvn exec:java -Dexec.mainClass="com.grafos.ui.RotaAppSwing"
```
---

> Projeto desenvolvido por **Victor Pimenta**, **Rayssa Ferreira** e **Samantha Conceição**.
