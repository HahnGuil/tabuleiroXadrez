package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Color;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

    private PartidaXadrez partidaXadrez;

    public Peao(Tabuleiro tabuleiro, Color color, PartidaXadrez partidaXadrez) {
        super(tabuleiro, color);
        this.partidaXadrez = partidaXadrez;
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
        Posicao auxP = new Posicao(0, 0);

        if (getColor() == Color.BRANCO) {
//            Testa valores a frente, do peão
            auxP.setValores(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePeca(auxP)) {
                mat[auxP.getLinha()][auxP.getColuna()] = true;
            }
//            Testa primeiro movimento do peão
            auxP.setValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePeca(auxP) && getTabuleiro().existePosicao(p2) && getTabuleiro().existePeca(p2) && getContadorMovimentos() == 0) {
                mat[auxP.getLinha()][auxP.getColuna()] = true;
            }
//            Testa movimento de ataque do peão para a esquerda
            auxP.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)) {
                mat[auxP.getLinha()][auxP.getColuna()] = true;
            }
//            Testa movimento de atque do peão a direita
            auxP.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if (getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)) {
                mat[auxP.getLinha()][auxP.getColuna()] = true;
            }
        }else {
            //  Testa valores a frente, do peão
            auxP.setValores(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePeca(auxP)) {
                mat[auxP.getLinha()][auxP.getColuna()] = true;
            }
//            Testa primeiro movimento do peão
            auxP.setValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().existePosicao(auxP) && !getTabuleiro().existePeca(auxP) && getTabuleiro().existePosicao(p2) && getTabuleiro().existePeca(p2) && getContadorMovimentos() == 0) {
                mat[auxP.getLinha()][auxP.getColuna()] = true;
            }
//            Testa movimento de ataque do peão para a esquerda
            auxP.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)) {
                mat[auxP.getLinha()][auxP.getColuna()] = true;
            }
//            Testa movimento de atque do peão a direita
            auxP.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if (getTabuleiro().existePosicao(auxP) && isPecaOponente(auxP)) {
                mat[auxP.getLinha()][auxP.getColuna()] = true;
            }
        }

        return mat;
    }

    @Override
    public String toString(){
        return "P";
    }
}
