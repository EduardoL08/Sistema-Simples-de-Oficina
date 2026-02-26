package Controller;

import DAO.FabricaConexao;
import Model.MClientes;
import Model.MDespesas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DespesasDAO {

    public ResultSet ListaDespesas() {

        String sql = "SELECT * FROM Despesas";

        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();

        } catch (SQLException e) {

            System.out.println("Erro ao validar despesa: " + e.getMessage());
        }

        return null;

    }

    public boolean inserirDespesa(MDespesas desp) {

        String sql = "INSERT INTO Despesas (nome, descricao, dataValidade, valor, status) VALUES (?, ?, ?, ?,?)";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, desp.getNomeDesp());
            stmt.setString(2, desp.getDescricao());
            stmt.setString(3, desp.getDataVenci());
            stmt.setString(4, desp.getValor());
            stmt.setString(5, desp.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir despesa: " + e.getMessage());
            return false;
        }
    }

    public void editarDespesa(MDespesas desp){

        // exibir  query sql de atualização
        String query = "UPDATE Despesas SET nome = ?, descricao = ?, dataValidade = ? , valor = ?, status = ? WHERE id_Despesa = ?";

        // Criar a conexão e preparar o statement

        try { Connection connection = FabricaConexao.conectar();
            PreparedStatement pstnt = connection.prepareStatement(query);

            //definir os parametros do preparestatement

            pstnt.setString(1,desp.getNomeDesp());
            pstnt.setString(2, desp.getDescricao());
            pstnt.setString(3, desp.getDataVenci());
            pstnt.setString(4, desp.getValor());
            pstnt.setString(5, desp.getStatus());
            pstnt.setInt(6, desp.getId());

            pstnt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }

    public void deletarDespesas (MDespesas desp) {

        String query = "DELETE FROM Despesas WHERE  id_Despesa = ? ";

        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstnt = connection.prepareStatement(query)) {

            pstnt.setInt(1,desp.getId());
            pstnt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }
}
