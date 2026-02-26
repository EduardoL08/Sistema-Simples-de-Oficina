package Controller;

import DAO.FabricaConexao;
import Model.MClientes;
import Model.MFornecedores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FornecedoresDAO {

    public ResultSet ListaFornecedor() {

        String sql = "SELECT * FROM Fornecedor";

        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();

        } catch (SQLException e) {

            System.out.println("Erro ao validar login: " + e.getMessage());
        }

        return null;

    }

    public boolean inserirFornecedor(MFornecedores forne) {

        String sql = "INSERT INTO Fornecedores (nome, cnpj, telefone, email, pecas_Fornecidas, quantidade) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, forne.getNome());
            stmt.setString(2, forne.getCnpj());
            stmt.setString(3, forne.getTelefone());
            stmt.setString(4, forne.getEmail());
            stmt.setString(5, forne.getPecaForne());
            stmt.setInt(6, forne.getQtd());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir Fornecedor: " + e.getMessage());
            return false;
        }
    }

    public void editarFornecedor(MFornecedores forne){

        // exibir  query sql de atualização
        String query = "UPDATE Fornecedores SET nome = ?, cnpj = ?, telefone = ? , email = ?, pecas_Fornecidas = ? , quantidade = ? WHERE id_Fornecedor = ?";

        // Criar a conexão e preparar o statement

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, forne.getNome());
            stmt.setString(2, forne.getCnpj());
            stmt.setString(3, forne.getTelefone());
            stmt.setString(4, forne.getEmail());
            stmt.setString(5, forne.getPecaForne());
            stmt.setInt(6, forne.getQtd());
            stmt.setInt(7,forne.getId());

            stmt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }

    public void deletarFornecedor(MFornecedores forne) {

        String query = "DELETE FROM Fornecedores WHERE  id_Fornecedor = ? ";

        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstnt = connection.prepareStatement(query)) {

            pstnt.setInt(1,forne.getId());
            pstnt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }


}
