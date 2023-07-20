package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez {
    public Cavalo(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);
    }

    private boolean podeMover(Posicao posicao){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao auxP = new Posicao(0, 0);


        auxP.setValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        auxP.setValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        auxP.setValores(posicao.getLinha() - 2 , posicao.getColuna() + 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        auxP.setValores(posicao.getLinha() - 1 , posicao.getColuna() + 2);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        auxP.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        auxP.setValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        auxP.setValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        auxP.setValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        return mat;
    }

    @Override
    public String toString(){
        return "C";
    }
}
