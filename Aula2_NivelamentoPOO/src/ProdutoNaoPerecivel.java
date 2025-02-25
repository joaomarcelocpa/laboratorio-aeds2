public class ProdutoNaoPerecivel extends Produto {

    public ProdutoNaoPerecivel(String desc, double precoCusto, double margemLucro){
        super(desc, precoCusto, margemLucro);
    }

    public ProdutoNaoPerecivel(String desc, double precoCusto){
        super(desc, precoCusto);
    }

    public double valorDeVenda(){
        return super.valorDeVenda();
    }

    @Override
    public String gerarDadosTexto() {
        return "Tipo 1; " + descricao + "; " + precoCusto + "; " + margemLucro + "; " + formatarValor(valorDeVenda());
    }

}