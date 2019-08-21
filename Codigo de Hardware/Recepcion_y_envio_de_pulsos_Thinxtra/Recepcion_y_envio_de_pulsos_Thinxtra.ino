// Include librairies
#include <SoftwareSerial.h>
#include <WISOL.h>
#include <Wire.h>
#include <math.h>

const int PRESION_ARTERIAL_ALTA  = 5;
const int PRESION_ARTERIAL_BAJA =  1;
const int ARRITMIA  =      3;
const int PARO_CARDIACO =  2;
const int HIPERPIREXIA  =  4;
const int SIN_SENAL     =  0;

float v1 = 4.98; // valor real de la alimentacion de Arduino, Vcc
float r1 = 200; //200ohm 
float r2 = 1000; // 1K
int porcentaje=0;

int TipoDePulso;
const uint8_t payloadSize = 4;
uint8_t buf_str[payloadSize];
SoftwareSerial PIC ( 10 , 11 ) ; // RX, TX 
Isigfox *Isigfox = new WISOL();

void setup(){
  //inicia el puerto serial
  Wire.begin();
  Wire.setClock(100000);
  // Init serial connection between Arduino and Modem
  Serial.begin(9600);
  PIC.begin(9600);//inicio de puerto serial entre PIC y thinxtra
  Isigfox->initSigfox();
  Isigfox->testComms(); 
  
  }
void loop(){
  float v = (analogRead(0) * v1) / 1024.0;
  float voltajeReal = v / (r2 / (r1 + r2));
  porcentaje=(voltajeReal/6)*100;  
  while (PIC.available()>0){
    //leemos la opcion enviada desde el PIC
    TipoDePulso=PIC.read();
    Serial.println(TipoDePulso);
  
    switch (TipoDePulso) {//selecciona senal 
      case SIN_SENAL:
      {
        addSinSenal(); 
        enviarDatos(buf_str, payloadSize);
        delay(20000);
        break;
      }
      case 1:
      {
        addPresionArterialBaja();             
        enviarDatos(buf_str, payloadSize);
        
        delay(20000);
      break;
      }
      case 2:
      {
        addParoCardiaco();
        enviarDatos(buf_str, payloadSize);
        delay(20000);
        break;
      }
      case 3:
      {
        addArritmia();     
        enviarDatos(buf_str, payloadSize);
        delay(20000);
        break;
      }
      case 4:
      {
        addHiperpirexia();             
        enviarDatos(buf_str, payloadSize);
        delay(20000);
        break;
      }
      case 5:
      {
        addPresionArterialAlta();           
        enviarDatos(buf_str, payloadSize);
        delay(20000);
        break;
      }
      default:
      {
        addSenalDesconocida(); 
        enviarDatos(buf_str, payloadSize);
        delay(20000);
        break;
      }
    } 
  }
  delay(1000); 
}
  
// SendPayload Function => Send messages to the Sigfox Network
void enviarDatos(uint8_t *sendData, int len) {
  recvMsg *RecvMsg;
  RecvMsg = (recvMsg *)malloc(sizeof(recvMsg));
  Isigfox->sendPayload(sendData, len, 0, RecvMsg);
  for (int i = 0; i < RecvMsg->len; i++) {
    Serial.print(RecvMsg->inData[i]);
  }
  Serial.println("");
  free(RecvMsg);
}
//funciones que agregan a la trama la data de los pulsos respectivamente
void addSinSenal(void){
  buf_str[0] = 4;
  buf_str[1] = 555/256;
  buf_str[2] = 555%256; 
  buf_str[3] = porcentaje;
}
void addPresionArterialBaja(void){
  buf_str[0] = 4;
  buf_str[1] = 550/256; 
  buf_str[2] = 550%256; 
  buf_str[3] = porcentaje;
}
void addPresionArterialAlta(void){
  buf_str[0] = 4;
  buf_str[1] = 554/256; 
  buf_str[2] = 554%256;
  buf_str[3] = porcentaje;
}
void addParoCardiaco(void){
  buf_str[0] = 4;
  buf_str[1] = 551/256; 
  buf_str[2] = 551%256;
  buf_str[3] = porcentaje;
 }
void addArritmia(void){
 buf_str[0] = 4; 
 buf_str[1] = 552/256;
 buf_str[2] = 552%256; 
 buf_str[3] = porcentaje;
}
void addHiperpirexia(void){
  buf_str[0] = 4; 
  buf_str[1] = 553/256;
  buf_str[2] = 553%256;
  buf_str[3] = porcentaje;
}
void addSenalDesconocida(void){
  buf_str[0] = 4; 
  buf_str[1] = 556/256;
  buf_str[2] = 556%256; 
  buf_str[3] = porcentaje;
}
