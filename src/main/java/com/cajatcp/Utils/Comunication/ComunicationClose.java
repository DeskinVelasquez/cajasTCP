/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

import com.cajatcp.Utils.Alerts;
import static com.cajatcp.Utils.Comunication.ComunicationTools.comando;
import com.cajatcp.Utils.Constans;
import com.cajatcp.view.JPanelPrincipal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deskin dev
 */
public class ComunicationClose extends ComunicationTools{
    
    public static boolean isLastTrx;
    public static boolean noSwitch;
    public static int countTrx;
    
     public ComunicationClose(JPanelPrincipal panel) {
        super(panel);
        listMsgOutput = new ArrayList<>();
        listMsgInput = new ArrayList<>();
    }
    
    public void iniciarProceso() {
        try {
            super.iniciarProceso(Constans.SOLICITUD_CIERRE);
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

        if (noSwitch) {
            //finaliza la comunicacion 
            lastMsg = true;
            noSwitch = false;
            countTrx = 0;
            msgSend = Constans.ACK_STRING;
            enviarMsg(msgSend);

        } else {
            switch (comando + 1) {
                case 3: //comando 3
                    needSend = false;
                    needReceived = true;
                    break;
                case 4: //comando 4
                    noSwitch = true;
                    msgSend = Constans.ACK_STRING;
                    needSend = true;
                    needReceived = true;
                    for (int i = 0; i < countTrx; i++) {
                        enviarMsg(msgSend);
                        recibir();
                    }
                    break;
                default:
                    Alerts.alert(true, "Error en la comunicación", 2);
                    lastMsg = true;
                    noSwitch = false;
            }
        }
    }

}
