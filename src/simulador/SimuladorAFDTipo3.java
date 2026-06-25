package simulador;

import linguagem.Linguagem;
import resultado.ResultadoSimulacao;

public class SimuladorAFDTipo3 extends SimuladorLinguagem {
    public ResultadoSimulacao simular(Linguagem linguagem, String cadeia) {
        if (linguagem.getCodigo() == 1) {
            return simularParDeA(cadeia);
        }
        if (linguagem.getCodigo() == 2) {
            return simularAEstrelaBEstrela(cadeia);
        }
        if (linguagem.getCodigo() == 3) {
            return simularABRepetido(cadeia);
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        resultado.setMensagemFinal("Linguagem regular nao reconhecida pelo simulador.");
        return resultado;
    }

    private ResultadoSimulacao simularParDeA(String cadeia) {
        if (contemSimboloInvalido(cadeia, "ab")) {
            return rejeitarPorSimboloInvalido(cadeia, "ab");
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        String estado = "qPar";
        resultado.adicionarPasso("Inicio no estado que representa quantidade par de a.",
                estado, mostrarVazio(cadeia), "AFD ainda nao leu simbolos.");

        for (int i = 0; i < cadeia.length(); i++) {
            char simbolo = cadeia.charAt(i);
            String estadoAnterior = estado;

            if (simbolo == 'a') {
                if (estado.equals("qPar")) {
                    estado = "qImpar";
                } else {
                    estado = "qPar";
                }
            }

            String operacao = "delta(" + estadoAnterior + ", " + simbolo + ") -> " + estado;
            resultado.adicionarOperacao(operacao);
            resultado.adicionarPasso("Leitura do simbolo '" + simbolo + "' na posicao " + i + ".", estado, mostrarVazio(cadeia.substring(0, i + 1)), operacao);
        }

        boolean aceita = estado.equals("qPar");
        resultado.setPertence(aceita);
        if (aceita) {
            resultado.setMensagemFinal("Cadeia aceita: a quantidade de a e par.");
        } else {
            resultado.setMensagemFinal("Cadeia rejeitada: a quantidade de a e impar.");
        }
        return resultado;
    }

    private ResultadoSimulacao simularAEstrelaBEstrela(String cadeia) {
        if (contemSimboloInvalido(cadeia, "ab")) {
            return rejeitarPorSimboloInvalido(cadeia, "ab");
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        String estado = "qA";
        boolean lendoBs = false;
        boolean erro = false;
        resultado.adicionarPasso("Inicio esperando a parte dos a.",
                estado, mostrarVazio(cadeia), "Estados de aceitacao: qA e qB.");

        for (int i = 0; i < cadeia.length(); i++) {
            char simbolo = cadeia.charAt(i);
            String estadoAnterior = estado;

            if (!lendoBs && simbolo == 'a') {
                estado = "qA";
            } else if (simbolo == 'b') {
                lendoBs = true;
                estado = "qB";
            } else {
                estado = "qErro";
                erro = true;
            }

            String operacao = "delta(" + estadoAnterior + ", " + simbolo + ") -> " + estado;
            resultado.adicionarOperacao(operacao);
            resultado.adicionarPasso("Leitura do simbolo '" + simbolo + "' na posicao " + i + ".", estado,
                    mostrarVazio(cadeia.substring(0, i + 1)), operacao);

            if (erro) {
                break;
            }
        }

        boolean aceita = !erro;
        resultado.setPertence(aceita);
        if (aceita) {
            resultado.setMensagemFinal("Cadeia aceita: todos os a aparecem antes dos b.");
        } else {
            resultado.setMensagemFinal("Cadeia rejeitada: apareceu a depois de b.");
        }
        return resultado;
    }

    private ResultadoSimulacao simularABRepetido(String cadeia) {
        if (contemSimboloInvalido(cadeia, "ab")) {
            return rejeitarPorSimboloInvalido(cadeia, "ab");
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        String estado = "q0";
        boolean erro = false;
        resultado.adicionarPasso("Inicio esperando o primeiro simbolo de um bloco ab.",
                estado, mostrarVazio(cadeia), "q0 e estado de aceitacao.");

        for (int i = 0; i < cadeia.length(); i++) {
            char simbolo = cadeia.charAt(i);
            String estadoAnterior = estado;

            if (estado.equals("q0") && simbolo == 'a') {
                estado = "q1";
            } else if (estado.equals("q1") && simbolo == 'b') {
                estado = "q0";
            } else {
                estado = "qErro";
                erro = true;
            }

            String operacao = "delta(" + estadoAnterior + ", " + simbolo + ") -> " + estado;
            resultado.adicionarOperacao(operacao);
            resultado.adicionarPasso("Leitura do simbolo '" + simbolo
                    + "' na posicao " + i + ".", estado,
                    mostrarVazio(cadeia.substring(0, i + 1)), operacao);

            if (erro) {
                break;
            }
        }

        boolean aceita = !erro && estado.equals("q0");
        resultado.setPertence(aceita);
        if (aceita) {
            resultado.setMensagemFinal("Cadeia aceita: a cadeia e formada por blocos ab.");
        } else {
            resultado.setMensagemFinal("Cadeia rejeitada: existe bloco incompleto ou fora do padrao ab.");
        }
        return resultado;
    }
}

