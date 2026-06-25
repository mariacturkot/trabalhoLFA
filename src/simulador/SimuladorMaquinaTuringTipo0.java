package simulador;

import linguagem.Linguagem;
import resultado.ResultadoSimulacao;

public class SimuladorMaquinaTuringTipo0 extends SimuladorLinguagem {
    public ResultadoSimulacao simular(Linguagem linguagem, String cadeia) {
        if (linguagem.getCodigo() == 1) {
            return simularMTAnBn(cadeia);
        }
        if (linguagem.getCodigo() == 2) {
            return simularMTParDeA(cadeia);
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        resultado.setMensagemFinal("Linguagem de Tipo 0 nao reconhecida pelo simulador.");
        return resultado;
    }

    private ResultadoSimulacao simularMTAnBn(String cadeia) {
        if (contemSimboloInvalido(cadeia, "ab")) {
            return rejeitarPorSimboloInvalido(cadeia, "ab");
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        if (!respeitaOrdemAB(cadeia)) {
            resultado.setPertence(false);
            resultado.setMensagemFinal("Cadeia rejeitada: a maquina espera bloco de a antes do bloco de b.");
            resultado.adicionarPasso("Ordem dos blocos invalida para a^n b^n.",
                    "qRejeita", mostrarVazio(cadeia), "esperado: a*b*");
            resultado.adicionarOperacao("Verificar ordem a*b*");
            return resultado;
        }

        char[] fita = cadeia.toCharArray();
        resultado.adicionarPasso("Inicio na fita de entrada.",
                "qBuscaA", mostrarVazio(cadeia), fitaComoTexto(fita));

        boolean erro = false;
        int rodada = 1;
        while (true) {
            int posA = procurarA(fita);
            if (posA == -1) {
                break;
            }

            fita[posA] = 'X';
            resultado.adicionarOperacao("Rodada " + rodada + ": marca a como X");
            resultado.adicionarPasso("Marcando o primeiro a nao processado.",
                    "qMarcaA", mostrarVazio(cadeia), fitaComoTexto(fita));

            int posB = procurarBDepois(fita, posA + 1);
            if (posB == -1) {
                erro = true;
                resultado.adicionarOperacao("Nao encontrou b correspondente");
                resultado.adicionarPasso("Nao existe b para casar com o a marcado.",
                        "qRejeita", mostrarVazio(cadeia), fitaComoTexto(fita));
                break;
            }

            fita[posB] = 'Y';
            resultado.adicionarOperacao("Rodada " + rodada + ": marca b como Y");
            resultado.adicionarPasso("Marcando o primeiro b disponivel a direita.",
                    "qMarcaB", mostrarVazio(cadeia), fitaComoTexto(fita));

            resultado.adicionarOperacao("Retorna ao inicio da fita");
            resultado.adicionarPasso("Cabecote retorna para nova varredura.",
                    "qVolta", mostrarVazio(cadeia), fitaComoTexto(fita));
            rodada++;
        }

        if (!erro && existeABNaoMarcado(fita)) {
            erro = true;
            resultado.adicionarOperacao("Verificacao final encontrou simbolo sem marca");
            resultado.adicionarPasso("Sobrou a ou b sem correspondencia.",
                    "qRejeita", mostrarVazio(cadeia), fitaComoTexto(fita));
        }

        resultado.setPertence(!erro);
        if (!erro) {
            resultado.setMensagemFinal("Cadeia aceita: a maquina marcou pares a/b corretamente.");
        } else {
            resultado.setMensagemFinal("Cadeia rejeitada: nao foi possivel formar pares a/b.");
        }
        return resultado;
    }

    private ResultadoSimulacao simularMTParDeA(String cadeia) {
        if (contemSimboloInvalido(cadeia, "ab")) {
            return rejeitarPorSimboloInvalido(cadeia, "ab");
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        String estado = "qPar";
        resultado.adicionarPasso("Inicio no estado qPar.",
                estado, mostrarVazio(cadeia), fitaComoTexto(cadeia.toCharArray()));

        int i;
        for (i = 0; i < cadeia.length(); i++) {
            char simbolo = cadeia.charAt(i);
            String estadoAnterior = estado;

            if (simbolo == 'a') {
                if (estado.equals("qPar")) {
                    estado = "qImpar";
                } else {
                    estado = "qPar";
                }
            }

            String operacao = "delta(" + estadoAnterior + ", " + simbolo
                    + ") -> " + estado + ", move D";
            resultado.adicionarOperacao(operacao);
            resultado.adicionarPasso("Leu simbolo na fita e moveu para a direita.",
                    estado, mostrarVazio(cadeia.substring(0, i + 1)),
                    "cabecote=" + (i + 1) + ", fita=" + fitaComoTexto(cadeia.toCharArray()));
        }

        boolean aceita = estado.equals("qPar");
        resultado.adicionarOperacao("Leu branco: decide pelo estado atual");
        resultado.adicionarPasso("Cabecote chegou ao branco final.",
                aceita ? "qAceita" : "qRejeita", mostrarVazio(cadeia),
                "estado anterior=" + estado + ", fita=" + fitaComoTexto(cadeia.toCharArray()));

        resultado.setPertence(aceita);
        if (aceita) {
            resultado.setMensagemFinal("Cadeia aceita: quantidade par de a.");
        } else {
            resultado.setMensagemFinal("Cadeia rejeitada: quantidade impar de a.");
        }
        return resultado;
    }

    private boolean respeitaOrdemAB(String cadeia) {
        boolean jaViuB = false;
        int i;
        for (i = 0; i < cadeia.length(); i++) {
            char simbolo = cadeia.charAt(i);
            if (simbolo == 'b') {
                jaViuB = true;
            } else if (simbolo == 'a' && jaViuB) {
                return false;
            }
        }
        return true;
    }

    private int procurarA(char[] texto) {
        int i;
        for (i = 0; i < texto.length; i++) {
            if (texto[i] == 'a') {
                return i;
            }
        }
        return -1;
    }

    private int procurarBDepois(char[] texto, int inicio) {
        int i;
        for (i = inicio; i < texto.length; i++) {
            if (texto[i] == 'b') {
                return i;
            }
        }
        return -1;
    }

    private boolean existeABNaoMarcado(char[] texto) {
        int i;
        for (i = 0; i < texto.length; i++) {
            if (texto[i] == 'a' || texto[i] == 'b') {
                return true;
            }
        }
        return false;
    }

    private String fitaComoTexto(char[] fita) {
        if (fita.length == 0) {
            return "_";
        }
        return new String(fita) + "_";
    }
}

