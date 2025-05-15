public class Fila {

    private Celula frente;
    private Celula tras;

    public Fila() {
        tras = null;
        frente = null;
    }

    public void insert(int elemento){
        Celula nova = new Celula(elemento);

        if (frente == null){
            frente = nova;
        }
        else {
            tras.proximo = nova;
        }
        tras = nova;
    }


    public int remove(){
        if (isEmpty()){
            throw new RuntimeException("Pilha Vazia");
        }
        int elemento = frente.elemento;
        frente = frente.proximo;
        if (frente==null){
            tras = null;
        }
        return elemento;
    }


    public boolean isEmpty(){
        if (frente==null){
            return true;
        }
        return false;
    }


}
