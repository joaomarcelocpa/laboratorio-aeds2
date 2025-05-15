public class Pilha<T> {

    private Celula<T> topo;

    public Pilha() {
        topo = null;
    }

    public void push(T elemento){
        Celula<T> novo = new Celula<>(elemento);
        novo.prox = topo;
        topo = novo;
    }

    public T pop() {
        if (topo == null) {
            throw new RuntimeException("Pilha vazia");
        }

        T elemento = topo.elemento;
        topo = topo.prox;
        return elemento;
    }

    public T top() {
        if (topo == null){
            throw new RuntimeException("Pilha vazia");
        }
        return topo.elemento;
    }

    public boolean isEmpty() {
        return topo == null;
    }








    public void concatenar(Pilha<T> outraPilha){
        Pilha<T> auxiliar = new Pilha<>();

        // Transfere os elementos da pilha passada como parâmetro (outraPilha) para a pilha auxiliar, invertendo a ordem no processo
        while (!outraPilha.isEmpty()) {
            auxiliar.push(outraPilha.pop());
        }
        // Transfere os elementos da pilha auxiliar para a pilha original, juntando as duas
        while (!auxiliar.isEmpty()){
            this.push(auxiliar.pop());
        }
    }

    public void concatenarPontas(Pilha<T> outraPilha) {
        if (outraPilha.isEmpty()) {
            return;
        }
        if (this.isEmpty()) {
            this.topo = outraPilha.topo;
        }

        else {
            // Encontra o último elemento da pilha atual (a "cauda")
            Celula<T> atual = this.topo;
            while (atual.prox != null) {
                atual = atual.prox;
            }
            // Conecta a cauda da pilha atual ao topo da outra pilha
            atual.prox = outraPilha.topo;
        }
        outraPilha.topo = null;
    }

    public int obterNumeroElementos() {
        int contador = 0;
        Celula<T> atual = topo;

        while(atual!=null){
            contador++;
            atual = atual.prox;
        }
        return contador;
    }

    public void inverter(Pilha<T> pilha) {
        Pilha<T> auxiliar1 = new Pilha<>();
        Pilha<T> auxiliar2 = new Pilha<>();

        while (!pilha.isEmpty()){
            auxiliar1.push(pilha.pop());
        }

        while (!auxiliar1.isEmpty()){
            auxiliar2.push(auxiliar1.pop());
        }

        while (!auxiliar2.isEmpty()){
            pilha.push(auxiliar2.pop());
        }

        pilha.exibir();
    }


    public void exibir() {
        Celula<T> pilha = topo;

        while (pilha != null) {
            System.out.print(pilha.elemento);
            pilha = pilha.prox;
            if (pilha != null) {
                System.out.print(", ");
            }
        }
    }
}