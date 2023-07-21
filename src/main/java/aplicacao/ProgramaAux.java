package aplicacao;

import xadrez.Color;
import xadrez.pecas.Peao;

public class ProgramaAux {
    public static void main(String[] args) {
//        colocaNovaPeca('a', 1, new Peao(tabuleiro, Color.BRANCO));
//        colocaNovaPeca(variavel, 2, new Peao(tabuleiro, Color.BRANCO));
//        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'

        String letras = "abcdefgh";

        for(int i = 0; i < 8; i++){
            System.out.println("colocaNovaPeca('" + letras.charAt(i) + "', 2, new Peao(tabuleiro, Color.BRANCO));");
        }
    }
}
