package DAO;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class FabricaConexao {

                                        //Driver  // ip propio pc:portaSql   /   DB
    private static final String URL = "jdbc:mysql://localhost:3306/OficinaMecanica";
    private static final String USUARIO = "root";
    //private static final String SENHA = "Np123456";
    //private static final String SENHA = "eduardo";
    private static final String SENHA = "root";

    public static Connection conectar(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");// Driver do Mysql 8+
            return DriverManager.getConnection(URL, USUARIO, SENHA);

        }catch (ClassNotFoundException e){

            System.out.println("Driver JDBC não encontrado: " + e.getMessage());

        }catch (SQLException e){

            System.out.println("Erro ao conectar" + e.getMessage());
        }
        return null;
    }

    public static void desconectar(Connection conn){

        if( conn != null){

            try {
                conn.close();
                System.out.println("Conexão encerrada. ");

            }catch (SQLException e) {

                System.out.println("Erro ao fazer conexão: " + e.getMessage());
            }
        }
    }



}
