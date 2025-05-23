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

    public void enfileirar(E elemento) {
        No novoNo = new No(elemento);

        if (isEmpty()) {
            inicio = fim = novoNo;
        } else {
            fim.proximo = novoNo;
            fim = novoNo;
        }
        tamanho++;
    }

    public E desenfileirar() {
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

    public E frente() {
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

    public double calcularValorMedio(Function<E, Double> extrator, int quantidade) {
        if (quantidade <= 0 || isEmpty()) {
            return 0.0;
        }

        double soma = 0.0;
        int contador = 0;
        No atual = inicio;

        while (atual != null && contador < quantidade) {
            Double valor = extrator.apply(atual.elemento);
            if (valor != null) {
                soma += valor;
            }
            atual = atual.proximo;
            contador++;
        }

        return contador > 0 ? soma / contador : 0.0;
    }

    public Fila<E> filtrar(Predicate<E> condicional, int quantidade) {
        Fila<E> filaFiltrada = new Fila<>();

        if (quantidade <= 0 || isEmpty()) {
            return filaFiltrada;
        }

        No atual = inicio;
        int contador = 0;

        while (atual != null && contador < quantidade) {
            if (condicional.test(atual.elemento)) {
                filaFiltrada.enfileirar(atual.elemento);
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
        sb.append("Fila [inicio -> fim]: ");

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