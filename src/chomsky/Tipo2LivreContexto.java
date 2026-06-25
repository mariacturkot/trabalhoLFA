package chomsky;

import java.util.Arrays;

import linguagem.Linguagem;
import simulador.SimuladorAutomatoPilhaTipo2;

public class Tipo2LivreContexto extends HierarquiaChomsky {
    public Tipo2LivreContexto() {
        super("Tipo 2 - Linguagens Livres de Contexto",
                "Classe reconhecida por automatos com pilha.");

        SimuladorAutomatoPilhaTipo2 simulador = new SimuladorAutomatoPilhaTipo2();

        adicionarLinguagem(new Linguagem(1,
                "a^n b^n",
                "Mesma quantidade de a seguida pela mesma quantidade de b.",
                "L = { a^n b^n | n >= 0 }",
                "{a,b}",
                Arrays.asList("epsilon", "ab", "aabb", "aaabbb"),
                Arrays.asList("aab", "abb", "aba"),
                simulador));

        adicionarLinguagem(new Linguagem(2,
                "Parenteses balanceados",
                "Toda abertura deve ter um fechamento correspondente e na ordem correta.",
                "L = cadeias bem formadas usando '(' e ')'",
                "{(,)}",
                Arrays.asList("epsilon", "()", "(())", "()()"),
                Arrays.asList("(()", ")(", "())("),
                simulador));

        adicionarLinguagem(new Linguagem(3,
                "Palindromos sobre {a,b}",
                "Cadeias que podem ser lidas igual da esquerda para a direita e ao contrario.",
                "L = { w em {a,b}* | w = reverso(w) }",
                "{a,b}",
                Arrays.asList("epsilon", "a", "bb", "abba", "aba"),
                Arrays.asList("ab", "aab", "abb"),
                simulador));
    }
}
