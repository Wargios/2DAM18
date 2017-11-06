import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

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
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class PrincipalClase {

	private static final String DOCUMENTO = "universidad.xml";
	private static final String ETIQUETA_EMPLEADO = "empleado";
	private static final String ETIQUETA_NOMBRE = "nombre";
	private static final String ETIQUETA_APELLIDO = "apellido";



	/**
	 * Modificar el fichero universidad.xml para que el dato nombre se modifique la
	 * etiqueta <nombre> de los empleados por dos etiquetas: <nombre> .... </nombre>
	 * <apellido>....</apelllido>
	 * 
	 * @param args
	 * @author elisabet
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
		
		//El Transformer se encargara de crear el nuevo documento XML a partir del arbol
		Source source = new DOMSource(arbolDocumento); //Origen
		Result result = new StreamResult(DOCUMENTO);  //Destino
		Transformer transformer = TransformerFactory.newInstance().newTransformer();   //Fabrica
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
		@SuppressWarnings("unused")
		Element elementoRaiz;

		Node raizDocumento;
		NodeList listaEmpleados;

		// Saco la raiz
		raizDocumento = arbolDocumento.getFirstChild();
		elementoRaiz = (Element) raizDocumento;

		listaEmpleados = arbolDocumento.getElementsByTagName(ETIQUETA_EMPLEADO);

		crearApellido(listaEmpleados, arbolDocumento);

	}

	// Saco los hijos de departamento(codigo, nombre, empleado)
	// Saco los hijos de empleado(puesto, nombre)
	// Annado a empleado un nuevo element a partir del apellido(Se encuentra en
	// nombre)

	

	private static void crearApellido(NodeList listaEmpleados, Document arbolDocumento) {

		Node nodeEmpleado;
		Element etiquetaEmpleado;

		for (int contador = 0; contador < listaEmpleados.getLength(); contador++) {

			nodeEmpleado = listaEmpleados.item(contador);
			etiquetaEmpleado = (Element) nodeEmpleado;

			descomponer(etiquetaEmpleado, arbolDocumento);

		}

	}

	private static void descomponer(Element etiquetaEmpleado, Document arbolDocumento) {
		Element nombre;
		String textoNombre, nombretxt, apellidotxt = "";
		StringTokenizer token;
		StringBuilder nombreCompleto = new StringBuilder();
		boolean seguir = true;

		nombre = (Element) etiquetaEmpleado.getElementsByTagName(ETIQUETA_NOMBRE).item(0);
		textoNombre = nombre.getTextContent();
		
		//System.out.println(textoNombre);
		
			
		token = new StringTokenizer(textoNombre, " ");
		
		while(token.hasMoreTokens() && seguir) {
			
			nombretxt = token.nextToken();
						
			if(!token.hasMoreTokens()) {
				apellidotxt = nombretxt;
			}else {
				nombreCompleto = nombreCompleto.append(nombretxt);
				//System.out.println(nombreCompleto.toString());
			}
			
		}
		
		Element apellido = arbolDocumento.createElement(ETIQUETA_APELLIDO);
		Text textoApellido = arbolDocumento.createTextNode(apellidotxt);
				
		etiquetaEmpleado.appendChild(apellido);
		apellido.appendChild(textoApellido);
		nombre.setTextContent(nombreCompleto.toString());
	

	}
}
