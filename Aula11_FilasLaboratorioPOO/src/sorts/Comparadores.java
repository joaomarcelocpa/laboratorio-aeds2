package sorts;

import produtos.Produto;
import produtos.ProdutoPerecivel;

import java.util.Comparator;

public class Comparadores {

    public static class ComparadorDescricao implements Comparator<Produto> {
        @Override
        public int compare(Produto p1, Produto p2) {
            return p1.descricao.compareTo(p2.descricao);
        }
    }

    public static class ComparadorPreco implements Comparator<Produto> {
        @Override
        public int compare(Produto p1, Produto p2) {
            int resultado = Double.compare(p1.valorDeVenda(), p2.valorDeVenda());

            if (resultado == 0) {
                return p1.compareTo(p2);
            }
            return resultado;
        }
    }

    public static class ComparadorValidade implements Comparator<Produto> {
        @Override
        public int compare(Produto p1, Produto p2) {

            if (p1 instanceof ProdutoPerecivel && p2 instanceof ProdutoPerecivel) {
                ProdutoPerecivel pp1 = (ProdutoPerecivel) p1;
                ProdutoPerecivel pp2 = (ProdutoPerecivel) p2;
                return pp1.getDataDeValidade().compareTo(pp2.getDataDeValidade());
            }
            else if (p1 instanceof ProdutoPerecivel) {
                return -1;
            }
            else if (p2 instanceof ProdutoPerecivel) {
                return 1;
            }
            else {
                return p1.compareTo(p2);
            }
        }
    }
}