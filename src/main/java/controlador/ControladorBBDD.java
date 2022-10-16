package controlador;

import java.sql.*;

public class ControladorBBDD {
	

	Connection c;
	String driver="com.mysql.cj.jdbc.Driver";
	private PreparedStatement ps;
	private ResultSet rs;
	private Statement cs;
	private String url = "jdbc:mysql://localhost:3306/siberia";
	private String usuario = "root";
	private String contrasena = "Afmamtz19923112001";

	
	public ControladorBBDD() throws SQLException, ClassNotFoundException {
		
		Class.forName(driver);
		c=DriverManager.getConnection(url, usuario, contrasena);
	}
	
	public void prepararConsulta(String query, int a, int b) throws SQLException {
		ps= c.prepareStatement(query,a,b);
	}
	
	public void crearConsultaNormal(int a, int b) throws SQLException{
		cs = c.createStatement(a,b);
		
	}
	
	public void darParametroString(int posición, String parámetro) throws SQLException {
		ps.setString(posición, parámetro);
	}
	
	public void darParametroInt(int posición, int parámetro) throws SQLException {
		ps.setInt(posición, parámetro);
	}
	
	public void darParamteroDouble(int posición, double parámetro) throws SQLException {
		ps.setDouble(posición, parámetro);
	}
	
	public void darParametroDate(int posición, Date parámetro) throws SQLException {
		ps.setDate(posición, parámetro);
	}
	
	public void darParametroTime(int posición, Time parámetro) throws SQLException {
		ps.setTime(posición, parámetro);
	}
	
	public void ExecuteCreateUpdateOrDelete() throws SQLException {
		ps.executeUpdate();
		ps.close();
		c.close();
	}
	
	public void cerrarOperaciónConsultaPreparada() throws SQLException {
		ps.close();
		rs.close();
		c.close();
	}
	
	public ResultSet EjecutarConsultaPreparada() throws SQLException {
		rs = ps.executeQuery();
		return rs;
	}
	
	public ResultSet EjecutarConsultaNormal(String query) throws SQLException{
		rs = cs.executeQuery(query);
		return rs;
	}
	
	public int dameCantidadRegistros() throws SQLException {
		rs.first();
		return rs.getRow();
		
	}
	
	public void cerrarConsultaNormal() throws SQLException{
		c.close();
		cs.close();
		rs.close();
	}
	
}