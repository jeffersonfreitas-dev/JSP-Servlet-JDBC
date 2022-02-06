package repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.SingleConnectionBanco;
import model.Login;

public class DAOLoginRepository {
	
	private static Connection conn;
	
	public DAOLoginRepository() {
		conn = SingleConnectionBanco.getConnection();
	}
	
	public Boolean validarLogin(Login login) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from login where upper(login) = upper(?) and senha = ?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, login.getLogin());
			stmt.setString(2, login.getSenha());
			rs = stmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			SingleConnectionBanco.closeStatement(stmt);
			SingleConnectionBanco.closeResultSet(rs);
		}
		return false;
	}

}
