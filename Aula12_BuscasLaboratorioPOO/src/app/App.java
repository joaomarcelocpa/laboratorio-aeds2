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
import estruturas.Fila;
import busca.IBuscador;
import busca.BuscaSequencial;
import busca.BuscaBinaria;

public class App {
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Produto> produtosOrdenadosPorDescricao = new ArrayList<>();
    private static final Map<String, Produto> produtosPorDescricaoHash = new HashMap<>();
    private static final Pilha<Pedido> pedidos = new Pilha<>();
    private static final Fila<Pedido> filaPedidos = new Fila<>();
    private static Pedido pedidoAtual = null;

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
            System.out.println("8. Listar produtos dos pedidos mais recentes");
            System.out.println("9. Exibir o valor total médio dos N primeiros pedidos");
            System.out.println("10. Exibir os primeiros pedidos com valor total acima de um determinado valor");
            System.out.println("11. Exibir os primeiros pedidos que apresentam um determinado produto");
            System.out.println("12. Busca sequencial por produtos");
            System.out.println("13. Busca binária por produtos");
            System.out.println("14. Comparar performance das buscas");
            System.out.println("15. Sair");

            int opcao = lerOpcaoValida(sc, "Digite sua opção: ", 0, 15);

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
                    exibirValorMedioPrimeiros(sc);
                    break;
                case 10:
                    exibirPedidosAcimaValor(sc);
                    break;
                case 11:
                    exibirPedidosComProduto(sc);
                    break;
                case 12:
                    fazerBuscaSequencial(sc);
                    break;
                case 13:
                    fazerBuscaBinaria(sc);
                    break;
                case 14:
                    compararPerformanceBuscas(sc);
                    break;
                case 15:
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

    public static void fazerBuscaSequencial(Scanner sc) {
        System.out.println("\n=== BUSCA SEQUENCIAL ===");
        System.out.print("Digite a descrição do produto: ");
        String descricao = sc.nextLine();

        Produto[] arrayProdutos = produtos.toArray(new Produto[0]);

        IBuscador<Produto> buscador = new BuscaSequencial<>(arrayProdutos);

        Produto resultado = buscador.buscar(descricao);

        exibirResultadoBusca(resultado, buscador, "BUSCA SEQUENCIAL");
    }

    public static void fazerBuscaBinaria(Scanner sc) {
        System.out.println("\n=== BUSCA BINÁRIA ===");
        System.out.println("Ordenando produtos para busca binária...");

        List<Produto> produtosOrdenados = new ArrayList<>(produtos);
        Produto[] arrayProdutos = produtosOrdenados.toArray(new Produto[0]);
        MergeSort<Produto> mergeSort = new MergeSort<>();
        arrayProdutos = mergeSort.sort(arrayProdutos);

        System.out.print("Digite a descrição do produto: ");
        String descricao = sc.nextLine();

        IBuscador<Produto> buscador = new BuscaBinaria<>(arrayProdutos);

        Produto resultado = buscador.buscar(descricao);

        exibirResultadoBusca(resultado, buscador, "BUSCA BINÁRIA");
    }

