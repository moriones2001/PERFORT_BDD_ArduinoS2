package entidades;

public class Marcas {
	
	String idCorreoDeportista;
	float ritmo;
	float frecuenciacardiaca;
	float temperatura;
	float humedad;
	
	public Marcas() {
		
	};
	
	public Marcas(String id, float ritmo , float frec, float temp , float hum) {
		this.idCorreoDeportista			= id;
		this.ritmo 			= ritmo;
		this.frecuenciacardiaca 	= frec;
		this.temperatura 	= temp;
		this.humedad		= hum;
	}
	
	// GETTERS
	public String getIdCorreoDeportista() {return idCorreoDeportista;}
	
	public float getRitmo() {return ritmo;}
	public float getFrec() {return frecuenciacardiaca;}
	public float getTemp(){return temperatura;}
	public float getHum() {return humedad;}
	

}
