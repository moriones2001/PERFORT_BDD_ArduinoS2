package entidades;

public class Entrenamientos {
	public String nombreEntreno;
	public String carrera;
	public String empujes;
	public String tracciones;
	public String pierna;
	
	public Entrenamientos(String nombreEntreno, String carrera, String empujes, String tracciones, String pierna) {
		this.nombreEntreno = nombreEntreno;
		this.carrera = carrera;
		this.empujes = empujes;
		this.tracciones = tracciones;
		this.pierna = pierna;
	}
	
	public String getNombreEntreno() {
		return nombreEntreno;
	}
	
	public String getCarrera() {
		return carrera;
	}
	
	public String getEmpujes() {
		return empujes;
	}
	
	public String getTracciones() {
		return tracciones;
	}
	
	public String getPierna() {
		return pierna;
	}
	
	public String toString() {
		String salida = 
				"Carrera; " + carrera +
				"\nEmpujes: " + empujes +
				"\nTracciones: " + tracciones +
				"\nPierna: " + pierna;
		return salida;
	}
}
