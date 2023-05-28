package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.jfoenix.controls.JFXListView;

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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorPantallaVerDatosEntrenador {

	 @FXML
	    private Button botonBack;

	    @FXML
	    private JFXListView<String> jfxLVDeportistasAsociados;

	    @FXML
	    private JFXListView<String> jfxLVDeportistasLibres;

	    @FXML
	    private Button botonAsignarDeportista;

	    @FXML
	    private Button botonChatEntrenador;

	    @FXML
	    private JFXListView<String> jfxLVCitasEntrenador;

	    @FXML
	    private Button btnCrearEntreno;

	    @FXML
	    private Label labelNombre;

    
    ControladorConexionBD conexion = new ControladorConexionBD();
    
    String correoEntrenador = null;
    
    // AQUÍ SE INICIALIZA TODO
    public void  mostrarInfo(String correo){
    	this.correoEntrenador=correo;
    	labelNombre.setText("Usuario: " + correo); // SETTEAMOS EL NOMBRE EN LA LABEL
    
    	// LLAMAMOS A LOS MÉTODOS QUE NOS VAN A MOSTRAR LA INFORMACIÓN EN LOS LISTVIEWS QUE HEMOS CREADO
    	//inicializarListaDeportistasAsociados();
    	inicializarListaDeportistasAsociados(correoEntrenador);
    	//inicializarListaDeportistasNoAsociados();
    	inicializarListaDeportistasNoAsociados(correoEntrenador);
    	inicializarListaCitas();
    };

    @FXML
    void asignaDeportista(ActionEvent event) { // MÉTODO PARA QUE UN DEPORTISTA QUEDE ENLAZADO CON UN ENTRENADOR
    	
    	String deportistaSeleccionado = jfxLVDeportistasLibres.getSelectionModel().getSelectedItem();
    	conexion.insertarDatosTablaEntrenadorDeportista(correoEntrenador, deportistaSeleccionado);   	// LLAMAMOS AL METODO DE LA CLASE CONEXIOBBDD
    }

    @FXML
    void irAFormulario(ActionEvent event) {
    	ControladorPantallaFormulario controladorFormulario = new ControladorPantallaFormulario();
    	String deportistaSeleccionado = jfxLVDeportistasAsociados.getSelectionModel().getSelectedItem(); // SACAMOS EL DEPORTISTA ASOCIADO DEL LISTVIEW PARA UTILIZARLO LUEGO
    	
    	if(deportistaSeleccionado != null) { // SI TENEMOS UN DEPORTISTA ASOCIADO SELECCIONADO PODRÁ ACCEDER A LA PANTALLA SINO NO
    		try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/formularioConsultasEntrenamientos.fxml")); // CARGA LA PANTALLA PRINCIPAL
				ControladorPantallaFormulario controlPantFormulario = new ControladorPantallaFormulario();
				
				loader.setController(controlPantFormulario);
				Parent root = loader.load();
				
				// PASAMOS LA INFORMACIÓN AL SIGUIENTE MÉTODO DEL FORMULARIO
				controlPantFormulario.pasarInfoAFormulario(correoEntrenador, deportistaSeleccionado, "Entrenamiento");

				
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
    			
    			
    			
    			
    			control4.recogeDatosEntrenador(correoEntrenador, usuarioSelecionado);
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
    	
    	
    	
   
    // MÉTODO PARA VOLVER ATRÁS AL PULSAR EL BOTÓN DE BACK
    @FXML
    void mostrarAlertConfirmation(ActionEvent event) { 
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
    
    

    
    
    //No funcionaba, mostraba todos los deportistas
    /*public void inicializarListaDeportistasAsociados() {
    	ObservableList<String> items = FXCollections.observableArrayList();
    	try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id_deportista FROM EntrenadorDeportista ")) {

               while (rs.next()) {
                   String deportista = rs.getString("id_deportista");
                   items.add(deportista);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
    	
    	jfxLVDeportistasAsociados.setItems(items);
    }*/
    
    public void inicializarListaDeportistasAsociados(String idEntrenador) {
        ObservableList<String> items = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
             PreparedStatement stmt = conn.prepareStatement("SELECT id_deportista FROM EntrenadorDeportista WHERE id_entrenador = ?");
        ) {
            stmt.setString(1, idEntrenador);
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


    
    
    public void inicializarListaDeportistasNoAsociados(String idEntrenador) {
        ObservableList<String> items = FXCollections.observableArrayList();
        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
             PreparedStatement stmt = conn.prepareStatement("SELECT correo FROM usuarios WHERE roll = 'Deportista' AND correo NOT IN (SELECT id_deportista FROM EntrenadorDeportista WHERE id_entrenador = ?)");
        ) {
            stmt.setString(1, idEntrenador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String idDeportista = rs.getString("correo");
                items.add(idDeportista);
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
        	
        	stmt.setString(1,correoEntrenador);
        	ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	String fecha = rs.getString("fecha");
                
                items.add(fecha);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        jfxLVCitasEntrenador.setItems(items);
   }
    


    
}


