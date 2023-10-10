/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

import com.cajatcp.Utils.Alerts;
import static com.cajatcp.Utils.Comunication.ComunicationTools.buscarDato;
import com.cajatcp.Utils.Constans;
import com.cajatcp.Utils.Util;
import com.cajatcp.view.JPanelPrincipal;
import java.util.ArrayList;
/**
 *
 * @author Deskin
 */
public class ComunicationICC {
    private static String monto;
    private ComunicationTools ct;
    private JPanelPrincipal panel;
    public static boolean lastMsg;
    private static String msgSend;
    private static boolean needReceived;;
    private static boolean needSend;
    private static ArrayList<String> listMsgOutput;
    private static ArrayList<String> listMsgInput;
    private static int comando = 0;
    
    public ComunicationICC(String monto, JPanelPrincipal panel) {
        
        this.monto = monto;
        this.panel = panel;
        ct = new ComunicationTools();
        
        listMsgOutput = new ArrayList<>();
        listMsgInput = new ArrayList<>();
        
    }
    
    public void iniciarProceso() throws InterruptedException {

        msgSend = Constans.ASCII_SOLICITUD_CONEXION;
        needSend = true;
        needReceived = true;

        do {

            if (needSend) {
                enviarMsg(msgSend);
            }

            if (needReceived) {;
                recibir();
            }
            
            validaMsg();
            
        } while (!lastMsg);

    }

