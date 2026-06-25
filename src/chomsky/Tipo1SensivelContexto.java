package chomsky;

import java.util.Arrays;

import linguagem.Linguagem;
import simulador.SimuladorMarcacaoTipo1;

public class Tipo1SensivelContexto extends HierarquiaChomsky {
    public Tipo1SensivelContexto() {
        super("Tipo 1 - Linguagens Sensiveis ao Contexto",
                "Classe que pode ser simulada por marcacoes com memoria limitada ao tamanho da entrada.");

        SimuladorMarcacaoTipo1 simulador = new SimuladorMarcacaoTipo1();

        adicionarLinguagem(new Linguagem(1,
                "a^n b^n c^n",
                "Mesma quantidade de a, b e c, nesta ordem.",
                "L = { a^n b^n c^n | n >= 1 }",
                "{a,b,c}",
                Arrays.asList("abc", "aabbcc", "aaabbbccc"),
                Arrays.asList("epsilon", "aabbc", "abcc", "abbccc"),
                simulador));

        adicionarLinguagem(new Linguagem(2,
                "ww, onde w pertence a {a,b}*",
                "Duas metades iguais, lado a lado.",
                "L = { ww | w em {a,b}* }",
                "{a,b}",
                Arrays.asList("epsilon", "aa", "abab", "baba", "aaaa"),
                Arrays.asList("ab", "aba", "abba"),
                simulador));
    }
}
