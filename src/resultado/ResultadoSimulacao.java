package resultado;

import java.util.ArrayList;
import java.util.List;

public class ResultadoSimulacao {
    private boolean pertence;
    private boolean simbolosValidos;
    private String mensagemFinal;
    private List<PassoSimulacao> passos;
    private List<String> operacoes;

    public ResultadoSimulacao() {
        this.pertence = false;
        this.simbolosValidos = true;
        this.mensagemFinal = "";
        this.passos = new ArrayList<PassoSimulacao>();
        this.operacoes = new ArrayList<String>();
    }

    public void adicionarPasso(String descricao, String estadoAtual,
            String cadeiaAtual, String estruturaAuxiliar) {
        int numero = passos.size() + 1;
        passos.add(new PassoSimulacao(numero, descricao, estadoAtual,
                cadeiaAtual, estruturaAuxiliar));
    }

    public void adicionarOperacao(String operacao) {
        operacoes.add(operacao);
    }

    public boolean isPertence() {
        return pertence;
    }

    public void setPertence(boolean pertence) {
        this.pertence = pertence;
    }

    public boolean isSimbolosValidos() {
        return simbolosValidos;
    }

    public void setSimbolosValidos(boolean simbolosValidos) {
        this.simbolosValidos = simbolosValidos;
    }

    public String getMensagemFinal() {
        return mensagemFinal;
    }

    public void setMensagemFinal(String mensagemFinal) {
        this.mensagemFinal = mensagemFinal;
    }

    public List<PassoSimulacao> getPassos() {
        return passos;
    }

    public List<String> getOperacoes() {
        return operacoes;
    }
}
