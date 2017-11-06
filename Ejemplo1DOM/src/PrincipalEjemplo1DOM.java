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


public class PrincipalEjemplo1DOM
{
	
	private static final String FICHERO_OBJETOS = "personas.dat";
	private static final String FICHERO_XML = "personas.xml";
	
	public static void main(String[] args) throws IOException
	{
		
		creaFicheroDatosPersonas();
		creaFicheroXMLPersonas();
		imprimirFicheroXMLPersonas();
	}
	
	
	//  Crea un fichero  que almacene varios objetos persona.
	private static void creaFicheroDatosPersonas() throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO_OBJETOS));

		oos.writeObject(new Persona("Pedro", 53));
		oos.writeObject(new Persona("Juan", 26));
		oos.writeObject(new Persona("Maria", 32));
		oos.writeObject(new Persona("Laura", 19));

		oos.close();
	}


	//Tomando como base el fichero anterior, crea un documento XML usando DOM.
	private static void creaFicheroXMLPersonas() throws IOException
	{
		FileInputStream fis = new FileInputStream(FICHERO_OBJETOS);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Persona persona;

		try
		{
			// Crea la cabecera del fichero XML en memoria:
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder= documentFactory.newDocumentBuilder();
			Document document= documentBuilder.getDOMImplementation().createDocument(null, "personas", null);
			document.setXmlVersion("1.0");
			Element raiz=document.getDocumentElement();

			// A�ade los objetos persona al XML:
			while( fis.available() > 0 )
			{
				persona = (Persona)ois.readObject();

				Element elementoPersona = document.createElement("persona");
				raiz.appendChild(elementoPersona);

				annadirElementos( elementoPersona, document, "nombre", persona.getNombre());
				annadirElementos( elementoPersona, document, "edad", String.valueOf(persona.getEdad()));
			}

			// Vuelca el XML a un fichero XML:
			TransformerFactory transformerFactory= TransformerFactory.newInstance();
			Transformer transformer=transformerFactory.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(FICHERO_XML));
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		ois.close();
		fis.close();
	}

	// Annade dos hijos al elemento element, uno el primero de tipo Element y el segundo Text
	private static void annadirElementos(Element raiz, Document document,String datoEmple, String valor )
	{
		Element elem = document.createElement(datoEmple);
		Text text = document.createTextNode(valor);
		raiz.appendChild(elem);
		elem.appendChild(text);
	}


	// c) Implementa una clase que permita leer el documento XML del apartado anterior.
	private static void imprimirFicheroXMLPersonas()
	{
		try
		{
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(FICHERO_XML);
			document.getDocumentElement().normalize();
			System.out.println(document.getDocumentElement().getNodeName());
			NodeList empleados = document.getElementsByTagName("persona");


			for( int i = 0 ; i < empleados.getLength() ; i++ )
			{
				Node emple = empleados.item(i);
				if( emple.getNodeType() == Node.ELEMENT_NODE )
				{
					Element elemento = (Element)emple;

					System.out.print("   " + getNodo("nombre", elemento));
					System.out.println(" \t" + getNodo("edad", elemento));
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
