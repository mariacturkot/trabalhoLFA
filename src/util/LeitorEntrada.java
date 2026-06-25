package util;

import java.util.Scanner;

public class LeitorEntrada {
    private Scanner scanner;

    public LeitorEntrada() {
        scanner = new Scanner(System.in);
    }

    public String lerLinha(String mensagem) {
        System.out.print(mensagem);
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return "";
    }

    public int lerInteiroOuPadrao(String mensagem, int padrao) {
        String texto = lerLinha(mensagem).trim();
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException erro) {
            return padrao;
        }
    }
}
