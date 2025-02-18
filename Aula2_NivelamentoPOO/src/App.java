import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Digite a descrição do produto:");
        String descricao = scanner.nextLine();

        System.out.println("Digite o preço de custo do produto:");
        double precoCusto = scanner.nextDouble();

        System.out.println("Digite a margem de lucro do produto:");
        double margemLucro = scanner.nextDouble();

        System.out.println("O produto é perecível? (s/n):");
        char perecivel = scanner.next().charAt(0);

        if (perecivel == 's') {
            System.out.println("Digite a data de validade do produto (dd/MM/yyyy):");
            String dataValidadeStr = scanner.next();
            LocalDate dataValidade = LocalDate.parse(dataValidadeStr, formatter);

            try {
                ProdutoPerecivel produtoPerecivel = new ProdutoPerecivel(descricao, precoCusto, margemLucro, dataValidade);
                System.out.println(produtoPerecivel);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.err.println(e.getMessage());
            }
        }
        else if (perecivel == 'n') {
            ProdutoNaoPerecivel produtoNaoPerecivel = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
            System.out.println(produtoNaoPerecivel);
        } else {
            System.err.println("Opção inválida.");
        }
        scanner.close();
    }
}