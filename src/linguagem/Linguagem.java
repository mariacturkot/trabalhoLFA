package linguagem;

import java.util.List;

import simulador.SimuladorLinguagem;

public class Linguagem {
    private int codigo;
    private String nome;
    private String descricao;
    private String regra;
    private String alfabeto;
    private List<String> exemplosAceitos;
    private List<String> exemplosRejeitados;
    private SimuladorLinguagem simulador;

    public Linguagem(int codigo, String nome, String descricao, String regra,
            String alfabeto, List<String> exemplosAceitos,
            List<String> exemplosRejeitados, SimuladorLinguagem simulador) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.regra = regra;
        this.alfabeto = alfabeto;
        this.exemplosAceitos = exemplosAceitos;
        this.exemplosRejeitados = exemplosRejeitados;
        this.simulador = simulador;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getRegra() {
        return regra;
    }

    public String getAlfabeto() {
        return alfabeto;
    }

    public List<String> getExemplosAceitos() {
        return exemplosAceitos;
    }

    public List<String> getExemplosRejeitados() {
        return exemplosRejeitados;
    }

    public SimuladorLinguagem getSimulador() {
        return simulador;
    }
}
