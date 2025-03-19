public class SomaVetor {

    public static double somaElementos(int[] A, int i) {

        if (i<0) {
            return 0;
        } else {
            return A[i] + somaElementos(A, i-1);
        }

    }


    public static void main(String[] args) {

        int[] A = {9, 8, 7, 6, 5};
        int i = A.length - 1;

        double somaElementos  = somaElementos(A, i);

        System.out.println("A soma dos elementos do vetor é: " + somaElementos);

    }
}
