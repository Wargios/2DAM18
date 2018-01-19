

package ejercicio3;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PrincipalEjercicio3 {
	private static Scanner teclado = new Scanner(System.in);

	// CONSTANTES
	private static final String URL_BD = "jdbc:mysql://localhost/BDCLIENTES";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String CONSTRASENNA = "1234";
	private static final String USUARIO = "root";
	
	// ATRIBUTO ESTÁTICO
	private static Connection conexion;
	
	public static void main(String[] args) {
		// VARIABLES
		String tabla;
		char caracter;
		
		// DESARROLLO
		try {
			
			PrincipalEjercicio3.abrirConexion();
			
			System.out.print("Introduce el nombre de la tabla: ");
			tabla = teclado.nextLine();
			
			System.out.print("Introduce el carácter: ");
			caracter = teclado.nextLine().charAt(0);
			
			PrincipalEjercicio3.borrarCamposPorLetra(tabla, caracter);
			
			PrincipalEjercicio3.cerrarConexion();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// MÉTODOS
	private static void abrirConexion() throws Exception {
		try {
			
			Class.forName(DRIVER);
			
			conexion = DriverManager.getConnection(URL_BD, USUARIO, CONSTRASENNA);
			
		} catch (SQLException e) {
			throw new Exception("Error al conectar a la base de datos.");
		}
	}
	
	private static void borrarCamposPorLetra(String tabla, char caracter) throws Exception {
		Statement sentenciaSelect, sentenciaAlter;
		ResultSet resultado;
		ResultSetMetaData metadatosResultado;
		String nombreColumna;
		boolean borradoCampo=false;
		
		if (PrincipalEjercicio3.comprobarTabla(tabla)) {
			throw new Exception("Error. No existe la tabla indicada.");
		}
		
		try {
			
			sentenciaSelect = conexion.createStatement();
			resultado = sentenciaSelect.executeQuery("SELECT * FROM " + tabla);
			metadatosResultado = resultado.getMetaData();
			
			for (int contador = 1; contador <= metadatosResultado.getColumnCount(); contador++) {
				nombreColumna = metadatosResultado.getColumnName(contador);
				
				if (nombreColumna.startsWith(String.valueOf(caracter))) {
					sentenciaAlter = conexion.createStatement();
					sentenciaAlter.execute("ALTER TABLE " + tabla + " DROP COLUMN " + nombreColumna);
					System.out.println("Se ha borrado el campo " + nombreColumna);
					borradoCampo=true;
				}
			}
			
			if (!borradoCampo) {
				System.out.println("No se ha borrado ningún campo");
			}
			
		} catch (SQLException e) {
			throw new Exception("Error al ejecutar una sentencia.");
		}
	}
	
	private static boolean comprobarTabla(String tabla) throws Exception {
		boolean existeTabla = false;
		DatabaseMetaData metadatosBD;
		ResultSet resultado;
		
		try {
			
			metadatosBD = conexion.getMetaData();
			resultado = metadatosBD.getTables(null, null, tabla, null);
			
			while (resultado.next()) {
				if (resultado.getString(1).equals(tabla)) {
					existeTabla = true;
				}
			}
			
		} catch (SQLException e) {
			throw new Exception("Error al extraer los metadatos de la base de datos.");
		}
		
		return existeTabla;
	}

	private static void cerrarConexion() throws Exception {
		try {
			
			conexion.close();
			
		} catch (SQLException e) {
			throw new Exception("Error al desconectar de la base de datos.");
		}
	}
}
