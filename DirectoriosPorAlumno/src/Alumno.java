
public class Alumno {
	private String nombre;
	private String curso;
	private int edad;
	private double nota;

	public Alumno(String nombre, String curso, int edad, double nota) {
		this.nombre = nombre;
		this.curso = curso;
		this.edad = edad;
		this.nota = nota;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCurso() {
		return curso;
	}

	public int getEdad() {
		return edad;
	}

	public double getNota() {
		return nota;
	}

}
