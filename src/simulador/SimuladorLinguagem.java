package simulador;

import linguagem.Linguagem;
import resultado.ResultadoSimulacao;

public class SimuladorLinguagem {
    public ResultadoSimulacao simular(Linguagem linguagem, String cadeia) {
        ResultadoSimulacao resultado = new ResultadoSimulacao();
        resultado.setPertence(false);
        resultado.setMensagemFinal("Simulador generico: nenhuma simulacao especifica foi executada.");
        resultado.adicionarPasso("Nenhum simulador especifico foi associado.",
                "sem estado", mostrarVazio(cadeia), "sem estrutura auxiliar");
        return resultado;
    }

    protected boolean contemApenas(String cadeia, String simbolosPermitidos) {
        int i;
        for (i = 0; i < cadeia.length(); i++) {
            if (simbolosPermitidos.indexOf(cadeia.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    protected ResultadoSimulacao rejeitarPorSimboloInvalido(
            String cadeia, String simbolosPermitidos) {
        ResultadoSimulacao resultado = new ResultadoSimulacao();
        resultado.setSimbolosValidos(false);
        resultado.setPertence(false);
        resultado.setMensagemFinal("Cadeia rejeitada: existe simbolo fora de {"
                + simbolosPermitidos + "}.");
        resultado.adicionarPasso("Validacao encontrou simbolo invalido.",
                "validacao", mostrarVazio(cadeia),
                "Simbolos permitidos: {" + simbolosPermitidos + "}");
        resultado.adicionarOperacao("Validar alfabeto da cadeia");
        return resultado;
    }

    protected String mostrarVazio(String texto) {
        if (texto.length() == 0) {
            return "epsilon";
        }
        return texto;
    }
}
