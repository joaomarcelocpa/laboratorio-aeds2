package src;

import java.util.Scanner;

public class Futebol {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] skills = new int[n];

        for (int i = 0; i < n; i++) {
            skills[i] = sc.nextInt();
        }
        sc.close();

        int maxTamanhoTime = maiorTimePossivel(skills);

        System.out.println("O tamanho maximo do time: " + maxTamanhoTime);
        System.out.println("A complexidade de tempo total do algoritmo = O(n^2), sendo O(n) para a para a verificao do tamanho do time e O(n^2) para a ordenacao");
    }


    private static int maiorTimePossivel(int[] skills) {
        ordenar(skills);

        int maxTamanho = 0;
        int inicio = 0;

        // percorre o vetor de skills
        for (int fim = 0; fim < skills.length; fim++){

            // enquanto a diferença entre o maior e o menor skill for maior que 5, incrementa o início
            while (skills[fim] - skills[inicio] > 5){
                inicio++;
            }

            // calcula o tamanho atual do time
            int tamanhoAtual = fim - inicio + 1;

            // atualiza o tamanho máximo do time
            if (tamanhoAtual > maxTamanho){
                maxTamanho = tamanhoAtual;
            }
        }
        return maxTamanho;
    }


    private static void ordenar(int[] vetor) {

        int n = vetor.length;
        // Bubble Sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (vetor[j] > vetor[j + 1]) {
                    int temp = vetor[j];
                    vetor[j] = vetor[j + 1];
                    vetor[j + 1] = temp;
                }
            }
        }
    }
}