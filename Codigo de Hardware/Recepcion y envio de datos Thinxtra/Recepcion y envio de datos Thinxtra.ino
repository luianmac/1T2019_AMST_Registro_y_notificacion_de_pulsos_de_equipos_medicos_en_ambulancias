
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

String sin_senal = "SSE";
String presion_arterial_baja = "PAB";
String presion_arterial_alta = "PAA";
String paro_cardiaco = "PCA";
String arritmia = "ARR";
String hiperpirexia = "HIP";
String senal_desconocida = "SED";
String descripcion = "NNN";

//unsigned char TipoDePulso;
int TipoDePulso;
const uint8_t payloadSize = 8;
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
  while (PIC.available()>0){
    //leemos la opcion enviada
    TipoDePulso=PIC.read();
    Serial.println(TipoDePulso);
  
    switch (TipoDePulso) {
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
void addSinSenal(void){
  buf_str[0] = sin_senal.charAt(0);//convierte a ASCCI
  buf_str[1] = sin_senal.charAt(1);
  buf_str[2] = sin_senal.charAt(2);
  buf_str[3] = 0/256; 
  buf_str[4] = 0%256;    
  buf_str[5] = descripcion.charAt(0);//convierte a ASCCI
  buf_str[6] = descripcion.charAt(1);
  buf_str[7] = descripcion.charAt(2);
}
void addPresionArterialBaja(void){
  buf_str[0] = presion_arterial_baja.charAt(0);//convierte a ASCCI
  buf_str[1] = presion_arterial_baja.charAt(1);
  buf_str[2] = presion_arterial_baja.charAt(2);
  buf_str[3] = 50/256; 
  buf_str[4] = 50%256;
  buf_str[5] = descripcion.charAt(0);//convierte a ASCCI
  buf_str[6] = descripcion.charAt(1);
  buf_str[7] = descripcion.charAt(2);  
}
void addPresionArterialAlta(void){
  buf_str[0] = presion_arterial_alta.charAt(0);//convierte a ASCCI
  buf_str[1] = presion_arterial_alta.charAt(1);
  buf_str[2] = presion_arterial_alta.charAt(2);
  buf_str[3] = 200/256; 
  buf_str[4] = 200%256;
  buf_str[5] = descripcion.charAt(0);//convierte a ASCCI
  buf_str[6] = descripcion.charAt(1);
  buf_str[7] = descripcion.charAt(2);  
}
void addParoCardiaco(void){
  buf_str[0] = paro_cardiaco.charAt(0);//convierte a ASCCI
  buf_str[1] = paro_cardiaco.charAt(1);
  buf_str[2] = paro_cardiaco.charAt(2);
  buf_str[3] = 75/256; 
  buf_str[4] = 75%256;
  buf_str[5] = descripcion.charAt(0);//convierte a ASCCI
  buf_str[6] = descripcion.charAt(1);
  buf_str[7] = descripcion.charAt(2);  
}
void addArritmia(void){
 buf_str[0] = arritmia.charAt(0);//convierte a ASCCI
 buf_str[1] = arritmia.charAt(1);
 buf_str[2] = arritmia.charAt(2);
 buf_str[3] = 100/256;
 buf_str[4] = 100%256;
 buf_str[5] = arritmia.charAt(0);//convierte a ASCCI
 buf_str[6] = arritmia.charAt(1);
 buf_str[7] = arritmia.charAt(2); 
}
void addHiperpirexia(void){
  buf_str[0] = hiperpirexia.charAt(0);//convierte a ASCCI
  buf_str[1] = hiperpirexia.charAt(1);
  buf_str[2] = hiperpirexia.charAt(2);
  buf_str[3] = 127/256; //LIMITE 128 
  buf_str[4] = 127%256;
  buf_str[5] = descripcion.charAt(0);//convierte a ASCCI
  buf_str[6] = descripcion.charAt(1);
  buf_str[7] = descripcion.charAt(2);  
}
void addSenalDesconocida(void){
  buf_str[0] = senal_desconocida.charAt(0);//convierte a ASCCI
  buf_str[1] = senal_desconocida.charAt(1);
  buf_str[2] = senal_desconocida.charAt(2);
  buf_str[3] = 300/256;
  buf_str[4] = 300%256; 
  buf_str[5] = descripcion.charAt(0);//convierte a ASCCI
  buf_str[6] = descripcion.charAt(1);
  buf_str[7] = descripcion.charAt(2);  
}
