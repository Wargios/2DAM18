import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Attr;
import org.xml.sax.SAXException;

public class Principal {

	private static final String DOCUMENTO = "universidad.xml";
	private static final String ETIQUETA_PUESTO = "puesto";
	private static final String ETIQUETA_EMPLEADO = "empleado";
	private static final String ETIQUETA_SALARIO = "salario";
	private static final double AUMENTO = 0.05;

	/**
	 * 10.- Modificar el fichero universidad.xml para subir el sueldo un 5% a los
	 * asociados.
	 * 
	 * @author elisabet
	 *
	 */

	public static void main(String[] args) {

		try {

			Document arbolDocumento = crearArbolDOM();

			construirArbolXMLDOM(arbolDocumento);

			transformaDOMenXML(arbolDocumento);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void transformaDOMenXML(Document arbolDocumento)
			throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError {

		// El Transformer se encargara de crear el nuevo documento XML a partir del
		// arbol
		Source source = new DOMSource(arbolDocumento); // Origen
		Result result = new StreamResult(DOCUMENTO); // Destino
		Transformer transformer = TransformerFactory.newInstance().newTransformer(); // Fabrica
		transformer.transform(source, result);

	}

	private static Document crearArbolDOM() throws ParserConfigurationException, IOException, SAXException {

		File fichero = new File(DOCUMENTO);
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factoria.newDocumentBuilder();
		Document arbolDocumento = builder.parse(fichero);

		return arbolDocumento;
	}

	private static void construirArbolXMLDOM(Document arbolDocumento) {
		Element elementoRaiz, puesto, empleado;

		Node raizDocumento, nodeEmpleado, nodePuesto;
		NodeList listaPuestos;

		Attr salario;
		String contenidoPuesto, asociado = "Asociado";
		Double sueldo;

		// Saco la raiz
		raizDocumento = arbolDocumento.getFirstChild();
		elementoRaiz = (Element) raizDocumento;

		listaPuestos = arbolDocumento.getElementsByTagName(ETIQUETA_PUESTO);

		for (int contador = 0; contador < listaPuestos.getLength(); contador++) {

			nodePuesto = listaPuestos.item(contador);
			empleado = (Element) arbolDocumento.getElementsByTagName(ETIQUETA_EMPLEADO).item(contador);
			// System.out.println(empleado.getTextContent());

			puesto = (Element) nodePuesto;
			contenidoPuesto = puesto.getTextContent();
			// System.out.println(contenidoPuesto);

			if (contenidoPuesto.equals(asociado)) {
				salario = (Attr) empleado.getAttributeNode(ETIQUETA_SALARIO);
				sueldo = Double.parseDouble(salario.getValue());
				System.out.println(sueldo);
				sueldo = (sueldo * AUMENTO) + sueldo;
				salario.setValue(sueldo.toString());
			}

		}

	}

}
