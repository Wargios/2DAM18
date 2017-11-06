
/*
	
		Realiza las siguientes tareas, a partir de la clase persona:
		
			a) Crea un fichero que almacene varios objetos persona.
			b) Tomando como base el fichero anterior, crea un documento XML usando DOM.
			c) Implementa una clase que permita leer el documento XML del apartado anterior.
*/


import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;


public class MainNEWDOM
{
	/*
	 * 	Se dispone de un fichero binarios con la siguiente estructura â€œEmpleados.datâ€� con la siguiente estructura:
			CÃ³digo de empleado ( del 1 en adelante, entero)
			Nombre del empleado ( de 20 caracteres en formato UNICODE)
			Sueldo (entero)
		Usa DOM para crear un Ã¡rbol en memoria y lo vuelque en un fichero â€œempleados.xmlâ€�
	 */
	private static final String FICHERO_EMPLEADOS = "empleados.dat";
	private static final String FICHERO_XML = "empleados.xml";

	
	public static void main(String[] args) throws IOException, TransformerException{
	
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		//Declaramos el Document y la raiz a null, para que nos permita usarlos fuera del TRY
		Document documento = null;
		Element raiz = null;
		try {
			DocumentBuilder builder = factoria.newDocumentBuilder();
			documento = builder.getDOMImplementation().createDocument(null, "empleados", null);
			documento.setXmlVersion("1.0");
			raiz = documento.getDocumentElement();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		leerFichero(raiz, documento);
		
		
		//VOLCAMOS EL ARBOL EN EL DOCUMENTO XML
		TransformerFactory transformerFactory= TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.transform(new DOMSource(documento), new StreamResult(FICHERO_XML));
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	private static void leerFichero(Element raiz, Document document) throws IOException{
		
		FileInputStream flujoLectura = new FileInputStream(FICHERO_EMPLEADOS);
		DataInputStream filtroLectura = new DataInputStream(flujoLectura);
		
		int codigo;
		String nombre;
		double sueldo;
		
		while (flujoLectura.available()!=0) {
			
		codigo = filtroLectura.readInt();
		nombre = leerCadenaUnicode(filtroLectura, 20);
		sueldo = filtroLectura.readDouble();
		
		annadirElementos(raiz, document, codigo, nombre, sueldo);
		//Descomentar para probar que funciona
		//System.out.println(raiz.getFirstChild().getTextContent());
		
		
		
		}
		
		filtroLectura.close();
		flujoLectura.close();
	}
	
	private static void annadirElementos(Element raiz, Document document, int codigo, String nombre, Double salario){
		
		//Creamos el elemento empleado
		Element empleado = document.createElement("empleado");
		//Creamos los elementos codigo, nombre y salario
		Element codigoE = document.createElement("codigo");
		//Creamos el elemento texto de codigo
		Text codigoText = document.createTextNode(String.valueOf(codigo));
		//Le aÃ±adimos el elemento codigoText al elemento codigo
		codigoE.appendChild(codigoText);
		System.out.println(codigo);
		
		Element nombreE = document.createElement("nombre");
		Text nombreText = document.createTextNode(nombre);
		nombreE.appendChild(nombreText);
		System.out.println(nombre);
		
		Element salarioE = document.createElement("salario");
		Text salarioText = document.createTextNode(String.valueOf(salario));
		salarioE.appendChild(salarioText);
		System.out.println(salario);
		
		//AÃ±adimos los elementos al Elemento Empleado
		empleado.appendChild(codigoE);
		empleado.appendChild(nombreE);
		empleado.appendChild(salarioE);
		//AÃ±adimos a la raiz --> EMPLEADO
		
		raiz.appendChild(empleado);
		
	}

	public static String leerCadenaUnicode(DataInputStream filtroLectura, int numeroCaracteres)
			throws IOException {
		char caracterLeido;
		StringBuilder sbCadenaLeida=new StringBuilder(numeroCaracteres);
		for (int i = 0; i < numeroCaracteres; i++) {
			caracterLeido = filtroLectura.readChar();
			sbCadenaLeida.append(caracterLeido);
		}
		return sbCadenaLeida.toString().trim();
	}
	

	//CLASE FATIMA PARA LEER FICHERO
	@SuppressWarnings("unused")
	private static void imprimirFicheroXMLPersonas()
	{
		try
		{
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(FICHERO_XML);
			document.getDocumentElement().normalize();
			System.out.println(document.getDocumentElement().getNodeName());
			NodeList empleados = document.getElementsByTagName("empleado");


			for( int i = 0 ; i < empleados.getLength() ; i++ )
			{
				Node emple = empleados.item(i);
				if( emple.getNodeType() == Node.ELEMENT_NODE )
				{
					Element elemento = (Element)emple;

					System.out.print("   " + getNodo("codigo", elemento));
					System.out.println(" \t" + getNodo("nombre", elemento));
					System.out.print("   " + getNodo("salario", elemento));

				}
			}

		}
		catch( Exception e )
		{
			System.err.println("Error: " + e);
		}
	}


	private static String getNodo(String etiqueta, Element elem)
	{
		NodeList nodo = elem.getElementsByTagName(etiqueta).item(0).getChildNodes();
		Node valorNodo = (Node)nodo.item(0);
		return valorNodo.getNodeValue();
	}


	
}
