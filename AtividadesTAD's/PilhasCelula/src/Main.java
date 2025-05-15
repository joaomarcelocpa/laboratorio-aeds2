public class Main {

    public static void main (String[] args){

        Pilha<String> pilha1 = new Pilha<>();
        Pilha<String> pilha2 = new Pilha<>();

        pilha1.push("10");
        pilha1.push("20");
        pilha1.push("30");
        pilha2.push("40");
        pilha2.push("50");
        pilha2.push("60");

//        System.out.println("Pilha antes da inversão:");
//        pilha.exibir();
//        System.out.println();
//        System.out.println("Pilha após inversão:");
//        pilha.inverter(pilha);
//
//
//        int total = pilha.obterNumeroElementos();
//        System.out.println();
//        System.out.println("Numero de elementos da pilha 1: " + total);
//
//
//
//        System.out.println("Elemento removido:" + pilha.pop());
//        System.out.println("Novo topo:" + pilha.top());
//
//        System.out.println("Pilha após remoção:");
//        pilha.exibir();


        System.out.println();
        System.out.println("Pilha após concatenação:");
        pilha1.concatenar(pilha2);
        pilha1.exibir();
    }

}
