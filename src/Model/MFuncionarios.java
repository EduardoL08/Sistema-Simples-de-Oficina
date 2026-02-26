package Model;

public class MFuncionarios {

    private int Id;
    private String Nome;
    private String Cpf;
    private String Email;
    private String Telefone;
    private String Especialidade;
    private String DataCon;
    private String Status;
    private String Obser;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getCpf() {
        return Cpf;
    }

    public void setCpf(String cpf) {
        this.Cpf = cpf;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        this.Telefone = telefone;
    }

    public String getEspecialidade() {
        return Especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.Especialidade = especialidade;
    }

    public String getDataCon() {
        return DataCon;
    }

    public void setDataCon(String dataCon) {
        this.DataCon = dataCon;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getObser() {
        return Obser;
    }

    public void setObser(String obser) {
        this.Obser = obser;
    }
}
