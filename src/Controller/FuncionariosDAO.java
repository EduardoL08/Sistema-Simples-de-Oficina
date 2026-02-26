package Controller;

import DAO.FabricaConexao;
import Model.MFuncionarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionariosDAO {

    public ResultSet ListaFuncionarios() {

        String sql = "SELECT * FROM Funcionarios";

        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            return stmt.executeQuery();

        }catch (SQLException e) {

            System.out.println("Erro ao listar funcionarios: " + e.getMessage());

            return null;
        }
    }
    public boolean inserirFuncionarios(MFuncionarios func){

        String sql = "INSERT INTO Funcionarios (nome, cpf, telefone, email, especialidades, dataContratacao, statu, observacao) VALUES (?,?,?,?,?,?,?,?)";

        try { Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, func.getNome());
            stmt.setString(2, func.getCpf());
            stmt.setString(3, func.getTelefone());
            stmt.setString(4, func.getEmail());
            stmt.setString(5, func.getEspecialidade());
            stmt.setString(6, func.getDataCon());
            stmt.setString(7, func.getStatus());
            stmt.setString(8, func.getObser());

            return stmt.executeUpdate() > 0;

        }catch (SQLException e) {

            System.out.println("Erro ao inserir funcionario: " + e.getMessage());

            return false;
        }
    }
    public void editarFuncionarios(MFuncionarios func){

        // exibir  query sql de atualização
        String query = "UPDATE Funcionarios SET nome = ?, cpf = ?, telefone = ? , email = ?, especialidades = ?, dataContratacao = ?, statu = ?, observacao = ? WHERE id_Funcionario = ?";

        // Criar a conexão e preparar o statement

        try { Connection connection = FabricaConexao.conectar();
            PreparedStatement pstnt = connection.prepareStatement(query);

            //definir os parametros do preparestatement

            pstnt.setString(1, func.getNome());
            pstnt.setString(2, func.getCpf());
            pstnt.setString(3, func.getTelefone());
            pstnt.setString(4, func.getEmail());
            pstnt.setString(5, func.getEspecialidade());
            pstnt.setString(6, func.getDataCon());
            pstnt.setString(7, func.getStatus());
            pstnt.setString(8, func.getObser());
            pstnt.setInt(9,    func.getId());

            pstnt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }
    public void deletarFuncionarios (MFuncionarios func) {

        String query = "DELETE FROM Funcionarios WHERE  id_Funcionario = ? ";

        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstnt = connection.prepareStatement(query)) {

            pstnt.setInt(1,func.getId());
            pstnt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }

}
