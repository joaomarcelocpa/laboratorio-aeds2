public class Main {
    public static void main(String[] args) {

        Lista<String> lista1 = new Lista<>();

        lista1.inserir("10 ");
        lista1.inserir("20 ");
        lista1.inserir("30 ");

        lista1.exibir();

        lista1.remover("30 ");

        lista1.exibir();
    }
}