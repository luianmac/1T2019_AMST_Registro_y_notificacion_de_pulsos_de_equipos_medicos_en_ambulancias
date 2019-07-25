/**
  Generated Pin Manager File

  Company:
    Microchip Technology Inc.

  File Name:
    pin_manager.c

  Summary:
    This is the Pin Manager file generated using PIC10 / PIC12 / PIC16 / PIC18 MCUs

  Description:
    This header file provides implementations for pin APIs for all pins selected in the GUI.
    Generation Information :
        Product Revision  :  PIC10 / PIC12 / PIC16 / PIC18 MCUs - 1.76
        Device            :  PIC16F18875
        Driver Version    :  2.11
    The generated drivers are tested against the following:
        Compiler          :  XC8 2.00
        MPLAB             :  MPLAB X 5.10

    Copyright (c) 2013 - 2015 released Microchip Technology Inc.  All rights reserved.
*/

/*
    (c) 2018 Microchip Technology Inc. and its subsidiaries. 
    
    Subject to your compliance with these terms, you may use Microchip software and any 
    derivatives exclusively with Microchip products. It is your responsibility to comply with third party 
    license terms applicable to your use of third party software (including open source software) that 
    may accompany Microchip software.
    
    THIS SOFTWARE IS SUPPLIED BY MICROCHIP "AS IS". NO WARRANTIES, WHETHER 
    EXPRESS, IMPLIED OR STATUTORY, APPLY TO THIS SOFTWARE, INCLUDING ANY 
    IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY, AND FITNESS 
    FOR A PARTICULAR PURPOSE.
    
    IN NO EVENT WILL MICROCHIP BE LIABLE FOR ANY INDIRECT, SPECIAL, PUNITIVE, 
    INCIDENTAL OR CONSEQUENTIAL LOSS, DAMAGE, COST OR EXPENSE OF ANY KIND 
    WHATSOEVER RELATED TO THE SOFTWARE, HOWEVER CAUSED, EVEN IF MICROCHIP 
    HAS BEEN ADVISED OF THE POSSIBILITY OR THE DAMAGES ARE FORESEEABLE. TO 
    THE FULLEST EXTENT ALLOWED BY LAW, MICROCHIP'S TOTAL LIABILITY ON ALL 
    CLAIMS IN ANY WAY RELATED TO THIS SOFTWARE WILL NOT EXCEED THE AMOUNT 
    OF FEES, IF ANY, THAT YOU HAVE PAID DIRECTLY TO MICROCHIP FOR THIS 
    SOFTWARE.
*/

#include "pin_manager.h"
#include "tmr0.h"
#include "mcc.h"





void PIN_MANAGER_Initialize(void)
{
    /**
    LATx registers
    */
    LATE = 0x00;
    LATD = 0x00;
    LATA = 0x00;
    LATB = 0x00;
    LATC = 0x00;

    /**
    TRISx registers
    */
    TRISE = 0x07;
    TRISA = 0xFB;
    TRISB = 0xFF;
    TRISC = 0xBF;
    TRISD = 0xFF;

    /**
    ANSELx registers
    */
    ANSELD = 0xFF;
    ANSELC = 0x3F;
    ANSELB = 0x7E;
    ANSELE = 0x07;
    ANSELA = 0x7B;

    /**
    WPUx registers
    */
    WPUD = 0x00;
    WPUE = 0x00;
    WPUB = 0x01;
    WPUA = 0x18;
    WPUC = 0x00;

    /**
    ODx registers
    */
    ODCONE = 0x00;
    ODCONA = 0x00;
    ODCONB = 0x00;
    ODCONC = 0x00;
    ODCOND = 0x00;

    /**
    SLRCONx registers
    */
    SLRCONA = 0xFF;
    SLRCONB = 0xFF;
    SLRCONC = 0xFF;
    SLRCOND = 0xFF;
    SLRCONE = 0x07;





   
    
	
    INTPPS = 0x08;   //RB0->EXT_INT:INT;    
    RXPPS = 0x17;   //RC7->EUSART:RX;    
    RC6PPS = 0x10;   //RC6->EUSART:TX;    
}
  
void PIN_MANAGER_IOC(void)
{   
    }

/**
 * Clasificara entre las senales disponibles
 */
int obtenerTipoPulso(int *contDePulsos){
        int tipoDeSenal=-1;
        if(*contDePulsos == SIN_SENAL){
            tipoDeSenal = 0;
            return tipoDeSenal;
        }
        else if(*contDePulsos >= (PRESION_ARTERIAL_BAJA-1) && *contDePulsos <= (PRESION_ARTERIAL_BAJA+1)){
            tipoDeSenal = 1;
            return tipoDeSenal;
        }
        else if(*contDePulsos >= (PARO_CARDIACO-1) && *contDePulsos <= (PARO_CARDIACO+1)){
            tipoDeSenal = 2;
            return tipoDeSenal;
        }
        else if(*contDePulsos >= (ARRITMIA-1) && *contDePulsos <= (ARRITMIA+1)){
            tipoDeSenal = 3;
            return tipoDeSenal;
        }
        else if(*contDePulsos >= (HIPERPIREXIA-1) && *contDePulsos <= (HIPERPIREXIA+1)){
            tipoDeSenal = 4;
            return tipoDeSenal;
        }
        else if(*contDePulsos >= (PRESION_ARTERIAL_ALTA-2) && *contDePulsos <= (PRESION_ARTERIAL_ALTA+2)){
            tipoDeSenal = 5;
            return tipoDeSenal;
        }
        else{
            tipoDeSenal = 6;
            return tipoDeSenal;
        }
            
    
}

/**
 End of File
*/