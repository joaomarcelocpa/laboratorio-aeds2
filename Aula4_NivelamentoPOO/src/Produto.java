import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Produto {
    protected static final double MARGEM_PADRAO = 0.2;
    protected String descricao;
    protected double precoCusto;
    protected double margemLucro;


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

    public abstract String gerarDadosTexto();

    public static Produto criarDoTexto(String linha) {
        String[] partes = linha.split(";");
        if (partes.length < 5) {
            throw new IllegalArgumentException("Formato de linha inválido: " + linha);
        }

        String tipo = partes[0].trim().split(" ")[1];
        String desc = partes[1].trim();
        double precoCusto;
        double margemLucro;

        try {
            precoCusto = Double.parseDouble(partes[2].trim());
            margemLucro = Double.parseDouble(partes[3].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato de número inválido na linha: " + linha, e);
        }

        if (tipo.equals("2")) {
            if (partes.length != 6) {
                throw new IllegalArgumentException("Formato de linha inválido para produto perecível: " + linha);
            }
            LocalDate dataDeValidade = LocalDate.parse(partes[4].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return new ProdutoPerecivel(desc, precoCusto, margemLucro, dataDeValidade);
        } else if (tipo.equals("1")) {
            return new ProdutoNaoPerecivel(desc, precoCusto, margemLucro);
        } else {
            throw new IllegalArgumentException("Tipo de produto inválido: " + tipo);
        }
    }


    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.00");
        return String.format("Nome do produto: %s\nValor de venda: R$ %s", descricao, df.format(valorDeVenda()));
    }

    public static String formatarValor(double valor) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(valor);
    }
}
