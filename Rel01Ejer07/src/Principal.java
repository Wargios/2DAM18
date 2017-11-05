import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Principal {
	private static final String NOMBRE_FICHERO_MAS_ALQUILADA = "masAlquilads.txt";
	
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			String nombreFicheroBinario = solicitarNombre(
					"Introduce el nombre del fichero binario con las peliculas :");

			crearFicheroConMasAlquiladas(nombreFicheroBinario);
			
			System.out.println("Fichero " + NOMBRE_FICHERO_MAS_ALQUILADA + " creado correctamente.");

		} catch (IOException e) {
			System.out.println("No se ha podido crear el fichero " + NOMBRE_FICHERO_MAS_ALQUILADA + "Error: " + e.getMessage());
		}

	}

	private static void crearFicheroConMasAlquiladas(String nombreFicheroBinario) throws IOException {

		@SuppressWarnings("unused")
		int codigoPelicula, numeroVecesAlquilada;
		String nombrePelicula;
		boolean fin = false;

		String nombreMasAlquilada1 = "", nombreMasAlquilada2 = "";
		int vecesMasAlquilada1 = 0, vecesMasAlquilada2 = 0;

	
		// Abrimos en lectura el fichero binario
		FileInputStream flujoLectura = new FileInputStream(nombreFicheroBinario);
		DataInputStream filtroLectura = new DataInputStream(flujoLectura);
		
		

		while (!fin) {
		
			try {
				codigoPelicula = filtroLectura.readInt();
				nombrePelicula = filtroLectura.readUTF();
				numeroVecesAlquilada = filtroLectura.readInt();
				

				if (numeroVecesAlquilada > vecesMasAlquilada1) {
					vecesMasAlquilada2 = vecesMasAlquilada1;
					nombreMasAlquilada2 = nombreMasAlquilada1;

					vecesMasAlquilada1 = numeroVecesAlquilada;
					nombreMasAlquilada1 = nombrePelicula;

				} else {
					if (numeroVecesAlquilada > vecesMasAlquilada2) {
						vecesMasAlquilada2 = numeroVecesAlquilada;
						nombreMasAlquilada2 = nombrePelicula;
					}

				}

			} catch (EOFException e) {
				
				fin = true;
			}

		}
		
		filtroLectura.close();
		flujoLectura.close();
		
		escribirDosMasAlquiladas( vecesMasAlquilada1, nombreMasAlquilada1, vecesMasAlquilada2, nombreMasAlquilada2);

	}

	private static void escribirDosMasAlquiladas(int vecesMasAlquilada1, String nombreMasAlquilada1,
			int vecesMasAlquilada2, String nombreMasAlquilada2) throws IOException {
		
		
		// Abrimos en escritura el fichero de texto
		FileWriter flujoEscrituraTexto = new FileWriter(NOMBRE_FICHERO_MAS_ALQUILADA);
		PrintWriter filtroEscrituraTexto = new PrintWriter(flujoEscrituraTexto);
		
		
		// Escribimos las dos peliculas más alquiladas
		filtroEscrituraTexto.println("Primera película mas alquilada " + nombreMasAlquilada1 + " se alquiló " + vecesMasAlquilada1);
		filtroEscrituraTexto.println("Primera película mas alquilada " + nombreMasAlquilada2 + " se alquiló " + vecesMasAlquilada2);
		
		filtroEscrituraTexto.close();
		flujoEscrituraTexto.close();
		
	}

	private static String solicitarNombre(String palabra) {
		System.out.println(palabra);
		return teclado.nextLine();
	}
}
