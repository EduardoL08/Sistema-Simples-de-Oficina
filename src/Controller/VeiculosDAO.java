package Controller;

import DAO.FabricaConexao;
import Model.MVeiculos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeiculosDAO {

    public ResultSet ListaVeiculos() {

        String sql = "SELECT * FROM Veiculos";

        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            return stmt.executeQuery();

        } catch (SQLException e) {

            System.out.println("Erro ao listar Veiculo: " + e.getMessage());
        }

        return null;

    }
    public boolean inserirVeiculo(MVeiculos veiculo){

        String sql = "INSERT INTO Veiculos (fk_id_Cliente, placa, marca, modelo, ano, cor, tipo, kms, observacao) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, veiculo.getIdCliente());
            stmt.setString(2, veiculo.getPlacas());
            stmt.setString(3, veiculo.getMarcas());
            stmt.setString(4, veiculo.getModelos());
            stmt.setInt(5, veiculo.getAnos());
            stmt.setString(6, veiculo.getCores());
            stmt.setString(7, veiculo.getTipos());
            stmt.setDouble(8, veiculo.getKms());
            stmt.setString(9, veiculo.getObser());

            return stmt.executeUpdate() > 0;

        }catch (SQLException e) {

            System.out.println("Erro ao inserir veiculo: " + e.getMessage());

            return false;
        }

    }
    public void editarVeiculo(MVeiculos veiculo){

        // exibir  query sql de atualização
        String query = "UPDATE Veiculos SET fk_id_Cliente = ?, placa = ?, marca = ?, modelo = ?, ano = ?, cor = ?, tipo = ?, kms = ?, observacao = ? WHERE id_Veiculo = ?";

        // Criar a conexão e preparar o statement

        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, veiculo.getIdCliente());
            stmt.setString(2, veiculo.getPlacas());
            stmt.setString(3, veiculo.getMarcas());
            stmt.setString(4, veiculo.getModelos());
            stmt.setInt(5, veiculo.getAnos());
            stmt.setString(6, veiculo.getCores());
            stmt.setString(7, veiculo.getTipos());
            stmt.setDouble(8, veiculo.getKms());
            stmt.setString(9, veiculo.getObser());
            stmt.setInt(10, veiculo.getId());

            stmt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }
    public void deletarVeiculo(MVeiculos veiculo) {

        String query = "DELETE FROM Veiculos WHERE  id_Veiculo = ? ";

        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstnt = connection.prepareStatement(query)) {

            pstnt.setInt(1,veiculo.getId());
            pstnt.executeUpdate();

        }catch (SQLException e){

            e.printStackTrace();
        }
    }
    public static boolean verificaClienteExiste(int idCliente) {
        String sql = "SELECT 1 FROM Clientes WHERE id_Cliente = ?";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.err.println("Erro ao verificar Cliente: " + e.getMessage());
            return false;
        }
    }
}