    public static void compararPerformanceBuscas(Scanner sc) {
        System.out.println("\n=== COMPARAÇÃO DE PERFORMANCE ===");
        System.out.print("Digite a descrição do produto para testar: ");
        String descricao = sc.nextLine();

        Produto[] arrayOriginal = produtos.toArray(new Produto[0]);

        List<Produto> produtosOrdenados = new ArrayList<>(produtos);
        Produto[] arrayOrdenado = produtosOrdenados.toArray(new Produto[0]);
        MergeSort<Produto> mergeSort = new MergeSort<>();
        arrayOrdenado = mergeSort.sort(arrayOrdenado);

        IBuscador<Produto> buscaSeq = new BuscaSequencial<>(arrayOriginal);
        Produto resultadoSeq = buscaSeq.buscar(descricao);

        IBuscador<Produto> buscaBin = new BuscaBinaria<>(arrayOrdenado);
        Produto resultadoBin = buscaBin.buscar(descricao);

        System.out.println("\n=== RESULTADOS DA COMPARAÇÃO ===");
        System.out.println("Total de produtos no sistema: " + produtos.size());
        System.out.println("Produto encontrado: " + (resultadoSeq != null ? "SIM" : "NÃO"));

        if (resultadoSeq != null) {
            System.out.println("Produto: " + resultadoSeq.descricao);
        }

        System.out.println("\nBUSCA SEQUENCIAL:");
        System.out.println("Comparações: " + buscaSeq.getComparacoes());
        System.out.println("Tempo: " + String.format("%,.0f", buscaSeq.getTempo()) + " nanossegundos");

        System.out.println("\nBUSCA BINÁRIA:");
        System.out.println("Comparações: " + buscaBin.getComparacoes());
        System.out.println("Tempo: " + String.format("%,.0f", buscaBin.getTempo()) + " nanossegundos");

        System.out.println("\n=== ANÁLISE DE EFICIÊNCIA ===");
        long diferencaComparacoes = buscaSeq.getComparacoes() - buscaBin.getComparacoes();
        double diferencaTempo = buscaSeq.getTempo() - buscaBin.getTempo();

        if (diferencaComparacoes > 0) {
            System.out.println("Busca binária fez " + diferencaComparacoes + " comparações a menos.");
        } else if (diferencaComparacoes < 0) {
            System.out.println("Busca sequencial fez " + Math.abs(diferencaComparacoes) + " comparações a menos.");
        } else {
            System.out.println("Ambas fizeram o mesmo número de comparações.");
        }

        if (diferencaTempo > 0) {
            System.out.println("Busca binária foi " + String.format("%,.0f", diferencaTempo) + " nanossegundos mais rápida.");
        } else if (diferencaTempo < 0) {
            System.out.println("Busca sequencial foi " + String.format("%,.0f", Math.abs(diferencaTempo)) + " nanossegundos mais rápida.");
        } else {
            System.out.println("Ambas tiveram o mesmo tempo de execução.");
        }

        System.out.println("\n=== COMPLEXIDADE TEÓRICA ===");
        System.out.println("Busca Sequencial: O(n) - Linear");
        System.out.println("Busca Binária: O(log n) - Logarítmica");
        System.out.println("Para " + produtos.size() + " elementos:");
        System.out.println("- Máximo de comparações sequencial: " + produtos.size());
        System.out.println("- Máximo de comparações binária: " + (int)Math.ceil(Math.log(produtos.size()) / Math.log(2)));
    }

    private static void exibirResultadoBusca(Produto produto, IBuscador<Produto> buscador, String tipoBusca) {
        System.out.println("\n=== RESULTADO DA " + tipoBusca + " ===");

        if (produto != null) {
            System.out.println("Produto encontrado:");
            System.out.println(produto.gerarDadosTexto());
        } else {
            System.out.println("Produto não encontrado.");
        }

        System.out.println("\nEstatísticas da busca:");
        System.out.println("Total de comparações: " + buscador.getComparacoes());
        System.out.println("Tempo gasto: " + String.format("%,.0f", buscador.getTempo()) + " nanossegundos");
    }

    public static void exibirValorMedioPrimeiros(Scanner sc) {
        if (filaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado na fila.");
            return;
        }

        int quantidade = lerOpcaoValida(sc, "Digite quantos primeiros pedidos considerar: ", 1, filaPedidos.tamanho());

        double valorMedio = filaPedidos.calcularValorMedio(Pedido::valorFinal, quantidade);

        System.out.println("\n=== VALOR MÉDIO DOS PRIMEIROS " + quantidade + " PEDIDOS ===");
        System.out.println("Valor médio: R$ " + Produto.formatarValor(valorMedio));
    }

    public static void exibirPedidosAcimaValor(Scanner sc) {
        if (filaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado na fila.");
            return;
        }

        double valorMinimo = lerDoubleValido(sc, "Digite o valor mínimo dos pedidos: R$ ");
        int quantidade = lerOpcaoValida(sc, "Digite quantos primeiros pedidos analisar: ", 1, filaPedidos.tamanho());

        Fila<Pedido> pedidosFiltrados = filaPedidos.filtrar(pedido -> pedido.valorFinal() > valorMinimo, quantidade);

        System.out.println("\n=== PEDIDOS COM VALOR ACIMA DE R$ " + Produto.formatarValor(valorMinimo) + " ===");

        if (pedidosFiltrados.isEmpty()) {
            System.out.println("Nenhum pedido encontrado com valor acima do especificado.");
        } else {
            List<Pedido> listaPedidos = pedidosFiltrados.listarElementos();
            for (int i = 0; i < listaPedidos.size(); i++) {
                Pedido pedido = listaPedidos.get(i);
                System.out.println("\n--- Pedido " + (i + 1) + " ---");
                System.out.println("Data: " + pedido.getDataPedido());
                System.out.println("Valor Total: R$ " + Produto.formatarValor(pedido.valorFinal()));
                System.out.println("Produtos:");
                Produto[] produtosDoPedido = pedido.getProdutos();
                for (int j = 0; j < pedido.getQuantProdutos(); j++) {
                    System.out.println("  • " + produtosDoPedido[j].descricao + " - R$ " +
                            Produto.formatarValor(produtosDoPedido[j].valorDeVenda()));
                }
            }
        }
    }

