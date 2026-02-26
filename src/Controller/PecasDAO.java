package Controller;

import DAO.FabricaConexao;
import Model.MPecas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PecasDAO {

    public ResultSet ListaPecas() {

        String sql = "SELECT * FROM Pecas";

        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            return stmt.executeQuery();

        } catch (SQLException e) {

            System.out.println("Erro ao listar Pecas: " + e.getMessage());
        }

        return null;

    }
    public boolean inserirPeca(MPecas peca){

        String sql = "INSERT INTO Pecas (nome, codigo, descricao, qtd_Estoque, preco_Custo, preco_Venda, fk_id_Fornecedor) VALUES (?,?,?,?,?,?,?)";

        try { Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, peca.getNomePeca());
            stmt.setString(2, peca.getCodigo());
            stmt.setString(3, peca.getDescricao());
            stmt.setInt(4, peca.getQuantidade());
            stmt.setDouble(5, peca.getPrecoCusto());
            stmt.setDouble(6, peca.getPrecoVenda());
            stmt.setInt(7, peca.getIdFornecedor());

            return stmt.executeUpdate() > 0;

        }catch (SQLException e) {

            System.out.println("Erro ao inserir peca: " + e.getMessage());

            return false;
        }
    }
    public void editarPeca(MPecas peca){

        // exibir  query sql de atualização
        String query = "UPDATE Pecas SET nome = ?, codigo = ?, descricao = ?, qtd_Estoque = ?, preco_Custo = ?, preco_Venda = ?, fk_id_Fornecedor = ? WHERE id_Peca = ?";

        // Criar a conexão e preparar o statement

        try { Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, peca.getNomePeca());
            stmt.setString(2, peca.getCodigo());
            stmt.setString(3, peca.getDescricao());
            stmt.setInt(4, peca.getQuantidade());
            stmt.setDouble(5, peca.getPrecoCusto());
            stmt.setDouble(6, peca.getPrecoVenda());
            stmt.setInt(7, peca.getIdFornecedor());
            stmt.setInt(8, peca.getId());


            stmt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }
    public void deletarPeca(MPecas peca) {

        String query = "DELETE FROM Pecas WHERE  id_Peca = ? ";

        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstnt = connection.prepareStatement(query)) {

            pstnt.setInt(1,peca.getId());
            pstnt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }
    public boolean verificaFornecedorExiste(int idFornecedor) {
        String sql = "SELECT 1 FROM Fornecedores WHERE id_Fornecedor = ?";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFornecedor);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.err.println("Erro ao verificar fornecedor: " + e.getMessage());
            return false;
        }
    }

}
