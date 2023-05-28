package entidades;

import java.util.Vector;

public class Entrenador extends Persona{
	
	public Vector<Deportista> DeportistasAsociados= new Vector<Deportista>();
	
	public Entrenador() {
		super();
	}
	
	public Entrenador(String correo, String nombre, String apellidos, String pass, Vector<Deportista> DeportistasAsociados, String roll) {
		super(correo,nombre,apellidos,pass, roll);
		this.DeportistasAsociados=DeportistasAsociados;
	}
	
	// GETTERS
	public String getCorreo() {return correo;}
	public String getNombre() {return nombre;}
	public String getApellidos() {return apellidos;}
	public String getPass() {return pass;}	
	
	// MÉTODO TOSTRING PARA LA LECTURA DE LOS DATOS
	public String toString() {
		String salida = 
				"Correo: " + correo +
				"\nNombre: " + nombre +
				"\nApellidos: " + apellidos;
		return salida;
	}
	
	public void addDeportista() {
		
	}
	

}
