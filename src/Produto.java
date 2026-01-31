package src;
public class Produto {

    // ================== ATRIBUTOS ==================
    // Nome do produto
    private final String nome;

    // ID único do produto
    private final int id;

    // Preço que a loja pagou no produto
    private final double precoCompra;

    // Preço que a loja vende o produto
    private final double precoVenda;

    // Quantidade disponível em estoque
    private int unidade;

    // ================== CONSTRUTOR ==================
    public Produto(String nome, int id, double precoCompra, double precoVenda, int unidade) {

        // Validação do nome
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome não pode ser vazio");

        // Validação do ID
        if (id <= 0)
            throw new IllegalArgumentException("ID deve ser maior que zero");

        // Validação de preços
        if (precoCompra < 0 || precoVenda < 0)
            throw new IllegalArgumentException("Preço não pode ser negativo");

        // Regra de negócio: não vender mais barato do que comprou
        if (precoVenda < precoCompra)
            throw new IllegalArgumentException("Preço de venda não pode ser menor que o de compra");

        // Validação de estoque
        if (unidade < 0)
            throw new IllegalArgumentException("Unidade não pode ser negativa");

        // Inicialização dos atributos
        this.nome = nome.trim();
        this.id = id;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.unidade = unidade;
    }

    // ================== GETTERS ==================
    // Retorna o nome do produto
    public String getNome() {
        return nome;
    }

    // Retorna o ID do produto
    public int getId() {
        return id;
    }

    // Retorna o preço de compra
    public double getPrecoCompra() {
        return precoCompra;
    }

    // Retorna o preço de venda
    public double getPrecoVenda() {
        return precoVenda;
    }

    // Retorna o estoque atual
    public int getUnidade() {
        return unidade;
    }

    // ================== MÉTODOS DE NEGÓCIO ==================

    // Adiciona unidades ao estoque
    public void adicionarEstoque(int quantidade) {
        // Não permite valores inválidos
        if (quantidade <= 0)
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");

        // Soma no estoque atual
        unidade += quantidade;
    }

    // Remove unidades do estoque (usado na venda)
    public void removerEstoque(int quantidade) {
        // Não permite valores inválidos
        if (quantidade <= 0)
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");

        // Impede vender mais do que tem
        if (quantidade > unidade)
            throw new IllegalArgumentException("Estoque insuficiente");

        // Subtrai do estoque
        unidade -= quantidade;
    }

    // ================== EXIBIÇÃO ==================
    // Formata o produto para impressão no terminal
    @Override
    public String toString() {
        return nome + 
               " | id: " + id + 
               " | estoque: " + unidade + 
               " | venda: R$ " + precoVenda;
    }
}
