import java.util.Scanner;

public class BuscaElemento {

    public static int pesquisa (int[] A, int n, int x) {

        if (n <= 0) {
            return -1;
        }
        else if (A[n-1] == x) {
            return n-1;
        }
        else {
            return pesquisa(A, n-1, x);
        }
    }

    public static void main (String args[]) {
        Scanner sc = new Scanner(System.in);

        int[] A = {1, 2, 3, 4, 5, 6, 7};
        int n = A.length;
        int x;

        System.out.println("Entre com o valor de x a ser procurado: ");
        x = sc.nextInt();

        int pesquisa = pesquisa(A, n, x);

        System.out.println("A posição no vetor do elemento pesquisado é: " + pesquisa);
    }
}
