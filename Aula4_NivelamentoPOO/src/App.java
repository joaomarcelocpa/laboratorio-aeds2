import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class App {
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();

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
            System.out.println("4. Iniciar novo pedido");
            System.out.println("5. Finalizar pedido");
            System.out.println("6. Sair");

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
                    iniciarNovoPedido(sc);
                    break;
                case 5:
                    finalizarPedido();
                    break;
                case 6:
                    running = false;
                    gravarResumosPedidos("resumos_pedidos.txt");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        sc.close();
        salvarProdutos("produtos.csv");
    }

    public static void iniciarNovoPedido(Scanner sc) {
        Pedido pedido = new Pedido(LocalDate.now());
        boolean adicionandoProdutos = true;

        while (adicionandoProdutos) {
            System.out.println("Digite o nome do produto para adicionar ao pedido (ou 'sair' para finalizar):");
            String nomeProduto = sc.nextLine().toLowerCase();

            if (nomeProduto.equals("sair")) {
                adicionandoProdutos = false;
            } else {
                Optional<Produto> produtoEncontrado = produtos.stream()
                        .filter(p -> p.descricao.equalsIgnoreCase(nomeProduto))
                        .findFirst();

                if (produtoEncontrado.isPresent()) {
                    pedido.incluirProduto(produtoEncontrado.get());
                    System.out.println("Produto adicionado ao pedido.");
                } else {
                    System.out.println("Produto não encontrado.");
                }
            }
        }
        pedidos.add(pedido);
        System.out.println("Pedido iniciado com sucesso!");
    }

    public static void finalizarPedido() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido em andamento.");
        } else {
            Pedido pedido = pedidos.get(pedidos.size() - 1);
            System.out.println("Pedido finalizado:\n" + pedido);
        }
    }

    public static void gravarResumosPedidos(String nomeArquivo) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(nomeArquivo), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            for (Pedido pedido : pedidos) {
                writer.write(pedido.resumo());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listarTodosOsProdutos() {
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println((i + 1) + ". " + produtos.get(i).gerarDadosTexto());
        }
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
            List<Produto> produtosList = new ArrayList<>();
            for (int i = 1; i <= quantidade; i++) {
                try {
                    Produto produto = Produto.criarDoTexto(linhas.get(i));
                    if (produto instanceof ProdutoPerecivel) {
                        ProdutoPerecivel perecivel = (ProdutoPerecivel) produto;
                        if (perecivel.getDataDeValidade().isBefore(LocalDate.now())) {
                            continue;
                        }
                    }
                    produtosList.add(produto);
                } catch (IllegalArgumentException e) {
                }
            }
            return produtosList.toArray(new Produto[0]);
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

    public static void cadastrarProduto(Scanner sc) {
        try {
            System.out.println("Digite o tipo do produto (1 para não perecível, 2 para perecível):");
            int tipo = sc.nextInt();
            if (tipo != 1 && tipo != 2) {
                System.out.println("Tipo de produto inválido.");
                return;
            }
            sc.nextLine();

            System.out.println("Digite nome do produto:");
            String descricao = sc.nextLine();

            double precoCusto = 0;
            while (true) {
                try {
                    System.out.println("Digite o preço de custo do produto:");
                    precoCusto = sc.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, tente novamente.");
                    sc.nextLine();
                }
            }

            double margemLucro = 0;
            while (true) {
                try {
                    System.out.println("Digite a margem de lucro do produto:");
                    margemLucro = sc.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, tente novamente.");
                    sc.nextLine();
                }
            }
            sc.nextLine();

            if (tipo == 1) {
                Produto produto = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
                produtos.add(produto);
            } else if (tipo == 2) {
                while (true) {
                    try {
                        System.out.println("Digite a data de validade do produto (yyyy-MM-dd):");
                        String dataValidadeStr = sc.nextLine();
                        LocalDate dataValidade = LocalDate.parse(dataValidadeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        if (dataValidade.isBefore(LocalDate.now())) {
                            throw new IllegalArgumentException("Data de validade não pode ser anterior ao dia atual.");
                        }
                        Produto produto = new ProdutoPerecivel(descricao, precoCusto, margemLucro, dataValidade);
                        produtos.add(produto);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato de data inválido. Por favor, tente novamente.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                System.out.println("Tipo de produto inválido.");
            }
            System.out.println("Produto cadastrado com sucesso!");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, tente novamente.");
            sc.nextLine();
        }
    }
}