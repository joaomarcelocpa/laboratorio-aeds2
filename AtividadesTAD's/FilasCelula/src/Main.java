public class Main {
    public static void main(String[] args) {

        Fila fila1 = new Fila();

        fila1.insert(10);
        fila1.insert(20);
        fila1.insert(30);
        fila1.insert(40);


        System.out.println("Elemento removido:" + fila1.remove());
    }

}