

public class Item {
    private String nome;
    private double pesoUnidade;
    private int quantidade;
    private double pesoTotal;

    public Item(String nome, double pesoUnidade, int quantidade) {
        this.nome = nome;
        this.pesoUnidade = pesoUnidade;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPesoUnidade() {
        return pesoUnidade;
    }

    public void setPesoUnidade(double pesoUnidade) {
        this.pesoUnidade = pesoUnidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public void calcularPesoQuantidade() {
        setPesoTotal(getPesoUnidade()*getQuantidade());
    }
    
    @Override
    public String toString() {
        return "Nome: " + nome + ", Peso unitário: " + pesoUnidade + ", Quantidade: " + quantidade;
        
    }
}
