package entidades;

import java.util.Vector;

public class Deportista extends Persona{
	
	private String idMedico;
	private Vector<String> idsEntrenadores = new Vector<String>();
	
	public  Deportista(String correo, String nombre,String apellidos, String pass , String roll, Vector<String> IdCorreosEntrenadores, String idCorreoMedico) {
		super(correo,nombre, apellidos, pass, roll);
		this.idsEntrenadores	= IdCorreosEntrenadores;
		this.idMedico = idCorreoMedico;
	}
	public Deportista() {} // CONSTRUCTOR VACIO


	public String getIdMedico() {
		return idMedico;
	}


	public void setIdMedico(String idMedico) {
		this.idMedico = idMedico;
	}


	public Vector<String> getIdsEntrenadores() {
		return idsEntrenadores;
	}


	public void setIdsEntrenadores(Vector<String> idsEntrenadores) {
		this.idsEntrenadores = idsEntrenadores;
	}


	public String getCorreo() {return correo;}
	public String getNombre() {return nombre;}
	public String getApellidos() {return apellidos;}
	public String getPass() {return pass;}

	
	public String toString() {
		
		String salida = 
				"Correo: " + correo +
				"\nNombre: " + nombre +
				"\nApellidos: " + apellidos +
				"\ncorreoEntrenadores: " + idsEntrenadores +
				"\ncorreoMed: " + idMedico;
		return salida;
	}

}