import java.sql.SQLException;

public class Principal {

	public static void main(String[] args) {
		String lista;
		AccesoBD bd=null;
		
		try {
			bd= new AccesoBD();
			
			bd.almacenarTipoPersona();
			
			lista = bd.obtenerListadoAsignaturas();
			
			System.out.println(lista);
			
			bd.cerrarConexion();
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			
			System.err.println(e.getMessage());
		
			if (bd != null) {
				System.out.println("ERROR, HACEMOS ROLLBACK");
				bd.rollback();
				
			}
			
			
		}catch(Exception e) {
			System.out.println("Otra excepcion");
		}
		
	}
}
