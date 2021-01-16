package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

	public Connection getConnection() {
		String nomeBD = "db_achei";
		String servidor = "" + nomeBD;
		String usuario = "";
		String senha = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexao = DriverManager.getConnection(servidor, usuario, senha);
			System.out.println("Conexao aberta");
			return conexao;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Erro ao obter conexao com o banco "+e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static void fecharConexao(Connection con) {
		try {
			con.close();
			System.out.println("Conexao fechada");
		} catch (SQLException e) {
			System.out.println(e);
			throw new RuntimeException(e);
		}
	}
}