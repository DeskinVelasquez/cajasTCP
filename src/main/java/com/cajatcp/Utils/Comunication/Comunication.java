/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

import com.cajatcp.Utils.Alerts;
import com.cajatcp.Utils.Constans;
import static com.cajatcp.Utils.Constans.BT_SEND_DATA;
import static com.cajatcp.Utils.Constans.STR_ENABLE_CONNECT;
import com.cajatcp.Utils.Util;
import com.cajatcp.view.JPanelPrincipal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.swing.JOptionPane;
import java.util.Timer;

/**
 *
 * @author WPOSS
 */
public class Comunication {
    InputStream inputStream;
    OutputStream outputStream;
    private static Socket socket;
    private static ServerSocket serverSocket;
    private JPanelPrincipal panel;
    String retorno = "";
    private static ArrayList<String> listMsgInput;
    private static ArrayList<String> listMsgOutput;

    public Comunication() {
      listMsgInput = new ArrayList<>();
      listMsgOutput = new ArrayList<>();
    }
    
    public void setPanel(JPanelPrincipal panel) {
        this.panel = panel;
    }
    
     public String disaableConnect() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
            return "Comunicación cerrada";
        } catch (IOException ex) {
            Logger.getLogger(Comunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error al desconectar";
    }
    
    public String enableConnect() {
        
        try {
            if (socket != null && socket.isClosed()) {
                socket = new Socket();
            }
            serverSocket = new ServerSocket(Constans.getPORT());
            panel.rspBox("Esperando una conexión...");
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Alerts.alert(true, "No se ha conectado ningun cliente", 1);
                    panel.rspBox("No se ha conectado ningun cliente");
                    panel.cambiarNombreBtnConecct(STR_ENABLE_CONNECT);
                    panel.rspBox(disaableConnect());
                    System.out.println("desconectado");
                    timer.cancel();
                }
            }, 3000);
            socket = serverSocket.accept();
            timer.cancel();
            if (socket != null && socket.isConnected()) {
                System.out.println("cliente conectado");
                retorno = "cliente conectado";
            }
            retorno = "fallo en la conexion con el cliente";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }
    
    public String openConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               enableConnect();
            }
        }).start();
        return retorno;
    }
    
    public void receiveRsp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] temporary = new byte[5000];
                    byte[] frame = null;
                    //BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    InputStream is = socket.getInputStream();
                    int len = is.read(temporary);
                    if (len > 1) {
                        frame = dicardEmpty(temporary);
                    } else if (len == 1) {
                        frame = new byte[1];
                        frame[0] = temporary[0];
                    }
                    
                    System.out.println("rsp: " + frame);
                    String msgRecibido;
                    if (frame.length == 1) {
                       msgRecibido = decodeMsg(frame); 
                    } else {
                        msgRecibido = hex2AsciiStr(decodeMsg(frame));  
                    }
                    listMsgInput.add(msgRecibido);
                    panel.rspBox("  POS  " + "========>  " +msgOnScreen(msgRecibido) + "  ========> "+ "  CAJA "+ "\n");
                    validateMsgInput(msgRecibido);

                } catch (IOException ex) {
                    Logger.getLogger(Comunication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
    
    private String decodeMsg(byte[] frame) {
        ArrayList<String> StrListFrame = toListStringFrame(frame);

        String msgRecibido = "";
        if (StrListFrame.size() == 1) {
            msgRecibido = StrListFrame.get(0);
        } else {
            msgRecibido = obtenerPresentationHeader(StrListFrame);
        }
        return msgRecibido;
    }
    
    private void validateMsgInput(String msgRecibido) {
        String ultimoMsgEnviado = listMsgOutput.get(listMsgOutput.size() -1);
        switch(msgRecibido){
            case Constans.ACK_STRING:
                receiveRsp();  
                break;
            case Constans.ULTIMA_TRANS:
                if (ultimoMsgEnviado.equals(Constans.SOLICITUD_CONEXION) || 
                        ultimoMsgEnviado.equals(Constans.SOLICITUD_CONEXION_QR)) {
                   send(Constans.BT_ACK);
                   send(Constans.BT_TRANS_NO_REV);
                }
                receiveRsp(); 
                break;
            case Constans.NUEVA_PANTALLA:
                send(Constans.BT_ACK);
                receiveRsp();
                break;
            case Constans.SOLICITUD_DATOS:
                send(Constans.BT_ACK);
                send(obtenerTramaData());
                receiveRsp();
                break;
            case Constans.RESP_HOST:
                send(Constans.BT_ACK);
                break;
        }
    }
    
    
    public boolean send(byte[] data) {
        String msgEnviado = decodeMsg(data);
        if (data.length == 1) {
            msgEnviado = decodeMsg(data);
        } else {
            msgEnviado = hex2AsciiStr(decodeMsg(data));
        }
        listMsgOutput.add(msgEnviado);
        try {
            if (Alerts.alert(socket == null, "Debe haber un cliente conectado", 1)) {
                return false;
            }
            OutputStream os = socket.getOutputStream();
            os.write(data);
            os.flush();
            panel.rspBox("  CAJA " + "========>  " + msgOnScreen(msgEnviado) + "  ========> "+ "  POS "+"\n");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Comunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }
    
    /**
     * Metodo para descartar los espacios sobrantes del arreglo temporal donde se recibe la trama
     * (SOLO MODO CAJA)
     *
     * @param data
     * @return arreglo de bytes con los datos exactos de la trama
     */
    private static byte[] dicardEmpty(byte[] data) {
        byte etx = 0x03;
        byte[] frame = null;
        int len = data.length;
        byte[] temporary = new byte[len];
        int i = 0;
        if (!manyAckAndNack(data)) {
            for (int j = 0; i < len; i++, j++) {
                byte b = data[i];
                if (Byte.compare(b, etx) == 0) {
                    temporary[j] = data[i];
                    temporary[++j] = data[++i];
                    break;
                }
                temporary[j] = data[i];
            }
            if (i == 5000)
                frame = new byte[i];
            else
                frame = new byte[++i];
            for (i = 0; i < frame.length; i++) {
                frame[i] = temporary[i];
            }
        } else {
            frame = new byte[1];
            frame[0] = data[0];
        }

        return frame;
    }
    
    private static boolean manyAckAndNack(byte[] data) {
        byte ack = 0x06;
        byte nack = 0x15;

        byte b = data[0];
        byte c = data[1];
        if ((Byte.compare(b, ack) == 0 && Byte.compare(c, ack) == 0) || (Byte.compare(b, nack) == 0 && Byte.compare(c, nack) == 0)
                || (Byte.compare(b, ack) == 0 && Byte.compare(c, nack) == 0) || (Byte.compare(b, nack) == 0 && Byte.compare(c, ack) == 0) ) {
            return true;
        }

        return false;
    }
    
    /**
     * Metodo que convierte en un arreglo de String con formato hexadeimal una trama
     *
     * @param frame
     * @return trama covertida a String
     */
    private  ArrayList<String> toListStringFrame(byte[] frame) {
        ArrayList<String> result = new ArrayList<>();
        int numRead = frame.length;
        String hexa;

        if (numRead != -1) {
            for (byte b : frame) {
                hexa = Integer.toHexString(b);
                if(b < 0){
                    int c = b + 256;
                    hexa = Integer.toHexString(c);
                }
                if (b < 10) {
                    if(b >= 0)
                        hexa = "0" + b;
                }
                if (validarHexaLetra(hexa)) {
                    hexa = "0" + hexa;
                }
                result.add(hexa);
            }
        }
        return result;
    }
    
    /**
     * Metodo utilizado para validar si el String es hexa: A,B,C,D,E o F (decimal 10 al 15)
     *
     * @param hexa --> valor hexa
     * @return true--> si es un decimal del 10 al 15, false
     * Creado por: Silvia Hernandez
     */
    private boolean validarHexaLetra(String hexa) {
        if (hexa.equalsIgnoreCase("a") || hexa.equalsIgnoreCase("b")
                || hexa.equalsIgnoreCase("c") || hexa.equalsIgnoreCase("d")
                || hexa.equalsIgnoreCase("e") || hexa.equalsIgnoreCase("f")) {
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Metodo utilizado para descomponer la parte fija del mensaje recibido
     * El presentation header
     * @param trama
     */
    private String obtenerPresentationHeader(ArrayList<String> trama) {
        String presentationHeader = "";
        if (trama.size() > 19) {
            for (int i = 13; i < 20; i++) {
                //es el que identifica la trasacción; ubicado desde la posición 13 a 19 de la trama
                presentationHeader = presentationHeader + trama.get(i);
            }
        }
        return presentationHeader;
    }
    
    private String hex2AsciiStr(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);
            temp.append(decimal);
        }

        return sb.toString();
    }
    
    public byte[] obtenerTramaData() {
        StringBuilder datos = new StringBuilder();
        for (byte b : BT_SEND_DATA) {

            char newDato = Util.byte2Char(b);
            datos.append(newDato);
        }
        String montoReplaced = datos.toString().substring(25, 37);
        int len = Constans.getMONTO().length();
        int countZero = 12 - len;
        String zero = "";
        for (int i = 0; i < countZero - 2; i++) {
            zero += "0";
        }
        String newDatos = datos.toString().replace(montoReplaced, zero + Constans.getMONTO() + "00");
        byte[] btData = new byte[BT_SEND_DATA.length];
        for (int i = 0; i < newDatos.length(); i++) {
            btData[i] = (byte) (int) newDatos.charAt(i);
            System.out.print(btData[i] + ", ");
        }
        return btData;
    }

    private String msgOnScreen(String msg) {
        String msgOnScreen = "";
        switch (msg) {
            case Constans.SOLICITUD_CONEXION:
                msgOnScreen = Constans.STR_SOLICITUD_CONEXION;
                break;
            case Constans.SOLICITUD_CONEXION_QR:
                msgOnScreen = Constans.STR_SOLICITUD_CONEXION_QR;
                break;
            case Constans.SOLICITUD_CONEXION_TIGOMONEY:
                msgOnScreen = Constans.STR_SOLICITUD_CONEXION_TIGOMONEY;
                break;
            case Constans.SOLICITUD_CONEXION_CONTACTLESS:
                msgOnScreen = Constans.STR_SOLICITUD_CONEXION_CONTACTLESS;
                break;
            case Constans.TRANS_REV_No:
                msgOnScreen = Constans.STR_TRANS_REV_No;
                break;
            case Constans.TRANSACCION_ENVIO_DATOS:
                msgOnScreen = Constans.STR_TRANSACCION_ENVIO_DATOS;
                break;
            case Constans.TARJETA_CONTACTLESS:
                msgOnScreen = Constans.STR_TARJETA_CONTACTLESS;
                break;
        //msg recibidos
            case Constans.ULTIMA_TRANS:
                msgOnScreen = Constans.STR_ULTIMA_TRANS;
                break;
            case Constans.NUEVA_PANTALLA:
                msgOnScreen = Constans.STR_NUEVA_PANTALLA;
                break;
            case Constans.SOLICITUD_DATOS:
                msgOnScreen = Constans.STR_SOLICITUD_DATOS;
                break;
            case Constans.RESP_HOST:
                msgOnScreen = Constans.STR_RESP_HOST;
                break;
            case Constans.RESP_HOST_CONTACTLESS:
                msgOnScreen = Constans.STR_RESP_HOST_CONTACTLESS;
                break;
            case Constans.ACK_STRING:
                msgOnScreen = Constans.STR_ACK_STRING;
                break;
            case Constans.NACK_STRING:
                msgOnScreen = Constans.STR_NACK_STRING;
                break;
            default:
                throw new AssertionError();
        }
        return msgOnScreen;
    }
}
