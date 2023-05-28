package entidades;

public class Medico extends Persona{
	
	public String especialidad;
	public String numeroColegiado;
	
	public Medico() {
		super();
	}
	
	public Medico(String correo, String nombre, String apellidos, String pass, String roll) {
		super(correo,nombre,apellidos,pass, roll);
	}
	
	// GETTERS
	public String getCorreo() {return correo;}
	public String getNombre() {return nombre;}
	public String getApellidos() {return apellidos;}
	public String getPass() {return pass;}
	public String getEspecialidad() {return especialidad;}
	public String getNumeroColegiado() {return numeroColegiado;}
	
	
	// MÉTODO TOSTRING PARA LA LECTURA DE LOS DATOS
	public String toString() {
		String salida = 
				"\nNombre: " + nombre +
				"\nApellidos: " + apellidos /*+
				"\nEdad: " + edad +
				"\nEspecialidad: " + especialidad + 
				"\nNumero Colegiado: " + numeroColegiado*/;
		return salida;
		
	}

}
