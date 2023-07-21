package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

    private PartidaXadrez partidaXadrez;

    public Rei(Tabuleiro tabuleiro, Color color, PartidaXadrez partidaXadrez) {
        super(tabuleiro, color);
        this.partidaXadrez = partidaXadrez;
    }

    public String toString(){
        return "K";
    }

    private boolean testaRooque(Posicao posicao){
        PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);

        return p != null && p instanceof Torre && p.getColor() == getColor() && p.getContadorMovimentos() == 0;
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

//        Rooque
        if(getContadorMovimentos() == 0 && !partidaXadrez.getCheck()){
//            Roque pequeno
            Posicao posicaoTorreUm = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if(testaRooque(posicaoTorreUm)){
                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null){
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }

//            Roque grande
            Posicao posicaoTorreDois = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if(testaRooque(posicaoTorreDois)){

                Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);

                if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null){
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }

        return mat;
    }
}
