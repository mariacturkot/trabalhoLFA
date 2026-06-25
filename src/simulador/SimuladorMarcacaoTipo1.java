package simulador;

import linguagem.Linguagem;
import resultado.ResultadoSimulacao;

public class SimuladorMarcacaoTipo1 extends SimuladorLinguagem {
    public ResultadoSimulacao simular(Linguagem linguagem, String cadeia) {
        if (linguagem.getCodigo() == 1) {
            return simularAnBnCn(cadeia);
        }
        if (linguagem.getCodigo() == 2) {
            return simularWW(cadeia);
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        resultado.setMensagemFinal("Linguagem sensivel ao contexto nao reconhecida pelo simulador.");
        return resultado;
    }

    private ResultadoSimulacao simularAnBnCn(String cadeia) {
        if (!contemApenas(cadeia, "abc")) {
            return rejeitarPorSimboloInvalido(cadeia, "abc");
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        if (cadeia.isEmpty()) {
            resultado.setPertence(false);
            resultado.setMensagemFinal("Cadeia rejeitada: para a^n b^n c^n foi usado n >= 1.");
            resultado.adicionarPasso("Entrada vazia nao atende ao n >= 1.",
                    "qRejeita", "epsilon", "sem marcacoes");
            return resultado;
        }

        if (!respeitaOrdemABC(cadeia)) {
            resultado.setPertence(false);
            resultado.setMensagemFinal("Cadeia rejeitada: os blocos devem estar na ordem a*b*c*.");
            resultado.adicionarPasso("Ordem dos blocos invalida.",
                    "qRejeita", cadeia, "esperado: todos os a, depois b, depois c");
            resultado.adicionarOperacao("Verificar ordem a*b*c*");
            return resultado;
        }

        char[] marcacao = cadeia.toCharArray();
        resultado.adicionarPasso("Inicio com a cadeia sem marcacoes.",
                "qBuscaA", cadeia, marcacaoComoTexto(marcacao));

        int rodada = 1;
        boolean erro = false;
        while (true) {
            int posA = procurarA(marcacao);
            if (posA == -1) {
                break;
            }

            marcacao[posA] = 'X';
            resultado.adicionarOperacao("Rodada " + rodada + ": marca a como X");
            resultado.adicionarPasso("Marcou o proximo a ainda nao usado.",
                    "qMarcaA", cadeia, marcacaoComoTexto(marcacao));

            int posB = procurarDepois(marcacao, 'b', posA + 1);
            if (posB == -1) {
                erro = true;
                resultado.adicionarOperacao("Nao encontrou b correspondente");
                resultado.adicionarPasso("Faltou b para o a marcado nesta rodada.",
                        "qRejeita", cadeia, marcacaoComoTexto(marcacao));
                break;
            }
            marcacao[posB] = 'Y';
            resultado.adicionarOperacao("Rodada " + rodada + ": marca b como Y");
            resultado.adicionarPasso("Marcou um b correspondente.",
                    "qMarcaB", cadeia, marcacaoComoTexto(marcacao));

            int posC = procurarDepois(marcacao, 'c', posB + 1);
            if (posC == -1) {
                erro = true;
                resultado.adicionarOperacao("Nao encontrou c correspondente");
                resultado.adicionarPasso("Faltou c para completar a rodada.",
                        "qRejeita", cadeia, marcacaoComoTexto(marcacao));
                break;
            }
            marcacao[posC] = 'Z';
            resultado.adicionarOperacao("Rodada " + rodada + ": marca c como Z");
            resultado.adicionarPasso("Marcou um c correspondente.",
                    "qMarcaC", cadeia, marcacaoComoTexto(marcacao));

            rodada++;
        }

        if (!erro && existeNaoMarcado(marcacao)) {
            erro = true;
            resultado.adicionarOperacao("Verificacao final encontrou simbolo sem par");
            resultado.adicionarPasso("Sobrou simbolo nao marcado.",
                    "qRejeita", cadeia, marcacaoComoTexto(marcacao));
        }

        resultado.setPertence(!erro);
        if (!erro) {
            resultado.setMensagemFinal("Cadeia aceita: as quantidades de a, b e c sao iguais.");
        } else {
            resultado.setMensagemFinal("Cadeia rejeitada: as quantidades nao sao iguais.");
        }
        return resultado;
    }

    private ResultadoSimulacao simularWW(String cadeia) {
        if (!contemApenas(cadeia, "ab")) {
            return rejeitarPorSimboloInvalido(cadeia, "ab");
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        if (cadeia.length() % 2 == 1) {
            resultado.setPertence(false);
            resultado.setMensagemFinal("Cadeia rejeitada: ww sempre tem tamanho par.");
            resultado.adicionarPasso("Tamanho impar impossibilita duas metades iguais.",
                    "qRejeita", mostrarVazio(cadeia), "tamanho = " + cadeia.length());
            resultado.adicionarOperacao("Verificar paridade do tamanho");
            return resultado;
        }

        char[] marcacao = cadeia.toCharArray();
        int metade = cadeia.length() / 2;
        boolean erro = false;

        resultado.adicionarPasso("Inicio comparando a primeira metade com a segunda.",
                "qCompara", mostrarVazio(cadeia), marcacaoComoTexto(marcacao));

        int i;
        for (i = 0; i < metade; i++) {
            char esquerda = cadeia.charAt(i);
            char direita = cadeia.charAt(i + metade);

            if (esquerda == direita) {
                marcacao[i] = Character.toUpperCase(esquerda);
                marcacao[i + metade] = Character.toUpperCase(direita);
                resultado.adicionarOperacao("Compara posicoes " + i + " e "
                        + (i + metade) + ": iguais");
                resultado.adicionarPasso("As posicoes correspondentes possuem o mesmo simbolo.",
                        "qCompara", mostrarVazio(cadeia), marcacaoComoTexto(marcacao));
            } else {
                erro = true;
                resultado.adicionarOperacao("Compara posicoes " + i + " e "
                        + (i + metade) + ": diferentes");
                resultado.adicionarPasso("As duas metades diferem nesta posicao.",
                        "qRejeita", mostrarVazio(cadeia), marcacaoComoTexto(marcacao));
                break;
            }
        }

        resultado.setPertence(!erro);
        if (!erro) {
            resultado.setMensagemFinal("Cadeia aceita: a segunda metade repete a primeira.");
        } else {
            resultado.setMensagemFinal("Cadeia rejeitada: a cadeia nao tem formato ww.");
        }
        return resultado;
    }

    private boolean respeitaOrdemABC(String cadeia) {
        int fase = 0;
        int i;
        for (i = 0; i < cadeia.length(); i++) {
            char simbolo = cadeia.charAt(i);
            if (simbolo == 'a') {
                if (fase > 0) {
                    return false;
                }
            } else if (simbolo == 'b') {
                if (fase > 1) {
                    return false;
                }
                fase = 1;
            } else if (simbolo == 'c') {
                fase = 2;
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

    private int procurarDepois(char[] texto, char simbolo, int inicio) {
        int i;
        for (i = inicio; i < texto.length; i++) {
            if (texto[i] == simbolo) {
                return i;
            }
        }
        return -1;
    }

    private boolean existeNaoMarcado(char[] texto) {
        int i;
        for (i = 0; i < texto.length; i++) {
            if (texto[i] == 'a' || texto[i] == 'b' || texto[i] == 'c') {
                return true;
            }
        }
        return false;
    }

    private String marcacaoComoTexto(char[] texto) {
        if (texto.length == 0) {
            return "epsilon";
        }
        return new String(texto);
    }
}
