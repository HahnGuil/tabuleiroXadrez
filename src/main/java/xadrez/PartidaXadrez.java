package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import tabuleiro.TabuleiroException;
import xadrez.pecas.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidaXadrez {

    private Tabuleiro tabuleiro;
    private int turno;
    private Color jogadorAtual;
    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();
    private boolean check;
    private boolean checkMate;

    public PartidaXadrez(){
        tabuleiro = new Tabuleiro(8,8);
        turno = 1;
        jogadorAtual = Color.BRANCO;
        iniciaPartida();
    }

    public int getTurno() {
        return turno;
    }

    public Color getJogadorAtual() {
        return jogadorAtual;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
    }

    public PecaXadrez[][] getPecas(){
       PecaXadrez[][] partida = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
       for(int i = 0; i < tabuleiro.getLinhas(); i++){
           for (int j = 0; j <tabuleiro.getColunas(); j++){
               partida[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
           }
       }
       return partida;
    }

    private void validaPosicaoOrigem(Posicao posicao){
        if(!tabuleiro.existePeca(posicao)){
            throw new XadrezException("Não existe peça na posição de origem");
        }
        if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getColor()){
            throw new XadrezException("A peça escolhida, não é a sua");
        }
        if(!tabuleiro.peca(posicao).isPeloMenosUmMovimentoPossivel()){
            throw new XadrezException("Não existe movimentos possiveis para está peça");
        }
    }

    private void validaPosicaoDestino(Posicao origem, Posicao destino){
        if(!tabuleiro.peca(origem).movimentoPossivel(destino)){
            throw new XadrezException("A peçca escolhida não pode ser movida para a posição destino");
        }
    }

    private Peca realizaMovimento(Posicao origem, Posicao destino){

        PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(origem);
        p.aumentaContadorMovimento();
        Peca pecaCapturada = tabuleiro.removePeca(destino);
        tabuleiro.colocaPeca(p, destino);

        if(pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }

        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada){
        PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(destino);
        p.diminuiContadorMovimento();
        tabuleiro.colocaPeca(p, origem);

        if(pecaCapturada != null){
            tabuleiro.colocaPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }

    }

    public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){

        Posicao posicao = posicaoOrigem.toPosition();
        validaPosicaoOrigem(posicao);

        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    public PecaXadrez executaMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino){

        Posicao origem = posicaoOrigem.toPosition();
        Posicao destino = posicaoDestino.toPosition();

        validaPosicaoOrigem(origem);
        validaPosicaoDestino(origem, destino);

        Peca pecaCapturada = realizaMovimento(origem, destino);

        if(testaCheck(jogadorAtual)){
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new XadrezException("Você não pode se coloar em check");
        }

//        PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino);

        check = (testaCheck(oponente(jogadorAtual))) ? true : false;

        if(testaCheckMate(oponente(jogadorAtual))){
            checkMate = true;
        }

        proximoTurno();
        return (PecaXadrez) pecaCapturada;
    }

    private void colocaNovaPeca(char coluna, int linha, PecaXadrez peca){
        tabuleiro.colocaPeca(peca, new PosicaoXadrez(coluna, linha).toPosition());
        pecasNoTabuleiro.add(peca);
    }

    private void proximoTurno(){
        turno++;
        jogadorAtual = (jogadorAtual == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
    }

    private  Color oponente(Color color){
        return (color == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
    }

    private PecaXadrez rei(Color color){
        List<Peca> listaPecas = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez)x).getColor() == color).collect(Collectors.toList());
        for(Peca p : listaPecas){
            if(p instanceof Rei){
                return (PecaXadrez) p;
            }
        }
        throw new IllegalStateException("Não existe o rei " + color + " no tabuleiroi");
    }

    private boolean testaCheckMate(Color color){
        if(!testaCheck(color)){
            return false;
        }
        List<Peca> listaPecas = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez)x).getColor() == color).collect(Collectors.toList());
        for(Peca p : listaPecas){
            boolean[][] mat = p.movimentosPossiveis();
            for(int i = 0; i < mat.length; i++){
                for (int j = 0; j < mat.length; j++){
                    if(mat[i][j]){
                        Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().toPosition();
                        Posicao destino = new Posicao(i, j);

                        Peca pecaCapturada = realizaMovimento(origem, destino);
                        boolean testaCheck = testaCheck(color);
                        desfazerMovimento(origem, destino, pecaCapturada);

                        if(!testaCheck){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean testaCheck(Color color){
        Posicao posicaoRei = rei(color).getPosicaoXadrez().toPosition();
        List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getColor() == color).collect(Collectors.toList());

        for(Peca p : pecasOponente){
            boolean[][] mat = p.movimentosPossiveis();
            if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
                    return true;
            }
        }
        return false;
    }

    private void iniciaPartida(){
//        Peças brancas

        colocaNovaPeca('a', 1, new Torre(tabuleiro, Color.BRANCO));
        colocaNovaPeca('h', 1, new Torre(tabuleiro, Color.BRANCO));

        colocaNovaPeca('e', 1, new Rei(tabuleiro, Color.BRANCO));
        colocaNovaPeca('d', 1, new Rainha(tabuleiro, Color.BRANCO));

        colocaNovaPeca('f', 1, new Bispo(tabuleiro, Color.BRANCO));
        colocaNovaPeca('c', 1, new Bispo(tabuleiro, Color.BRANCO));

        colocaNovaPeca('g', 1, new Cavalo(tabuleiro, Color.BRANCO));
        colocaNovaPeca('b', 1, new Cavalo(tabuleiro, Color.BRANCO));
//
        colocaNovaPeca('a', 2, new Peao(tabuleiro, Color.BRANCO, this));
        colocaNovaPeca('b', 2, new Peao(tabuleiro, Color.BRANCO, this));
        colocaNovaPeca('c', 2, new Peao(tabuleiro, Color.BRANCO, this));
        colocaNovaPeca('d', 2, new Peao(tabuleiro, Color.BRANCO, this));
        colocaNovaPeca('e', 2, new Peao(tabuleiro, Color.BRANCO, this));
        colocaNovaPeca('f', 2, new Peao(tabuleiro, Color.BRANCO, this));
        colocaNovaPeca('g', 2, new Peao(tabuleiro, Color.BRANCO, this));
        colocaNovaPeca('h', 2, new Peao(tabuleiro, Color.BRANCO, this));
//
////        Peças Pretas
//
        colocaNovaPeca('a', 8, new Torre(tabuleiro, Color.PRETO));
        colocaNovaPeca('h', 8, new Torre(tabuleiro, Color.PRETO));

        colocaNovaPeca('e', 8, new Rei(tabuleiro, Color.PRETO));
        colocaNovaPeca('d', 8, new Rainha(tabuleiro, Color.PRETO));

        colocaNovaPeca('f', 8, new Bispo(tabuleiro, Color.PRETO));
        colocaNovaPeca('c', 8, new Bispo(tabuleiro, Color.PRETO));

        colocaNovaPeca('g', 8, new Cavalo(tabuleiro, Color.PRETO));
        colocaNovaPeca('b', 8, new Cavalo(tabuleiro, Color.PRETO));
//
        colocaNovaPeca('a', 7, new Peao(tabuleiro, Color.PRETO, this));
        colocaNovaPeca('b', 7, new Peao(tabuleiro, Color.PRETO, this));
        colocaNovaPeca('c', 7, new Peao(tabuleiro, Color.PRETO, this));
        colocaNovaPeca('d', 7, new Peao(tabuleiro, Color.PRETO, this));
        colocaNovaPeca('e', 7, new Peao(tabuleiro, Color.PRETO, this));
        colocaNovaPeca('f', 7, new Peao(tabuleiro, Color.PRETO, this));
        colocaNovaPeca('g', 7, new Peao(tabuleiro, Color.PRETO, this));
        colocaNovaPeca('h', 7, new Peao(tabuleiro, Color.PRETO, this));





    }

}
