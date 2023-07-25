package tabuleiro;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

public class PosicaoTest {

    static Tabuleiro tabuleiro;
    static Posicao posicao;

    @BeforeAll
    @DisplayName("Inicia objetos")
    static void iniciaObjetosParaOsTestes(){
        tabuleiro = new Tabuleiro(8, 8);
    }
}
