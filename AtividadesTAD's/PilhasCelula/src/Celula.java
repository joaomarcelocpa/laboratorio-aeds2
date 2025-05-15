public class Celula<T> {

    T elemento;
    Celula<T> prox;

    public Celula(T elemento){
        this.elemento = elemento;
        this.prox = null;
    }

}
