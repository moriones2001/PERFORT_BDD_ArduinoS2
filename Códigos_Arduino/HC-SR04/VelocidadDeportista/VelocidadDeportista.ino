#include <NewPing.h>
#include "Credentials.h"

#if ! (ESP8266 || ESP32 )
  #error This code is intended to run on the ESP8266/ESP32 platform! Please check your Tools->Board setting
#endif


#define MYSQL_DEBUG_PORT      Serial

// Debug Level from 0 to 4
#define _MYSQL_LOGLEVEL_      1

#include <MySQL_Generic.h>

#define USING_HOST_NAME     false

#if USING_HOST_NAME
  // Optional using hostname, and Ethernet built-in DNS lookup
  char server[] = "your_account.ddns.net"; // change to your server's hostname/URL
#else
  IPAddress server(195, 235, 211, 197);
#endif
// Pines de los sensores HC-SR04
const int TRIG_PIN1 = 2;  // Reemplaza X con el número del pin Trig del primer sensor
const int ECHO_PIN1 = 18;  // Reemplaza Y con el número del pin Echo del primer sensor
const int TRIG_PIN2 = 4;  // Reemplaza Z con el número del pin Trig del segundo sensor
const int ECHO_PIN2 = 5;  // Reemplaza W con el número del pin Echo del segundo sensor




uint16_t server_port = 3306;    //3306;

char default_database[] = "priperfort";           //"test_arduino";
char default_table[]    = "sensor";          //"test_arduino";

String sensor = "HC-SR04";
String correo = "jaime@gmail.com";
String tipo = "Velocidad (km/h)";
float valor = 0;
String INSERT_SQLVelocidad = "";
unsigned long startTime = 0;
unsigned long endTime = 0;
unsigned long elapsedTime = 0;
float speed = 0.0;
float speedkm_h = 0.0;

MySQL_Connection conn((Client *)&client);

MySQL_Query *query_mem;
NewPing sonar1(TRIG_PIN1, ECHO_PIN1);
NewPing sonar2(TRIG_PIN2, ECHO_PIN2);

void setup() {
  Serial.begin(9600);
  // Configuraciones iniciales, si las necesitas
   while (!Serial && millis() < 5000); // wait for serial port to connect

  MYSQL_DISPLAY1("\nStarting Basic_Insert_ESP on", ARDUINO_BOARD);
  MYSQL_DISPLAY(MYSQL_MARIADB_GENERIC_VERSION);

  // Begin WiFi section
  MYSQL_DISPLAY1("Connecting to", ssid);
  
  WiFi.begin(ssid, pass);
  
  while (WiFi.status() != WL_CONNECTED) 
  {
    delay(500);
    MYSQL_DISPLAY0(".");
  }

  // print out info about the connection:
  MYSQL_DISPLAY1("Connected to network. My IP address is:", WiFi.localIP());

  MYSQL_DISPLAY3("Connecting to SQL Server @", server, ", Port =", server_port);
  MYSQL_DISPLAY5("User =", user, ", PW =", password, ", DB =", default_database);
}

  void runInsert(float valor){
    String INSERT_SQLVelocidad = String("INSERT INTO ") + default_database + "." + default_table 
                 + " (sensor, tipo, valor,correo) VALUES ('"+ sensor +"', '" + tipo + "', '" + valor + "', '" + correo + "');";
  MySQL_Query query_mem = MySQL_Query(&conn);

  if (conn.connected())
  {
    MYSQL_DISPLAY(INSERT_SQLVelocidad);
    
    // Execute the query
    // KH, check if valid before fetching
    if ( !query_mem.execute(INSERT_SQLVelocidad.c_str()) )
    {
      MYSQL_DISPLAY("Insert error");
    }
    else
    {
      MYSQL_DISPLAY("Data Inserted.");
    }
  }
  else
  {
    MYSQL_DISPLAY("Disconnected from Server. Can't insert.");
  }
  }

    float calculateSpeed(unsigned long elapsedTime) {
  // Distancia entre los sensores en metros
  float distance = 0.30;

  // Convierte el tiempo transcurrido a segundos
  float elapsedTimeSec = elapsedTime / 1000000.0;

  // Calcula la velocidad en metros por segundo (m/s)
  float speed = distance / elapsedTimeSec;
  return speed;
  
}
void loop() {
  // Espera a que se detecte un objeto a menos de 10 cm en el primer sensor
  while (sonar1.ping_cm() > 10) {
    delay(10);
  }

  // Inicia el tiempo cuando se detecta el objeto en el primer sensor
  startTime = micros();

  // Espera a que el objeto pase al segundo sensor
  while (sonar2.ping_cm() > 10) {
    delay(10);
  }

  // Detiene el tiempo cuando se detecta el objeto en el segundo sensor
  endTime = micros();

  // Calcula el tiempo transcurrido en microsegundos
  elapsedTime = endTime - startTime;

  // Calcula la velocidad en metros por segundo (m/s)
  speed = calculateSpeed(elapsedTime);
  speedkm_h = speed*3.6;

  Serial.print("Velocidad: ");
  Serial.print(speed);
  Serial.println(" km/h");
  valor = speedkm_h;

  // Realiza otras acciones o espera un tiempo antes de la siguiente medición
  delay(1000);


  MYSQL_DISPLAY("Connecting...");
  
  //if (conn.connect(server, server_port, user, password))
  if (conn.connectNonBlocking(server, server_port, user, password) != RESULT_FAIL)
  {
    delay(500);
    // Query Temperatura y Humedad
    runInsert(valor);
    conn.close();                     // close the connection
  } 
  else 
  {
    MYSQL_DISPLAY("\nConnect failed. Trying again on next iteration.");
  }

  MYSQL_DISPLAY("\nSleeping...");
  MYSQL_DISPLAY("================================================");
 
  delay(500);
}
