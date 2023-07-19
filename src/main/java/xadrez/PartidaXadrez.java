package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import tabuleiro.TabuleiroException;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

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

        Peca p = tabuleiro.removePeca(origem);
        Peca pecaCapturada = tabuleiro.removePeca(destino);
        tabuleiro.colocaPeca(p, destino);

        if(pecaCapturada != null){
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }

        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada){
        Peca p = tabuleiro.removePeca(destino);
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


    }

}
