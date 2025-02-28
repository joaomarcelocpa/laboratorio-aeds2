import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Digite a descrição do produto:");
        String descricao = scanner.nextLine();

        double precoCusto = 0;
        boolean precoValido = false;
        while (!precoValido) {
            System.out.println("Digite o preço de custo do produto:");
            try {
                precoCusto = scanner.nextDouble();
                precoValido = true;
            } catch (InputMismatchException e) {
                System.err.println("Preço de custo inválido. Por favor, insira um número.");
                scanner.next();
            }
        }

        double margemLucro = 0;
        boolean margemValida = false;
        while (!margemValida) {
            System.out.println("Digite a margem de lucro do produto:");
            try {
                margemLucro = scanner.nextDouble();
                margemValida = true;
            } catch (InputMismatchException e) {
                System.err.println("Margem de lucro inválida. Por favor, insira um número.");
                scanner.next();
            }
        }

        char perecivel = ' ';
        boolean opcaoValida = false;
        while (!opcaoValida) {
            System.out.println("O produto é perecível? (s/n):");
            perecivel = scanner.next().charAt(0);
            if (perecivel == 's' || perecivel == 'n') {
                opcaoValida = true;
            } else {
                System.err.println("Opção inválida. Por favor, insira 's' para sim ou 'n' para não.");
            }
        }

        if (perecivel == 's') {
            LocalDate dataValidade = null;
            boolean dataValida = false;
            while (!dataValida) {
                System.out.println("Digite a data de validade do produto (dd/MM/yyyy):");
                String dataValidadeStr = scanner.next();
                try {
                    dataValidade = LocalDate.parse(dataValidadeStr, formatter);
                    ProdutoPerecivel produtoPerecivel = new ProdutoPerecivel(descricao, precoCusto, margemLucro, dataValidade);
                    System.out.println(produtoPerecivel);
                    dataValida = true;
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
        } else {
            ProdutoNaoPerecivel produtoNaoPerecivel = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
            System.out.println(produtoNaoPerecivel);
        }
        scanner.close();
    }
}
