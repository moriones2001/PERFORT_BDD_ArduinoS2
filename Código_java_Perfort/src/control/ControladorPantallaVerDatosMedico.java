package control;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import entidades.Consulta;
import entidades.Deportista;
import entidades.Medico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControladorPantallaVerDatosMedico {


	@FXML
    private Button botonBack;

    @FXML
    private Button jfxBtnAsignarDepor;

    @FXML
    private Button botonChatMedico;

    @FXML
    private JFXListView<String> jfxLVDeportistasAsociados;

    @FXML
    private JFXListView<String> jfxLVDeportistasLibres;

    @FXML
    private JFXListView<String> jfxLVCitas;

    @FXML
    private Button jfxBtnProgCita;

    @FXML
    private Label labelNombre;


    
   

    
    
	
    
	String correoMedico;
    public void  mostrarInfo(String correo){
    	this.correoMedico=correo;
    	labelNombre.setText("Usuario: " + correo); 
    	
    	inicializarListaDeportistasAsociados(correoMedico);
    	inicializarListaDeportistasNoAsociados();
    	inicializarListaCitas();
    	
    	
    };
    
    
    @FXML
    void irPantallaChat(ActionEvent event) {
    	String usuarioSelecionado = jfxLVDeportistasAsociados.getSelectionModel().getSelectedItem();
    	
    	if(usuarioSelecionado == null) {
    		
    	} else {
    		try {
    			// CREAMOS LOS OBJETOS
    			FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/view/PantallaChat.fxml")); // NOS LLEVA A LA NUEVA VENTANA
    			ControladorChat control4 = new ControladorChat(); // IMPORTANTE CAMBIAR ESTO
    			loader4.setController(control4);
    			Parent root4 = loader4.load();
    			
    			
    			
    			
    			control4.recogeDatosEntrenador(correoMedico, usuarioSelecionado);
    			control4.inicializar();
    			
    			// CIERRA LA VENTANA ANTERIOR
    			// Stage myStage = (Stage)this.miBotonLogin.getScene().getWindow();
    			// myStage.close();
    			
    			// INICIA LA VENTANA
    			Stage stage = new Stage();
    			stage.setScene(new Scene(root4));
    			stage.initModality(Modality.WINDOW_MODAL);
    			stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
    			stage.show();
    			
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    public void inicializarListaDeportistasAsociados(String idMedico) {
        ObservableList<String> items = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
             PreparedStatement stmt = conn.prepareStatement("SELECT id_deportista FROM MedicoDeportista WHERE id_medico = ?");
        ) {
            stmt.setString(1, idMedico);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String idDeportista = rs.getString("id_deportista");
                items.add(idDeportista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        jfxLVDeportistasAsociados.setItems(items);
    }
    
    
    public void inicializarListaDeportistasNoAsociados() {
         ObservableList<String> items = FXCollections.observableArrayList();
         try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery("SELECT u.correo FROM usuarios u WHERE u.correo NOT IN (SELECT de.id_deportista FROM MedicoDeportista de) AND roll = 'deportista'")) {

             while (rs.next()) {
                 String deportista = rs.getString("correo");
                 items.add(deportista);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         jfxLVDeportistasLibres.setItems(items);
    }
    
    
    public void inicializarListaCitas() { // SE MOSTRARAN LAS CITAS PENDIENTES CON LOS DEPORTISTAS  
        ObservableList<String> items = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
            
        	PreparedStatement stmt = conn.prepareStatement("SELECT fecha FROM consulta WHERE remitente = ?")) {
        	
        	stmt.setString(1,correoMedico);
        	ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	String fecha = rs.getString("fecha");
                
                items.add(fecha);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        jfxLVCitas.setItems(items);
   }
    
    @FXML
    private void asignarAdeportista(ActionEvent event) {
    	ControladorConexionBD conexion = new ControladorConexionBD();
    	String deportistaSeleccionado = jfxLVDeportistasLibres.getSelectionModel().getSelectedItem();
    	conexion.insertarDatosTablaMedicoDeportista(correoMedico, deportistaSeleccionado);   	// LLAMAMOS AL METODO DE LA CLASE CONEXIOBBDD
    }
    
    
    
    

	
   
    
    @FXML
    private void mostrarAlertConfirmation(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmacion");
        alert.setContentText("¿Deseas realmente confirmar?");
        Optional<ButtonType> btnOpcion = alert.showAndWait();
        if(btnOpcion.get() == ButtonType.OK){
        	try {
    			// CIERRA LA VENTANA ANTERIOR
    			Stage myStage = (Stage) this.botonBack.getScene().getWindow();
    			myStage.close();

    			
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PantallaPrincipal.fxml")); // CARGA LA PANTALLA PRINCIPAL
				ControladorPantallaPrincipal controlPantPrincipal = new ControladorPantallaPrincipal();
				
				loader.setController(controlPantPrincipal);
				Parent root = loader.load();
				    			
    			Stage stage = new Stage();
    			stage.setScene(new Scene(root));
    			stage.initModality(Modality.WINDOW_MODAL);
    			stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
    			stage.show();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
        }else {
        	
        }
    }
    
    
    
    int contFallos;
    

    @FXML
    public void irAFormulario(ActionEvent event){
    	ControladorPantallaFormulario controladorFormulario = new ControladorPantallaFormulario();
    	contFallos=0;
    		///
    		
    		comprobarSelecDepor();
    		System.out.println(contFallos);
			if(contFallos==0) {

				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/formularioConsultasEntrenamientos.fxml")); // CARGA LA PANTALLA PRINCIPAL
					ControladorPantallaFormulario controlPantFormulario = new ControladorPantallaFormulario();
					
					loader.setController(controlPantFormulario);
					Parent root = loader.load();
					controlPantFormulario.pasarInfoAFormulario(correoMedico, jfxLVDeportistasAsociados.getSelectionModel().getSelectedItem(), "Medico");
	
					
					Stage stage = new Stage();
					stage.setScene(new Scene(root));
					stage.initModality(Modality.WINDOW_MODAL);
					stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
					stage.show();
					
				
				} catch(Exception e) {
					e.printStackTrace();
				}
    		}
    }		
    
     


    public void comprobarSelecDepor() {
//    	String deporSelec = jfxLVDeportistasAsociados.getSelectionModel().getSelectedItem();
//    	if(deporSelec== null) {
//    		lbErrDeportista.setText("Debes seleccionar un deportista antes de hacer una asignacion");
//    		lbErrDeportista.setStyle("-fx-text-fill: red");
//    		lbErrDeportista.setVisible(true);
//    		contFallos++;
//    	}else {
//    		lbErrDeportista.setVisible(false);
//    	}
    }
//    

}
