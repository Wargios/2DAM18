package ejemplo.R13E04;
import java.io.Serializable;


public class Vehiculo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String matricula;
	String marca;
	String modelo;
	
	Vehiculo(String paramMat,String paramMarca,String paramModelo){
		this.matricula=paramMat;
		this.marca=paramMarca;
		this.modelo=paramModelo;
	}

	@Override
	public String toString() {
		return "Vehiculo [matricula=" + matricula + ", marca=" + marca
				+ ", modelo=" + modelo + "]";
	}

	public String getMarca() {
		return marca;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

}
