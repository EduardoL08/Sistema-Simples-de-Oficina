package Model;

public class MPecas {

    private int Id;
    private String NomePeca;
    private String Codigo;
    private String Descricao;
    private int Quantidade;
    private double PrecoCusto;
    private double PrecoVenda;
    private int IdFornecedor;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNomePeca() {
        return NomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.NomePeca = nomePeca;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        this.Codigo = codigo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        this.Descricao = descricao;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.Quantidade = quantidade;
    }

    public double getPrecoCusto() {
        return PrecoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.PrecoCusto = precoCusto;
    }

    public double getPrecoVenda() {
        return PrecoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.PrecoVenda = precoVenda;
    }

    public int getIdFornecedor() {
        return IdFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.IdFornecedor = idFornecedor;
    }
}
