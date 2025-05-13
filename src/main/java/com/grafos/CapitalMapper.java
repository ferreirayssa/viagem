package com.grafos;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

class CapitalMapper {
    private static final Map<String, String> normalizadoParaCapital = new HashMap<>();

    static {
        adicionar("Acre", "Rio Branco", "AC");
        adicionar("Alagoas", "Maceió", "AL");
        adicionar("Amapá", "Macapá", "AP");
        adicionar("Amazonas", "Manaus", "AM");
        adicionar("Bahia", "Salvador", "BA");
        adicionar("Ceará", "Fortaleza", "CE");
        adicionar("Distrito Federal", "Brasília", "DF");
        adicionar("Espírito Santo", "Vitória", "ES");
        adicionar("Goiás", "Goiânia", "GO");
        adicionar("Maranhão", "São Luís", "MA");
        adicionar("Mato Grosso", "Cuiabá", "MT");
        adicionar("Mato Grosso do Sul", "Campo Grande", "MS");
        adicionar("Minas Gerais", "Belo Horizonte", "MG");
        adicionar("Pará", "Belém", "PA");
        adicionar("Paraíba", "João Pessoa", "PB");
        adicionar("Paraná", "Curitiba", "PR");
        adicionar("Pernambuco", "Recife", "PE");
        adicionar("Piauí", "Teresina", "PI");
        adicionar("Rio de Janeiro", "Rio de Janeiro", "RJ");
        adicionar("Rio Grande do Norte", "Natal", "RN");
        adicionar("Rio Grande do Sul", "Porto Alegre", "RS");
        adicionar("Rondônia", "Porto Velho", "RO");
        adicionar("Roraima", "Boa Vista", "RR");
        adicionar("Santa Catarina", "Florianópolis", "SC");
        adicionar("São Paulo", "São Paulo", "SP");
        adicionar("Sergipe", "Aracaju", "SE");
        adicionar("Tocantins", "Palmas", "TO");
    }

    private static void adicionar(String estado, String capital, String sigla) {
        String capNorm = normalizar(capital);
        normalizadoParaCapital.put(capNorm, capital);
        normalizadoParaCapital.put(normalizar(sigla), capital);
        normalizadoParaCapital.put(normalizar(estado), capital);
    }

    private static String normalizar(String entrada) {
        return Normalizer.normalize(entrada.trim().toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[^a-z]", "");
    }

    public static String capitalPadronizada(String entradaUsuario) {
        String chave = normalizar(entradaUsuario);
        return normalizadoParaCapital.getOrDefault(chave, entradaUsuario.trim());
    }

    public static boolean desejaContinuar(String entrada) {
        String e = entrada.trim().toLowerCase();
        return e.equals("s") || e.equals("sim");
    }
}