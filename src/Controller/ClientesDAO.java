package Controller;

import DAO.FabricaConexao;
import Model.MClientes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientesDAO {

    public ResultSet ListaClientes() {

        String sql = "SELECT * FROM Clientes";

        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();

        } catch (SQLException e) {

            System.out.println("Erro ao validar login: " + e.getMessage());
        }

        return null;

    }

    public boolean inserirClientes(MClientes cliente) {

        String sql = "INSERT INTO Clientes (nome, cpf, telefone, email, endereco, dataCadastro) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getEndereco());
            stmt.setString(6, cliente.getDataCadastro());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
            return false;
        }
    }

    public void editarClientes (MClientes cli){

        // exibir  query sql de atualização
        String query = "UPDATE Clientes SET nome = ?, cpf = ?, telefone = ? , email = ?, endereco = ? , dataCadastro = ? WHERE id_Cliente = ?";

        // Criar a conexão e preparar o statement

        try { Connection connection = FabricaConexao.conectar();
            PreparedStatement pstnt = connection.prepareStatement(query);

            //definir os parametros do preparestatement

            pstnt.setString(1,cli.getNome());
            pstnt.setString(2, cli.getCpf());
            pstnt.setString(3, cli.getTelefone());
            pstnt.setString(4, cli.getEmail());
            pstnt.setString(5, cli.getEndereco());
            pstnt.setString(6, cli.getDataCadastro());
            pstnt.setInt(7, cli.getId());

            pstnt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }

    public void deletarClientes (MClientes cli) {

        String query = "DELETE FROM Clientes WHERE  id_Cliente = ? ";

        try (Connection connection = FabricaConexao.conectar();
            PreparedStatement pstnt = connection.prepareStatement(query)) {

            pstnt.setInt(1,cli.getId());
            pstnt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }
    
}