import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * EJERCICIO 7 Se dispone del fichero “resultados.xml” con los datos de los
 * resultados del partido de la última jornada de liga. Usando DOM y Transformer
 * construir un nuevo fichero xml llamado “resultadosNuevo.xml” donde se hagan
 * el siguiente cambio sobre el fichero original :
 * 
 * ◦ Crear un atributo en el elemento partido con el nombre resultadoQuiniela
 * con los valores ‘1’, ‘X’, ‘2’ (2 ptos)
 * 
 * @author fcojg
 *
 */
public class Principal {

	private static final String NOMBRE_ARCHIVO = "resultados.xml";

	public static void main(String[] args) {

		Node raiz;
		Element partidoActual;
		NodeList listaPartidos;
		int golesEquipo1, golesEquipo2;
		String valorQuiniela;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder builder = factory.newDocumentBuilder();
			Document arbol = builder.parse(new File(NOMBRE_ARCHIVO));

			raiz = arbol.getFirstChild();
			Element elementoRaiz = (Element) raiz;
			listaPartidos = elementoRaiz.getElementsByTagName("partido");

			for (int i = 0; i < listaPartidos.getLength(); i++) {
				partidoActual = (Element) listaPartidos.item(i);

				NodeList listaGolesPartido = partidoActual.getElementsByTagName("goles");

				golesEquipo1 = Integer.parseInt(listaGolesPartido.item(0).getTextContent().trim());
				golesEquipo2 = Integer.parseInt(listaGolesPartido.item(1).getTextContent().trim());

				valorQuiniela = calcularResultadoQuiniela(golesEquipo1, golesEquipo2);
				partidoActual.setAttribute("resultadoQuiniela", valorQuiniela);
				
			}

			Source source = new DOMSource(arbol);
			Result result = new StreamResult(("nuevo" + NOMBRE_ARCHIVO));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String calcularResultadoQuiniela(int golesEquipo1, int golesEquipo2) {
		String valorQuiniela;

		if (golesEquipo1 > golesEquipo2) {
			valorQuiniela = "1";

		} else if (golesEquipo1 < golesEquipo2) {
			valorQuiniela = "2";

		} else {
			valorQuiniela = "X";

		}
		return valorQuiniela;
	}

}
