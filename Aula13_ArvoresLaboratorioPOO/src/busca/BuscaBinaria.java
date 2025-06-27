package busca;

import produtos.Produto;

public class BuscaBinaria<T> implements IBuscador<T> {
    private long comparacoes;
    private long tempoNanos;
    private T[] dados;

    public BuscaBinaria(T[] dados) {
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
        int esquerda = 0;
        int direita = dados.length - 1;

        while (esquerda <= direita) {
            int meio = esquerda + (direita - esquerda) / 2;
            comparacoes++;

            if (dados[meio] instanceof Produto) {
                Produto produto = (Produto) dados[meio];
                String descricaoProduto = produto.descricao.toLowerCase();

                if (descricaoProduto.contains(termoBusca)) {
                    resultado = dados[meio];

                    if (descricaoProduto.equals(termoBusca)) {
                        break;
                    }

                    for (int i = meio - 1; i >= 0; i--) {
                        comparacoes++;
                        if (dados[i] instanceof Produto) {
                            Produto prodEsq = (Produto) dados[i];
                            if (prodEsq.descricao.toLowerCase().contains(termoBusca)) {
                                if (prodEsq.descricao.toLowerCase().equals(termoBusca)) {
                                    resultado = dados[i];
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }

                    for (int i = meio + 1; i < dados.length; i++) {
                        comparacoes++;
                        if (dados[i] instanceof Produto) {
                            Produto prodDir = (Produto) dados[i];
                            if (prodDir.descricao.toLowerCase().contains(termoBusca)) {
                                if (prodDir.descricao.toLowerCase().equals(termoBusca)) {
                                    resultado = dados[i];
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                    break;
                }

                int comparacao = descricaoProduto.compareTo(termoBusca);
                if (comparacao < 0) {
                    esquerda = meio + 1;
                } else {
                    direita = meio - 1;
                }
            }
        }

        long fim = System.nanoTime();
        tempoNanos = fim - inicio;

        return resultado;
    }
}