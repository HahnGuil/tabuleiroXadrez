package aplicacao;

import tabuleiro.Peca;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Programa {
//    Projeto feito a partir das aulas do curso https://www.udemy.com/course/java-curso-completo/ no capitulo 16;
    public static void main(String[] args) {
//      Declarações: Inicia-se o scanner e a partida de Xadrez;
        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();
        List<PecaXadrez> pecasCapturadas = new ArrayList<>();


//      While que inicia as operações de receber as posições das peças
        while (!partidaXadrez.getCheckMate()){

            try {
                UI.limpaTela();
                UI.imprimePartida(partidaXadrez, pecasCapturadas);
                System.out.println();

                System.out.print("Posição de origem: ");
                PosicaoXadrez origem = UI.lePosicaoPecaXadrez(sc);

                boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
                UI.limpaTela();
                UI.imprimeTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);

                System.out.print("Posição de destino: ");
                PosicaoXadrez destino = UI.lePosicaoPecaXadrez(sc);

                PecaXadrez pecaCapturada = partidaXadrez.executaMovimentoXadrez(origem, destino);

                if(pecaCapturada != null){
                    pecasCapturadas.add(pecaCapturada);
                }

                if(partidaXadrez.getPromocao() != null){
                    System.out.print("Entre com a peça a ser promovida: (B/C/Q/T): ");
                    String tipo = sc.nextLine().toUpperCase();
                    while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")){
                        System.out.print("Valor Invalido digite uma das 4 peças: (B/C/Q/T): ");
                        tipo = sc.nextLine().toUpperCase();
                    }
                    partidaXadrez.replacePecaPromovida(tipo);
                }

            }
            catch (XadrezException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.limpaTela();
        UI.imprimePartida(partidaXadrez, pecasCapturadas);

    }
}
