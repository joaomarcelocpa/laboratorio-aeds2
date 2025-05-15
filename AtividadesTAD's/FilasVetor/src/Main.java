public class Main {
    public static void main(String[] args) {

        FilasVetor fila1 = new FilasVetor(5);

        fila1.inserir(10);
        fila1.inserir(15);
        fila1.inserir(20);
        fila1.inserir(25);

        System.out.println("Fila:");
        fila1.exibir();

        System.out.println();
        System.out.println("Removido: " + fila1.remover());

        System.out.println("Fila:");
        fila1.exibir();

        System.out.println();
        System.out.println("Primeiro elemento da fila:");
        System.out.println(fila1.frente());


    }
}