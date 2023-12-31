package tabuleiro;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private Peca [][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if(linhas < 1 || colunas < 1){
            throw new TabuleiroException("Erro na criação do tabuleiro, é necessário que tenha ao menos uma linha e uma coluna");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca (int linha, int coluna){
        if(!existePosicao(linha, coluna)){
            throw new TabuleiroException("Posição não existe no tabuleiro");
        }
        return pecas[linha][coluna];
    }

    public Peca peca (Posicao posicao){
        if(!existePosicao(posicao)){
            throw new TabuleiroException("Posiçã não existe no tabuleiro");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void colocaPeca(Peca peca, Posicao posicao){
        if(existePeca(posicao)){
            throw new TabuleiroException("Já existe uma peça nesta posição" + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    public Peca removePeca(Posicao posicao){
        if(!existePosicao(posicao)){
            throw new TabuleiroException("Essa posição não existe no tabuleiro");
        }

        if(peca(posicao) == null){
            return null;
        }

        Peca aux = peca(posicao);
        aux.posicao = null;
        pecas[posicao.getLinha()][posicao.getColuna()] = null;
        return aux;

    }

    private boolean existePosicao(int coluna, int linha){
        return (linha >= 0 && linha < linhas) && (coluna >= 0 && coluna < colunas);
    }

    public boolean existePosicao(Posicao posicao){
        return existePosicao(posicao.getLinha(), posicao.getColuna());
    }

    public boolean existePeca(Posicao posicao){
        if(!existePosicao(posicao)){
            throw new TabuleiroException("Posiçã não existe no tabuleiro");
        }
        return peca(posicao) != null;
    }
}
