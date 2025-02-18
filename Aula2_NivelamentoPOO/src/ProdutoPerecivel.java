import java.time.LocalDate;

public class ProdutoPerecivel extends Produto {

    public static final double DESCONTO = 0.25;
    private LocalDate dataDeValidade;
    private static final int PRAZO_DESCONTO = 7;

    public ProdutoPerecivel(String desc, double precoCusto, double margemLucro, LocalDate dataDeValidade) {
        super(desc, precoCusto, margemLucro);
        if (dataDeValidade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de validade não pode ser anterior ao dia atual.");
        }
        this.dataDeValidade = dataDeValidade;
    }

    @Override
    public double valorDeVenda() {
        LocalDate dataDesconto = dataDeValidade.minusDays(PRAZO_DESCONTO);
        if (LocalDate.now().isAfter(dataDesconto) && !LocalDate.now().isAfter(dataDeValidade)) {
            return super.valorDeVenda() * (1 - DESCONTO);
        }
        return super.valorDeVenda();
    }

    @Override
    public String toString() {
        return super.toString() + " \nData de validade: " + dataDeValidade;
    }
}