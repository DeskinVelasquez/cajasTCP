/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

import com.cajatcp.Utils.Alerts;
import static com.cajatcp.Utils.Comunication.ComunicationTools.comando;
import static com.cajatcp.Utils.Comunication.ComunicationTools.listMsgInput;
import com.cajatcp.Utils.Constans;
import com.cajatcp.Utils.Util;
import com.cajatcp.view.JPanelPrincipal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author DESKIN
 */
public class ComunicationTG extends ComunicationTools {
    
    private String telefono;
    
    public ComunicationTG(JPanelPrincipal panel, String telefono) {
        super(panel);
        super.setMonto();
        this.telefono = telefono;
        listMsgOutput = new ArrayList<>();
        listMsgInput = new ArrayList<>();
    }

    public  void iniciarProceso() {
        try {
            super.iniciarProceso(Constans.SOLICITUD_CONEXION_TIGOMONEY);
        } catch (InterruptedException ex) {
            Logger.getLogger(ComunicationICC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte[] armarTramaVariable(String tipo) {
        byte[] retorno;
        
        switch (tipo) {
            case Constans.TRANSACCION_ENVIO_DATOS:
                retorno = armarEnvioDatosTG();
                break;
            default:
                retorno = null;
                System.out.println("Error switch comunicationCTL-armarTramaVariable");
                
        }
        return retorno;
   }

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
                comando++;//el envio del dispositivo al host
                comando++; //el envio del host al dispositivo
                needReceived = true;
                break;
            case 10:
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = false;
                break;
            case 11:
                lastMsg = true;
                break;
            default:
                Alerts.alert(true, "Error en la comunicación", 2);
                lastMsg = true;

        }
   }

    private byte[] armarEnvioDatosTG() {
        
         String montoPadleft = Util.padleft(monto + "", 12, '0');
        
         int dinamycSize = 0;
         
        byte[] frame = new byte[50];
        byte[] campoMonto = ComunicationTools.crearCampo(montoPadleft, 40, 12);
        System.arraycopy(campoMonto, 0, frame, dinamycSize, campoMonto.length);
        dinamycSize+= campoMonto.length;
        
         byte[] codigoResp = ComunicationTools.hacerCodigoRespuesta(true);
        System.arraycopy(codigoResp, 0, frame, dinamycSize, codigoResp.length);
        dinamycSize+= codigoResp.length;
        
        byte[] campoTel = ComunicationTools.crearCampo(telefono, 57, 8);
        System.arraycopy(campoTel, 0, frame, dinamycSize, campoTel.length);
        dinamycSize+= campoTel.length;
        
        byte[] etx = {Constans.ETX};
        System.arraycopy(etx, 0, frame, dinamycSize, etx.length);
        
         byte[] frame2 = ComunicationTools.dicardEmpty(frame);
         byte[] retorno = new byte[frame2.length-2];
         for (int i = 0; i < frame2.length-2; i++) {
            retorno[i] = frame2[i];
        }
         
        return retorno;
    }
    
    public static boolean validatePhone(String phone){
        
        String tel = "+591" + phone;
        
        // Expresión regular para un número de teléfono boliviano
        String regex = "\\+591\\d{8,9}|0\\d{7,8}";

        // Compilar la expresión regular en un objeto Pattern
        Pattern pattern = Pattern.compile(regex);

        // Crear un objeto Matcher con la cadena proporcionada
        Matcher matcher = pattern.matcher(tel);

        // Realizar la validación
        return matcher.matches();
        
    }
    
}
