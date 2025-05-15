public class PilhaVetor {

    int topo;
    int[] dados;

    public PilhaVetor(int capacidade){
        topo = 0;
        dados = new int[capacidade];
    }

    public void push(int elemento){

        if (topo==dados.length){
            System.out.println("Pilha cheia");
        }
        else {
            dados[topo] = elemento;
            topo++;
        }
    }


    public int pop(){

        if (topo==0){
            System.out.println("Pilha Vazia");
        }
        topo--;
        return dados[topo];
    }


    public int top() {
        if (topo==0) {
            System.out.println("Pilha vazia");
        }
        return dados[topo-1];
    }


}
