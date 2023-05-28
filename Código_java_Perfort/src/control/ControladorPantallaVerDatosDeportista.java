package control;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;

import com.jfoenix.controls.JFXListView;

import entidades.Consulta;
import entidades.Deportista;
import entidades.Entrenamientos;
import entidades.Marcas;
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
import javafx.stage.StageStyle;

public class ControladorPantallaVerDatosDeportista {

	@FXML
    private Button botonBack;
	
	@FXML
	private JFXListView<String> jfxLVDatos;

    @FXML
    private JFXListView<String> jfxLVMarcas;
    
    @FXML
    private JFXListView<String> jfxLVConsultas;

    @FXML
    private Button botonDetalles;

    @FXML
    private Button botonProgreso;

    @FXML
    private Button botonchatDeportista;

    // CONTROLADOR DE LA CLASE CONEXIONBD PARA SACAR Y METER DATOS EN LA BD
    ControladorConexionBD conex = new ControladorConexionBD();
    
    
    // STRING PARA ALMACENAR EL CORREO DEL DEPORTISTA QUE PASAREMOS DESDE LA PANTALLA PPAL
    String correoDeportista = null;
    
    

    @FXML
void mostrarProgresoDeportista(ActionEvent event) {
    	
    	try {
			// CREAMOS LOS OBJETOS
			FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/view/GraficaMarcas.fxml")); // NOS LLEVA A LA NUEVA VENTANA
			ControladorGrafica control4 = new ControladorGrafica(); // IMPORTANTE CAMBIAR ESTO
			loader4.setController(control4);
			Parent root4 = loader4.load();

			
			control4.initialize(correoDeportista);
			
//			control4.recogeDatosDeportista(correoDeportista, usuarioSelecionado);
//			control4.inicializar();
			
			
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
    
    @FXML
    void irPantallaChat(ActionEvent event) {
    	
    	String usuarioSelecionado = jfxLVDatos.getSelectionModel().getSelectedItem();
    	if(usuarioSelecionado == null || usuarioSelecionado.equals(correoDeportista)) {
    		
    	} else {
    		try {
    			// CREAMOS LOS OBJETOS
    			FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/view/PantallaChat.fxml")); // NOS LLEVA A LA NUEVA VENTANA
    			ControladorChat control4 = new ControladorChat(); // IMPORTANTE CAMBIAR ESTO
    			loader4.setController(control4);
    			Parent root4 = loader4.load();

    			control4.recogeDatosDeportista(correoDeportista, usuarioSelecionado);
    			control4.inicializar();
    			
    			
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
    
   
    
    
    // MÉTODO DONDE SE PONE TODO
    public void  mostrarInfo(String correo){
    	ObservableList<String> items = FXCollections.observableArrayList();
    	correoDeportista = correo;
    	
    	
    	String entrenador 	= conex.dameRelacionDeportistaEntrenador(correo);
    	String medico 		= conex.dameRelacionDeportistaMedico(correo);
    	
    	// AÑADIMOS LOS USUARIOS AL PANEL
    	items.add(correo);
    	items.add(entrenador);
    	items.add(medico);
    	

    	// SETEAMOS LOS ITEMS
    	jfxLVDatos.setItems(items);
    	
    	// RELLENAMOS LAS DEMAS LISTAS
    	inicializarListaMarcas();
    	inicializarListaCitas();
    	
    };
     
    
    // INICIALIZACIÓN DE LAS LISTAS 
    public void inicializarListaMarcas() {
    	ObservableList<String> items = FXCollections.observableArrayList();
    	
    	try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT fecha, tipo FROM sensor WHERE correo = '" +correoDeportista+ "'")) {

               while (rs.next()) {
                   String fecha = rs.getString("fecha");
                   String tipo = rs.getString("tipo");
                   items.add(tipo + "=" + fecha);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
    	
    	jfxLVMarcas.setItems(items);
    }
    
    public void inicializarListaCitas() {
    	ObservableList<String> items = FXCollections.observableArrayList();
    	
    	try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT fecha_objetivo FROM consulta WHERE destinatario = '" +correoDeportista+ "'")) {

               while (rs.next()) {
                   String fecha = rs.getString("fecha_objetivo");
                   items.add(fecha);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
    	
    	jfxLVConsultas.setItems(items);
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
    
    
    
    // ESTO IGUAL NI FUNCIONA....
    @FXML
  public void mostrarMarcaDetalle(ActionEvent event){
    	
    	
    	
    	
    	String marcaSeleccionada = null;

        String itemSeleccionado = jfxLVMarcas.getSelectionModel().getSelectedItem();
        if (itemSeleccionado != null) {
            // Extraer la fecha del item seleccionado
            String[] partes = itemSeleccionado.split("=");
            System.out.println(partes[1]);
            
                marcaSeleccionada = partes[1].trim();
            
        }
    	
    	
    	 if(marcaSeleccionada == null) {
    		
    	} else {
    		try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerDatosMarcas.fxml")); // CARGA LA PANTALLA PRINCIPAL
				ControladorVerDatosCompleto controlVerDatosCompleto = new ControladorVerDatosCompleto();			
				loader.setController(controlVerDatosCompleto);
				Parent root = loader.load();
				
				// controlVerDatosCompleto.mostrarInfo(marcaSeleccionada);
		   
				controlVerDatosCompleto.mostrarInfo(marcaSeleccionada);				
			
				Stage stage = new Stage();
    			stage.setScene(new Scene(root));
    			stage.initModality(Modality.WINDOW_MODAL);
    			stage.initOwner(((Node)(event.getSource())).getScene().getWindow()); // ESTO HACE QUE NO PUEDAS VOLVER A SUBMIT HASTA QUE CIERRES LA VENTANA
    			stage.show();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
    	}
    				
			
    	}    	
	    	
 }
   
    


