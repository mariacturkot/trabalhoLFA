package resultado;

public class PassoSimulacao {
    private int numero;
    private String descricao;
    private String estadoAtual;
    private String cadeiaAtual;
    private String estruturaAuxiliar;

    public PassoSimulacao(int numero, String descricao, String estadoAtual,
            String cadeiaAtual, String estruturaAuxiliar) {
        this.numero = numero;
        this.descricao = descricao;
        this.estadoAtual = estadoAtual;
        this.cadeiaAtual = cadeiaAtual;
        this.estruturaAuxiliar = estruturaAuxiliar;
    }

    public int getNumero() {
        return numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEstadoAtual() {
        return estadoAtual;
    }

    public String getCadeiaAtual() {
        return cadeiaAtual;
    }

    public String getEstruturaAuxiliar() {
        return estruturaAuxiliar;
    }
}
