
/**
 * 1.
 * Se proporciona una base de datos en SQLITE (BDEjercicio1.db) con los datos de
 * los alumnos de un centro de enseñanza. La BD dispone de dos tablas, una tabla
 * Alumno y otra tabla Notas. 
 * 
 * La tabla Notas tiene un campo DNIAlumno que referencia a la tabla Alumno.
 * Se pide realizar un programa que realice lo siguiente:
 * 
 * a) Mostrar por pantalla todos los datos que contiene la tabla Notas. 
 * 
 * b) Actualizar la nota de los alumnos de forma que a los alumnos que tienen
 * más de 20 faltas se le reste un punto en todas las asignaturas.   Tener en
 * cuenta que la nota tiene que seguir estando en el rango de 0­10. 
 * 
 * c) Volver a mostrar los datos que contiene la tabla Notas.
 * 
 * @author fran
 *
 */

import java.sql.*;

public class Principal {
	private static final String URL_BD = "jdbc:sqlite:bd/BDEjercicio1.db";
	private static final String DRIVER_SQLITE = "org.sqlite.JDBC";
	private static final int FALTAS_MIN = 20;

	public static void main(String[] args) {

		try {

			Class.forName(DRIVER_SQLITE);
			Connection conexion = DriverManager.getConnection(URL_BD);

			Statement sentencia = conexion.createStatement();

			mostrarAlumnosFaltas(sentencia);
			System.out.println();
			mostrarAlumnosNotas(sentencia);
			System.out.println();
			quitarPuntos(conexion);
			mostrarAlumnosNotas(sentencia);

			sentencia.close();
			conexion.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void mostrarAlumnosFaltas(Statement sentencia) throws SQLException {

		ResultSet resultadoQuery;
		resultadoQuery = sentencia.executeQuery("select * from alumnos");

		while (resultadoQuery.next()) {
			System.out.print("dni:" + resultadoQuery.getString(1));
			System.out.print(" Nombre:" + resultadoQuery.getString(2));
			System.out.println(" faltas:" + resultadoQuery.getString(3));
		}
	}

	private static void mostrarAlumnosNotas(Statement sentencia) throws SQLException {

		ResultSet resultadoQuery;
		resultadoQuery = sentencia.executeQuery("select * from notas");

		while (resultadoQuery.next()) {
			System.out.print("dni:" + resultadoQuery.getString(1));
			System.out.print(" asignatura:" + resultadoQuery.getString(2));
			System.out.println(" nota:" + resultadoQuery.getString(3));
		}
	}

	private static void quitarPuntos(Connection conexion) throws SQLException {
		// Recoger lista dni alumnos < min
		ResultSet resultadoQuery;
		Statement sentenciaselect;
		Statement sentenciaupdate;

		sentenciaselect = conexion.createStatement();
		sentenciaupdate = conexion.createStatement();

		resultadoQuery = sentenciaselect.executeQuery("select dni from alumnos where faltas > " + FALTAS_MIN);

		// Quitar Punto a lista notas

		while (resultadoQuery.next()) {
			if (sentenciaupdate.executeUpdate("update notas set nota = nota - 1 where dni = "
					+ resultadoQuery.getString(1) + " and nota > 0;") > 0)
				System.out.println("Hecho argo");
			else
				System.out.println("NO HECHO NA");
		}
		
		sentenciaselect.close();
		sentenciaupdate.close();

	}

}
