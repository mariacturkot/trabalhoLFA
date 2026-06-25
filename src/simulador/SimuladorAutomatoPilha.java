package simulador;

import java.util.ArrayList;
import java.util.List;

import linguagem.Linguagem;
import resultado.ResultadoSimulacao;

public class SimuladorAutomatoPilha extends SimuladorLinguagem {
    public ResultadoSimulacao simular(Linguagem linguagem, String cadeia) {
        if (linguagem.getCodigo() == 1) {
            return simularAnBn(cadeia);
        }
        if (linguagem.getCodigo() == 2) {
            return simularParenteses(cadeia);
        }
        if (linguagem.getCodigo() == 3) {
            return simularPalindromo(cadeia);
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        resultado.setMensagemFinal("Linguagem livre de contexto nao reconhecida pelo simulador.");
        return resultado;
    }

    private ResultadoSimulacao simularAnBn(String cadeia) {
        if (!contemApenas(cadeia, "ab")) {
            return rejeitarPorSimboloInvalido(cadeia, "ab");
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        List<Character> pilha = new ArrayList<Character>();
        String estado = "qEmpilha";
        boolean erro = false;

        resultado.adicionarPasso("Inicio empilhando um marcador para cada a.",
                estado, mostrarVazio(cadeia), pilhaComoTexto(pilha));

        int i;
        for (i = 0; i < cadeia.length(); i++) {
            char simbolo = cadeia.charAt(i);

            if (simbolo == 'a' && estado.equals("qEmpilha")) {
                pilha.add(Character.valueOf('A'));
                resultado.adicionarOperacao("Leu a: empilha A");
                resultado.adicionarPasso("Empilhou A para o simbolo a.",
                        estado, mostrarVazio(cadeia.substring(0, i + 1)),
                        pilhaComoTexto(pilha));
            } else if (simbolo == 'b') {
                estado = "qDesempilha";
                if (pilha.size() == 0) {
                    erro = true;
                    resultado.adicionarOperacao("Leu b com pilha vazia: erro");
                    resultado.adicionarPasso("Nao ha A para casar com este b.",
                            "qErro", mostrarVazio(cadeia.substring(0, i + 1)),
                            pilhaComoTexto(pilha));
                    break;
                }
                pilha.remove(pilha.size() - 1);
                resultado.adicionarOperacao("Leu b: desempilha A");
                resultado.adicionarPasso("Desempilhou A para casar com b.",
                        estado, mostrarVazio(cadeia.substring(0, i + 1)),
                        pilhaComoTexto(pilha));
            } else {
                erro = true;
                estado = "qErro";
                resultado.adicionarOperacao("Leu a depois de b: erro");
                resultado.adicionarPasso("Apareceu a depois do inicio dos b.",
                        estado, mostrarVazio(cadeia.substring(0, i + 1)),
                        pilhaComoTexto(pilha));
                break;
            }
        }

        boolean aceita = !erro && pilha.size() == 0;
        resultado.setPertence(aceita);
        if (aceita) {
            resultado.setMensagemFinal("Cadeia aceita: todo a teve um b correspondente.");
        } else {
            resultado.setMensagemFinal("Cadeia rejeitada: as quantidades ou a ordem nao batem.");
        }
        return resultado;
    }

    private ResultadoSimulacao simularParenteses(String cadeia) {
        if (!contemApenas(cadeia, "()")) {
            return rejeitarPorSimboloInvalido(cadeia, "()");
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        List<Character> pilha = new ArrayList<Character>();
        String estado = "qLeitura";
        boolean erro = false;

        resultado.adicionarPasso("Inicio com pilha vazia.",
                estado, mostrarVazio(cadeia), pilhaComoTexto(pilha));

        int i;
        for (i = 0; i < cadeia.length(); i++) {
            char simbolo = cadeia.charAt(i);

            if (simbolo == '(') {
                pilha.add(Character.valueOf('('));
                resultado.adicionarOperacao("Leu (: empilha (");
                resultado.adicionarPasso("Guardou uma abertura na pilha.",
                        estado, mostrarVazio(cadeia.substring(0, i + 1)),
                        pilhaComoTexto(pilha));
            } else {
                if (pilha.size() == 0) {
                    erro = true;
                    resultado.adicionarOperacao("Leu ) com pilha vazia: erro");
                    resultado.adicionarPasso("Fechamento sem abertura correspondente.",
                            "qErro", mostrarVazio(cadeia.substring(0, i + 1)),
                            pilhaComoTexto(pilha));
                    break;
                }
                pilha.remove(pilha.size() - 1);
                resultado.adicionarOperacao("Leu ): desempilha (");
                resultado.adicionarPasso("Fechamento casou com a abertura do topo.",
                        estado, mostrarVazio(cadeia.substring(0, i + 1)),
                        pilhaComoTexto(pilha));
            }
        }

        boolean aceita = !erro && pilha.size() == 0;
        resultado.setPertence(aceita);
        if (aceita) {
            resultado.setMensagemFinal("Cadeia aceita: parenteses balanceados.");
        } else {
            resultado.setMensagemFinal("Cadeia rejeitada: existe abertura ou fechamento sem par.");
        }
        return resultado;
    }

    private ResultadoSimulacao simularPalindromo(String cadeia) {
        if (!contemApenas(cadeia, "ab")) {
            return rejeitarPorSimboloInvalido(cadeia, "ab");
        }

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        List<Character> pilha = new ArrayList<Character>();
        String estado = "qEmpilhaMetade";
        boolean erro = false;
        int meio = cadeia.length() / 2;
        boolean tamanhoImpar = cadeia.length() % 2 == 1;

        resultado.adicionarPasso("Inicio: empilha a primeira metade da cadeia.",
                estado, mostrarVazio(cadeia), pilhaComoTexto(pilha));

        int i;
        for (i = 0; i < meio; i++) {
            pilha.add(Character.valueOf(cadeia.charAt(i)));
            resultado.adicionarOperacao("Empilha " + cadeia.charAt(i));
            resultado.adicionarPasso("Empilhou simbolo da primeira metade.",
                    estado, cadeia.substring(0, i + 1), pilhaComoTexto(pilha));
        }

        if (tamanhoImpar) {
            resultado.adicionarOperacao("Ignora simbolo central " + cadeia.charAt(meio));
            resultado.adicionarPasso("Ignorou o simbolo central da cadeia impar.",
                    "qMeio", cadeia.substring(0, meio + 1), pilhaComoTexto(pilha));
        }

        estado = "qCompara";
        int inicioSegundaMetade = meio;
        if (tamanhoImpar) {
            inicioSegundaMetade = meio + 1;
        }

        for (i = inicioSegundaMetade; i < cadeia.length(); i++) {
            char simbolo = cadeia.charAt(i);
            if (pilha.size() == 0) {
                erro = true;
                resultado.adicionarPasso("Pilha acabou antes da comparacao terminar.",
                        "qErro", cadeia.substring(0, i + 1), pilhaComoTexto(pilha));
                break;
            }

            char topo = pilha.get(pilha.size() - 1).charValue();
            if (topo == simbolo) {
                pilha.remove(pilha.size() - 1);
                resultado.adicionarOperacao("Compara " + simbolo + " com topo " + topo
                        + ": desempilha");
                resultado.adicionarPasso("Simbolo da segunda metade casou com o topo.",
                        estado, cadeia.substring(0, i + 1), pilhaComoTexto(pilha));
            } else {
                erro = true;
                resultado.adicionarOperacao("Compara " + simbolo + " com topo " + topo
                        + ": erro");
                resultado.adicionarPasso("Simbolo nao corresponde ao espelho esperado.",
                        "qErro", cadeia.substring(0, i + 1), pilhaComoTexto(pilha));
                break;
            }
        }

        boolean aceita = !erro && pilha.size() == 0;
        resultado.setPertence(aceita);
        if (aceita) {
            resultado.setMensagemFinal("Cadeia aceita: a cadeia e palindromo.");
        } else {
            resultado.setMensagemFinal("Cadeia rejeitada: a segunda metade nao espelha a primeira.");
        }
        return resultado;
    }

    private String pilhaComoTexto(List<Character> pilha) {
        if (pilha.size() == 0) {
            return "pilha vazia";
        }

        StringBuilder texto = new StringBuilder();
        texto.append("topo -> ");
        int i;
        for (i = pilha.size() - 1; i >= 0; i--) {
            texto.append(pilha.get(i));
            if (i > 0) {
                texto.append(" ");
            }
        }
        return texto.toString();
    }
}
