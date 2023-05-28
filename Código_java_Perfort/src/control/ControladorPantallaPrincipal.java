package control;

import java.util.Vector;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorPantallaPrincipal {
	
	
	
    @FXML
    private JFXTextField fxtfUsuarioPantP;

    @FXML
    private JFXPasswordField  fxtfContrasenaPantP;

    @FXML
    private Button miBotonLogin;

    @FXML
    private Label miLabel1;

    @FXML
    private Label miLabel2;

    @FXML
    private Button miBotonRegister;
    
    @FXML
    private Label lbErrorLogin;
    
	


    @FXML
    void accedeLogIn(ActionEvent event) { // VERIFICA LAS CREDENCIALES DEL USUARIO

    	// CREAMOS EL OBJETO CON EL QUE VAMOS A PODER LLAMAR A LA CLASE CONTROLADORCONEXIONBD
    	ControladorConexionBD conex = new ControladorConexionBD();

		// ALMACENAMOS LO OBTENIDOS POR LOS TEXTFIELDS EN LAS SIGUIENTES VARIABLES PARA REALIZAR LA COMPROBACIÓN
		String correo = fxtfUsuarioPantP.getText();		String pass = fxtfContrasenaPantP.getText();
		
		if(conex.concedeAcceso(correo, pass) == true) {		// SI LAS CREDENCIALES CONCUERAN
			String direccionador = conex.dameRollUsuario(correo); // VARIABLE PARA SACAR EL ROLL DEL USUARIO
			
			if(direccionador.equals("Deportista")) {
				try {
					// CREAMOS LOS OBJETOS
					FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/view/PantallaVerDatosDeportista.fxml")); // NOS LLEVA A LA NUEVA VENTANA
					ControladorPantallaVerDatosDeportista control4 = new ControladorPantallaVerDatosDeportista(); // IMPORTANTE CAMBIAR ESTO
					loader4.setController(control4);
					Parent root4 = loader4.load();
					control4.mostrarInfo(correo);
					
					// CIERRA LA VENTANA ANTERIOR
					Stage myStage = (Stage)this.miBotonLogin.getScene().getWindow();
					myStage.close();
					
					// INICIA LA VENTANA
					Stage stage = new Stage();
					stage.setScene(new Scene(root4));
					stage.initModality(Modality.WINDOW_MODAL);
					stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
					stage.show();
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else if(direccionador.equals("Entrenador")) {
				try {
					
					FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/view/PantallaVerDatosEntrenador.fxml")); // NOS LLEVA A LA NUEVA VENTANA
					ControladorPantallaVerDatosEntrenador control4 = new ControladorPantallaVerDatosEntrenador(); // IMPORTANTE CAMBIAR ESTO
					loader4.setController(control4);
					Parent root4 = loader4.load();  
				control4.mostrarInfo(correo);	// LLAMA AL CONTROLADOR DE ENTRENADORES Y PASA EL CORREO
					
					// CIERRA LA VENTANA ANTERIOR
					Stage myStage = (Stage)this.miBotonLogin.getScene().getWindow();
					myStage.close();
					
					// INICIA LA VENTANA
					Stage stage = new Stage();
					stage.setScene(new Scene(root4));
					stage.initModality(Modality.WINDOW_MODAL);
					stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
					stage.show();
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else if(direccionador.equals("Medico")) {
				try {
					// CREAMOS LOS OBJETOS
					FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/view/PantallaVerDatosMedico.fxml")); // NOS LLEVA A LA NUEVA VENTANA
					ControladorPantallaVerDatosMedico control4 = new ControladorPantallaVerDatosMedico(); // IMPORTANTE CAMBIAR ESTO
					loader4.setController(control4);
					Parent root4 = loader4.load();
					control4.mostrarInfo(correo);
					
					// CIERRA LA VENTANA ANTERIOR
					Stage myStage = (Stage)this.miBotonLogin.getScene().getWindow();
					myStage.close();
					
					// INICIA LA VENTANA
					Stage stage = new Stage();
					stage.setScene(new Scene(root4));
					stage.initModality(Modality.WINDOW_MODAL);
					stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
					stage.show();
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			}else{
				fxtfUsuarioPantP.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
				lbErrorLogin.setText("Contraseña o usuario incorrectos");
				fxtfContrasenaPantP.setStyle("-fx-prompt-text-fill: red; -jfx-unfocus-color: red;");
				lbErrorLogin.setVisible(true);
			}
				
		}

}
    
    
    
    @FXML
    void registraUsuario(ActionEvent event) { // PERMITE ACCEDER AL MENÚ DE REGISTRACIÓN
    	
    	try {
    		// CREAMOS LOS OBJETOS
			FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/view/PantallaRegistro.fxml")); // NOS LLEVA A LA NUEVA VENTANA
			ControladorPantallaLogIn control = new ControladorPantallaLogIn(); // IMPORTANTE CAMBIAR ESTO
			loader2.setController(control);
			Parent root2 = loader2.load();
				
			// CIERRA LA VENTANA ANTERIOR
			Stage myStage = (Stage)this.miBotonLogin.getScene().getWindow();
			myStage.close();
			
			// INICIA LA VENTANA
			Stage stage = new Stage();
			stage.setScene(new Scene(root2));
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}	

    }

    @FXML
    void resetTextField(MouseEvent event) {
		fxtfUsuarioPantP.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
		fxtfContrasenaPantP.setStyle("-fx-prompt-text-fill: black; -jfx-unfocus-color: black;");
		lbErrorLogin.setVisible(false);
    }
    
}
