package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {
    public Rei(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro, color);
    }

    public String toString(){
        return "K";
    }

    private boolean podeMover(Posicao posicao){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao auxP = new Posicao(0, 0);

//        Acima
        auxP.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//       Abaixo
        auxP.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//      Esquerda
        auxP.setValores(posicao.getLinha(), posicao.getColuna()  - 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//      Direita
        auxP.setValores(posicao.getLinha(), posicao.getColuna()  + 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//      Noroeste
        auxP.setValores(posicao.getLinha()   - 1, posicao.getColuna()  - 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//      Nordeste
        auxP.setValores(posicao.getLinha() - 1, posicao.getColuna()  + 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//      Suldoeste
        auxP.setValores(posicao.getLinha()   + 1, posicao.getColuna() - 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

//      Sudeste
        auxP.setValores(posicao.getLinha()   + 1, posicao.getColuna()  + 1);
        if(getTabuleiro().existePosicao(auxP) && podeMover(auxP)){
            mat[auxP.getLinha()][auxP.getColuna()] = true;
        }

        return mat;
    }
}
