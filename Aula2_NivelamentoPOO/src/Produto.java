import java.text.DecimalFormat;

public abstract class Produto {
    private static final double MARGEM_PADRAO = 0.2;
    private String descricao;
    private double precoCusto;
    private double margemLucro;


    private void init(String desc, double precoCusto, double margemLucro){

        if(desc.length()<3 ||precoCusto<=0||margemLucro<=0)
            throw new IllegalArgumentException("Valores inválidos para o produto");
        this.descricao = desc;
        this.precoCusto = precoCusto;
        this.margemLucro = margemLucro;
    }

    public Produto(String desc, double precoCusto, double margemLucro){
        init(desc, precoCusto, margemLucro);
    }

    public Produto(String desc, double precoCusto){
        init(desc, precoCusto, MARGEM_PADRAO);
    }

    public double valorDeVenda(){
        return precoCusto * (1 + margemLucro);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");
        return String.format("Nome do produto: %s\nValor de venda: R$ %s", descricao, df.format(valorDeVenda()));
    }
}
