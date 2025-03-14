package sorts;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int size = 20;
        Integer[] array = new Integer[size];
        Random random = new Random();

        // Gerar vetor de inteiros aleatórios
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000); // valores entre 0 e 999
        }

        // Instanciar BubbleSort e ordenar o vetor
        BubbleSort<Integer> bubbleSort = new BubbleSort<>();
        Integer[] sortedArray = bubbleSort.sort(array);

        // Exibir resultados
        System.out.println("Vetor ordenado: ");
        for (int num : sortedArray) {
            System.out.print(num + " ");
        }
        System.out.println("\nComparações: " + bubbleSort.getComparisons());
        System.out.println("Trocas: " + bubbleSort.getSwaps());
        System.out.println("Tempo (ns): " + bubbleSort.getTime());
    }
}