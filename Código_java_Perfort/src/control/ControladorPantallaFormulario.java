package control;

import java.util.ArrayList;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import entidades.Consulta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ControladorPantallaFormulario {

    @FXML
    private JFXTextField jfxtfIdRemitenteForm;

    @FXML
    private JFXTextField jfxIdDestinatarioForm;
    
    @FXML
    private JFXTextField fxtfTipoConsulta;

    @FXML
    private JFXDatePicker jfxDatePicker;

    @FXML
    private JFXTimePicker jfxTimePicker;

    @FXML
    private JFXComboBox<String> jfxCBoxDuracionCons;
	
    @FXML
    private Button btnVolverDesdeFormulario;
    
    @FXML
    private Button jfxbtnSolicitar;
    
    @FXML
    private JFXTextArea jxtfCuerpo;
    
    @FXML
    private Label lbErrForm;
    
    @FXML
    private Label lbErrFechForm;
    
    @FXML
    void initialize() {
    	ObservableList<String>ObslistContenidoComboBox= FXCollections.observableArrayList();
    	ObslistContenidoComboBox.addAll("30min", "45min", "1h", "1h15min", "1h30", "2h");
    	jfxCBoxDuracionCons.getItems().setAll(ObslistContenidoComboBox);
    }
    
        
	public void pasarInfoAFormulario(String correoEntrenador, String deportistaSeleccionado, String tipoConsulta){
		jfxtfIdRemitenteForm.setText(correoEntrenador);
		jfxtfIdRemitenteForm.setEditable(false);
		jfxIdDestinatarioForm.setText(deportistaSeleccionado);
		jfxIdDestinatarioForm.setEditable(false);
		fxtfTipoConsulta.setText(tipoConsulta);
		fxtfTipoConsulta.setEditable(false);
	}
	
	@FXML
	void volver(ActionEvent event) {
		try {
			
			// CIERRA LA VENTANA ANTERIOR
			Stage myStage = (Stage) this.btnVolverDesdeFormulario.getScene().getWindow();
			myStage.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	int contFallos = 0;
	
	@FXML
	void solicitarEntreno(ActionEvent event) { 
		ControladorConexionBD conexion = new ControladorConexionBD();
		
	
		contFallos=0;
		compCampoCuerpo();
		compCampoFech();
		compCampoHora();
		compCampoDurCons();
		
		
		LocalDate fechaDate = jfxDatePicker.getValue();
		String fechaString = fechaDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


		
		 conexion.insertarDatosTablaConsulta(jfxtfIdRemitenteForm.getText(), jfxIdDestinatarioForm.getText(), fxtfTipoConsulta.getText(), jxtfCuerpo.getText(), fechaString);
			
		
	}
	
	
	
	
	
	
	
	public void compCampoFech() {
		LocalDate fechaForm = jfxDatePicker.getValue();
		LocalDate fechaHoy = LocalDate.now();
		if(fechaForm==null) {
			jfxDatePicker.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
			lbErrFechForm.setText("El campo de la fecha no esta seleccionado");
			lbErrFechForm.setStyle("-fx-text-fill: red");
			lbErrFechForm.setVisible(true);
			contFallos++;
		}else if (fechaHoy.compareTo(fechaForm)>0) {
			jfxDatePicker.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
			lbErrFechForm.setText("Tu te cree'h que pudes viajar al pasado o que churrita");
			lbErrFechForm.setStyle("-fx-text-fill: red");
			lbErrFechForm.setVisible(true);
			contFallos++;
		}else {
			jfxDatePicker.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
			lbErrFechForm.setVisible(false);
		}
	}
	
	public void compCampoHora() {
		System.out.println("Aqui entra ???");
		if(jfxTimePicker.getValue()==null) {
			System.out.println("A q u i       e n t r a" + jfxTimePicker.getValue() + "------");
			jfxTimePicker.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
			lbErrForm.setText("EL campo de la hora no esta seleccionado");
			lbErrForm.setStyle("-fx-text-fill: red");
			lbErrForm.setVisible(true);
			contFallos++;
		}else {
			jfxTimePicker.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
			lbErrForm.setVisible(false);
		}
	}
	
	public void compCampoCuerpo() {
		if(jxtfCuerpo.getText().equals("")) {
			jxtfCuerpo.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
			lbErrForm.setText("EL campo del cuerpo no tiene contenido");
			lbErrForm.setStyle("-fx-text-fill: red");
			lbErrForm.setVisible(true);
			contFallos++;
		}else {
			jxtfCuerpo.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
			lbErrForm.setVisible(false);
		}
	}
	 public void compCampoDurCons() {
		 if(jfxCBoxDuracionCons.getValue()==null){
			 jfxCBoxDuracionCons.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
			 lbErrForm.setText("Elije la duracion de la consulta");
			 lbErrForm.setStyle("-fx-text-fill: red");
			 lbErrForm.setVisible(true);
			 contFallos++;
		 }else {
			 jfxCBoxDuracionCons.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
			 lbErrForm.setVisible(false);
		 } 
	 }
	
}
