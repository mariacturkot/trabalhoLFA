package chomsky;

import java.util.Arrays;

import linguagem.Linguagem;
import simulador.SimuladorMaquinaTuringTipo0;

public class Tipo0RecursivamenteEnumeravel extends HierarquiaChomsky {
    public Tipo0RecursivamenteEnumeravel() {
        super("Tipo 0 - Linguagens Recursivamente Enumeraveis", "Classe mais geral, associada a maquinas de Turing.");

        SimuladorMaquinaTuringTipo0 simulador = new SimuladorMaquinaTuringTipo0();

        adicionarLinguagem(new Linguagem(1,
                "Maquina de Turing simples para a^n b^n",
                "A maquina marca um a e procura um b correspondente.",
                "L = { a^n b^n | n >= 0 }",
                "{a,b}",
                Arrays.asList("epsilon", "ab", "aabb", "aaabbb"),
                Arrays.asList("aab", "abb", "ba"),
                simulador));

        adicionarLinguagem(new Linguagem(2,
                "Maquina de Turing simples para quantidade par de a",
                "A maquina alterna entre estado par e impar ao ler a.",
                "L = { w em {a,b}* | quantidade de a em w e par }",
                "{a,b}",
                Arrays.asList("epsilon", "bb", "aa", "abba"),
                Arrays.asList("a", "aba", "baaa"),
                simulador));
    }
}
