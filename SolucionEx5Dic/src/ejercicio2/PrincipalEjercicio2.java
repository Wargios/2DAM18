
package ejercicio2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrincipalEjercicio2 {
	// CONSTANTES
	// Base de datos H2.
	private static final String URL_TIENDA = "jdbc:h2:./bd/TIENDA";
	private static final String DRIVER_TIENDA = "org.h2.Driver";
	private static final String USUARIO_TIENDA = "sa";
	private static final String CONTRASENNA_TIENDA = "";
	// Base de datos MySQL.
	private static final String URL_NUEVATIENDA =  "jdbc:mysql://localhost/NUEVATIENDA";;
	private static final String DRIVER_NUEVATIENDA ="com.mysql.jdbc.Driver"; ;
	private static final String USUARIO_NUEVATIENDA = "root";
	private static final String CONTRASENNA_NUEVATIENDA = "1234";
	
	// VARIABLES ESTÁTICAS
	private static Connection conexionH2, conexionMySql;

	public static void main(String[] args) {
		// DESARROLLO
		try {
			
			PrincipalEjercicio2.abrirConexiones();
			
			PrincipalEjercicio2.trasvase();
			
			PrincipalEjercicio2.cerrarConexiones();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// MÉTODOS
	private static void abrirConexiones() throws Exception {
		try {
			
			Class.forName(DRIVER_TIENDA);
			conexionH2 = DriverManager.getConnection(URL_TIENDA, USUARIO_TIENDA, CONTRASENNA_TIENDA);
			
			System.out.println("Se abre la conexión con la base de datos Tienda.\n");
			
			
			Class.forName(DRIVER_NUEVATIENDA);
			conexionMySql = DriverManager.getConnection(URL_NUEVATIENDA, USUARIO_NUEVATIENDA, CONTRASENNA_NUEVATIENDA);
			
			
			// No se ejecutan las sentencias hasta que se haga commit.
			conexionH2.setAutoCommit(false);
			conexionMySql.setAutoCommit(false);
			System.out.println("Se abre la conexión con la base de datos Nueva Tienda.\n");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception("Error al establecer la conexión");
			
		}
		
	}
	
	private static void trasvase() throws Exception {		
		PrincipalEjercicio2.obtenerEInsertarDatosProveedoresEspanha();
		PrincipalEjercicio2.obtenereInsertarDatosArticulosEspanha();
		
					
	}

	private static ResultSet obtenerEInsertarDatosProveedoresEspanha() throws Exception {
		Statement sentencia;
		ResultSet resultado = null;
		
		try {
			
			
			
			// Se obtienen los datos de proveedores de España.
			sentencia = conexionH2.createStatement();
			resultado = sentencia.executeQuery("SELECT * FROM PROVEEDOR WHERE PAIS = 'España'");
			
			// Se insertan los datos obtenidos.
			PrincipalEjercicio2.insertarDatosProveedoresEspanha(resultado);
			
			// Se cierran resultado y sentencia.
			resultado.close();
			sentencia.close();
			
		
			
		} catch (SQLException e) {
			conexionH2.rollback();
			conexionMySql.rollback();
			throw new Exception("Error al consultar los proveedores españoles.");
		}
		
		System.out.println("Se obtienen los datos de los proveedores españoles en H2.\n");
		
		return resultado;
	}
	
	private static ResultSet obtenereInsertarDatosArticulosEspanha() throws Exception {
		Statement sentencia;
		ResultSet resultado = null;
		String sql;
		
		try {
			
		
			
			// Se obtienen los artículos de proveedores españoles.
			sql = "SELECT A.* "
					+ "FROM ARTICULO AS A, PROVEEDOR AS P "
					+ "WHERE P.ID = A.ID_PROVEEDOR AND P.PAIS = 'España'";
			
			sentencia = conexionH2.createStatement();
			resultado = sentencia.executeQuery(sql);
			
			// Se insertan los datos obtenidos,
			PrincipalEjercicio2.insertarDatosArticulosEspanha(resultado);
			
			// Se cierran resultado y sentencia.
			resultado.close();
			sentencia.close();
			
			// Se ejecutan todas las sentencias.
			conexionH2.commit();
			conexionMySql.commit();
			
		} catch (SQLException e) {
			conexionH2.rollback();
			conexionMySql.rollback();
			throw new Exception("Error al consultar los artículos de proveedores españoles.");
		}
		
		System.out.println("Se obtienen los datos de los artículos de proveedores españoles en H2.\n");
		
		return resultado;
	}
	
	private static void insertarDatosProveedoresEspanha(ResultSet resultadoProveedores) throws Exception {
		PreparedStatement sentenciaPreparada = null;
		String sql, nombre, pais;
		int id;
		
		try {
			
			while (resultadoProveedores.next()) {
				
				// Se prepara el INSERT.
				sql = "INSERT INTO PROVEEDOR(ID, NOMBRE, PAIS) VALUES(?, ?, ?)";
				sentenciaPreparada = conexionMySql.prepareStatement(sql);
				
				// Se recogen y añaden los valores.
				id = resultadoProveedores.getInt(1);
				sentenciaPreparada.setInt(1, id);
				nombre = resultadoProveedores.getString(2);
				sentenciaPreparada.setString(2, nombre);
				pais = resultadoProveedores.getString(3);
				sentenciaPreparada.setString(3, pais);
				
				// Se ejecuta la sentencia.
				sentenciaPreparada.executeUpdate();
			}
			
			// Se cierra la sentencia.
			sentenciaPreparada.close();
			
		} catch (SQLException e) {
			throw new Exception("Error al insertar los datos de proveedores españoles.");
		}
		
		System.out.println("Se insertan los datos de los proveedores españoles en MySQL.\n");
	}
	
	private static void insertarDatosArticulosEspanha(ResultSet resultadoArticulos) throws Exception {
		PreparedStatement sentenciaPreparada = null;
		String sql, nombre, descripcion;
		int id, idProveedor;
		
		try {
			
			while (resultadoArticulos.next()) {
				
				// Se prepara el INSERT.
				sql = "INSERT INTO ARTICULO(ID, NOMBRE, DESCRIPCION, ID_PROVEEDOR) VALUES(?, ?, ?, ?)";
				sentenciaPreparada = conexionMySql.prepareStatement(sql);
				
				// Se recogen y añaden los valores.
				id = resultadoArticulos.getInt(1);
				sentenciaPreparada.setInt(1, id);
				
				nombre = resultadoArticulos.getString(2);
				sentenciaPreparada.setString(2, nombre);
				
				descripcion = resultadoArticulos.getString(3);
				sentenciaPreparada.setString(3, descripcion);
				
				idProveedor = resultadoArticulos.getInt(4);
				sentenciaPreparada.setInt(4, idProveedor);
				
				// Se ejecuta la sentencia.
				sentenciaPreparada.executeUpdate();
			}
			
			// Se cierra la sentencia.
			sentenciaPreparada.close();
			
		} catch (SQLException e) {
			throw new Exception("Error al insertar los artículos de proveedores españoles.");
		}
		
		System.out.println("Se insertan los datos de los artículos de proveedores españoles en MySQL.\n");
	}

	private static void cerrarConexiones() throws Exception {
		try {
			
			conexionH2.close();
			System.out.println("Se cierra la conexión con la base de datos H2.\n");
			
		} catch (SQLException e) {
			throw new Exception("Error al cerrar la conexión con la base de datos H2.");
		}
		
		try {
			
			conexionMySql.close();
			System.out.println("Se cierra la conexión con la base de datos MySQL.\n");
			
		} catch (SQLException e) {
			throw new Exception("Error al cerrar la conexión con la base de datos MySQL.");
		}
	}
}