    /**
     * termina con el valor del comando anterior (por ello se le suma una unidad),
     * es decir, para preparar el siguiente comando
     */
    private void validaMsg() {
        
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
                throw new AssertionError();
                
        }
    }
    
    private void enviarMsg(String tipoMsg) {
        byte[] trama;
        switch (tipoMsg) {
            case Constans.ASCII_SOLICITUD_CONEXION:
                trama = ct.armarTrama(300, tipoMsg, Constans.PH_SOLICITUD_CONEXION);
                enviar(trama);
                break;
            case Constans.ACK_STRING:
                enviar(Constans.BT_ACK);
                break;
            case Constans.TRANS_REV_No:
                trama = ct.armarTrama(300, tipoMsg, Constans.PH_TRANS_NO_REV);
                enviar(trama);
                break;
            case Constans.TRANSACCION_ENVIO_DATOS:
                trama = ct.armarTrama(300, tipoMsg, Constans.PH_ENVIO_DATA);
                enviar(trama);
                break;
        }
    }
     public void enviar(byte[] frame) {
         byte[] trama = ComunicationTools.dicardEmpty(frame); 
        String msgEnviado = ct.decodeMsg(trama);
        StringBuilder stringBuilder = new StringBuilder();

        if (trama.length == 1) {
            msgEnviado = ct.decodeMsg(trama);
        } else {
            msgEnviado = Util.hex2AsciiStr(ct.decodeMsg(trama));
            ComunicationICC.listMsgOutput.add(msgEnviado);
        }

        ArrayList<String> msgComplete = ct.toListStringFrame(trama);

        for (String element : msgComplete) {
            stringBuilder.append(element);
        }
        
        if (ct.send(trama)) {
            panel.rspBox("CAJA ->  " + ct.msgOnScreen(msgEnviado));
            panel.rspBox("Trama:  " + stringBuilder.toString() + "\n");
            ComunicationICC.comando++;
        } else {
            panel.rspBox("Error en el envio de la trama: " + stringBuilder.toString() + "\n");
            ComunicationICC.comando = -1;
        }
        
    }
     
    public void recibir() {

        byte[] tramaReceived = ct.receiveRsp();
        ComunicationICC.comando++;

        if (tramaReceived == null) {
            ComunicationICC.comando = -1;
            panel.rspBox("POS ->  null");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> msgComplete = ct.toListStringFrame(tramaReceived);

        for (String element : msgComplete) {
            stringBuilder.append(element);
        }

        System.out.println("rsp: " + stringBuilder.toString());
        String msgRecibido;

        if (tramaReceived.length == 1) {
            msgRecibido = ct.decodeMsg(tramaReceived);
        } else {
            msgRecibido = Util.hex2AsciiStr(ct.decodeMsg(tramaReceived));
            ComunicationICC.listMsgInput.add(msgRecibido);
        }
        panel.rspBox("POS ->  " + ct.msgOnScreen(msgRecibido));
        panel.rspBox("Trama:  " + stringBuilder.toString() + "\n");

        if (msgRecibido.equals(Constans.RESP_ERROR)) {
            panel.rspBox("POS ->  " + manejoError(msgComplete));
        }
    }
     
     private String manejoError(ArrayList<String> mensaje) {
       
       int typeError = Integer.parseInt(Util.conversorAString(buscarDato(mensaje,48)));
       String msgEror;
       
         switch (typeError) {
             case 1:
                 msgEror = "TIEMPO DE ESPERA AGOTADO";
                 break;
             case 2:
                 msgEror = "ERROR AL LEER TARJETA";
                 break;
             case 3:
                 msgEror = "TRANSACCION NO EXISTE";
                 break;
             case 4:
                 msgEror = "NO EXISTEN TRANSACCIONES";
                 break;
             case 5:
                 msgEror = "LA TRANSACCION YA ESTA ANULADA";
                 break;
             case 7:
                 msgEror = "LOTE LLENO, REALICE CIERRE";
                 break;
             case 8:
                 msgEror = "MONTO INVALIDO";
                 break;
             case 9:
                 msgEror = "TARJETA NO SOPORTADA";
                 break;
             case 10:
                 msgEror = "LA TARJETA NO ES LA ORIGINAL";
                 break;
             case 11:
                 msgEror = "TARJETA EXPIRADA";
                 break;
             case 12:
                 msgEror = "USUARIO CANCELO";
                 break;
             case 13:
                 msgEror = "PIN INVALIDO";
                 break;
             case 14:
                 msgEror = "TRANSACCION NO PERMITIDA";
                 break;
             case 16:
                 msgEror = "ERROR EN LA BUSQUEDA DE LA TARJETA";
                 break;
             case 17:
                 msgEror = "ERROR DURANTE EL PROCESO DE TRANSACCION";
                 break;
             case 18:
                 msgEror = "MENSAJE INESPERADO";
                 break;
             case 19:
                 msgEror = "ERROR EN LA PANTALLA DE PIN";
                 break;
             case 20:
                 msgEror = "ERROR DE COMUNICACIÓN";
                 break;
             default:
                 msgEror = "ERROR NO CONTEMPLADO";
         }
       comando = -1;
       return msgEror;
    }
    
    public static byte[] armarTrama48XX() {
        byte[] retorno = new byte[7]; 
        byte[] codigoResp = ComunicationTools.hacerCodigoRespuesta(false);
        System.arraycopy(codigoResp, 0, retorno, 0, codigoResp.length);

        return retorno;
    }
    
    public static byte[] tramaEnvioDatos() {

        String montoPadleft = Util.padleft(monto + "", 12, '0');

        byte[] frame = new byte[100];
        byte[] campoMonto = ComunicationTools.crearCampo(montoPadleft, 40, 12);
        System.arraycopy(campoMonto, 0, frame, 0, campoMonto.length );

        byte[] noCaja = ComunicationTools.crearCampo("123", 42, 10);//10
        System.arraycopy(noCaja, 0, frame, campoMonto.length, noCaja.length);

        byte[] codigoResp = ComunicationTools.hacerCodigoRespuesta(true);
        System.arraycopy(codigoResp, 0, frame, campoMonto.length+noCaja.length, codigoResp.length);

        byte[] noTrx = ComunicationTools.crearCampo("123456", 53, 10);//10
        System.arraycopy(noTrx, 0, frame, campoMonto.length+noCaja.length+codigoResp.length, noTrx.length);

        byte[] typeAccount = ComunicationTools.crearCampo("1", 88, 12);//12
        System.arraycopy(typeAccount, 0, frame,campoMonto.length+noCaja.length+codigoResp.length+noTrx.length, typeAccount.length);
        
        byte[] etx = {Constans.ETX};
        System.arraycopy(etx, 0, frame, typeAccount.length+campoMonto.length+noCaja.length+codigoResp.length+noTrx.length, etx.length);
        
         byte[] frame2 = ComunicationTools.dicardEmpty(frame);
         byte[] retorno = new byte[frame2.length-2];
         for (int i = 0; i < frame2.length-2; i++) {
            retorno[i] = frame2[i];
        }
         
        return retorno;
    }
}