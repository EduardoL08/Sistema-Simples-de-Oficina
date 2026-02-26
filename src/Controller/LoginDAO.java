package Controller;

import DAO.FabricaConexao;
import Model.MUsuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO { // DBA = DATA ACCESS OBJECT / OBJETO DE ACESSO A DADOS

    public MUsuarios validarLogin (String login, String senha, String perfil) {

        MUsuarios levaDados;
        levaDados = null;

        String sql = " SELECT * FROM usuarios WHERE login = ? AND senha = ? AND perfil = ? ";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // STMT É O OBJETO QUE VAI NO BANCO E  VAI TRAZER OS DADOS QUE FORAM EZIGIDOS

            stmt.setString(1, login);
            stmt.setString(2, senha);
            stmt.setString(3, perfil);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                levaDados = new MUsuarios();
                levaDados.setLogin(rs.getNString("login"));
                levaDados.setSenha(rs.getNString("senha"));
                levaDados.setPerfil(rs.getNString("perfil"));
            }

        } catch (SQLException e) {

            System.out.println("Erro ao validar login: " + e.getMessage());

        }

        return levaDados;

    }
}
