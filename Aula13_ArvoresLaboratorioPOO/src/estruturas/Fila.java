package estruturas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Fila<E> {

    private class No {
        E elemento;
        No proximo;

        No(E elemento) {
            this.elemento = elemento;
            this.proximo = null;
        }
    }

    private No inicio;
    private No fim;
    private int tamanho;

    public Fila() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    public void enqueue(E elemento) {
        No novoNo = new No(elemento);

        if (isEmpty()) {
            inicio = fim = novoNo;
        } else {
            fim.proximo = novoNo;
            fim = novoNo;
        }
        tamanho++;
    }

    public E dequeue() {
        if (isEmpty()) {
            return null;
        }

        E elemento = inicio.elemento;
        inicio = inicio.proximo;

        if (inicio == null) {
            fim = null;
        }

        tamanho--;
        return elemento;
    }

    public E front() {
        if (isEmpty()) {
            return null;
        }
        return inicio.elemento;
    }

    public boolean isEmpty() {
        return inicio == null;
    }

    public int tamanho() {
        return tamanho;
    }

    /**
     * Tarefa 1: Calcula o valor médio de um atributo dos primeiros elementos da fila
     * @param extrator função que extrai um valor numérico de cada elemento
     * @param quantidade número de primeiros elementos a serem considerados
     * @return valor médio calculado
     */
    public double calcularValorMedio(Function<E, Double> extrator, int quantidade) {
        if (quantidade <= 0 || isEmpty()) {
            return 0.0;
        }

        int qtdElementos = Math.min(quantidade, tamanho);
        double soma = 0.0;
        No atual = inicio;
        int contador = 0;

        while (atual != null && contador < qtdElementos) {
            Double valor = extrator.apply(atual.elemento);
            if (valor != null) {
                soma += valor;
            }
            atual = atual.proximo;
            contador++;
        }

        return contador > 0 ? soma / contador : 0.0;
    }

    /**
     * Tarefa 2: Filtra os primeiros elementos da fila que satisfazem uma condição
     * @param condicional função que testa se um elemento deve ser incluído
     * @param quantidade número de primeiros elementos a serem testados
     * @return nova fila com elementos filtrados
     */
    public Fila<E> filtrar(Predicate<E> condicional, int quantidade) {
        Fila<E> filaFiltrada = new Fila<>();

        if (quantidade <= 0 || isEmpty()) {
            return filaFiltrada;
        }

        int qtdElementos = Math.min(quantidade, tamanho);
        No atual = inicio;
        int contador = 0;

        while (atual != null && contador < qtdElementos) {
            if (condicional.test(atual.elemento)) {
                filaFiltrada.enqueue(atual.elemento);
            }
            atual = atual.proximo;
            contador++;
        }

        return filaFiltrada;
    }

    public List<E> listarElementos() {
        List<E> lista = new ArrayList<>();
        No atual = inicio;

        while (atual != null) {
            lista.add(atual.elemento);
            atual = atual.proximo;
        }

        return lista;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fila [início -> fim]: ");

        No atual = inicio;
        boolean primeiro = true;

        while (atual != null) {
            if (!primeiro) {
                sb.append(", ");
            }
            sb.append(atual.elemento);
            atual = atual.proximo;
            primeiro = false;
        }

        return sb.toString();
    }
}