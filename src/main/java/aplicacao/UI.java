package aplicacao;

import xadrez.Color;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void limpaTela(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static PosicaoXadrez lePosicaoPecaXadrez(Scanner sc){

        try {
            String s = sc.nextLine();
            char colunaLida = s.charAt(0);
            int linhaLida = Integer.parseInt(s.substring(1));

            return new PosicaoXadrez(colunaLida, linhaLida);
        }
        catch (RuntimeException e){
            throw new InputMismatchException("Erro na leitura de posição, valores válidos são de a1 até h8.");
        }
    }

    public static void imprimePartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturadas){
        imprimeTabuleiro(partidaXadrez.getPecas());
        System.out.println();

        imprimePecasCapturadas(capturadas);
        System.out.println();

        System.out.println("Turno " + partidaXadrez.getTurno());

        if(!partidaXadrez.getCheckMate()){
            System.out.println("Aguardando o jogador das peças " + partidaXadrez.getJogadorAtual());
            if(partidaXadrez.getCheck()){
                System.out.println("CHECK");
            }
        }else {
            System.out.println("CHECKMATE!");
            System.out.println("Vencedor " + partidaXadrez.getJogadorAtual());
        }
    }

    public static void imprimeTabuleiro(PecaXadrez[][] pecas){
        for(int i = 0; i < pecas.length; i++){
            System.out.print((8 - i ) + " ");
            for (int j = 0; j < pecas.length; j++){
                imprimePeca(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void imprimeTabuleiro(PecaXadrez[][] pecas, boolean[][]movimentosPossiveis){
        for(int i = 0; i < pecas.length; i++){
            System.out.print((8 - i) + " ");
            for(int j = 0; j < pecas.length; j++){
                imprimePeca(pecas[i][j], movimentosPossiveis[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void imprimePeca(PecaXadrez peca, boolean background){
        if(background){
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if(peca == null){
            System.out.print("-" + ANSI_RESET);
        }else {
            if(peca.getColor() == Color.BRANCO){
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void imprimePecasCapturadas(List<PecaXadrez> capturadas){
        List<PecaXadrez> pecasBrancas = capturadas.stream().filter(x-> x.getColor() == Color.BRANCO).collect(Collectors.toList());
        List<PecaXadrez> pecasPretas = capturadas.stream().filter(x-> x.getColor() == Color.PRETO).collect(Collectors.toList());

        System.out.println("Peças capturadas: ");
        System.out.print("Brancas: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(pecasBrancas.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Pretas: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(pecasPretas.toArray()));
        System.out.print(ANSI_RESET);
    }
}
