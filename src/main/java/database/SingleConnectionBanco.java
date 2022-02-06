package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SingleConnectionBanco {

	private static String url = "jdbc:postgresql://localhost:5432/jdbc-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "1q2w3e4r5t";
	private static Connection conn = null;
	
	public static Connection getConnection(){
		return conn;
	}
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() {
		conectar();
	}
	
	private static void conectar() {
		try {
			if(conn == null) {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection(url, user, senha);
				conn.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException("Erro ao fechar a conexão com o banco de dados::: " + e.getMessage());
			}
		}
	}
	
	public static void closeStatement(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException("Erro ao fechar o Statement::: " + e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException("erro ao fechar o ResultSet::: " + e.getMessage());
			}
		}
	}
}
