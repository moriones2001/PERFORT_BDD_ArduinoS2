const unsigned long interval = 15000;  // Intervalo de tiempo en milisegundos (30 segundos)
const unsigned long sampleInterval = 2;  // Intervalo de muestreo del sensor en milisegundos (5 ms)
unsigned long previousMillis = 0;
unsigned long sampleMillis = 0;
int maxSensorValue = 0;
int currentSensorValue = 0;
const int ECG_PIN = 34;
const int LO_NEG_PIN = 32;
const int LO_POS_PIN = 33;
#if ! (ESP8266 || ESP32 )
  #error This code is intended to run on the ESP8266/ESP32 platform! Please check your Tools->Board setting
#endif

#include "Credentials.h"
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


uint16_t server_port = 3306;    //3306;

char default_database[] = "priperfort";           //"test_arduino";
char default_table[]    = "sensor";          //"test_arduino";

String sensor = "AD8232";
String correo = "rafanadal@tenis.com";
String tipo = "Electro";
int valor = 0;
String INSERT_SQLEcg = "";

MySQL_Connection conn((Client *)&client);

MySQL_Query *query_mem;

void setup() {
  Serial.begin(115200);
  // Configurar el sensor AD8232, si es necesario
  pinMode(ECG_PIN, INPUT);
  pinMode(LO_NEG_PIN, INPUT);
  pinMode(LO_POS_PIN, INPUT);
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
void runInsert(int valor)
{
   String INSERT_SQL_Ecg = String("INSERT INTO ") + default_database + "." + default_table 
                 + " (sensor, tipo, valor,correo) VALUES ('"+ sensor +"', '" + tipo + "', '" + valor + "', '" + correo + "');";
  MySQL_Query query_mem = MySQL_Query(&conn);

  if (conn.connected())
  {
    MYSQL_DISPLAY(INSERT_SQLEcg);
    
    // Execute the query
    // KH, check if valid before fetching
    if ( !query_mem.execute(INSERT_SQL_Ecg.c_str()) )
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
  


void loop() {
  unsigned long currentMillis = millis();

  // Realizar una lectura del sensor cada 'sampleInterval' milisegundos
  if (currentMillis - sampleMillis >= sampleInterval) {
    sampleMillis = currentMillis;
    
    // Lectura del valor del sensor AD8232
    currentSensorValue = analogRead(ECG_PIN);  // Ajusta el pin según cómo esté conectado el sensor
    
    if (currentSensorValue > maxSensorValue) {
      maxSensorValue = currentSensorValue;
    }
  }

  // Calcular el valor máximo cada 'interval' milisegundos
  if (currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;
    
    Serial.print("Valor máximo del sensor AD8232 en los últimos 15 segundos: ");
    Serial.println(maxSensorValue);
    valor=maxSensorValue;
    MYSQL_DISPLAY("Connecting...");
  
  //if (conn.connect(server, server_port, user, password))
  if (conn.connectNonBlocking(server, server_port, user, password) != RESULT_FAIL)
  {
    delay(500);
    // Query ECG
    if(valor<=190 && valor>=50){ //VALOR ENTRE 50 Y 190 PARA Q SEA REAL
    runInsert(valor);
    conn.close();                     // close the connection
    }
  }
  else 
  {
    MYSQL_DISPLAY("\nConnect failed. Trying again on next iteration.");
  }

  MYSQL_DISPLAY("\nSleeping...");
  MYSQL_DISPLAY("================================================");
 
  delay(15000);
 
    // Reiniciar el valor máximo
    maxSensorValue = 0;
  }
}
