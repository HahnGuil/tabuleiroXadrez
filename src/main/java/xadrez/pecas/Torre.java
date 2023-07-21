package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez {
    public Torre(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);
    }

    @Override
    public String toString(){
        return "T";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao auxP = new Posicao(0, 0);

//        Verificando acima
        auxP.setValores(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePeca(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
            auxP.setLinha(auxP.getLinha() - 1);
        }
        if(getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//        Verificando para baixo
        auxP.setValores(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePeca(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
            auxP.setLinha(auxP.getLinha() + 1);
        }
        if(getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//        Verificando a esquerda
        auxP.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePeca(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
            auxP.setColuna(auxP.getColuna() - 1);
        }
        if(getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//        Vericando a direita
        auxP.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePeca(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
            auxP.setColuna(auxP.getColuna() + 1);
        }
        if(getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        return mat;
    }
}
