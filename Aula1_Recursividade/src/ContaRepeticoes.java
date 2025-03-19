import java.util.Scanner;

public class ContaRepeticoes {

    public static int contaRep(int[] A, int i, int x) {

        if (i < 0) {
            return 0;
        }
        else if (A[i] == x) {
            return 1 + contaRep(A, i-1, x);
        }
        else {
            return contaRep(A, i-1, x);
        }
    }

    public static void main (String args[]){
        Scanner sc = new Scanner(System.in);

        int[] A = {9, 8, 7, 6, 5, 9, 8, 4, 4, 2, 1};
        int i = A.length - 1;
        int x = 0;


        System.out.println("Entre com o valor de x a ser procurado: ");
        x = sc.nextInt();

        int contaRep = contaRep(A, i, x);

        System.out.println("A quantidade de repetições de " + x + " = " + contaRep);

    }
}
