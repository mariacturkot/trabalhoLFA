package menu;

import java.util.ArrayList;
import java.util.List;

import chomsky.HierarquiaChomsky;
import chomsky.Tipo0RecursivamenteEnumeravel;
import chomsky.Tipo1SensivelContexto;
import chomsky.Tipo2LivreContexto;
import chomsky.Tipo3Regular;
import linguagem.Linguagem;
import resultado.PassoSimulacao;
import resultado.ResultadoSimulacao;
import util.LeitorEntrada;

public class MenuPrincipal {
    private List<HierarquiaChomsky> hierarquias;
    private LeitorEntrada leitor;

    public MenuPrincipal() {
        leitor = new LeitorEntrada();
        hierarquias = new ArrayList<HierarquiaChomsky>();
        hierarquias.add(new Tipo3Regular());
        hierarquias.add(new Tipo2LivreContexto());
        hierarquias.add(new Tipo1SensivelContexto());
        hierarquias.add(new Tipo0RecursivamenteEnumeravel());
    }

    public void iniciar() {
        boolean sair = false;

        while (!sair) {
            exibirMenuPrincipal();
            int opcao = leitor.lerInteiroOuPadrao("Escolha uma opcao: ", -1);

            if (opcao == 0) {
                sair = true;
                System.out.println("Encerrando o simulador.");
            } else if (opcao >= 1 && opcao <= hierarquias.size()) {
                abrirHierarquia(hierarquias.get(opcao - 1));
            } else {
                System.out.println("Opcao invalida. Tente novamente.");
            }
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println(" Simulador da Hierarquia de Chomsky");
        System.out.println("==============================================");
        System.out.println("1 - Tipo 3: Linguagens Regulares");
        System.out.println("2 - Tipo 2: Linguagens Livres de Contexto");
        System.out.println("3 - Tipo 1: Linguagens Sensiveis ao Contexto");
        System.out.println("4 - Tipo 0: Linguagens Recursivamente Enumeraveis");
        System.out.println("0 - Sair");
    }

    private void abrirHierarquia(HierarquiaChomsky hierarquia) {
        boolean voltar = false;

        while (!voltar) {
            System.out.println();
            System.out.println("----------------------------------------------");
            System.out.println(hierarquia.getNome());
            System.out.println(hierarquia.getDescricao());
            System.out.println("----------------------------------------------");
            listarLinguagens(hierarquia);
            System.out.println("0 - Voltar ao menu principal");

            int opcao = leitor.lerInteiroOuPadrao("Escolha uma linguagem: ", -1);
            if (opcao == 0) {
                voltar = true;
            } else {
                Linguagem linguagem = hierarquia.buscarLinguagem(opcao);
                if (linguagem == null) {
                    System.out.println("Linguagem invalida. Tente novamente.");
                } else {
                    testarLinguagem(linguagem);
                }
            }
        }
    }

    private void listarLinguagens(HierarquiaChomsky hierarquia) {
        List<Linguagem> linguagens = hierarquia.getLinguagens();
        int i;
        for (i = 0; i < linguagens.size(); i++) {
            Linguagem linguagem = linguagens.get(i);
            System.out.println(linguagem.getCodigo() + " - " + linguagem.getNome());
            System.out.println("  Descricao: " + linguagem.getDescricao());
            System.out.println("  Regra: " + linguagem.getRegra());
            System.out.println("  Alfabeto: " + linguagem.getAlfabeto());
            System.out.println("  Exemplos aceitos: "
                    + listaComoTexto(linguagem.getExemplosAceitos()));
            System.out.println("  Exemplos rejeitados: "
                    + listaComoTexto(linguagem.getExemplosRejeitados()));
            System.out.println();
        }
    }

    private void testarLinguagem(Linguagem linguagem) {
        boolean voltar = false;

        while (!voltar) {
            System.out.println();
            System.out.println("Digite a cadeia para testar.");
            System.out.println("ENTER ou epsilon = cadeia vazia | V = voltar para as linguagens");
            String cadeia = leitor.lerLinha("Cadeia: ");

            if (cadeia.equalsIgnoreCase("v") || cadeia.equalsIgnoreCase("voltar")) {
                voltar = true;
            } else {
                if (cadeia.equalsIgnoreCase("epsilon")) {
                    cadeia = "";
                }

                ResultadoSimulacao resultado = linguagem.getSimulador().simular(linguagem, cadeia);
                mostrarResultado(cadeia, resultado);

                String continuar = leitor.lerLinha("Testar outra cadeia nesta linguagem? (s/n): ");
                if (!continuar.equalsIgnoreCase("s")) {
                    voltar = true;
                }
            }
        }
    }

    private void mostrarLinguagem(Linguagem linguagem) {
        System.out.println();
        System.out.println("Linguagem: " + linguagem.getNome());
        System.out.println("Descricao: " + linguagem.getDescricao());
        System.out.println("Regra: " + linguagem.getRegra());
        System.out.println("Alfabeto: " + linguagem.getAlfabeto());
        System.out.print("Exemplos aceitos: ");
        mostrarLista(linguagem.getExemplosAceitos());
        System.out.print("Exemplos rejeitados: ");
        mostrarLista(linguagem.getExemplosRejeitados());
    }

    private void mostrarResultado(String cadeia, ResultadoSimulacao resultado) {
        System.out.println();
        System.out.println("Cadeia testada: " + mostrarVazio(cadeia));

        System.out.println();
        System.out.println("Transicoes, regras ou operacoes usadas:");
        if (resultado.getOperacoes().isEmpty()) {
            System.out.println("- Nenhuma operacao registrada.");
        } else {
            for (int i = 0; i < resultado.getOperacoes().size(); i++) {
                System.out.println("- " + resultado.getOperacoes().get(i));
            }
        }

        System.out.println();
        System.out.println("Simulacao passo a passo:");
        List<PassoSimulacao> passos = resultado.getPassos();
        for (int i = 0; i < passos.size(); i++) {
            PassoSimulacao passo = passos.get(i);
            System.out.println("Passo " + passo.getNumero());
            System.out.println("  Descricao: " + passo.getDescricao());
            System.out.println("  Estado atual: " + passo.getEstadoAtual());
            System.out.println("  Cadeia atual: " + passo.getCadeiaAtual());
            System.out.println("  Estrutura auxiliar: " + passo.getEstruturaAuxiliar());
        }

        System.out.println();
        System.out.println("Resultado final: " + resultado.getMensagemFinal());
        if (resultado.isPertence()) {
            System.out.println("Conclusao: a cadeia pertence a linguagem.");
        } else {
            System.out.println("Conclusao: a cadeia nao pertence a linguagem.");
        }
    }

    private void mostrarLista(List<String> itens) {
        System.out.println(listaComoTexto(itens));
    }

    private String listaComoTexto(List<String> itens) {
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < itens.size(); i++) {
            texto.append(itens.get(i));
            if (i < itens.size() - 1) {
                texto.append(", ");
            }
        }
        return texto.toString();
    }

    private String mostrarVazio(String texto) {
        if (texto.isEmpty()) {
            return "epsilon";
        }
        return texto;
    }
}
