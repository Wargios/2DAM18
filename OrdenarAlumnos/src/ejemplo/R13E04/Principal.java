package ejemplo.R13E04;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Principal {
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {
		int opcion;
		do {
			opcion = solicitarOpcion();
			tratarOpcion(opcion);
		} while (opcion != 4);

	}

	private static void tratarOpcion(int opcion) {
		String nombreFichero;
		switch (opcion) {
		case 1:
			System.out.println("Introduzca nombre del archivo");
			nombreFichero = teclado.nextLine();
			crearFicheroVehiculos(nombreFichero);
			break;
		case 2:
			System.out.println("Introduzca nombre del fichero a leer: ");
			nombreFichero = teclado.nextLine();
			mostrarFicheroVehiculos(nombreFichero);
			break;
		case 3:
			System.out.println("Introduzca nombre del fichero de vehiculos");
			nombreFichero = teclado.nextLine();
			System.out.println("Introduzca la marca");
			String marca = teclado.nextLine();
			mostrarVehiculosPorMarca(nombreFichero, marca);

		}
	}

	private static void mostrarFicheroVehiculos(String nombreFichero) {
		Vehiculo vehiculo;

		try {

			FileInputStream flujoEntrada = new FileInputStream(nombreFichero);
			ObjectInputStream filtroEntrada = new ObjectInputStream(flujoEntrada);

			while (flujoEntrada.available() != 0) {
				vehiculo = (Vehiculo) filtroEntrada.readObject();
				System.out.println(vehiculo);

			}

			flujoEntrada.close();
			filtroEntrada.close();

		} catch (FileNotFoundException e) {
			System.out.println("No se encuentra el fichero " + nombreFichero);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Error de conversión. No se puede leer el fichero " + nombreFichero);
		}
	}

	private static void mostrarVehiculosPorMarca(String nombreFichero, String marca) {
		Vehiculo vehiculo;

		try {	

			FileInputStream flujoEntrada = new FileInputStream(nombreFichero);
			ObjectInputStream filtroEntrada = new ObjectInputStream(flujoEntrada);
			while (flujoEntrada.available() != 0) {

				vehiculo = (Vehiculo) filtroEntrada.readObject();
				if (vehiculo.getMarca().equals(marca)) {
					System.out.println(vehiculo.toString());
				}
			}
			flujoEntrada.close();
			filtroEntrada.close();

		} catch (FileNotFoundException e) {
			System.out.println("No se encuentra el fichero " + nombreFichero);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	private static Vehiculo crearVehiculo() {
		String modelo;
		String marca;
		String matricula;
		Vehiculo vehiculo;
		
		System.out.println("Introduzca modelo");
		modelo = teclado.nextLine();
		System.out.println("Introduzca marca");
		marca = teclado.nextLine();
		System.out.println("Introduzca matricula");
		matricula = teclado.nextLine();
		vehiculo = new Vehiculo(matricula, marca, modelo);
		return vehiculo;
	}

	private static void crearFicheroVehiculos(String nombreFichero) {
		Vehiculo vehiculo;

		FileOutputStream flujoSalida;
		ObjectOutputStream filtroSalida;
	
		char continuar;

		
		try {
			File fichero = new File(nombreFichero);
			if (fichero.exists() && fichero.isFile()) {
				System.out.println("Nos se puede crear. El fichero " + nombreFichero + " ya existe");
			} else {
				flujoSalida = new FileOutputStream(fichero);
				filtroSalida = new ObjectOutputStream(flujoSalida);
				do {
					vehiculo = crearVehiculo();
					filtroSalida.writeObject(vehiculo);
					continuar = solicitarRespuestaSN();
				} while (continuar == 'S');
				filtroSalida.close();
				flujoSalida.close();
			}

		
		} catch (IOException e) {

			System.out.println(e.getMessage());
		}

	}

	private static char solicitarRespuestaSN() {
		char continuar;
		System.out.println("¿Desea introducir otro vehiculo? S/N");
		continuar = teclado.nextLine().charAt(0);
		continuar = Character.toUpperCase(continuar);
		return continuar;
	}

	private static int solicitarOpcion() {
		int opcion;

		System.out.println("MENU DE OPCIONES");
		System.out.println("1. Crear fichero vehículos");
		System.out.println("2. Mostrar información de fichero");
		System.out.println("3. Mostrar información según la marca");
		System.out.println("4. Salir");
		System.out.println();

		do {
			System.out.println("Introduzca opción");
			opcion = Integer.parseInt(teclado.nextLine());
		} while (opcion < 1 || opcion > 4);
		return opcion;
	}

}
