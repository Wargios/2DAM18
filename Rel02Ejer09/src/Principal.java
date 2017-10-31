import java.io.File;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 * 
 * @author fran
 *
 */

public class Principal {
	private static final String NOMBRE_ARCHIVO = "universidad.xml";

	public static void main(String[] args) {

		Node raiz;
		Element empleado;
		NodeList listaEmpleados;

		int golesEquipo1, golesEquipo2;
		String valorQuiniela;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document arbol = builder.parse(new File(NOMBRE_ARCHIVO));

			raiz = arbol.getFirstChild();
			Element elementoRaiz = (Element) raiz;
			listaEmpleados = elementoRaiz.getElementsByTagName("partido");

			for (int i = 0; i < listaEmpleados.getLength(); i++) {
				empleado = (Element) listaEmpleados.item(i);
				//
				// NodeList listaGolesPartido = partidoActual.getElementsByTagName("goles");
				//
				// golesEquipo1 =
				// Integer.parseInt(listaGolesPartido.item(0).getTextContent().trim());
				// golesEquipo2 =
				// Integer.parseInt(listaGolesPartido.item(1).getTextContent().trim());
				//
				//

			}

			Source source = new DOMSource(arbol);
			Result result = new StreamResult(("nuevo" + NOMBRE_ARCHIVO));

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void crearApellidos(NodeList listaEmpleados, Document document) {

		Node nodoEmpleado;

	}

	private static void descomponerEnApellidoYNombre(Element elementoEmpleado, Document document) {

		Element nombre;
		String textoNombre;
		StringTokenizer token;

		nombre = (Element) elementoEmpleado.getElementsByTagName("nombre").item(0);

		textoNombre = nombre.getTextContent();

		System.out.println(textoNombre);

		Element apellido = document.createElement("apellido");
		Text textoApellido = document.createTextNode("Perez");

		elementoEmpleado.appendChild(apellido);
		apellido.appendChild(textoApellido);

	}

}
