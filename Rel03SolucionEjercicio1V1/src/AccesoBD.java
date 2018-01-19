

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
	

	private static final String SELECT_ALUMNOS_CON_FALTAS_MAYOR_A 
				= "select dni from alumnos where faltas > ";
	private static final String SELECT_NOTAS = "select * from notas";
	private static final String UPDATE_BAJAR_NOTA = 
			"update notas set nota=nota -1 where nota >0 and dni=";

	
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
		Statement sentenciaSelect=conexion.createStatement();
	
		
		ResultSet resul = sentenciaSelect.executeQuery(SELECT_ALUMNOS_CON_FALTAS_MAYOR_A + faltas);
		Statement nuevaSentencia= conexion.createStatement(); 
	
		
		
		while(resul.next()){
			dni=resul.getString(1);
			System.out.println(dni);
			
			
			nuevaSentencia.executeUpdate(UPDATE_BAJAR_NOTA + dni);
		}
				
		sentenciaSelect.close();
		nuevaSentencia.close();
		
	}
	
	
	
	public void cerrarConexion() throws SQLException{
		conexion.close();
	}
	
	
}

