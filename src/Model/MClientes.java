package Model;

public class MClientes {

    private int id;
    private String Nome;
    private String Cpf;
    private String Telefone;
    private String Email;
    private String Endereco;
    private String DataCadastro;


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
        this.Nome = nome;
    }

    public String getCpf() {
        return Cpf;
    }

    public void setCpf(String cpf) {
        this.Cpf = cpf;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        this.Telefone = telefone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        this.Endereco = endereco;
    }

    public String getDataCadastro() {
        return DataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.DataCadastro = dataCadastro;
    }
}