    public static void exibirPedidosComProduto(Scanner sc) {
        if (filaPedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado na fila.");
            return;
        }

        System.out.print("Digite o nome do produto a ser localizado: ");
        String nomeProduto = sc.nextLine().toLowerCase();

        int quantidade = lerOpcaoValida(sc, "Digite quantos primeiros pedidos analisar: ", 1, filaPedidos.tamanho());

        Fila<Pedido> pedidosFiltrados = filaPedidos.filtrar(pedido -> {
            Produto[] produtosDoPedido = pedido.getProdutos();
            for (int i = 0; i < pedido.getQuantProdutos(); i++) {
                if (produtosDoPedido[i].descricao.toLowerCase().contains(nomeProduto)) {
                    return true;
                }
            }
            return false;
        }, quantidade);

        System.out.println("\n=== PEDIDOS QUE CONTÊM O PRODUTO: " + nomeProduto.toUpperCase() + " ===");

        if (pedidosFiltrados.isEmpty()) {
            System.out.println("Nenhum pedido encontrado com o produto especificado.");
        } else {
            List<Pedido> listaPedidos = pedidosFiltrados.listarElementos();
            for (int i = 0; i < listaPedidos.size(); i++) {
                Pedido pedido = listaPedidos.get(i);
                System.out.println("\n--- Pedido " + (i + 1) + " ---");
                System.out.println("Data: " + pedido.getDataPedido());
                System.out.println("Valor Total: R$ " + Produto.formatarValor(pedido.valorFinal()));
                System.out.println("Produtos:");
                Produto[] produtosDoPedido = pedido.getProdutos();
                for (int j = 0; j < pedido.getQuantProdutos(); j++) {
                    String marcador = produtosDoPedido[j].descricao.toLowerCase().contains(nomeProduto) ? " ★ " : "   ";
                    System.out.println(marcador + produtosDoPedido[j].descricao + " - R$ " +
                            Produto.formatarValor(produtosDoPedido[j].valorDeVenda()));
                }
            }
        }
    }

