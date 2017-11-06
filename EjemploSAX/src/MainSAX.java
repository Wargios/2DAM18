/*
		Utiliza SAX para visualizar el contenido del fichero un fichero XML
*/


import java.io.FileNotFoundException;
import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class MainSAX
{

	private static final String NOMBRE_FICHERO = "personas.xml";

	
	public static void main(String[] args) throws FileNotFoundException, SAXException, IOException
	{
		
		leerXMLConSax(NOMBRE_FICHERO);
	}
	
	private static void leerXMLConSax(String nombreFichero) throws FileNotFoundException, SAXException, IOException
	{
		XMLReader procesadorXML = XMLReaderFactory.createXMLReader();
		procesadorXML.setContentHandler(new GestionContenido());
		procesadorXML.parse(new InputSource(nombreFichero));
	}



}
