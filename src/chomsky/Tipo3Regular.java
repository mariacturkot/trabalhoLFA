package chomsky;

import java.util.Arrays;

import linguagem.Linguagem;
import simulador.SimuladorAFDTipo3;

public class Tipo3Regular extends HierarquiaChomsky {
    public Tipo3Regular() {
        super("Tipo 3 - Linguagens Regulares",
                "Menor classe da hierarquia. Pode ser reconhecida por automato finito.");

        SimuladorAFDTipo3 simulador = new SimuladorAFDTipo3();

        adicionarLinguagem(new Linguagem(1,
                "Quantidade par de a sobre {a,b}",
                "Cadeias formadas por a e b com numero par de simbolos a.",
                "L = { w em {a,b}* | quantidade de a em w e par }",
                "{a,b}",
                Arrays.asList("epsilon", "bb", "aa", "abba"),
                Arrays.asList("a", "aba", "baaa"),
                simulador));

        adicionarLinguagem(new Linguagem(2,
                "a*b*",
                "Zero ou mais a seguidos de zero ou mais b.",
                "L = { a^i b^j | i >= 0 e j >= 0 }",
                "{a,b}",
                Arrays.asList("epsilon", "a", "b", "aaabbb"),
                Arrays.asList("ba", "abab", "abbab"),
                simulador));

        adicionarLinguagem(new Linguagem(3,
                "(ab)^n",
                "Repeticoes do bloco ab.",
                "L = { (ab)^n | n >= 0 }",
                "{a,b}",
                Arrays.asList("epsilon", "ab", "abab", "ababab"),
                Arrays.asList("a", "aba", "abb"),
                simulador));
    }
}
