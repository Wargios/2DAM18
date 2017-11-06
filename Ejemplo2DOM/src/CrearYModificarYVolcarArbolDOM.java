

import java.io.File;
import java.util.Scanner;

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


public class CrearYModificarYVolcarArbolDOM  {
	private static Scanner teclado=new Scanner (System.in);

	public static void main(String[] args) {
		
		
		Node raiz, n;
		Element ele, nuevoElemento;
		String domicilio;
		
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document arbol=builder.parse(new File("empleados.xml"));
			
			
			raiz=arbol.getFirstChild();
			NodeList listaNodos=raiz.getChildNodes();
			
			
			for(int i=0; i<listaNodos.getLength(); i++){
				n=listaNodos.item(i);
				if (n.getNodeType()== Node.ELEMENT_NODE){
					
					ele=(Element) n;
					
					
					// Mostrar el nodo
					System.out.println("ID:" + getNodo("id", ele));
					System.out.println("Apellido:" + getNodo("apellido", ele));
					
					//Añado un nuevo atributo  tipo con valor "S" a todos los empleados
					ele.setAttribute("tipo", "S"); 
					
					
					// Creo un nuevo nodo domicilio
					nuevoElemento= arbol.createElement("domicilio");
					System.out.println("Introduce el domicilio para el empleado " + getNodo("apellido", ele));
					domicilio= teclado.nextLine();
					nuevoElemento.appendChild(arbol.createTextNode(domicilio));
					ele.appendChild((Node)nuevoElemento);
				}
			}
			
			
			
			
			//Pasar el arbol dom a un fichero xml llamado nuevoxml.xml
			
			Source source=new DOMSource(arbol);
			Result  result=new StreamResult("EmpleadosNuevo.xml");
			Transformer transformer=TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private static String getNodo(String etiqueta, Element ele) {
		
		Node nodo=ele.getElementsByTagName(etiqueta).item(0);
		Node hijo=nodo.getFirstChild();
		return hijo.getNodeValue();
	}

}
