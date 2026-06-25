package chomsky;

import java.util.ArrayList;
import java.util.List;

import linguagem.Linguagem;

public class HierarquiaChomsky {
    private String nome;
    private String descricao;
    private List<Linguagem> linguagens;

    public HierarquiaChomsky(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.linguagens = new ArrayList<Linguagem>();
    }

    protected void adicionarLinguagem(Linguagem linguagem) {
        linguagens.add(linguagem);
    }

    public Linguagem buscarLinguagem(int codigo) {
        int i;
        for (i = 0; i < linguagens.size(); i++) {
            if (linguagens.get(i).getCodigo() == codigo) {
                return linguagens.get(i);
            }
        }
        return null;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Linguagem> getLinguagens() {
        return linguagens;
    }
}
