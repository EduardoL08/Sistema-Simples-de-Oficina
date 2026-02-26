package Model;

public class MDespesas {

    private int id;
    private String NomeDesp;
    private String descricao;
    private String Valor;
    private String DataVenci;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDesp() {
        return NomeDesp;
    }

    public void setNomeDesp(String nomeDesp) {
        NomeDesp = nomeDesp;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataVenci() {
        return DataVenci;
    }

    public void setDataVenci(String dataVenci) {
        DataVenci = dataVenci;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
