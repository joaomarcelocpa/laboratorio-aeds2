public class SomaFatorial {

    public static int somaFat(int n){

        if (n == 0){  //  0! = 1
            return 1;
        } else {
            return n * (somaFat(n-1));
        }
    }


    public static void main (String args[]){

        int n = 5;

        int somaFat = somaFat(n);

        System.out.println("A soma do fatorial de " + n + " = " + somaFat);

    }
}
