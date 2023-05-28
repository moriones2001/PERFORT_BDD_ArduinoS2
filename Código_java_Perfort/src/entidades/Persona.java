package entidades;



public class Persona {
	
	public String correo;
	public String nombre;
	public String apellidos;
	public String pass;
	public String roll;
	
	public Persona(String correo, String nombre, String apellidos, String pass, String roll) {
		
		this.correo = correo;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.pass = pass;
		this.roll = roll;
	}
	

	
	public Persona() {} // CONSTRUCTOR VACIO
	
	// GETTERS
	public String getCorreo() {
		return correo;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public String getPass() {
		return pass;
	}
	
	/*public String getRoll() {
		return roll;
	}*/

	
	// MÉTODO TOSTRING PARA LA LECTURA DE LOS DATOS
	public String toString() {
		String salida = 
				"Correo: " + correo +
				"\nNombre: " + nombre +
				"\nApellidos: " + apellidos;/* +
				"\nRoll: " + roll;*/
		
		return salida;
		
	}
	
	
	

}
