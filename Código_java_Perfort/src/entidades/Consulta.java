package entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import javafx.scene.control.DatePicker;

public class Consulta {
	String Remitente;
	String Destinatario;
	LocalDate fecha;
	LocalTime horaConsulta;
	String duracionConsulta;
	String Tipo;
	String Cuerpo;
	
	public Consulta(){};//constructor vacio
	
	public Consulta(String Remitente, String Destinatario, LocalDate localDate, LocalTime localTime, String duracionConsulta, String Tipo, String Cuerpo){
		this.Remitente= Remitente;
		this.Destinatario= Destinatario;
		this.fecha= localDate;
		this.horaConsulta=localTime;
		this.duracionConsulta = duracionConsulta;
		this.Tipo= Tipo;
		this.Cuerpo= Cuerpo;
		
	}
	
	public String getRemitente() {
		return Remitente;
	}
	public void setRemitente(String remitente) {
		Remitente = remitente;
	}
	
	public String getDestinatario() {
		return Destinatario;
	}
	public void setDestinatario(String destinatario) {
		Destinatario = destinatario;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraConsulta() {
		return horaConsulta;
	}

	public void setHoraConsulta(LocalTime horaConsulta) {
		this.horaConsulta = horaConsulta;
	}

	public String getDuracionConsulta() {
		return duracionConsulta;
	}

	public void setDuracionConsulta(String duracionConsulta) {
		this.duracionConsulta = duracionConsulta;
	}

	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	
	public String getCuerpo() {
		return Cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		Cuerpo = cuerpo;
	}
	

	
}
