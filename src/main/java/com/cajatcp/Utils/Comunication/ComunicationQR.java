/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

import com.cajatcp.Utils.Alerts;
import com.cajatcp.Utils.Constans;
import com.cajatcp.Utils.Util;
import com.cajatcp.view.JPanelPrincipal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deskin
 */
public class ComunicationQR extends ComunicationTools {
    
    public ComunicationQR(JPanelPrincipal panel) {
        super(panel);
        super.setMonto();
        listMsgOutput = new ArrayList<>();
        listMsgInput = new ArrayList<>();
    }

    public  void iniciarProceso() {
        try {
            super.iniciarProceso(Constans.SOLICITUD_CONEXION_QR);
        } catch (InterruptedException ex) {
            Logger.getLogger(ComunicationICC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * termina con el valor del comando anterior (por ello se le suma una
     * unidad), es decir, para preparar el siguiente comando
     */
    @Override
    public void mensajeria() {
        
        msgSend = "";
        System.out.println("Preparó comando: " + (comando + 1));

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
                msgSend = Constans.TRANSACCION_ENVIO_DATOS;
                needSend = true;
                needReceived = true;
                break;
            case 7:
                needSend = false;
                needReceived = true; //aqui va a recibir la referencia pendiente
                break;
            case 8:
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = true;
                break;
            case 10:
                needSend = false;
                needReceived = true;
                break;
            case 11:
                msgSend = Constans.ACK_STRING;
                needSend = true;
                comando++; //el envio del dispositivo al host
                comando++; //el envio del host al dispositivo
                needReceived = true; //aqui se recibe la respuesta del autorizador
                break;
            case 15:
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = false;
                break;
            case 16:
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
            case Constans.TRANSACCION_ENVIO_DATOS:
                retorno = armarEnvioDatos();
                break;
            default:
                retorno = null;
                System.out.println("Error switch comunicationCTL-armarTramaVariable");
                
        }
        return retorno;
    }
    
    private byte[] armarEnvioDatos() {
        
        String montoPadleft = Util.padleft(monto + "", 12, '0');

        byte[] frame = new byte[50];
        byte[] campoMonto = ComunicationTools.crearCampo(montoPadleft, 40, 12);
        System.arraycopy(campoMonto, 0, frame, 0, campoMonto.length);
        
         byte[] codigoResp = ComunicationTools.hacerCodigoRespuesta(true);
        System.arraycopy(codigoResp, 0, frame, campoMonto.length, codigoResp.length);
        
        byte[] etx = {Constans.ETX};
        System.arraycopy(etx, 0, frame, campoMonto.length+codigoResp.length, etx.length);
        
         byte[] frame2 = ComunicationTools.dicardEmpty(frame);
         byte[] retorno = new byte[frame2.length-2];
         for (int i = 0; i < frame2.length-2; i++) {
            retorno[i] = frame2[i];
        }
         
        return retorno;
    }
      
}
