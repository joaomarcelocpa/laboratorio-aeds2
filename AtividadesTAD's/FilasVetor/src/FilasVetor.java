public class FilasVetor {

     int[] dados;
     int frente;
     int tras;
     int capacidade;
     int tamanho;

    public FilasVetor(int capacidade) {
        this.capacidade = capacidade;
        dados = new int [capacidade];
        frente = 0;
        tras = 0;
        tamanho = 0;
    }

    public void inserir(int elemento) {
        if (tamanho == capacidade){
            throw new RuntimeException("Pilha Cheia");
        }
        dados[tras] = elemento;
        tras = (tras+1) % capacidade;
        tamanho++;
    }

    public int remover(){
        if (isEmpty()){
            throw new RuntimeException("Pilha Vazia");
        }
        int elemento = dados[frente]; // adiciona o elemento
        frente = (frente+1) % capacidade; // acha qual a posição para inserir o proximo
        tamanho--; // controla o espaço disponível
        return elemento;
    }



    public boolean isEmpty(){
        return tamanho == 0;
    }

    public int frente() {
        if (isEmpty()){
            throw new RuntimeException("Pilha Vazia");
        }
        return dados[frente];
    }








    public void exibir() {
        if(isEmpty()){
            System.out.println("Pilha vazia");
        }

        int i = frente;
        for (int contador = 0; contador < tamanho; contador++) {
            System.out.print(dados[i] + " ");
            i = (i + 1) % capacidade;
        }
    }
}
