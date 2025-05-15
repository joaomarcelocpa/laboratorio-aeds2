public class Lista<T> {

    private No inicio;
    private No fim;

    public Lista() {
        this.inicio = null;
        this.fim = null;
    }


    public void inserir(T valor) {
        No<T> novo = new No<>(valor);

        if (inicio == null) {
            inicio = novo;
            fim = novo;
        } else {
            fim.proximo = novo;
            novo.anterior = fim;
            fim = novo;
        }
    }


    public void remover(T valor) {
        if (inicio == null) return;

        No atual = inicio;

        while (atual != null) {
            if (atual.valor== valor) {

                if (atual == inicio) {
                    inicio = atual.proximo;

                } else if (atual == fim) {
                    fim = atual.anterior;
                    if (fim != null) {
                        fim.proximo = null;
                    }
                } else {
                    atual.anterior.proximo = atual.proximo;
                    atual.proximo.anterior = atual.anterior;
                }

                return;
            }

            atual = atual.proximo;
        }
    }



    public void exibir() {
        if (inicio == null) {
            System.out.println("Lista vazia");
            return;
        }

        No atual = inicio;
        System.out.print("Lista: ");

        while (atual != null) {
            System.out.print(atual.valor);
            atual = atual.proximo;
        }

        System.out.println();
    }
}