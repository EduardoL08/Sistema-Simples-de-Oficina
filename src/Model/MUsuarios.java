package Model;

public class MUsuarios {

    private int id;
    private String Login;
    private String Senha;
    private String Perfil;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        this.Login = login;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        this.Senha = senha;
    }

    public String getPerfil() {
        return Perfil;
    }

    public void setPerfil(String perfil) {
        this.Perfil = perfil;
    }
}
