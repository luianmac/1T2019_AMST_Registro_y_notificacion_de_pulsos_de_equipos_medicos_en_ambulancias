int FREQUENCIA=50;//frecuencia 
int Salida = 11; // pin de salida
void setup() {
  pinMode(Salida,OUTPUT);
}

void loop() {   
    for(int i = 0; i <FREQUENCIA; i++){
          digitalWrite(Salida, HIGH);
          delayMicroseconds(1000000L/(2*FREQUENCIA));//delay necesario para cada frecuencia de pulsos
          digitalWrite(Salida, LOW);
          delayMicroseconds(1000000L/(2*FREQUENCIA));
    }
  
  
}
