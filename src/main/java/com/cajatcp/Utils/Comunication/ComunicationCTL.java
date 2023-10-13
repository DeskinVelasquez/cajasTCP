/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

import DataManager.DataClasses.Trx;
import com.cajatcp.Utils.Alerts;
import static com.cajatcp.Utils.Comunication.ComunicationTools.buscarDato;
import com.cajatcp.Utils.Constans;
import com.cajatcp.Utils.Util;
import com.cajatcp.view.JPanelPrincipal;
import java.util.ArrayList;

/**
 *
 * @author DESKIN
 */
public class ComunicationCTL implements ImpComunication  {
    
    private static String monto;
    private ComunicationTools ct;
    private JPanelPrincipal panel;
    public static boolean lastMsg;
    private static String msgSend;
    private static boolean needReceived;;
    private static boolean needSend;
    public static ArrayList<String> listMsgOutput;
    public static ArrayList<String> listMsgInput;
    private static int comando = 0;
    private static final ArrayList<Trx> listTrx = new ArrayList<>();; 
    
    public ComunicationCTL(String monto, JPanelPrincipal panel) {
        
        this.monto = monto;
        this.panel = panel;
        ct = new ComunicationTools(this);
        ct.setPanel(panel);
        
        listMsgOutput = new ArrayList<>();
        listMsgInput = new ArrayList<>();
        
    }
    
    public void iniciarProceso() throws InterruptedException {

        msgSend = Constans.SOLICITUD_CONEXION_CONTACTLESS;
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
        
        finalizarCo();
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
                needReceived = false;
                break;
            case 9:
                msgSend = Constans.TRANSACCION_ENVIO_DATOS;
                needSend = true;
                needReceived = true;
                break;
            case 11:
                needSend = false;
                needReceived = true;
                break;
            case 12:
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = false;
                break;
            case 13:
                msgSend = Constans.TARJETA_CONTACTLESS;
                needSend = true;
                needReceived = true;
                break;
            case 15:
                needSend = false;
                needReceived = true;
                break;
            case 16:
                msgSend = Constans.ACK_STRING;
                needSend = true;
                comando++; //el envio del dispositivo al host
                comando++; //el envio del host al dispositivo
                needReceived = true;
                break;
            case 20:
                msgSend = Constans.ACK_STRING;
                needSend = true;
                needReceived = false;
                break;
            case 21:
                lastMsg = true;
                break;
            default:
                Alerts.alert(true, "Error en la comunicación", 2);
                lastMsg = true;
                
        }
    }
    
    @Override
    public void enviarMsg(String tipoMsg) {
        byte[] trama;
        switch (tipoMsg) {
            case Constans.SOLICITUD_CONEXION_CONTACTLESS:
                trama = ct.armarTrama(300, tipoMsg, Constans.PH_SOLICITUD_CONEXION_CTL);
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
            case Constans.TARJETA_CONTACTLESS://se Debe validar este mensaje
                trama = ct.armarTrama(300, tipoMsg, Constans.PH_TARJETA_CTL);
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
            ComunicationCTL.comando++;
        } else {
            panel.rspBox("Error en el envio de la trama: " + stringBuilder.toString() + "\n");
            ComunicationCTL.comando = -1;
        }
        
    }
     
    @Override
    public void recibir() {

        byte[] tramaReceived = ct.receiveRsp();
        ComunicationCTL.comando++;

        if (tramaReceived == null) {
            ComunicationCTL.comando = -1;
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
        if (comando == 17) {
            desempaquetarDatos(msgComplete);
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
    
    /**
     * Metodo que desempaqueta los campos que llegan a la caja
     * @param mensaje El mensaje o la trama completa recibida
     */


    public int desempaquetarDatos(ArrayList<String> mensaje) {
       
        try {
            String codigoAut = Util.conversorAString(buscarDato(mensaje,01));
            String montoString = Util.conversorAString(buscarDato(mensaje,40));
            String numRecibo = Util.conversorAString(buscarDato(mensaje,43));
            String RRN = Util.conversorAString(buscarDato(mensaje,44));
            String tid = Util.conversorAString(buscarDato(mensaje,45));
            String dateTXR = Util.conversorAString(buscarDato(mensaje,46));
            String timeTXR = Util.conversorAString(buscarDato(mensaje,47));
            String codRsp = Util.conversorAString(buscarDato(mensaje,48));
            String typeAccount = Util.conversorAString(buscarDato(mensaje,50));
            String numCuotas = Util.conversorAString(buscarDato(mensaje,51));
            String last4Digits = Util.conversorAString(buscarDato(mensaje,54));
            String msgError = Util.conversorAString(buscarDato(mensaje,61));
            
            Trx trx = new Trx(codigoAut, montoString, numRecibo, RRN, tid, dateTXR, timeTXR, codRsp, typeAccount, numCuotas, last4Digits, msgError);
            listTrx.add(trx);
            panel.rspBox(panel.dataTrx(trx));            
        } catch (Exception e) {
            System.out.println("Error al desempaquetar los datos ");
            return 2;
        }
        return 0;
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
            case Constans.TARJETA_CONTACTLESS:
                retorno = tramaTipoLectura();
                break;
            default:
                retorno = null;
                System.out.println("Error switch comunicationCTL-armarTramaVariable");
                
        }
        
        return retorno;
    }
    
    private void finalizarCo() {
        comando = 0;
        ct.disaableConnect();
        ct.openConnect();
     }

    private byte[] tramaTipoLectura() {
        return null;
    }
}
