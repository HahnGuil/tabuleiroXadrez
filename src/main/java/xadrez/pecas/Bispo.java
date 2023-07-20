package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez {

    public Bispo(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao auxP = new Posicao(0, 0);
//        Verificando acima esquerda
        auxP.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        while (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePeca(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
            auxP.setValores(auxP.getLinha() - 1, auxP.getColuna() - 1);
        }
        if(getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//        Verificando acima direita
        auxP.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        while (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePeca(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
            auxP.setValores(auxP.getLinha() - 1, auxP.getColuna() + 1);
        }
        if(getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//        Verificando abaixo esquerda
        auxP.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        while (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePosicao(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
            auxP.setValores(auxP.getLinha() + 1, auxP.getColuna() - 1);
        }
        if(getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//        Vericando abaixo direita
        auxP.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        while (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePosicao(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
            auxP.setValores(auxP.getLinha() + 1, auxP.getColuna() + 1);
        }
        if(getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        return mat;
    }

    @Override
    public String toString(){
        return "B";
    }
}
