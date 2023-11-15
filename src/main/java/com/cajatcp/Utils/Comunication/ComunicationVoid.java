/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

import DataManager.DataClasses.Trx;
import com.cajatcp.Utils.Alerts;
import static com.cajatcp.Utils.Comunication.ComunicationTools.buscarDato;
import static com.cajatcp.Utils.Comunication.ComunicationTools.comando;
import static com.cajatcp.Utils.Comunication.ComunicationTools.listMsgInput;
import com.cajatcp.Utils.Constans;
import com.cajatcp.Utils.Util;
import com.cajatcp.view.JPanelPrincipal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deskin dev
 */
public class ComunicationVoid extends ComunicationTools{
    private JPanelPrincipal panel;
    private int numRef;

    public ComunicationVoid(JPanelPrincipal panel, int numRef) {
        super(panel);
        this.panel = panel;
        this.numRef = numRef;
        listMsgOutput = new ArrayList<>();
        listMsgInput = new ArrayList<>();
    }
    
    public  void iniciarProceso() {
        try {
            super.iniciarProceso(Constans.SOLICITUD_ANULACION);
        } catch (InterruptedException ex) {
            Logger.getLogger(ComunicationICC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public byte[] armarTramaVariable(String tipo) {
        byte[] retorno;
        
        switch (tipo) {
            case Constans.REFERENCIA_TRANSACCION_ANULACION:
                retorno = tramaRefVoid();
                break;
            case Constans.CONFIRMACION_ANULACION:
                retorno = tramaRefSuccess();
                break;
            default:
                retorno = null;
                System.out.println("Error switch comunicationICC-armarTramaVariable");
                
        }
        
        return retorno;
    }

    @Override
    public void mensajeria() {
        
        msgSend = "";
        
          switch (comando + 1) {
            case 3: 
                needSend = false; 
                needReceived = true;
                break;
            case 4: 
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = false;
                break;
            case 5: 
                msgSend = Constans.REFERENCIA_TRANSACCION_ANULACION;
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
                needReceived = false;
                break;
            case 9:
                msgSend = Constans.CONFIRMACION_ANULACION;
                needSend = true;
                needReceived = true;
                comando++; //el envio del dispositivo al host
                comando++; //el envio del host al dispositivo
                break;
            case 13:
                needSend = false;
                needReceived = true;
                break;
            case 14:
                desempaquetarDatosVoid(msgComplete);
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = false;
                break;
            case 15:
                lastMsg = true;
                break;
            default:
                Alerts.alert(true, "Error en la comunicación", 2);
                lastMsg = true;
                break;
        }
   }

    private byte[] tramaRefVoid() {
        String refPadleft = Util.padleft(numRef + "", 6, '0');
        int dinamicLength = 0;
        byte[] frame = new byte[100];
        
        byte[] noRef = ComunicationTools.crearCampo(refPadleft, 43, 6);//10
        System.arraycopy(noRef, 0, frame, dinamicLength, noRef.length);
        dinamicLength += noRef.length;

        byte[] codigoResp = ComunicationTools.hacerCodigoRespuesta(true);
        System.arraycopy(codigoResp, 0, frame, dinamicLength, codigoResp.length);
        dinamicLength += codigoResp.length;

        byte[] etx = {Constans.ETX};
        System.arraycopy(etx, 0, frame, dinamicLength, etx.length);

        byte[] frame2 = ComunicationTools.dicardEmpty(frame);
        byte[] retorno = new byte[frame2.length - 2];
        for (int i = 0; i < frame2.length - 2; i++) {
            retorno[i] = frame2[i];
        }

        return retorno;
    }

    private byte[] tramaRefSuccess() {
        byte retorno[] = new byte[7];
        retorno[0] = Constans.SEPARADOR;
        //Nombre del campo
        retorno[1] = 0x34;
        retorno[2] = 0x38;
        //Longitud
        retorno[3] = 0x00;
        retorno[4] = 0x02;
        //campo
        retorno[5] = 0x30;
        retorno[6] = 0x30;

        return retorno;
    }

    private void desempaquetarDatosVoid(ArrayList<String> mensaje) {
    try {
            String codigoAut = Util.conversorAString(buscarDato(mensaje, 01));
            String montoString = Util.conversorAString(buscarDato(mensaje, 40));
            String numRecibo = Util.conversorAString(buscarDato(mensaje, 43));
            String RRN = Util.conversorAString(buscarDato(mensaje, 44));
            String tid = Util.conversorAString(buscarDato(mensaje, 45));
            String dateTXR = Util.conversorAString(buscarDato(mensaje, 46));
            String timeTXR = Util.conversorAString(buscarDato(mensaje, 47));
            String codRsp = Util.conversorAString(buscarDato(mensaje, 48));
            String last4Digits = Util.conversorAString(buscarDato(mensaje, 54));
            String msgError = Util.conversorAString(buscarDato(mensaje, 61));
            String binTarget = Util.conversorAString(buscarDato(mensaje, 75));
            

            Trx trx = new Trx(codigoAut, montoString, numRecibo, RRN, tid, dateTXR, timeTXR, codRsp, "NA", "NA", last4Digits, msgError);
            
            panel.rspBox(panel.dataTrx(trx));
        } catch (Exception e) {
            System.out.println("Error al desempaquetar los datos ");
        }
    }
}
