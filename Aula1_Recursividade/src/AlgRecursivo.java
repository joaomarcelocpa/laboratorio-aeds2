import java.util.Scanner;

public class AlgRecursivo {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        lerInput();
    }

    private static void lerInput() {

        System.out.println("Digite um número inteiro positivo: ");
        int start = sc.nextInt();
        int limite = sc.nextInt();

        int somaParesIter = somaParesIter(limite);
        int somaParesRec = somaParesRec(limite);
        int somaParesRecDo0 = somaParesRecDo0(start, limite);


        System.out.println("A soma de todos os números pares até " + limite + " é: " + somaParesIter);
        System.out.println("A soma de todos os números pares até " + limite + " é: " + somaParesRec);
        System.out.println("A soma de todos os números pares de " + start + " até " + limite + " é: " + somaParesRecDo0);

    }

    // Utilizando a recursividade de pensando do limite até 0 (0 é o caso base)

    private static int somaParesRec(int limite) {

        if (limite == 0) {
            return 0;
        }

        if (limite % 2 == 0) {
            return limite + somaParesRec(limite - 2);
        }

        return somaParesRec(limite - 1);
    }


    // Utilizando recursividade de pensando do start (caso base definido pelo usuário) até o limite

    private static int somaParesRecDo0(int start, int limite) {

        if (start > limite) {
            return 0;
        }

        if (start % 2 == 0) {
            return start + somaParesRecDo0(start + 2, limite);
        }

        return somaParesRecDo0(start + 1, limite);

    }


    // Utilizando laço de repetição for
    private static int somaParesIter(int limite) {

        int res = 0;

        for (int i = 0; i <= limite; i++) {
            if (i % 2 == 0) {
                res = res + i;
            }
        }
        return res;
    }
}
