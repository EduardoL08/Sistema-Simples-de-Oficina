package DAO;

import java.sql.Connection;

public class TesteBanco {

    public static void main(String[] args){

        Connection conn = FabricaConexao.conectar();

        if (conn != null ){

            System.out.println("Conectado ao banco de dados! ");
            FabricaConexao.desconectar(conn);

        }else {

            System.out.println("Falha na conexão. ");

        }

    }


}
