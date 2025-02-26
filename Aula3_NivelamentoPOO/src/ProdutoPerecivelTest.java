import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Locale;

public class ProdutoPerecivelTest {

    static ProdutoPerecivel produto;

    @BeforeAll
    static public void prepare(){
        Locale.setDefault(new Locale("pt", "BR"));
        produto = new ProdutoPerecivel("Produto perecível teste", 100, 0.1, LocalDate.now().plusDays(10));
    }

    @Test
    public void calculaPrecoCorretamente(){
        Assertions.assertEquals(110.0, produto.valorDeVenda(), 0.01);
    }

    @Test
    public void aplicaDescontoCorretamente(){
        produto = new ProdutoPerecivel("Produto perecível teste", 100, 0.1, LocalDate.now().plusDays(5));
        Assertions.assertEquals(82.5, produto.valorDeVenda(), 0.01);
    }

    @Test
    public void naoCriaProdutoComDataDeValidadePassada(){
        assertThrows(IllegalArgumentException.class, () -> new ProdutoPerecivel("teste", 100, 0.1, LocalDate.now().minusDays(1)));
    }

    @Test
    public void geraDadosTextoCorretamente(){
        String dadosTexto = produto.gerarDadosTexto();
        assertTrue(dadosTexto.contains("Tipo 2; Produto perecível teste; 100.0; 0.1;"));
    }
}