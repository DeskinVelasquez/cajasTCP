/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

import com.cajatcp.Utils.Alerts;
import com.cajatcp.Utils.Constans;
import com.cajatcp.view.JPanelPrincipal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Deskin
 */
public class ComunicationICC extends ComunicationTools {
    
    public ComunicationICC(JPanelPrincipal panel) {
        super(panel);
        super.setMonto();
        listMsgOutput = new ArrayList<>();
        listMsgInput = new ArrayList<>();
    }
    
    public void iniciarProceso() {
        try {
            super.iniciarProceso(Constans.SOLICITUD_CONEXION);
        } catch (InterruptedException ex) {
            Logger.getLogger(ComunicationICC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * termina con el valor del comando anterior (por ello se le suma una unidad),
     * es decir, para preparar el siguiente comando
     */
    @Override
    public void mensajeria() {
        
        msgSend = "";
        
          switch (comando + 1) {
            case 3: //comando 3
                needSend = false;
                needReceived = true;
                break;
            case 4: //comando 4
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = false;
                break;
            case 5: //comando 5
                msgSend = Constans.TRANS_REV_No;
                needSend = true;
                needReceived = true;
                break;
            case 7:
                needSend = false;
                needReceived = true;
                break;
            case 8:
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = true;
                break;
            case 10:
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = false;
                break;
            case 11:
                msgSend = Constans.TRANSACCION_ENVIO_DATOS;
                needSend = true;
                needReceived = true;
                break;
            case 13:
                needSend = false;
                needReceived = true;
                break;
            case 14:
                msgSend = Constans.ACK_STRING;
                needSend = true;
                comando++; //el envio del dispositivo al host
                comando++; //el envio del host al dispositivo
                needReceived = true;
                break;
            case 18:
                desempaquetarDatos(msgComplete, true);
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = false;
                break;
            case 19:
                lastMsg = true;
                break;
            default:
                Alerts.alert(true, "Error en la comunicación", 2);
                lastMsg = true;
                
        }
    }
    
    @Override
    public byte[] armarTramaVariable(String tipo) {
        byte[] retorno;
        
        switch (tipo) {
            case Constans.TRANS_REV_No:
                retorno = armarTrama48XX();
                break;
            case Constans.TRANSACCION_ENVIO_DATOS:
                retorno = tramaEnvioDatos();
                break;
            default:
                retorno = null;
                System.out.println("Error switch comunicationICC-armarTramaVariable");
                
        }
        
        return retorno;
    }
}