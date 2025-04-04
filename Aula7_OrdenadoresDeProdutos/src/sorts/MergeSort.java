package sorts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort<T extends Comparable<T>> implements ISorter<T> {

    @Override
    public T[] sort(T[] array) {
        return sort(array, Comparable::compareTo);
    }

    @Override
    public T[] sort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length <= 1) {
            return array;
        }

        T[] resultado = array.clone();
        mergeSort(resultado, 0, resultado.length - 1, comparator);

        return resultado;
    }

    private void mergeSort(T[] array, int inicio, int fim, Comparator<T> comparator) {
        if (inicio < fim) {
            int meio = inicio + (fim - inicio) / 2;

            mergeSort(array, inicio, meio, comparator);
            mergeSort(array, meio + 1, fim, comparator);
            merge(array, inicio, meio, fim, comparator);
        }
    }

    private void merge(T[] array, int inicio, int meio, int fim, Comparator<T> comparator) {

        int tamanhoEsquerda = meio - inicio + 1;
        int tamanhoDireita = fim - meio;

        List<T> listaEsquerda = new ArrayList<>(tamanhoEsquerda);
        List<T> listaDireita = new ArrayList<>(tamanhoDireita);

        for (int i = 0; i < tamanhoEsquerda; i++) {
            listaEsquerda.add(array[inicio + i]);
        }
        for (int j = 0; j < tamanhoDireita; j++) {
            listaDireita.add(array[meio + 1 + j]);
        }

        int i = 0, j = 0;
        int k = inicio;

        while (i < tamanhoEsquerda && j < tamanhoDireita) {
            if (comparator.compare(listaEsquerda.get(i), listaDireita.get(j)) <= 0) {
                array[k] = listaEsquerda.get(i);
                i++;
            } else {
                array[k] = listaDireita.get(j);
                j++;
            }
            k++;
        }

        while (i < tamanhoEsquerda) {
            array[k] = listaEsquerda.get(i);
            i++;
            k++;
        }

        while (j < tamanhoDireita) {
            array[k] = listaDireita.get(j);
            j++;
            k++;
        }
    }
}