package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

    private Color color;
    private int contadorMovimentos;
    public PecaXadrez(Tabuleiro tabuleiro, Color color) {
        super(tabuleiro);
        this.color = color;
        contadorMovimentos = 0;
    }

    public Color getColor() {
        return color;
    }

    protected boolean isPecaOponente(Posicao posicao){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p != null && p.getColor() != color;
    }

    public int getContadorMovimentos() {
        return contadorMovimentos;
    }

    protected void aumentaContadorMovimento(){
        contadorMovimentos++;
    }

    protected void diminuiContadorMovimento(){
        contadorMovimentos--;
    }


    public PosicaoXadrez getPosicaoXadrez(){
        return PosicaoXadrez.posicaoOrigem(posicao);
    }

}
