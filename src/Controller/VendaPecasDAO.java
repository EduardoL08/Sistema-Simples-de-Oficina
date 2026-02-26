package Controller;

import DAO.FabricaConexao;
import Model.MVendaPecas;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VendaPecasDAO {

    public void SalvarVendas(MVendaPecas venda) {

        Connection conn = null ;
        PreparedStatement ps = null;
        PreparedStatement psPV = null;

        try {
            conn = FabricaConexao.conectar();
            conn.setAutoCommit(false); // Inicia uma transação

            String sql = "INSERT INTO VendasPecas (codigo_Venda, fk_id_Vendedor, fk_id_Cliente,data_Venda, forma_Pagamento, desconto , valor_Total) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Aqui está a correção - inicialização do PreparedStatement
            ps = conn.prepareStatement(sql);

            ps.setString(1, venda.getCodigoVenda());
            ps.setInt(2, venda.getIdVendedor());
            ps.setInt(3, venda.getIdCliente());
            ps.setString(4, venda.getDataVenda());
            ps.setString(5, venda.getFormaPagamento());
            ps.setDouble(6, venda.getDesconto());
            ps.setDouble(7, venda.getValorTotal());

            ps.executeUpdate();

            String sqlPV = "INSERT INTO VendasPecas_Tem_Pecas (fk_codigo_Venda, fk_codigo_Peca, quantidade, valor_Unitario) VALUES (?, ?, ?, ?)";
            psPV = conn.prepareStatement(sqlPV);

            for (MVendaPecas.PecasVendidas pv : venda.getPV()) {
                psPV.setString(1, venda.getCodigoVenda());
                psPV.setString(2, pv.getCodigoPeca());
                psPV.setInt(3, pv.getQuantidade());
                psPV.setDouble(4, pv.getValorUnitario());
                psPV.executeUpdate();
            }

            conn.commit();
            JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");

        } catch (SQLException e) {
            try {

                if (conn != null) {
                    conn.rollback();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Erro ao salvar venda: " + e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                if (ps != null) ps.close();
                if (psPV != null) psPV.close();
                if (conn != null) conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ExcluirVenda(String codigo) {
        try {
            String sql = "DELETE FROM VendasPecas WHERE codigo_Venda = ?";
            PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
            ps.setString(1, codigo);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Venda excluída com sucesso!");
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir venda: " + e.getMessage());
        }
    }
}

