package src;
import java.time.LocalDateTime;

public class Vendas {
    String nomeProduto;
    int idProduto;
    int quantidade;
    LocalDateTime dataHora;
    double total;

    public Vendas(String nomeProduto, int quantidade, double total){
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.dataHora = dataHora.now();
        this.total = total;

    }

    public String getNome(){ return nomeProduto;}
    public int getQuantidade(){return quantidade;} public LocalDateTime getDataHora(){ return dataHora;} public double getTotal(){return total;}

    @Override
    public String toString(){
        return dataHora + " | "+ nomeProduto +
               " | qtd: " + quantidade +
               " | total: R$ " + total;
    }
}
