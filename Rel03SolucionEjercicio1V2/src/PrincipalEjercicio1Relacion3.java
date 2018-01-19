/**
 * Descripcion: 1. Se proporciona una base de datos en SQLITE (BDEjercicio1.db) 
 * con los datos de los alumnos de un centro de enseñanza. La BD dispone de dos 
 * tablas, una tabla Alumno y otra tabla Notas.
 * La tabla Notas tiene un campo DNIAlumno que referencia a la tabla Alumno.
 * Se pide realizar un programa que realice lo siguiente:
 * 
 * a) Mostrar por pantalla todos los datos que contiene la tabla Notas.
 * b) Actualizar  la nota de los alumnos de forma que a los alumnos que tienen
 * 	  más de 20 faltas se le reste un punto en todas las asignaturas.   
 * 	  Tener en cuenta que la nota tiene que seguir estando en el rango de 0­10
 * c) Volver a mostrar los datos que contiene la tabla Notas.
 * 
 *
 */

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalEjercicio1Relacion3 {

	private static final int LIMITE_FALTAS = 20;
	private final static Logger LOGGER = Logger.getLogger(AccesoBD.class.getName());
	public static void main(String[] args) {

		try {
			AccesoBD acceso = new AccesoBD();
			System.out.println("Antes de la actualización");
			listarNotas(acceso);

			acceso.modificarNotas(LIMITE_FALTAS);
			System.out.println("\nDespués de la actualización");
			listarNotas(acceso);

			acceso.cerrarConexion();
			//LOGGER.log(Level.WARNING, "Mensajito");
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.WARNING,"Error de clase no encontrada" , e);
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void listarNotas(AccesoBD acceso) throws SQLException {
		ResultSet rs;

		rs = acceso.obtenerNotas();
		ResultSetMetaData rsmd = rs.getMetaData();
		String campo;

		// Escribir la cabecera, el nombre de los campos
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			System.out.print(rsmd.getColumnName(i) + "\t");
		}
		System.out.println();

		// Recorrer el resultSet para mostrar los resultados
		while (rs.next()) {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				campo = rs.getString(i);
				System.out.print(campo + "\t");
			}
			System.out.println();
		}

		rs.close();
	}

}
