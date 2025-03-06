import java.time.LocalDate;

public class Pedido {

    private int MAX_PRODUTOS = 10;
    private Produto[] produtos;
    private LocalDate dataPedido;
    private int quantProdutos;

    public Pedido(LocalDate dataPedido){
        this.dataPedido = dataPedido;
        this.produtos = new Produto[MAX_PRODUTOS];
        this.quantProdutos = 0;
    }

    public int incluirProduto(Produto produto){
        if (quantProdutos < MAX_PRODUTOS) {
            produtos[quantProdutos++] = produto;
            return quantProdutos;
        } else {
            return -1;
        }
    }

    public double valorFinal(){
        double total = 0;
        for (int i = 0; i < quantProdutos; i++) {
            total += produtos[i].valorDeVenda();
        }
        return total;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Data do Pedido: ").append(dataPedido).append("\n");
        sb.append("Produtos:\n");
        for (int i = 0; i < quantProdutos; i++) {
            sb.append(produtos[i].toString()).append("\n");
        }
        sb.append("Valor Total: ").append(valorFinal());
        return sb.toString();
    }

    public String resumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Data: ").append(dataPedido).append(", Total: ").append(valorFinal()).append("\n");
        sb.append("Produtos:\n");
        for (int i = 0; i < quantProdutos; i++) {
            sb.append(produtos[i].descricao).append(" - ").append(produtos[i].valorDeVenda()).append("\n");
        }
        return sb.toString();
    }
}