
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ControladorXmlResultados extends DefaultHandler {

	private String nombreEquipo1, nombreEquipo2;
	private int golesEquipo1, golesEquipo2;
	private boolean hayEquipo, hayGoles;

	public void startDocument() {
		System.out.println("------");
	}

	public void endDocument() {
		System.out.println("------");
	}

	public void startElement(String uri, String localName, String nombreC, Attributes attributes) throws SAXException {

		switch (nombreC) {

		case "partido": // Encontramos partido y lo reseteamos

			nombreEquipo1 = "";
			nombreEquipo2 = "";
			golesEquipo1 = -1;
			golesEquipo2 = -1;
			hayEquipo = false;
			hayGoles = false;

			break;

		case "equipo": // encontramos equipo

			hayEquipo = true;

			break;
			
		case "goles":
			hayGoles = true;
			break;
			
		default:
			break;
		}

	}

	public void endElement(String uri, String nombre, String nombreC) {
		
		switch (nombreC) {
		case "partido":
			if (!nombreEquipo1.isEmpty() && !nombreEquipo2.isEmpty()) {
				System.out.println(nombreEquipo1 + " - " + nombreEquipo2 +" "+ escribirResultado());

			}

			break;
		}

	}

	private char escribirResultado() {
		char resul = '0';
		if (golesEquipo1 > golesEquipo2)
			resul = '1';
		else if (golesEquipo1 < golesEquipo2) {
			resul = '2';
		} else
			resul = 'X';
		return resul;
	}

	public void characters(char[] xml, int charInicio, int longitud) throws SAXException {
		String cadena = new String(xml, charInicio, longitud);
		cadena = cadena.replaceAll("[\t\n ]", ""); // eliminamos los saltos de linea, blanco y tabuladores

		if (!cadena.equals("")) {// solo la mostramos si tiene algo

			if (hayEquipo) {	// si salta la etiqueta equipo introducimos datos
				if (nombreEquipo1.isEmpty()) {
					nombreEquipo1 = cadena;
				} else
					nombreEquipo2 = cadena;
				hayEquipo = false;
			}

			if (hayGoles) {		// si salta la etiqueta goles introducimos datos
				if (golesEquipo1 == -1) {
					golesEquipo1 = Integer.parseInt(cadena);
				} else
					golesEquipo2 = Integer.parseInt(cadena);
				hayGoles = false;
			}

		}
	}
}
