import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccesoBD {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String RUTA_DB = "jdbc:mysql://localhost/academia";
	private static final String USER = "root";
	private static final String PASS = "1234";

	
	private Connection conexion;
	
	public AccesoBD() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		conexion = DriverManager.getConnection(RUTA_DB, USER, PASS);
		conexion.setAutoCommit(false);
		System.out.println();
	}
	
	public void cerrarConexion() throws SQLException {
		conexion.close();
	}
	
	private void crearCampoTipo() throws SQLException {
		Statement sentencia = conexion.createStatement();
		sentencia.executeUpdate("ALTER TABLE persona ADD tipo VARCHAR(1)");
	
		
		
		sentencia.close();
	}
	
	public void almacenarTipoPersona() throws SQLException {
		this.crearCampoTipo();
		
		this.actualizarAlumnos();
		this.actualizarProfesores();
		conexion.commit();
	
	}
	
	public void rollback() {
		try {
			conexion.rollback();
			System.out.println("Hace rollback");
			
		} catch (SQLException e) {
			System.out.println("Error al realizar el rollback");
		}
	}
	
	private void actualizarAlumnos() throws SQLException {
		String dni;
		
		Statement sentencia1 = conexion.createStatement();
		ResultSet result = sentencia1.executeQuery("SELECT alumno.dni FROM alumno");
		
		while (result.next()) {
			dni = result.getString(1);
			
			
			PreparedStatement sentencia2 = conexion.prepareStatement("UPDATE persona SET tipo='A' WHERE dni = ?");
			sentencia2.setString(1, dni);
			sentencia2.executeUpdate();
		}
	}
	
	private void actualizarProfesores() throws SQLException {
		String dni;
		
		Statement sentencia1 = conexion.createStatement();
		ResultSet result = sentencia1.executeQuery("SELECT dni FROM profesor;");
		
		while (result.next()) {
			dni = result.getString(1);
			
			PreparedStatement sentencia2 = conexion.prepareStatement("UPDATE persona SET tipo = 'P' WHERE dni = ?");

			sentencia2.setString(1, dni);
			sentencia2.executeUpdate();
		}
	}
	
	public String obtenerListadoAsignaturas() throws SQLException {
		String idAsignatura, nombreAsignatura, numAlumnos;
		StringBuilder msg = new StringBuilder();
		
		Statement sentencia = conexion.createStatement();
		ResultSet result = sentencia.executeQuery(
				"SELECT asignatura.Idasignatura, asignatura.Nombre, "
				+ " COUNT(alumno_asignatura.Idalumno)"
				+ " FROM asignatura, alumno_asignatura"
				+ " WHERE alumno_asignatura.Idasignatura = asignatura.Idasignatura "
				+ " GROUP BY asignatura.Idasignatura ");
		
		while (result.next()) {
			idAsignatura = result.getString(1);
			nombreAsignatura = result.getString(2);
			numAlumnos = result.getString(3);			

			msg.append("ID ASIGNATURA: "+idAsignatura+"\tNOMBRE ASIG: "+nombreAsignatura+"\tNUMERO DE ALUMNOS: "+numAlumnos+"\n");
		
			msg.append(this.obtenerListadoAlumnosPorAsignatura(idAsignatura));
		}
		
		return msg.toString();
	}
	
	private String obtenerListadoAlumnosPorAsignatura(String idAsignatura) throws SQLException {
		String dni, nombre, apellidos;
		StringBuilder msg = new StringBuilder();
		
		PreparedStatement sentencia = conexion.prepareStatement(
				"SELECT persona.dni, persona.Nombre, persona.Apellido "
				+ "FROM persona, alumno, alumno_asignatura "
				+ "WHERE persona.dni = alumno.dni "
				+ "AND alumno.idalumno = alumno_asignatura.Idalumno "
				+ "AND alumno_asignatura.Idasignatura = ? "
				+ "ORDER BY persona.Apellido;");
		
		sentencia.setString(1, idAsignatura);
		ResultSet result = sentencia.executeQuery();
		
		msg.append("DNI\tApellidos\tNombre\n");
		
		while (result.next()) {
			dni = result.getString(1);
			nombre = result.getString(2);
			apellidos = result.getString(3);
			
			msg.append(dni+"\t"+apellidos+"\t"+nombre+"\n");
		}
		
		return msg.toString();
	}
}
