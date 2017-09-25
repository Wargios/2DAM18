package principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Principal {
	public static void main(String[] args) {
		try {
			OrdenarAlumnos("Alumnos.txt", "AlumnosOrdenados.txt");
			System.out.println("Se completó correctamente");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error");
		}
	}

	public static void OrdenarAlumnos(String nombreArchivoLectura, String nombreArchivoEscritura) throws IOException {
		String linea;
		FileReader archivoLectura;
		BufferedReader filtroLectura;
		FileWriter archivoEscritura;
		PrintWriter filtroEscritura;

		archivoLectura = new FileReader(nombreArchivoLectura);
		filtroLectura = new BufferedReader(archivoLectura);

		ArrayList<String> lista = new ArrayList<String>();

		while ((linea = filtroLectura.readLine()) != null) {
			lista.add(linea);
		}
		filtroLectura.close();

		Collections.sort(lista);

		archivoEscritura = new FileWriter(nombreArchivoEscritura);
		filtroEscritura = new PrintWriter(archivoEscritura);

		for (String str : lista) {
			filtroEscritura.println(str);
		}

		if (null != archivoEscritura)
			archivoEscritura.close();

	}
}
