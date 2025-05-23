package estruturas;

import java.util.ArrayList;
import java.util.List;

public class Pilha<E> {

    private class No {
        E elemento;
        No proximo;

        No(E elemento) {
            this.elemento = elemento;
            this.proximo = null;
        }
    }

    private No topo;
    private int tamanho;

    public Pilha() {
        this.topo = null;
        this.tamanho = 0;
    }

    public void push(E elemento) {
        No novoNo = new No(elemento);
        novoNo.proximo = topo;
        topo = novoNo;
        tamanho++;
    }

    public E pop() {
        if (isEmpty()) {
            return null;
        }

        E elemento = topo.elemento;
        topo = topo.proximo;
        tamanho--;
        return elemento;
    }

    public E top() {
        if (isEmpty()) {
            return null;
        }
        return topo.elemento;
    }

    public boolean isEmpty() {
        return topo == null;
    }

    public int tamanho() {
        return tamanho;
    }

    public Pilha<E> subPilha(int numItens) {
        Pilha<E> novaPilha = new Pilha<>();

        if (numItens <= 0 || numItens > tamanho()) {
            return novaPilha;
        }

        @SuppressWarnings("unchecked")
        E[] elementos = (E[]) new Object[numItens];

        for (int i = 0; i < numItens; i++) {
            if (!isEmpty()) {
                elementos[i] = pop();
            }
        }

        for (int i = numItens - 1; i >= 0; i--) {
            novaPilha.push(elementos[i]);
            push(elementos[i]);
        }

        return novaPilha;
    }

    public List<E> listarElementos() {
        List<E> lista = new ArrayList<>();
        No atual = topo;

        while (atual != null) {
            lista.add(atual.elemento);
            atual = atual.proximo;
        }

        return lista;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pilha [topo -> base]: ");

        No atual = topo;
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