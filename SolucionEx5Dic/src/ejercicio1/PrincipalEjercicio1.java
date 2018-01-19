

package ejercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrincipalEjercicio1 {
	// CONSTANTES
	private static final String URL_TIENDA = "jdbc:h2:./bd/TIENDA";
	private static final String DRIVER_TIENDA = "org.h2.Driver";
	private static final String USUARIO_TIENDA = "sa";
	private static final String CONTRASENNA_TIENDA = "";
	
	// VARIABLE ESTÁTICA
	private static Connection conexion;
	
	public static void main(String[] args) {
		// DESARROLLO
		try {
			
			PrincipalEjercicio1.abrirConexion();
			
			PrincipalEjercicio1.listadoProveedorMinimoUnProducto();
			
			PrincipalEjercicio1.cerrarConexion();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// MÉTODOS
	private static void abrirConexion() throws Exception {
		try {
			
			Class.forName(DRIVER_TIENDA);
			
			conexion = DriverManager.getConnection(URL_TIENDA, USUARIO_TIENDA, CONTRASENNA_TIENDA);
			
		} catch (SQLException e) {
			throw new Exception("Error al conectar a la base de datos.");
		}
	}
	
	private static void listadoProveedorMinimoUnProducto() throws Exception {
		Statement sentencia;
		ResultSet resultado;
		String sql;
		
		try {
			
			sql = "SELECT P.NOMBRE, COUNT(A.ID_PROVEEDOR) AS NUMERO_PRODUCTOS "
					+ "FROM PROVEEDOR AS P, ARTICULO AS A "
					+ "WHERE P.ID = A.ID_PROVEEDOR "
					+ "GROUP BY A.ID_PROVEEDOR";
			
			sentencia = conexion.createStatement();
			resultado = sentencia.executeQuery(sql);
			
			// Si al menos hay un resultado, se muestra un encabezado y el primer resultado.
			if (resultado.first()) {
				System.out.println("Proveedor" + "\tNúmero de artículos suministrados");
				System.out.println(resultado.getString(1) + "\t\t" + resultado.getInt(2));
				
				// Si hay más resultados, se van mostrando.
				while (resultado.next()) {
					System.out.println(resultado.getString(1) + "\t\t" + resultado.getInt(2));
				}
			}
			
			resultado.close();
			sentencia.close();
			
		} catch (SQLException e) {
			throw new Exception("Error al hacer una sentencia en la base de datos.");
		}
	}
	
	private static void cerrarConexion() throws Exception {
		try {
			
			conexion.close();
			
		} catch (SQLException e) {
			throw new Exception("Error al desconectar de la base de datos.");
		}
	}
}