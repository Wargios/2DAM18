import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Principal {

	public static void main(String[] args) {

		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		try {
			llenarArrayAlumnos(alumnos, "Alumnos.txt");
			crearDirectoriosAlumnos(alumnos);
			System.out.println("Operación completada");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	private static void crearDirectoriosAlumnos(ArrayList<Alumno> alumnos) {
		for (Alumno alumno : alumnos) {
			crearDirectorio( alumno.getNombre());
		}
		// "alumnos/" +
	}

	public static void crearDirectorio(String nombreDirectorio) throws SecurityException {
		File directorio = new File(nombreDirectorio);

		if (!directorio.exists()) {
			boolean resul = false;

			directorio.mkdir();
			resul = true;

			if (resul)
				System.out.println("Directorio " + nombreDirectorio + " creado.");

		}

	}

	public static void llenarArrayAlumnos(ArrayList<Alumno> arrayAlumnos, String archivoAlumnosTxt) throws IOException {
		String linea;
		FileReader archivoLectura;
		BufferedReader filtroLectura;

		archivoLectura = new FileReader(archivoAlumnosTxt);
		filtroLectura = new BufferedReader(archivoLectura);

		while ((linea = filtroLectura.readLine()) != null) {
			arrayAlumnos.add(crearAlumno(linea));
		}

		filtroLectura.close();
		archivoLectura.close();
	}

	private static Alumno crearAlumno(String linea) throws IOException {
		int edad;
		double nota;
		Alumno alumno;
		String nombre;
		String curso;

		StringTokenizer st = new StringTokenizer(linea, ";");
		if (st.countTokens() != 4)
			throw new IOException("Formato incorrecto");
		nombre = st.nextToken();
		curso = st.nextToken();
		edad = Integer.parseInt(st.nextToken());
		nota = Double.parseDouble(st.nextToken());

		alumno = new Alumno(nombre, curso, edad, nota);

		return alumno;
	}

}
