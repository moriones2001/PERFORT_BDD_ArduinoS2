package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ControladorGrafica {

	@FXML
    private LineChart<String, Number> idGrafLineasPts;

    @FXML
    public void initialize(String correo) {
        // Configurar los ejes del gráfico
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Valor");
        idGrafLineasPts.setCreateSymbols(true);
        idGrafLineasPts.setAnimated(false);
        idGrafLineasPts.setTitle("Gráfico de Líneas");

        // Obtener los datos de la base de datos y agregarlos al gráfico
        query(correo);
    }

    void query(String correo) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT fecha, valor FROM sensor WHERE sensor = 'HC-SR04' AND correo = '" + correo + "'")) {

            while (rs.next()) {
                String fecha = rs.getString("fecha");
                float valor = rs.getFloat("valor");

                // Agregar los datos al series
                series.getData().add(new XYChart.Data<>(fecha, valor));
            }

            // Agregar el series al gráfico
            idGrafLineasPts.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}