    public static int lerOpcaoValida(Scanner sc, String mensagem, int min, int max) {
        int opcao;
        while (true) {
            try {
                System.out.print(mensagem);
                opcao = sc.nextInt();
                sc.nextLine();

                if (opcao >= min && opcao <= max) {
                    return opcao;
                } else {
                    System.out.println("Opção inválida. Digite um número entre " + min + " e " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite apenas números.");
                sc.nextLine();
            }
        }
    }

    public static double lerDoubleValido(Scanner sc, String mensagem) {
        double valor;
        while (true) {
            try {
                System.out.print(mensagem);
                valor = sc.nextDouble();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
                sc.nextLine();
            }
        }
    }

    public static void finalizarPedido() {
        if (pedidoAtual == null) {
            System.out.println("Nenhum pedido em andamento para finalizar.");
            return;
        }

        if (pedidoAtual.getQuantProdutos() == 0) {
            System.out.println("Não é possível finalizar um pedido vazio.");
            return;
        }

        pedidos.push(pedidoAtual);
        filaPedidos.enqueue(pedidoAtual);

        System.out.println("Pedido finalizado com sucesso!");
        System.out.println(pedidoAtual);
        pedidoAtual = null;
    }

    public static void listarProdutosPedidosRecentes(Scanner sc) {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido finalizado cadastrado.");
            return;
        }

        int numPedidos = lerOpcaoValida(sc, "Quantos pedidos mais recentes deseja visualizar? ", 1, pedidos.tamanho());

        try {
            Pilha<Pedido> pedidosRecentes = pedidos.subPilha(numPedidos);

            if (pedidosRecentes.isEmpty()) {
                System.out.println("Não há pedidos suficientes.");
                return;
            }

            System.out.println("\n=== PRODUTOS DOS " + Math.min(numPedidos, pedidos.tamanho()) + " PEDIDOS MAIS RECENTES ===");

            Produto[] produtosUnicos = new Produto[produtos.size()];
            int numProdutosUnicos = 0;

            List<Pedido> listaPedidos = pedidosRecentes.listarElementos();
            int contador = 1;

            for (Pedido pedido : listaPedidos) {
                System.out.println("\n--- Pedido " + contador + " (Data: " + pedido.getDataPedido() + ") ---");

                Produto[] produtosDoPedido = pedido.getProdutos();

                for (int i = 0; i < pedido.getQuantProdutos(); i++) {
                    Produto produto = produtosDoPedido[i];
                    if (produto != null) {
                        System.out.println("  • " + produto.descricao + " - " + Produto.formatarValor(produto.valorDeVenda()));

                        boolean jaExiste = false;
                        for (int j = 0; j < numProdutosUnicos && !jaExiste; j++) {
                            if (produtosUnicos[j].equals(produto)) {
                                jaExiste = true;
                            }
                        }

                        if (!jaExiste) {
                            produtosUnicos[numProdutosUnicos++] = produto;
                        }
                    }
                }
                contador++;
            }

            System.out.println("\n=== RESUMO: PRODUTOS ÚNICOS ENCONTRADOS ===");
            System.out.println("Total de produtos únicos: " + numProdutosUnicos);
            for (int i = 0; i < numProdutosUnicos; i++) {
                System.out.println("• " + produtosUnicos[i].descricao);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void iniciarNovoPedido(Scanner sc) {
        if (pedidoAtual != null) {
            System.out.println("Já existe um pedido em andamento. Finalize-o antes de criar um novo.");
            return;
        }

        pedidoAtual = new Pedido(LocalDate.now());
        boolean adicionandoProdutos = true;

        System.out.println("Novo pedido iniciado!");

        while (adicionandoProdutos) {
            System.out.println("Digite o nome do produto para adicionar ao pedido (ou 'sair' para parar de adicionar):");
            String nomeProduto = sc.nextLine().toLowerCase();

            if (nomeProduto.equals("sair")) {
                adicionandoProdutos = false;
            } else {
                Optional<Produto> produtoEncontrado = produtos.stream()
                        .filter(p -> p.descricao.toLowerCase().contains(nomeProduto))
                        .findFirst();

                if (produtoEncontrado.isPresent()) {
                    int resultado = pedidoAtual.incluirProduto(produtoEncontrado.get());
                    if (resultado == -1) {
                        System.out.println("Pedido já está cheio (máximo 10 produtos).");
                    } else {
                        System.out.println("Produto adicionado ao pedido. Total de produtos: " + resultado);
                    }
                } else {
                    System.out.println("Produto não encontrado.");
                }
            }
        }

        System.out.println("Pedido criado! Use a opção 7 para finalizá-lo.");
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

        int opcao = lerOpcaoValida(sc, "Digite sua opção: ", 1, 3);

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
            int tipo = lerOpcaoValida(sc, "Digite o tipo do produto (1 para não perecível, 2 para perecível): ", 1, 2);

            System.out.print("Digite nome do produto: ");
            String descricao = sc.nextLine();

            double precoCusto = lerDoubleValido(sc, "Digite o preço de custo do produto: ");
            double margemLucro = lerDoubleValido(sc, "Digite a margem de lucro do produto: ");

            if (tipo == 1) {
                Produto produto = new ProdutoNaoPerecivel(descricao, precoCusto, margemLucro);
                produtos.add(produto);
            } else {
                while (true) {
                    try {
                        System.out.print("Digite a data de validade do produto (yyyy-MM-dd): ");
                        String dataValidadeStr = sc.nextLine();
                        LocalDate dataValidade = LocalDate.parse(dataValidadeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        if (dataValidade.isBefore(LocalDate.now())) {
                            throw new IllegalArgumentException("Data de validade não pode ser anterior ao dia atual.");
                        }
                        Produto produto = new ProdutoPerecivel(descricao, precoCusto, margemLucro, dataValidade);
                        produtos.add(produto);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato de data inválido. Use o formato yyyy-MM-dd (ex: 2024-12-31).");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            System.out.println("Produto cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }
}