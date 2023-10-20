/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

import com.cajatcp.Utils.Alerts;
import static com.cajatcp.Utils.Comunication.ComunicationTools.comando;
import static com.cajatcp.Utils.Comunication.ComunicationTools.listMsgInput;
import com.cajatcp.Utils.Constans;
import com.cajatcp.view.JPanelPrincipal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deskin dev
 */
public class ComunicationInit extends ComunicationTools{

    

    public ComunicationInit(JPanelPrincipal panel) {
        super(panel);
        listMsgOutput = new ArrayList<>();
        listMsgInput = new ArrayList<>();
    }
    
    public  void iniciarProceso() {
        try {
            super.iniciarProceso(Constans.SOLICITUD_INIT);
        } catch (InterruptedException ex) {
            Logger.getLogger(ComunicationICC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public byte[] armarTramaVariable(String tipo) {
        return null;
    }

    @Override
    public void mensajeria() {
         
        msgSend = "";
        
        
          switch (comando + 1) {
            case 3: //comando 3
                comando++; //el envio del dispositivo al host
                comando++; //el envio del host al dispositivo
                //se prepara el comando 5
                needSend = false; 
                needReceived = true;
                break;
            case 6: //comando 4
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = false;
                break;
            case 7:
                lastMsg = true;
                break;
            default:
                Alerts.alert(true, "Error en la comunicación", 2);
                lastMsg = true;    
        }
   }
    
}
