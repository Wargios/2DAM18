import java.io.*;
import java.util.*;

public class Principal {
	private static final String DELIMITADOR = ",";
	// Constantes
	private static final String NOMBRE_DIRECTORIO = "Alumnos";
	private static final String FICHERO_CSV_ALUMNOS = "alumnos.csv";
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		// Inicio
		try {
			Principal.crearCarpetasConNombresAlumnos();
			System.out.println("Directorios creados correctamente.");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// Métodos
	private static void crearCarpetasConNombresAlumnos() throws IOException {
		String linea;
		boolean continuar;

		continuar = Principal.crearDirectorioAlumnos(NOMBRE_DIRECTORIO);
		if (!continuar) {
			throw new IOException("Proceso interrumpido");
		}
		FileReader flujoLectura = new FileReader(FICHERO_CSV_ALUMNOS);
		BufferedReader filtroLectura = new BufferedReader(flujoLectura);

		linea = filtroLectura.readLine();
		while (linea != null) {
			Principal.tratarLineaFichero(linea);
			linea = filtroLectura.readLine();
		}

		filtroLectura.close();
		flujoLectura.close();
	}



	private static boolean crearDirectorioAlumnos(String nombre) throws IOException {
		File directorio;
		char respuesta;
		boolean continuar = true;

		directorio = new File(nombre);
		if (directorio.exists() && directorio.isDirectory()) {
			System.out.println("Ya existe la carpeta " + nombre + ".Si "
					+ "continua perdera toda la informacion, ¿Desea continuar (S/N)?");
			respuesta = Character.toUpperCase(teclado.nextLine().charAt(0));
			if (respuesta == 'S') {
				borrarDirectorio(directorio);
			} else
				continuar = false;

		}
		
		if (continuar){ //  Creo el directorio Alumnos de nuevo
			continuar=directorio.mkdir();
		}

		return continuar;
	}

	private static void borrarDirectorio(File directorio) {

		File[] arrayHijosFile;
		File hijo;

		if (directorio.isDirectory()) {
			arrayHijosFile = directorio.listFiles();
			for (int i = 0; i < arrayHijosFile.length; i++) {
				hijo = arrayHijosFile[i];
		
				if (hijo.isDirectory()) {
					borrarDirectorio(hijo);
				} else {
					hijo.delete();
				}
			}
			directorio.delete();
		}

	}

	private static void crearDirectorioNombresAlumnos(String nombre) throws IOException {
		File directorio;
		boolean ok;
		
		

		directorio = new File(nombre);
		if (!(directorio.exists() && directorio.isDirectory())) {
			ok = directorio.mkdir();
			if (!ok) {
				throw new IOException("No se puede crear el directorio " + nombre);
			}
		}

	}

	private static void tratarLineaFichero(String linea) throws IOException {
		StringTokenizer st = new StringTokenizer(linea, DELIMITADOR);
		String nombre;

		nombre = st.nextToken();

		Principal.crearDirectorioNombresAlumnos(NOMBRE_DIRECTORIO + "/" + nombre);
	}
}