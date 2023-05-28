package control;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entidades.Marcas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControladorVerDatosCompleto {

	   @FXML
	    private Label lbDatoRitmo;

	    @FXML
	    private Label lbDatoPulsaciones;

	    @FXML
	    private Label lbDatoTemperatura;

	    @FXML
	    private Label lbDatoHumedad;


	   

    public void  mostrarInfo(String fecha){
        
   	 float ritmo = verDatosRitmo(fecha);
   	 float pulsaciones = verDatosPulsaciones(fecha);
   	 float temperatura = verDatosTemperatura(fecha);
   	 float humedad = verDatosHumedad(fecha);
   	 
   	 
   	 
   	 
   	 
         lbDatoRitmo.setText("Ritmo: " + ritmo + "\tkm/h"); 
         lbDatoPulsaciones.setText("Frecuencia máxima: " + pulsaciones + "\tmáx frecuencia");
         lbDatoTemperatura.setText("Temperatura: " + temperatura + "\tCº");
         lbDatoHumedad.setText("Humedad: " + humedad + "\t%");

        
        
        
        


    };
    
    
    public void inicializar() {
   	 lbDatoRitmo.setText("Que noxe pedrero");
    }


    public float verDatosRitmo(String fecha) {
   	    try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
   	        String sql = "SELECT valor FROM sensor WHERE fecha = ? AND sensor = ?";
   	        PreparedStatement statement = conn.prepareStatement(sql);
   	        statement.setString(1, fecha);
   	        statement.setString(2, "HC-SR04");

   	        ResultSet resultSet = statement.executeQuery();

   	        if (resultSet.next()) {
   	            float valor = resultSet.getFloat("valor");
   	            return valor;
   	        }
   	    } catch (SQLException e) {
   	        e.printStackTrace();
   	    }

   	    return 0.0f; // Valor por defecto si no se encuentra un registro
   	}

    public float verDatosPulsaciones(String fecha) {
   	 
   	  try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
 	        String sql = "SELECT valor FROM sensor WHERE fecha = ? AND sensor = ?";
 	        PreparedStatement statement = conn.prepareStatement(sql);
 	        statement.setString(1, fecha);
 	        statement.setString(2, "AD8232");

 	        ResultSet resultSet = statement.executeQuery();

 	        if (resultSet.next()) {
 	            float valor = resultSet.getFloat("valor");
 	            return valor;
 	        }
 	    } catch (SQLException e) {
 	        e.printStackTrace();
 	    }

 	    return 0.0f; // Valor por defecto si no se encuentra un registro

    }

    public float verDatosTemperatura(String fecha) {
   	 try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
  	        String sql = "SELECT valor FROM sensor WHERE fecha = ? AND sensor = ? AND tipo = ?";
  	        PreparedStatement statement = conn.prepareStatement(sql);
  	        statement.setString(1, fecha);
  	        statement.setString(2, "DHT11");
  	        statement.setString(3, "temperatura (C)");

  	        ResultSet resultSet = statement.executeQuery();

  	        if (resultSet.next()) {
  	            float valor = resultSet.getFloat("valor");
  	            return valor;
  	        }
  	    } catch (SQLException e) {
  	        e.printStackTrace();
  	    }

  	    return 0.0f; // Valor por defecto si no se encuentra un registro

    }

    public float verDatosHumedad(String fecha) {
   	 try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
   	        String sql = "SELECT valor FROM sensor WHERE fecha = ? AND sensor = ? AND tipo = ?";
   	        PreparedStatement statement = conn.prepareStatement(sql);
   	        statement.setString(1, fecha);
   	        statement.setString(2, "DHT11");
   	        statement.setString(3, "humedad (%)");

   	        ResultSet resultSet = statement.executeQuery();

   	        if (resultSet.next()) {
   	            float valor = resultSet.getFloat("valor");
   	            return valor;
   	        }
   	    } catch (SQLException e) {
   	        e.printStackTrace();
   	    }

   	    return 0.0f; // Valor por defecto si no se encuentra un registro
    }
}
