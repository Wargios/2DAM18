/*
	Ejercicio:	2x4x02
	Fecha:		09/10/2012
*/


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class GestionContenido extends DefaultHandler
{
	public GestionContenido()
	{
		super();
	}


	public void startDocument()
	{
		System.out.println("INICIO XML");
	}


	public void endDocument()
	{
		System.out.println("FIN XML");
	}


	public void startElement(String nameSpace, String nombre, String nombreC, Attributes atts)
	{
		System.out.println("\tPrincipio Elemento: "+  nombreC);
		
	
		if ( atts !=null){
			
			for (int i = 0; i < atts.getLength(); i++) {
				System.out.println("\t\tAtributo:" + atts.getQName(i) + "-" + atts.getValue(i));
			}
		}
		
	}


	public void endElement(String uri, String nombre, String nombreC)
	{
		System.out.println("\tFin Elemento: " + nombreC);
	}


	public void characters(char[] xml, int charInicio, int longitud) throws SAXException
	{
		String cadena= new String(xml, charInicio, longitud);
		cadena = cadena.replaceAll("[\t\n ]", ""); // eliminamos los saltos de linea, blanco y tabuladores
		if (!  cadena.equals("")) // solo la mostramos si tiene algo
			System.out.println("\tValor: " + cadena);
	}
}
