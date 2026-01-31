package src;
import java.util.HashMap;
import java.util.Map;

public class Loja {

    // ================== ATRIBUTOS ==================

    // Valor total em caixa
    private double caixa = 0.0;

    // Estrutura de dados principal:
    // Chave = nome do produto (normalizado)
    // Valor = objeto Produto
    private Map<String, Produto> produtos = new HashMap<>();

    // ================== CADASTRO ==================

    // Cadastra um novo produto na loja
    public void criarProduto(Produto p) {
        // Proteção contra valores nulos
        if (p == null || p.getNome() == null) return;

        // Normaliza o nome para evitar duplicatas (Coca / coca / COCA)
        String chave = p.getNome().trim().toLowerCase();

        // Insere no HashMap
        produtos.put(chave, p);
    }

    // ================== BUSCA ==================

    // Busca produto pelo nome
    public Produto buscarProduto(String nome) {
        if (nome == null) return null;

        // Normaliza a chave de busca
        String chave = nome.trim().toLowerCase();

        return produtos.get(chave);
    }

    // ================== CAIXA ==================

    // Soma valor ao caixa
    public void caixaSet(double valor) {
        this.caixa += valor;
    }

    // Retorna valor atual do caixa
    public double getCaixa() {
        return this.caixa;
    }

    // ================== LISTAGEM ==================

    // Lista todos os produtos cadastrados
    public void listarProdutos() {
        for (Produto p : produtos.values()) {
            // Usa o toString() do Produto
            System.out.println(p);
        }
    }

    // ================== VENDA ==================

    // Realiza uma venda
    public double vender(String nome, int qtd) {

        // Busca produto pelo nome
        Produto p = buscarProduto(nome);

        // Se não existir, cancela
        if (p == null) {
            System.out.println("Produto não encontrado.");
            return 0.0;
        }

        try {
            // Tenta remover do estoque
            p.removerEstoque(qtd);
        } catch (IllegalArgumentException e) {
            // Captura erros de estoque insuficiente ou quantidade inválida
            System.out.println("Erro na venda: " + e.getMessage());
            return 0.0;
        }

        // Calcula total da venda
        double total = p.getPrecoVenda() * qtd;

        // Soma no caixa
        caixaSet(total);

        // Retorna valor da venda
        return total;
    }
}
