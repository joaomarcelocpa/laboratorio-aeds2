public class Main {
    
    public static void main (String[] args){

        PilhaVetor pilha = new PilhaVetor(5);


        pilha.push(1);
        pilha.push(2);
        pilha.push(3);
        pilha.push(5);
        pilha.push(7);


        System.out.println(pilha.pop());
        System.out.println(pilha.top());

    }
    
}
