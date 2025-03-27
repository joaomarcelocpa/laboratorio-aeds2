public class BubbleSortWhile {

    // Complexidade no pior caso: O(n^2)
    // Complexidade no melhor caso: O(n)
    public static void bubblesort(int[] A){
        int i;
        boolean trocado;

        do {
            trocado = false;
            for (i =0; i <= (A.length - 2); i++){

                if (A[i] > A[i+1]){// se trocar aqui por < ordena de forma decrescente

                    int temp = A[i];
                    A[i] = A[i+1];
                    A[i+1] = temp;
                    trocado = true;
                }
            }
        } while (trocado == true);
    }


    public static void main(String[] args){
        int A[] = {5, 3, 8, 6, 2};
        bubblesort(A);

        for (int i = 0; i < A.length; i++) {
            System.out.print(A[i] + " ");
        }
    }
}
