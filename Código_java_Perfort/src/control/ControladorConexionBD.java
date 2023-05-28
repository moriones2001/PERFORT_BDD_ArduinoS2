package control;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorConexionBD {
	
	
	Connection conn = null;
    Statement stmt = null;
    String sql;
    
    public void crearTablas() { // HAY QUE ACTUALIZAR PARA LAS CLAVES FORÁNEAS Y DEMÁS
    	
   	 try {
        	Class.forName("org.mariadb.jdbc.Driver");

           conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");
            
            // CREANDO LA TABLA CONSULTA
            String tablaConsulta = "CREATE TABLE IF NOT EXISTS consulta (" +
                    "id_consulta INT AUTO_INCREMENT PRIMARY KEY," +
                    "remitente VARCHAR(255)," +
                    "destinatario VARCHAR(255)," +
                    "fecha TIMESTAMP," +
                    "tipo VARCHAR(255)," +
                    "cuerpo VARCHAR(255)," +
                    "fecha_objetivo DATE" +
                    ")";
            
            // CREANDO LA TABLA USUARIOS
            String tablaUsuarios = "CREATE TABLE IF NOT EXISTS usuarios "
              		+ "  (correo VARCHAR(255) not NULL ,"
              		+ "  nombre VARCHAR(255),"
              		+ "  apellidos VARCHAR(255),"
              		+ "  pass VARCHAR(255),"
              		+ "  roll VARCHAR(255),"
              		+ "	 PRIMARY KEY (correo))";
            
            
            // CREANDO LA TABLA SENSOR
            String tablaSensor = "CREATE TABLE IF NOT EXISTS sensor "
           		    + "  (id INTEGER AUTO_INCREMENT PRIMARY KEY,"
           		    + "  sensor VARCHAR(50),"
           		    + "  tipo VARCHAR(255),"
           		    + "  valor FLOAT,"
           		    + "  fecha TIMESTAMP,"
           		    + "  correo VARCHAR(255),"
           		    + "  FOREIGN KEY (correo) REFERENCES usuarios(correo)"
           		    + ")";
            

            String tablaMedicoDeportista = "CREATE TABLE IF NOT EXISTS MedicoDeportista (" +
		             "id_medico VARCHAR(255)," +
		             "id_deportista VARCHAR(255)," +
		             "FOREIGN KEY (id_medico) REFERENCES usuarios(correo)," +
		             "FOREIGN KEY (id_deportista) REFERENCES usuarios(correo)" +
		             ")";
            
            String tablaEntrenadorDeportista = "CREATE TABLE IF NOT EXISTS EntrenadorDeportista (" +
                    "id_entrenador VARCHAR(255)," +
                    "id_deportista VARCHAR(255)," +
                    "FOREIGN KEY (id_entrenador) REFERENCES usuarios(correo)," +
                    "FOREIGN KEY (id_deportista) REFERENCES usuarios(correo)" +
                    ")";
            
            String tablaChat = "CREATE TABLE IF NOT EXISTS chat ("
        		    + "  	id_chat INT AUTO_INCREMENT PRIMARY KEY,"
        		    + "  	id_emisor VARCHAR(255),"
        		    + "  	id_receptor VARCHAR(255),"
        		    + " 	cuerpo VARCHAR(255),"
        		    + "  	fecha TIMESTAMP"
        		    + "		)";
//            
            String tablaRelacionUsuariosConsultas = "CREATE TABLE IF NOT EXISTS UsuariosConsultas ("
             		+ "    id_usuario1 VARCHAR(255) NOT NULL,"
             		+ "    id_usuario2 VARCHAR(255) NOT NULL,"
             		+ "    id_consulta INT NOT NULL,"            		
             		+ "    FOREIGN KEY (id_consulta) REFERENCES consulta(id_consulta)"
             	
             		+ ")"; 
            	
            

            
            
            // CREAMOS LA DECLARACIÓN Y LAS TABLAS
            stmt = conn.createStatement();		
            stmt.executeUpdate(tablaConsulta);
            stmt.executeUpdate(tablaUsuarios);
            stmt.executeUpdate(tablaSensor);	
            stmt.executeUpdate(tablaMedicoDeportista);	
            stmt.executeUpdate(tablaEntrenadorDeportista);
            stmt.executeUpdate(tablaChat);
            stmt.executeUpdate(tablaRelacionUsuariosConsultas);
            stmt.close();
        

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
   }
   
   public boolean concedeAcceso(String correo, String contrasena) { // COMPRUEBA QUE EL CORREO Y LA CONTRASEÑA ESTAN EN LA BBDD
       try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
           String sql = "SELECT Correo FROM usuarios WHERE Correo = ? AND pass = ?";
           PreparedStatement statement = conn.prepareStatement(sql);
           statement.setString(1, correo);
           statement.setString(2, contrasena);

           ResultSet resultSet = statement.executeQuery();
           return resultSet.next(); // DEVUELVE TRUE SI ENCUENTRA UN REGISTRO, FALSE DE LO CONTRARIO
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return false;
   }
   
   public String dameRollUsuario(String correo) { // DEVUELVE UN STRING CON EL ROLL DEL USUARIO DEL CUAL HEMOS PASADO SU CORREO QUE ES ÚNICO
   	try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
           String sql = "SELECT roll FROM usuarios WHERE Correo = ?";
           PreparedStatement statement = conn.prepareStatement(sql);
           statement.setString(1, correo);

           ResultSet resultSet = statement.executeQuery();
           if (resultSet.next()) {
               return resultSet.getString("roll");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return null; // Retorna null si no se encuentra el correo
   }
   
   
   
   
   
   public void insertarDatosTablaUsuarios(String correo , String nombre , String apellidos , String pass , String roll) {
   	 try {
            // REGISTRAMOS EL DRIVER
        	Class.forName("org.mariadb.jdbc.Driver");
        	
        	// CONECTAMOS
        	conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja");

            
            // ALMACENAMOS LA SENTENCIA DE INTRODUCCIÓN DE DATOS EN LA VARIABLE STRING QUE CREAMOS AL INICIO
            sql = "Insert into usuarios(correo, nombre, apellidos, pass, roll)"
            		+ " Select '" +correo+ "', '" +nombre+ "', '" +apellidos+ "', '" +pass+ "', '" +roll+ "'"
            		+ " Where Not EXISTS ("
            			+ "Select correo From usuarios Where correo = '" +correo+ "');";  
            		

            stmt = conn.createStatement();	// CREMOS LA DECLARACIÓN
            stmt.executeUpdate(sql);  		// EJECUTAMOS LA SENTENCIA
            stmt.close();					// CERRAMOS LA DECLARACIÓN
            
            
   			
   	 // CERRAMOS CONEXIÓN
   	 conn.close();

        } catch (SQLException se) { // CAPTA LOS POSIBLES ERRORES DEL DRIVER
            se.printStackTrace();
        } catch (Exception e) {	// CAPTA LOS POSIBLES ERRORES DE LA CLASE FORNAME
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {conn.close();}
            } catch (SQLException se) {}
            
            try {
                if (conn != null) {conn.close();}
            } catch (SQLException se) {
                se.printStackTrace();
            }	// AQUÍ TERMINA EL  FINALLY TRY
        }//AQUÍ TERMINA EL TRY
   	}
   

   public void insertarDatosTablaConsulta(String remitente, String destinatario, String tipo, String cuerpo, String fechaObjetivo) {
	    try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
	        String sql = "INSERT INTO consulta (remitente, destinatario, fecha, tipo, cuerpo, fecha_objetivo) " +
	                "VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?, ?)";

	        String sqlAdicional = "SELECT id_consulta FROM consulta ORDER BY id_consulta DESC LIMIT 1";

	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, remitente);
	        statement.setString(2, destinatario);
	        statement.setString(3, tipo);
	        statement.setString(4, cuerpo);
	        statement.setString(5, fechaObjetivo);

	        statement.executeUpdate();

	        PreparedStatement statement2 = conn.prepareStatement(sqlAdicional);
	        ResultSet rs = statement2.executeQuery();
	        
	        int idConsulta = 0;
	        if (rs.next()) {
	            idConsulta = rs.getInt("id_consulta");
	        }
	        
	        String sql2 = "INSERT INTO UsuariosConsultas (id_usuario1, id_usuario2, id_consulta) VALUES (?, ?, ?)";

	        PreparedStatement statement3 = conn.prepareStatement(sql2);
	        statement3.setString(1, remitente);
	        statement3.setString(2, destinatario);
	        statement3.setInt(3, idConsulta);

	        statement3.executeUpdate();
	        System.out.println("Consulta insertada correctamente.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
   
   
   public void insertarDatosTablaEntrenadorDeportista(String id_entrenador, String id_deportista) {
   		try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
   		
       	sql = "Insert into EntrenadorDeportista(id_entrenador, id_deportista)"
            		+ " Select '" +id_entrenador+ "', '" +id_deportista+ "'"
       			
       			+ "WHERE NOT EXISTS ("
       			+	"Select id_entrenador, id_deportista from EntrenadorDeportista where id_entrenador = '" +id_entrenador+ "' and id_deportista = '" +id_deportista+ "');";
       			
       	
       	
       	stmt = conn.createStatement();	// CREMOS LA DECLARACIÓN
           stmt.executeUpdate(sql);  		// EJECUTAMOS LA SENTENCIA
           stmt.close();	
          
           //statement.executeUpdate();
           System.out.println("Consulta insertada correctamente.");
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
   
   public void insertarDatosTablaMedicoDeportista(String id_medico, String id_deportista) {
   	try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
   		
       	sql = "Insert into MedicoDeportista(id_medico, id_deportista)"
            		+ " Select '" +id_medico+ "', '" +id_deportista+ "'"
       			
       			+ "WHERE NOT EXISTS ("
       			+	"Select id_medico, id_deportista from MedicoDeportista where id_medico = '" +id_medico+ "' and id_deportista = '" +id_deportista+ "');";
       			
       	
       	
       	stmt = conn.createStatement();	// CREMOS LA DECLARACIÓN
           stmt.executeUpdate(sql);  		// EJECUTAMOS LA SENTENCIA
           stmt.close();	
          
           //statement.executeUpdate();
           System.out.println("Consulta insertada correctamente.");
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
   
   public void insertarDatosTablaUsuariosConsultas(String id_deportista, String id_usuario, int id_consulta) {
   	try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
   		
       	sql = "Insert into usuariosconsultas(id_deportista, id_usuario,id_consulta)"
            		+ " Select '" +id_deportista+ "', '" +id_usuario+ "', '" + id_consulta+ "'" 
       			
       			+ "WHERE NOT EXISTS ("
       			+	"Select id_deportista, id_usuario, id_consulta from usuariosconsultas "
       			+ 		"where id_deportista = '" +id_deportista+ "' and id_usuario = '" +id_usuario+ "' and id_consulta = '"+id_consulta+"')";;
       			
       	
       	
       	stmt = conn.createStatement();	// CREMOS LA DECLARACIÓN
           stmt.executeUpdate(sql);  		// EJECUTAMOS LA SENTENCIA
           stmt.close();	
          
           //statement.executeUpdate();
           System.out.println("Consulta insertada correctamente.");
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
   
   
   public void insertarDatosTablaSensor(String sensor, String tipo, int valor, String correo) {
   	try (Connection connection = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
           String sql = "INSERT INTO sensor (sensor, tipo, valor, correo) VALUES (?, ?, ?, ?)";
           PreparedStatement statement = connection.prepareStatement(sql);
           statement.setString(1, sensor);
           statement.setString(2, tipo);
           statement.setInt(3, valor);
           statement.setString(4, correo);

           statement.executeUpdate();
           
       } catch (SQLException e) {
           
       }
   }
   
   
   public String dameRelacionDeportistaEntrenador(String id_deportista) {
		try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
			
          String sql = "SELECT id_entrenador FROM EntrenadorDeportista WHERE id_deportista = ?";
          PreparedStatement statement = conn.prepareStatement(sql);
          statement.setString(1, id_deportista);

          ResultSet resultSet = statement.executeQuery();
          if (resultSet.next()) {
        	  return resultSet.getString("id_entrenador"); 
          } 
      } catch (SQLException e) {
          e.printStackTrace();
      }
		return "No tienes un entrenador";   
   }
   
   public String dameRelacionDeportistaMedico(String id_deportista) {
		try (Connection conn = DriverManager.getConnection("jdbc:mariadb://195.235.211.197/priperfort", "pri_perfort", "yinorivja")) {
			
         String sql = "SELECT id_medico FROM MedicoDeportista WHERE id_deportista = ?";
         PreparedStatement statement = conn.prepareStatement(sql);
         statement.setString(1, id_deportista);

         ResultSet resultSet = statement.executeQuery();
         if (resultSet.next()) {
       	  return resultSet.getString("id_medico"); 
         } 
     } catch (SQLException e) {
         e.printStackTrace();
     }
		return "No tienes un medico";   
  }
   
   
   
    
    
//    public void verDeportistasDeLosEntrenadores(String id_entrenador) {
//    	try (Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1/NombreBD_", "Alberto", "1234")) {
//            String sql = "SELECT id_deportista FROM usuarios WHERE Correo = ?";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setString(1, id_entrenador);
//
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }   
//    }
    
}
    


    
    

   
	
	

