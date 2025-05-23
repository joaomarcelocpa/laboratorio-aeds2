package app;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import produtos.Produto;
import produtos.ProdutoPerecivel;
import produtos.ProdutoNaoPerecivel;
import pedidos.Pedido;
import sorts.MergeSort;
import sorts.Comparadores;
import estruturas.Pilha;

public class App {
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Produto> produtosOrdenadosPorDescricao = new ArrayList<>();
    private static final Map<String, Produto> produtosPorDescricaoHash = new HashMap<>();
    private static final Pilha<Pedido> pedidos = new Pilha<>();

    public static void main(String[] args) {
        String caminhoArquivo = "produtos.csv";
        produtos = new ArrayList<>(Arrays.asList(lerProdutos(caminhoArquivo)));

        criarCopiasOrdenadas();

        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        boolean running = true;

        while (running) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Listar todos os produtos");
            System.out.println("2. Cadastrar novo produto");
            System.out.println("3. Localizar produto no vetor original");
            System.out.println("4. Localizar produto por descrição (Tabela Hash)");
            System.out.println("5. Ordenar produtos");
            System.out.println("6. Criar novo pedido");
            System.out.println("7. Finalizar pedido em andamento");
            System.out.println("8. Listar produtos dos pedidos mais recentes"); // Nova opção
            System.out.println("9. Sair");

            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    listarTodosOsProdutos();
                    break;
                case 2:
                    cadastrarProduto(sc);
                    criarCopiasOrdenadas();
                    break;
                case 3:
                    localizarProdutos();
                    break;
                case 4:
                    localizarProdutoPorDescricao(sc);
                    break;
                case 5:
                    ordenarProdutos(sc);
                    break;
                case 6:
                    iniciarNovoPedido(sc);
                    break;
                case 7:
                    finalizarPedido();
                    break;
                case 8:
                    listarProdutosPedidosRecentes(sc);
                    break;
                case 9:
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

    public static void finalizarPedido() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido em andamento.");
        } else {
            Pedido pedido = pedidos.top();
            System.out.println("Pedido finalizado:\n" + pedido);
            System.out.println("Pedido mantido na pilha de pedidos recentes.");
        }
    }

    public static void listarProdutosPedidosRecentes(Scanner sc) {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
            return;
        }

        System.out.println("Quantos pedidos mais recentes deseja visualizar?");
        int numPedidos = sc.nextInt();
        sc.nextLine();

        if (numPedidos <= 0) {
            System.out.println("Número inválido.");
            return;
        }

        Pilha<Pedido> pedidosRecentes = pedidos.subPilha(numPedidos);

        if (pedidosRecentes.isEmpty()) {
            System.out.println("Não há pedidos suficientes.");
            return;
        }

        System.out.println("\n=== PRODUTOS DOS " + Math.min(numPedidos, pedidos.tamanho()) + " PEDIDOS MAIS RECENTES ===");

        Set<String> produtosUnicos = new LinkedHashSet<>();
        List<Pedido> listaPedidos = pedidosRecentes.listarElementos();

        int contador = 1;
        for (Pedido pedido : listaPedidos) {
            System.out.println("\n--- Pedido " + contador + " (Data: " + pedido.getDataPedido() + ") ---");

            List<Produto> produtosDoPedido = extrairProdutosDoPedido(pedido);

            for (Produto produto : produtosDoPedido) {
                String descricaoProduto = produto.descricao + " - " + Produto.formatarValor(produto.valorDeVenda());
                System.out.println("  • " + descricaoProduto);
                produtosUnicos.add(produto.descricao);
            }
            contador++;
        }

        System.out.println("\n=== RESUMO: PRODUTOS ÚNICOS ENCONTRADOS ===");
        System.out.println("Total de produtos únicos: " + produtosUnicos.size());
        for (String produto : produtosUnicos) {
            System.out.println("• " + produto);
        }
    }

    private static List<Produto> extrairProdutosDoPedido(Pedido pedido) {
        List<Produto> produtosList = new ArrayList<>();

        String pedidoStr = pedido.toString();
        String[] linhas = pedidoStr.split("\n");

        for (String linha : linhas) {

            if (linha.contains("Nome do produto:")) {
                String nomeProduto = linha.substring(linha.indexOf(":") + 1).trim();


                for (Produto produto : produtos) {
                    if (produto.descricao.equals(nomeProduto)) {
                        produtosList.add(produto);
                        break;
                    }
                }
            }
        }

        return produtosList;
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
                    int resultado = pedido.incluirProduto(produtoEncontrado.get());
                    if (resultado == -1) {
                        System.out.println("Pedido já está cheio (máximo 10 produtos).");
                    } else {
                        System.out.println("Produto adicionado ao pedido.");
                    }
                } else {
                    System.out.println("Produto não encontrado.");
                }
            }
        }

        pedidos.push(pedido);
        System.out.println("Pedido criado com sucesso e adicionado à pilha de pedidos!");
    }

    public static void gravarResumosPedidos(String nomeArquivo) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(nomeArquivo), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            List<Pedido> listaPedidos = pedidos.listarElementos();
            Collections.reverse(listaPedidos);

            for (Pedido pedido : listaPedidos) {
                writer.write(pedido.resumo());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void criarCopiasOrdenadas() {
        produtosOrdenadosPorDescricao = new ArrayList<>(produtos);
        Produto[] arrayProdutos = produtosOrdenadosPorDescricao.toArray(new Produto[0]);

        MergeSort<Produto> mergeSort = new MergeSort<>();
        arrayProdutos = mergeSort.sort(arrayProdutos);

        produtosOrdenadosPorDescricao = new ArrayList<>(Arrays.asList(arrayProdutos));

        produtosPorDescricaoHash.clear();
        for (Produto produto : produtos) {
            produtosPorDescricaoHash.put(produto.descricao.toLowerCase(), produto);
        }
    }

    public static void ordenarProdutos(Scanner sc) {
        System.out.println("\nEscolha o critério de ordenação:");
        System.out.println("1. Por descrição");
        System.out.println("2. Por data de validade");
        System.out.println("3. Por preço");

        int opcao = sc.nextInt();
        sc.nextLine();

        List<Produto> produtosParaExibicao = new ArrayList<>(produtos);
        Produto[] arrayProdutos = produtosParaExibicao.toArray(new Produto[0]);
        MergeSort<Produto> mergeSort = new MergeSort<>();

        switch (opcao) {
            case 1:
                arrayProdutos = mergeSort.sort(arrayProdutos, new Comparadores.ComparadorDescricao());
                break;
            case 2:
                arrayProdutos = mergeSort.sort(arrayProdutos, new Comparadores.ComparadorValidade());
                break;
            case 3:
                arrayProdutos = mergeSort.sort(arrayProdutos, new Comparadores.ComparadorPreco());
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        System.out.println("Produtos ordenados:");
        for (int i = 0; i < arrayProdutos.length; i++) {
            System.out.println((i + 1) + ". " + arrayProdutos[i].gerarDadosTexto());
        }
    }

    public static void localizarProdutoPorDescricao(Scanner sc) {
        System.out.println("Entre com o nome exato do produto para busca rápida:");
        String nomeProduto = sc.nextLine().toLowerCase();

        if (produtosPorDescricaoHash.containsKey(nomeProduto)) {
            System.out.println("Produto encontrado:");
            System.out.println(produtosPorDescricaoHash.get(nomeProduto).gerarDadosTexto());
        } else {
            System.out.println("Produto não encontrado com nome exato. Buscando correspondências parciais:");
            boolean encontrado = false;

            for (Produto produto : produtosOrdenadosPorDescricao) {
                if (produto.descricao.toLowerCase().contains(nomeProduto)) {
                    System.out.println(produto.gerarDadosTexto());
                    encontrado = true;
                }
            }

            if (!encontrado) {
                System.out.println("Nenhum produto encontrado com esse nome.");
            }
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

    public static void listarTodosOsProdutos() {
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println((i + 1) + ". " + produtos.get(i).gerarDadosTexto());
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