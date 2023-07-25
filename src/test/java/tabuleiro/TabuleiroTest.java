package tabuleiro;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

public class TabuleiroTest {

    static Tabuleiro tabuleiro;

    @BeforeAll
    @DisplayName("Inicia Tabuleiro")
    static void iniciaTabuleiro(){
        tabuleiro = new Tabuleiro(8, 8);
    }

    @Test
    @DisplayName("Testa linhas tabuleiro")
    public void testaQuantidadeLinhas(){
        assertTrue(tabuleiro.getLinhas() == 8);
    }

    @Test
    @DisplayName("Testa colunas Tabuleiro")
    public void testaQuantidadeColunas(){
        assertTrue(tabuleiro.getColunas() == 8);
    }

    @Test
    @DisplayName("Testa tabuleiro completo")
    public void testaTabuleiro(){
        assertTrue(tabuleiro.getLinhas() == 8 && tabuleiro.getColunas() == 8);
    }


}
