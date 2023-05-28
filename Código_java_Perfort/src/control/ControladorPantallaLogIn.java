package control;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import entidades.Deportista;
import entidades.Entrenador;
import entidades.Medico;
import entidades.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorPantallaLogIn {

	@FXML
    private JFXTextField fxtfEmail;

    @FXML
    private JFXTextField fxtfNombre;

    @FXML
    private JFXTextField fxtfApellido;

    @FXML
    private JFXPasswordField fxtfContrasena;

    @FXML
    private Button botonRegistro;

    @FXML
    private JFXPasswordField fxtfConfirmCont;

    @FXML
    private Label miLabel;

    @FXML
    private DatePicker fechaRegistro;
    
    @FXML
    private TextField especialityRegistro;
    
    @FXML
    private Label labelName;

    @FXML
    private Label labelSurname;

    @FXML
    private Label labelPassword;

    @FXML
    private Label labelCompletado;
    
    @FXML
    private Button backButton;
    
    @FXML
    private RadioButton rbDeportista;

    @FXML
    private ToggleGroup tgRoll;

    @FXML
    private RadioButton rbEntrenador;

    @FXML
    private RadioButton rbMedico;
    
    @FXML
    private Label lbErrorRoll;
    
    @FXML
    private Label lbErrCampos;
    
    @FXML
    void volver(ActionEvent event) {
    	try {
			FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/view/PantallaPrincipal.fxml")); // NOS LLEVA A LA NUEVA VENTANA
			ControladorPantallaPrincipal control = new ControladorPantallaPrincipal(); // IMPORTANTE CAMBIAR ESTO
			loader3.setController(control);
			Parent root2 = loader3.load();
			
			//cierra la ventana anterior
			Stage myStage = (Stage)this.backButton.getScene().getWindow();
			myStage.close();
			
			Stage stage = new Stage();
			stage.setScene(new Scene(root2));
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    
    
    
    int contSinFallo=0;
    @FXML
    void registraEsto(ActionEvent event) {
    	contSinFallo=0;
    	comprobarRoll();		comprobarCampoNombre();		comprobarCampoApellido();		validarEmail();
    	validarContrasena();	validarPassword2();			existeUsuarioYa();
    	
    	ControladorConexionBD conexion = new ControladorConexionBD();

    	
    	
    	if(contSinFallo==0) {
    			String mail 		= fxtfEmail.getText();
    			String nombre 		= fxtfNombre.getText();
    			String apellido 	= fxtfApellido.getText();
    			String contrasena 	= fxtfContrasena.getText();

			
    			
    			if(rbDeportista.isSelected()) {

    				// CLASE CONEXIONBD
    				conexion.insertarDatosTablaUsuarios(mail, nombre, apellido, contrasena, "Deportista"); 
    				
    				try {
	    				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PantallaVerDatosDeportista.fxml")); // CARGA LA PANTALLA PRINCIPAL
	    				ControladorPantallaVerDatosDeportista controlPantDeportista = new ControladorPantallaVerDatosDeportista();
	    				
	    				loader.setController(controlPantDeportista);
	    				Parent root = loader.load();
	    				controlPantDeportista.mostrarInfo(mail);
	
	    				//cierra la ventana anterior
	        			Stage myStage = (Stage)this.backButton.getScene().getWindow();
	        			myStage.close(); 
	        			
	        			Stage stage = new Stage();
	        			stage.setScene(new Scene(root));
	        			stage.initModality(Modality.WINDOW_MODAL);
	        			stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
	        			stage.show();
	    			} catch(Exception e) {
						e.printStackTrace();
					}
        			
    			}else if(rbEntrenador.isSelected()){
//    				
    				// CLASE CONEXIONBD
    				conexion.insertarDatosTablaUsuarios(mail, nombre, apellido, contrasena, "Entrenador");
    				
    				try {
	    				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PantallaVerDatosEntrenador.fxml")); // CARGA LA PANTALLA PRINCIPAL
	    				ControladorPantallaVerDatosEntrenador controlPantEntrenador = new ControladorPantallaVerDatosEntrenador();
	    				
	    				loader.setController(controlPantEntrenador);
	    				Parent root = loader.load();
	    				// controlPantEntrenador.mostrarInfo(mail);
	    				
	    				//cierra la ventana anterior
	        			Stage myStage = (Stage)this.backButton.getScene().getWindow();
	        			myStage.close();
	        			
	        			Stage stage = new Stage();
	        			stage.setScene(new Scene(root));
	        			stage.initModality(Modality.WINDOW_MODAL);
	        			stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
	        			stage.show();
    				} catch(Exception e) {
    					e.printStackTrace();
    				}
    			}else if(rbMedico.isSelected()) {
//    				
    				conexion.insertarDatosTablaUsuarios(mail, nombre, apellido, contrasena, "Medico");
    				
    				try {
	    				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PantallaVerDatosMedico.fxml")); // CARGA LA PANTALLA PRINCIPAL
	    				ControladorPantallaVerDatosMedico controlPantMedico = new ControladorPantallaVerDatosMedico();
	    				
	    				loader.setController(controlPantMedico);
	    				Parent root = loader.load();
	    				controlPantMedico.mostrarInfo(mail);
	    				
	    				//cierra la ventana anterior
	        			Stage myStage = (Stage)this.backButton.getScene().getWindow();
	        			myStage.close();
	        			
	        			Stage stage = new Stage();
	        			stage.setScene(new Scene(root));
	        			stage.initModality(Modality.WINDOW_MODAL);
	        			stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
	        			stage.show();
    				} catch(Exception e) {
    					e.printStackTrace();
    				}
    			} else {
    	    		lbErrCampos.setText("Error en los campos señalados");
    	    		lbErrCampos.setVisible(true);
    	    	}
    		} 
    	}
    			
    			
    			

    
    public void comprobarRoll(){
    	if((rbDeportista.isSelected() == false) && (rbMedico.isSelected() == false) && (rbEntrenador.isSelected() == false)) {
    		
    		lbErrorRoll.setText("No se ha seleccionado el roll correctamente");
    		
    		lbErrorRoll.setVisible(true);
    		contSinFallo++;//aumenta el contador con cualquier cosa que no sea 0 no entra;
    	}else {
    		lbErrorRoll.setVisible(false);
    		contSinFallo = 0;
    	}
    }
    
    public void comprobarCampoNombre() {//falta validar que no meta numeros
    	if(fxtfNombre.getText().equals("")) {
    		fxtfNombre.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
    		contSinFallo++;//aumenta el contador con cualquier cosa que no sea 0 no entra;
    	}else {
    		fxtfNombre.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
    	}
    }
    
    public void comprobarCampoApellido() {
    	if(fxtfApellido.getText().equals("")) {
    		fxtfApellido.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
    		contSinFallo++;//aumenta el contador con cualquier cosa que no sea 0 no entra;
    	}else{
    		fxtfApellido.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
    	}
    }
    
    public void validarEmail() {
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
 
        // El email a validar
        String email = fxtfEmail.getText();
        Matcher mather = pattern.matcher(email);
 
        if (mather.find() == true) {
        	
    		
    		
    		
            fxtfEmail.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
        } else {
        	fxtfEmail.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
            
            contSinFallo++;//aumenta el contador con cualquier cosa que no sea 0 no entra;
        }
    }
	
    public void validarContrasena() {
    	int tamCont =fxtfContrasena.getText().length();
    	if(tamCont<8) {
    		
    		fxtfContrasena.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
    		contSinFallo++;//aumenta el contador con cualquier cosa que no sea 0 no entra;
    	}else {
    		fxtfContrasena.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
    	}
    }
    
    public void validarPassword2(){
    	String pass = fxtfContrasena.getText();
    	String confirmPass = fxtfConfirmCont.getText();
    	if(!pass.equals(confirmPass)){
    		
    		fxtfConfirmCont.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
    		contSinFallo++;//aumenta el contador con cualquier cosa que no sea 0 no entra;
    	}else {
    		fxtfConfirmCont.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
    	}
    }
    
    public void existeUsuarioYa() {

    	ControladorConexionBD conexion = new ControladorConexionBD();
    	if(conexion.concedeAcceso(fxtfEmail.getText(), fxtfContrasena.getText()) == true) {
    		
    		fxtfEmail.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
    		contSinFallo++;
    	} else {
    		fxtfEmail.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
    	}	

    	
	
    }

}

