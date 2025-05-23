package testes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import produtos.Produto;
import produtos.ProdutoNaoPerecivel;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class ProdutoNaoPerecivelTest {

    static Produto produto;

    @BeforeAll
    static public void prepare(){
        Locale.setDefault(new Locale("pt", "BR"));
        produto = new ProdutoNaoPerecivel("produtos.Produto teste", 100, 0.1);
    }

    @Test
    public void calculaPrecoCorretamente(){
        Assertions.assertEquals(110.0, produto.valorDeVenda(), 0.01);
    }

    @Test
    public void naoCriaProdutoComPrecoNegativo(){
        assertThrows(IllegalArgumentException.class, () -> new ProdutoNaoPerecivel("teste", -5, 0.5));
    }

    @Test
    public void stringComDescricaoEValor(){
        String desc = produto.toString();
        assertTrue(desc.contains("produtos.Produto teste") && desc.contains("R$") && desc.contains("110,00"));
    }

    @Test
    public void naoCriaProdutoComMargemNegativa(){
        assertThrows(IllegalArgumentException.class, () -> new ProdutoNaoPerecivel("teste", 5, -1));
    }

    @Test
    public void geraDadosTextoCorretamente(){
        String dadosTexto = produto.gerarDadosTexto();
        assertTrue(dadosTexto.contains("Tipo 1; produtos.Produto teste; 100.0; 0.1;"));
    }

}