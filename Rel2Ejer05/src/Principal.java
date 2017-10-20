import java.io.FileNotFoundException;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Se dispone del fichero resultados.xml con los datos de los resultados del
 * partido de la última jornada de liga.
 * 
 * Se pide obtener un listado por pantalla donde aparezca los resultados de la
 * quiniela de esa jornada
 * 
 * Sevilla  Madrid  1
 * 
 * Barcelona  Betis 2
 * 
 * Osasuna Valencia X
 * 
 * ...
 * 
 * @author fcojg
 *
 */
public class Principal {

	private static final String NOMBRE_FICHERO = "resultados.xml";

	public static void main(String[] args) {

		try {
			leerXMLConSax(NOMBRE_FICHERO);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void leerXMLConSax(String nombreFichero) throws FileNotFoundException, SAXException, IOException {
		XMLReader procesadorXML = XMLReaderFactory.createXMLReader();
		procesadorXML.setContentHandler(new ControladorXmlResultados());
		procesadorXML.parse(new InputSource(nombreFichero));
	}

}
