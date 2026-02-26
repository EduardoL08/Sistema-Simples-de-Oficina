package Model;

public class MPecasUtiizadas {
    private int id;
    private String Nome;
    private String Codigo;
    private int QtdUsadas;
    private double PrecoUnitario;
    private double Total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public int getQtdUsadas() {
        return QtdUsadas;
    }

    public void setQtdUsadas(int qtdUsadas) {
        QtdUsadas = qtdUsadas;
    }

    public double getPrecoUnitario() {
        return PrecoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        PrecoUnitario = precoUnitario;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }
}
