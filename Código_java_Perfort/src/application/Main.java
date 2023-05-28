package application;
import java.util.Vector;

import control.ControladorConexionBD;

import control.ControladorPantallaPrincipal;
import entidades.Deportista;
import entidades.Entrenador;
import entidades.Marcas;
import entidades.Medico;
import entidades.Persona;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PantallaPrincipal.fxml")); // CARGA LA PANTALLA PRINCIPAL
			ControladorPantallaPrincipal control = new ControladorPantallaPrincipal();
			loader.setController(control);
			Parent root = loader.load();			
			
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) { 
		
		// ESTO FUNCIONA
    	ControladorConexionBD conex = new ControladorConexionBD();
    	conex.crearTablas();

		launch(args); // LANZA EL PROGRAMA	
    	
    	
    	
    	
	}
}
