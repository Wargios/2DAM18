

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;


public class CrearYRecorrerArbolDOM {

	public static void main(String[] args) {
		
		Node n;
		Element ele;
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		
		try{
			DocumentBuilder builder=factory.newDocumentBuilder();  // crea un objeto builder que permite crear el arbol DOM
		
			Document arbol=builder.parse(new File("empleados.xml")); // crea el arbol DOM a partir del fichero
			
			
			Node raiz=arbol.getFirstChild(); // obtiene el primer nodo, raiz del arbol
			
			NodeList listaNodos=raiz.getChildNodes(); // obtiene una lista de nodos hijo del raiz
			
			
			for(int i=0; i<listaNodos.getLength(); i++){ // recorremos la lista de nodos
				
				n=listaNodos.item(i); // obtiene el nodo i-esimo de la lista
				
				if (n.getNodeType()== Node.ELEMENT_NODE){ // si es un elemento
					
					ele=(Element) n; // lo convierto a Element
					
					System.out.println("id:" + getNodo("id", ele)); 
					System.out.println("Apellido:" + getNodo("apellido", ele));
				}
			}
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private static String getNodo(String etiqueta, Element ele) {
		
		NodeList listaDeHijosDeEtiqueta=ele.getElementsByTagName(etiqueta); // obtengo la lista de nodos hijo de la etiqueta id o apellido
		
		Node nodo=listaDeHijosDeEtiqueta.item(0).getFirstChild(); // obtengo el primero de ellos que es el texto de la etiquta
		
		return nodo.getNodeValue(); // devuelvo su valor, nodo.getNodeType seria #text
	}

}
