import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {
    private static List<Produto> produtos = new ArrayList<>();

    public static void main(String[] args) {
        String caminhoArquivo = "produtos.csv";
        produtos = new ArrayList<>(Arrays.asList(lerProdutos(caminhoArquivo)));
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        boolean running = true;

        while (running) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Listar todos os produtos");
            System.out.println("2. Cadastrar novo produto");
            System.out.println("3. Localizar produto");
            System.out.println("4. Sair");

            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    listarTodosOsProdutos();
                    break;
                case 2:
                    cadastrarProduto(sc);
                    break;
                case 3:
                    localizarProdutos();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        sc.close();
        salvarProdutos("produtos.csv");
    }

    public static void listarTodosOsProdutos() {
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println((i + 1) + ". " + produtos.get(i).gerarDadosTexto());
        }
    }

    static void cadastrarProduto(Scanner sc) {
        System.out.println("Digite o tipo do produto (1 para não perecível, 2 para perecível):");
        int tipo = sc.nextInt();
        sc.nextLine();

        System.out.println("Digite nome do produto:");
        String descricao = sc.nextLine();

        System.out.println("Digite o preço de custo do produto:");
        double precoCusto = sc.nextDouble();

        System.out.println("Digite a margem de lucro do produto:");
        double margemLucro = sc.nextDouble();
        sc.nextLine();

        if (tipo == 1) {
            Produto produto = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
            produtos.add(produto);
        } else if (tipo == 2) {
            System.out.println("Digite a data de validade do produto (yyyy-MM-dd):");
            String dataValidadeStr = sc.nextLine();
            LocalDate dataValidade = LocalDate.parse(dataValidadeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Produto produto = new ProdutoPerecivel(descricao, precoCusto, margemLucro, dataValidade);
            produtos.add(produto);
        } else {
            System.out.println("Tipo de produto inválido.");
        }
        System.out.println("Produto cadastrado com sucesso!");
    }

    public static Produto[] lerProdutos(String nomeArquivoDados) {
        try {
            List<String> linhas = Files.readAllLines(Paths.get(nomeArquivoDados));
            if (linhas.isEmpty()) {
                System.out.println("O arquivo está vazio.");
                return new Produto[0];
            }
            int quantidade = Integer.parseInt(linhas.get(0));
            if (linhas.size() < quantidade + 1) {
                System.out.println("O arquivo não contém dados suficientes.");
                return new Produto[0];
            }
            Produto[] produtos = new Produto[quantidade];
            for (int i = 1; i <= quantidade; i++) {
                produtos[i - 1] = Produto.criarDoTexto(linhas.get(i));
            }
            return produtos;
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            return new Produto[0];
        }
    }

    public static void localizarProdutos() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entre com o nome do produto:");
        String nomeProduto = sc.nextLine().toLowerCase();

        Optional<Produto> produtoEncontrado = produtos.stream()
                .filter(p -> p.descricao.toLowerCase().contains(nomeProduto))
                .findFirst();

        if (produtoEncontrado.isPresent()) {
            System.out.println(produtoEncontrado.get().gerarDadosTexto());
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    public static void salvarProdutos(String nomeArquivo) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(nomeArquivo))) {
            writer.write(String.valueOf(produtos.size()));
            writer.newLine();
            for (Produto produto : produtos) {
                writer.write(produto.gerarDadosTexto());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
