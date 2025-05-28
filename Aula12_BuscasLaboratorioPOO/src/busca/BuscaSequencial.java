package busca;

import produtos.Produto;

public class BuscaSequencial<T> implements IBuscador<T> {
    private long comparacoes;
    private long tempoNanos;
    private T[] dados;

    public BuscaSequencial(T[] dados) {
        this.dados = dados;
        this.comparacoes = 0;
        this.tempoNanos = 0;
    }

    @Override
    public long getComparacoes() {
        return comparacoes;
    }

    @Override
    public double getTempo() {
        return tempoNanos;
    }

    @Override
    public T buscar(String nome) {
        String termoBusca = nome.toLowerCase();
        comparacoes = 0;

        long inicio = System.nanoTime();

        T resultado = null;

        for (int i = 0; i < dados.length; i++) {
            comparacoes++;
            if (dados[i] != null) {
                if (dados[i] instanceof Produto) {
                    Produto produto = (Produto) dados[i];
                    if (produto.descricao.toLowerCase().contains(termoBusca)) {
                        resultado = dados[i];
                        break;
                    }
                }
            }
        }

        long fim = System.nanoTime();
        tempoNanos = fim - inicio;

        return resultado;
    }
}