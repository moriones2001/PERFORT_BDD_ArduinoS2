package control;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jfoenix.controls.JFXListView;

public class ControladorChat  {
	
  
    @FXML
    private TextField remitenteChat;

    @FXML
    private JFXListView<String> panelChat;

    @FXML
    private TextField mensajeChat;

    @FXML
    private Button envioChat;

    @FXML
    private TextField destinatarioChat;
    
    public void inicializar() {

    	loadChatMessages();
    	
    	
    }

    
    
    // PODRÍA SER SOLO UN METODO RECOGE DATOS
    public void recogeDatosDeportista(String id_deportista, String id_usuario1) {
    	remitenteChat.setText(id_deportista);
    	destinatarioChat.setText(id_usuario1);
    }
    
    public void recogeDatosEntrenador(String id_entrenador, String id_usuario1) {
    	remitenteChat.setText(id_entrenador);
    	destinatarioChat.setText(id_usuario1);
    }
    
    public void recogeDatosMedico(String id_medico, String id_usuario1) {
    	remitenteChat.setText(id_medico);
    	destinatarioChat.setText(id_usuario1);
    }

    
    
    
    private void loadChatMessages() {
    	
    	ObservableList<String> items = FXCollections.observableArrayList();
    	
    	String id_deportista = remitenteChat.getText();

    	String id_usuario1 = destinatarioChat.getText();
    	
    	try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
    	
    			
    
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id_emisor, cuerpo FROM chat "
            		+ "Where id_emisor = '" +id_deportista+ "' AND id_receptor = '" +id_usuario1+ "' OR "
            		+ "id_emisor = '" +id_usuario1+ "' AND id_receptor = '" +id_deportista + "'")) {

    		
    		
    		
            while (resultSet.next()) {
                String sender = resultSet.getString("id_emisor");
                String message = resultSet.getString("cuerpo");
                String chatMessage = sender + ": " + message; 
                
                items.add(chatMessage);
                
                panelChat.setItems(items);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void enviarMensaje(ActionEvent event) {
    	   String sender 		= remitenteChat.getText();
           String recipient 	= destinatarioChat.getText();
           String message 		= mensajeChat.getText();
           
           LocalDateTime timestamp 	= LocalDateTime.now();
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
           String formattedTimestamp 	= timestamp.format(formatter);
           
           
           try {
           	Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
               PreparedStatement statement = conn.prepareStatement(
                       "INSERT INTO chat (id_emisor, id_receptor, cuerpo, fecha) VALUES (?, ?, ?, ?)");
               statement.setString(1, sender);
               statement.setString(2, recipient);
               statement.setString(3, message);
               statement.setString(4, formattedTimestamp);
               statement.executeUpdate();

              
           } catch (SQLException e) {
               e.printStackTrace();
           }
           
           loadChatMessages();
    }
   
}