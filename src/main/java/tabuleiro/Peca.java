package tabuleiro;

public abstract class Peca {

    protected Posicao posicao;

    private Tabuleiro tabuleiro;

    public Peca(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

//    Operação de movimentos possiveis da peça
    public abstract boolean[][] movimentosPossiveis();

    public boolean movimentoPossivel(Posicao posicao){
        return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
    }

    public boolean isPeloMenosUmMovimentoPossivel(){

        boolean[][] mat = movimentosPossiveis();
        for(int i = 0; i< mat.length; i++){
            for(int j = 0; j < mat.length; j++){
                if(mat[i][j]){
                    return true;
                }
            }
        }

        return false;
    }


}
