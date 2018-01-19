

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class AccesoBD {
	

	private static final String URL_BD = "jdbc:sqlite:bd/BDEjercicio1.db";
	private static final String DRIVER_SQLITE = "org.sqlite.JDBC";
	

	private static final String SELECT_ALUMNOS_CON_FALTAS_MAYOR_A = "select dni from alumnos where faltas > ?";
	private static final String SELECT_NOTAS = "select * from notas";
	private static final String UPDATE_BAJAR_NOTA = "update notas set nota=nota -1 where dni=? AND nota >0";

	
	private Connection conexion;
	
	AccesoBD() throws SQLException, ClassNotFoundException{
		Class.forName(DRIVER_SQLITE);
		conexion = DriverManager.getConnection(URL_BD);
	}
	
	
	public ResultSet obtenerNotas() throws SQLException{
		Statement sentencia=conexion.createStatement();
		ResultSet resul= sentencia.executeQuery(SELECT_NOTAS);
		
		return resul;
	}
	

	public void modificarNotas(int faltas) throws SQLException{
		
		String dni;
		PreparedStatement sentencia=conexion.prepareStatement(SELECT_ALUMNOS_CON_FALTAS_MAYOR_A);
		sentencia.setInt(1, faltas);
		
		ResultSet resul = sentencia.executeQuery();
		
		PreparedStatement nuevaSentencia= conexion.prepareStatement(UPDATE_BAJAR_NOTA);
		
		
		while(resul.next()){
			dni=resul.getString(1);
			
			nuevaSentencia.setString(1, dni);
			nuevaSentencia.executeUpdate();
		}
				
		sentencia.close();
		nuevaSentencia.close();
		resul.close();
	}
	
	
	
	public void cerrarConexion() throws SQLException{
		conexion.close();
	}
	
	
}

