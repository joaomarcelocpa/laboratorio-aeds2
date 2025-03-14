package sorts;

import java.util.Arrays;

public class BubbleSort<T extends Comparable<T>> implements ISorter<T> {

    private int comparisons = 0;
    private int swaps = 0;
    private Util<T> util = new Util<T>();
    private long startTime = 0;
    private long endTime = 0;

    @Override
    public T[] sort(T[] dados) {

        T[] arrayCopy = Arrays.copyOf(dados, dados.length);
        int n = arrayCopy.length;

        startTime();
        for (int i = 1; i < n; i++) { // percorre o array
            for (int j = 0; j < n - i; j++) { // compara todas as duplas
                comparisons++;
                if (arrayCopy[j].compareTo(arrayCopy[j + 1]) > 0) { // se tiver fora de ordem
                    util.swap(arrayCopy, j, j + 1); // troca
                    swaps++;
                }
            }
        }
        finishTime();
        return arrayCopy;
    }

    @Override
    public int getComparisons() {
        return comparisons;
    }

    @Override
    public int getSwaps() {
        return swaps;
    }

    @Override
    public int getTime() {
        return (int) (endTime - startTime);
    }


    private void startTime() {
        startTime = System.nanoTime();
    }

    private void finishTime() {
        endTime = System.nanoTime();
    }
}