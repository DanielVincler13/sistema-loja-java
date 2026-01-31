package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    LocalDateTime now = LocalDateTime.now();
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public String formatted = now.format(formatter); // e.g., "2026-01-31 15:30:45"
    // Mostra os comandos disponíveis no sistema
    private static void menu() {
        
        System.out.println("Comandos:");
        System.out.println("  listar     - Lista todos os produtos");
        System.out.println("  buscar     - Busca um produto pelo nome");
        System.out.println("  cadastrar  - Cadastra um novo produto");
        System.out.println("  entrada    - Adiciona estoque a um produto");
        System.out.println("  vender     - Realiza uma venda");
        System.out.println("  historico  - Mostra todas as vendas");
        System.out.println("  apagar     - Desfaz a última venda");
        System.out.println("  caixa      - Mostra o total em caixa");
        System.out.println("  ajuda      - Mostra o menu");
        System.out.println("  sair       - Encerra o sistema");
    }

    public static void main(String[] args) {

        // Estruturas de vendas
        Stack<Vendas> pilha = new Stack<>();     // CTRL+Z
        List<Vendas> historico = new ArrayList<>(); // Histórico completo

        // Scanner para leitura do teclado
        Scanner sc = new Scanner(System.in);

        // Instância da loja
        Loja loja = new Loja();

        // Controle de ID automático de produtos
        int id = 0;

        // Produtos iniciais
        loja.criarProduto(new Produto("Coca", ++id, 4.00, 6.00, 10));
        loja.criarProduto(new Produto("Pao", ++id, 0.40, 0.80, 50));

        System.out.println("Sistema da Loja iniciado.");
        menu();

        // Loop principal do sistema
        while (true) {

            System.out.print("> ");
            String comando = sc.nextLine().trim().toLowerCase();

            switch (comando) {

                // ================== SAIR ==================
                case "sair":
                    sc.close();
                    System.out.println("Sistema finalizado.");
                    return;

                // ================== LISTAR ==================
                case "listar":
                    loja.listarProdutos();
                    break;

                // ================== CAIXA ==================
                case "caixa":
                    System.out.println("Total no caixa: R$ " + loja.getCaixa());
                    break;

                // ================== VENDER ==================
                case "vender": {
                    System.out.print("Nome do produto: ");
                    String nome = sc.nextLine();

                    System.out.print("Quantidade: ");
                    int quantidade;
                    try {
                        quantidade = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Quantidade inválida.");
                        break;
                    }

                    // Busca produto antes de vender
                    Produto p = loja.buscarProduto(nome);
                    if (p == null) {
                        System.out.println("Produto não encontrado.");
                        break;
                    }

                    // Realiza venda
                    double total = loja.vender(nome, quantidade);

                    // Se não vendeu (estoque insuficiente, por exemplo)
                    if (total <= 0) break;

                    // Cria objeto da venda
                    Vendas v = new Vendas(nome, quantidade, total);

                    // Guarda no histórico e na pilha
                    historico.add(v);
                    pilha.push(v);

                    // Mostra resultado
                    System.out.println("Total da venda: R$ " + total);
                    System.out.println("Total no caixa: R$ " + loja.getCaixa());
                    break;
                }

                // ================== BUSCAR ==================
                case "buscar": {
                    System.out.print("Nome do produto: ");
                    String nome = sc.nextLine();

                    Produto p = loja.buscarProduto(nome);

                    if (p == null) {
                        System.out.println("Produto não encontrado.");
                    } else {
                        System.out.println(p);
                    }
                    break;
                }

                // ================== CADASTRAR ==================
                case "cadastrar": {
                    System.out.print("Nome: ");
                    String nome = sc.nextLine().trim();

                    if (nome.isEmpty()) {
                        System.out.println("Nome não pode ser vazio.");
                        break;
                    }

                    System.out.print("Preço compra: ");
                    double precoCompra = Double.parseDouble(sc.nextLine());

                    System.out.print("Preço venda: ");
                    double precoVenda = Double.parseDouble(sc.nextLine());

                    System.out.print("Estoque inicial: ");
                    int estoque = Integer.parseInt(sc.nextLine());

                    loja.criarProduto(
                        new Produto(nome, ++id, precoCompra, precoVenda, estoque)
                    );

                    System.out.println("Produto cadastrado! ID: " + id);
                    break;
                }

                // ================== ENTRADA DE ESTOQUE ==================
                case "entrada": {
                    System.out.print("Nome do produto: ");
                    String nome = sc.nextLine();

                    Produto p = loja.buscarProduto(nome);

                    if (p == null) {
                        System.out.println("Produto não encontrado.");
                        break;
                    }

                    System.out.print("Quantidade para adicionar: ");
                    int qtd = Integer.parseInt(sc.nextLine());

                    try {
                        p.adicionarEstoque(qtd);
                        System.out.println("Estoque atualizado!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                }

                // ================== HISTÓRICO ==================
                case "historico":
                    if (historico.isEmpty()) {
                        System.out.println("Nenhuma venda registrada.");
                    } else {
                        for (Vendas v : historico) {
                            System.out.println(v);
                        }
                    }
                    break;

                // ================== APAGAR ÚLTIMA VENDA ==================
                case "apagar": {
                    if (pilha.isEmpty()) {
                        System.out.println("Não existe venda para apagar.");
                        break;
                    }

                    // Pega última venda
                    Vendas v = pilha.pop();

                    // Remove do histórico
                    historico.remove(v);

                    // Devolve estoque
                    Produto p = loja.buscarProduto(v.getNome());
                    if (p != null) {
                        p.adicionarEstoque(v.getQuantidade());
                    }

                    // Remove valor do caixa
                    loja.caixaSet(-v.getTotal());

                    System.out.println("Venda cancelada com sucesso:");
                    System.out.println(v);
                    System.out.println("Caixa atual: R$ " + loja.getCaixa());
                    break;
                }

                // ============== RELATORIO ===================

                
               
                case "relatorio": {
                String data = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                String msg = """
                    =============================
                        RELATÓRIO
                    =============================
                    Data: %s
                    Total em caixa: R$ %.2f
                    Qntd vendas: %d
                    =============================
                    """.formatted(data, loja.getCaixa(), historico.size());

                System.out.println(msg);
                break;
            }


                // ================== AJUDA ==================
                case "ajuda":
                    menu();
                    break;

                // ================== COMANDO INVÁLIDO ==================
                default:
                    System.out.println("Comando inválido. Digite 'ajuda'.");
            }
        }
    }
}
