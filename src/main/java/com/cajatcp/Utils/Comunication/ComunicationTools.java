/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

import DataManager.DataClasses.Trx;
import com.cajatcp.Utils.Alerts;
import com.cajatcp.Utils.Constans;
import static com.cajatcp.Utils.Constans.STR_ENABLE_CONNECT;
import com.cajatcp.Utils.Util;
import com.cajatcp.view.JPanelPrincipal;
import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Timer;

/**
 *
 * @author deskin
 */
public abstract class ComunicationTools {

    private InputStream inputStream;
    private OutputStream outputStream;
    private static Socket socket;
    private static ServerSocket serverSocket;
    public static boolean isTCP;
    private JPanelPrincipal panel;
    String retorno = "";
    private static final ArrayList<Trx> listTrx = new ArrayList<>();

    //heredar
    protected static int comando = 0;
    protected String msgSend;
    protected boolean needSend;
    protected boolean needReceived;
    protected boolean lastMsg;
    protected ArrayList<String> listMsgOutput;
    protected static ArrayList<String> listMsgInput;
    protected ArrayList<String> msgComplete;
    protected int monto;

    //comunicacion USB
    public static SerialPort serialPort;
    private static boolean conecctUSB;

    public ComunicationTools(JPanelPrincipal panel) {
        this.panel = panel;
    }

    public void setMonto() {
        monto = panel.getMonto();
    }

    public static ArrayList<String> getListMsgInput() {
        return listMsgInput;
    }

    public String disaableConnect() {
        String retorno;
        if (isTCP) {
            retorno = disaableConnectTCP();
        } else {
            retorno = disaableConnectUSB();
        }
        return retorno;
    }

    public String disaableConnectTCP() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
            return "Comunicación cerrada";
        } catch (IOException ex) {
            Logger.getLogger(ComunicationTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error al desconectar";
    }

    public String disaableConnectUSB() {

        return "Comunicación cerrada";
    }

    public void enableConnect() {
        int retorno;
        panel.rspBox("Esperando una conexión...");
        if (isTCP) {
            retorno = comunicationTCPenable();
        } else {
            retorno = comunicationUSBenable();
        }

        if (retorno != 0) {
            panel.rspBox("fallo en la conexion con el cliente");
        } else {
            panel.rspBox("cliente conectado");
            panel.setEnableButtons(true);
        }

    }

    private int comunicationUSBenable() {
        if (!conecctUSB) {
            // Obtener la lista de puertos seriales disponibles
            SerialPort[] ports = SerialPort.getCommPorts();

            // Elegir un puerto
            String port = "";
            if (ports.length == 1) {
                String p = ports[0].getDescriptivePortName().split(" ")[3];
                port = ports[0].getDescriptivePortName().split(" ")[3].substring(1, p.length() - 1);
                System.out.println("Port: " + port);
            } else {
                System.out.println("varios dispositivos conectados");
            }

            serialPort = SerialPort.getCommPort(port);
            serialPort.setComPortParameters(9600, 8, 1, 0); // Configura los parámetros del puerto

            // Abrir el puerto
            try {
                if (serialPort.openPort()) {
                    // Configurar los parámetros del puerto
                    serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
                    // Antes de la lectura, configure un tiempo de espera de 60 segundos
                    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 60000, 0);

                    inputStream = serialPort.getInputStream();
                    outputStream = serialPort.getOutputStream();
                    System.out.println("Puerto abierto correctamente. " + serialPort.getDescriptivePortName());
                } else {
                    System.err.println("No se pudo abrir el puerto.");
                    return 1;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        }
        return 0;
    }

    private int comunicationTCPenable() {
        try {
            if (socket != null && socket.isClosed()) {
                socket = new Socket();
            }
            serverSocket = new ServerSocket(Constans.getPORT());
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
            }, 5000);
            socket = serverSocket.accept();
            timer.cancel();
            if (socket != null && socket.isConnected()) {
                System.out.println("cliente conectado");
            }
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void openConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                enableConnect();
            }
        }).start();
    }

    public byte[] receiveRsp() {

        byte[] temporary = new byte[5000];
        byte[] frame = null;
        int len = 0;
        try {
            if (isTCP) {
                inputStream = socket.getInputStream();
            } else {
                inputStream = serialPort.getInputStream();
            }
            
            len = inputStream.read(temporary);
        } catch (IOException ex) {
            Logger.getLogger(ComunicationTools.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (len > 1) {
            frame = dicardEmpty(temporary);
        } else if (len == 1) {
            frame = new byte[1];
            frame[0] = temporary[0];
        } else {
            return null;
        }

        return frame;

    }

    public String decodeMsg(byte[] frame) {
        ArrayList<String> strListFrame = toListStringFrame(frame);

        String msgRecibido = "";
        if (strListFrame.size() == 1) {
            msgRecibido = strListFrame.get(0);
        } else {
            msgRecibido = obtenerPresentationHeader(strListFrame);
        }
        return msgRecibido;
    }

    public boolean send(byte[] data) {
        try {/*
            if (Alerts.alert(socket == null, "Debe haber un cliente conectado", 1)) {
                return false;
            }*/
            if (isTCP) {
                outputStream = socket.getOutputStream();
            } else {
                outputStream = serialPort.getOutputStream();
            }
            
            outputStream.write(data);
            outputStream.flush();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ComunicationTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Metodo para descartar los espacios sobrantes del arreglo temporal donde
     * se recibe la trama (SOLO MODO CAJA)
     *
     * @param data
     * @return arreglo de bytes con los datos exactos de la trama
     */
    public static byte[] dicardEmpty(byte[] data) {
        byte etx = 0x03;
        byte[] frame = null;
        int len = data.length;
        if (len == 1) {
            return data;
        }
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
            if (i == 5000) {
                frame = new byte[i];
            } else {
                frame = new byte[++i];
            }
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
                || (Byte.compare(b, ack) == 0 && Byte.compare(c, nack) == 0) || (Byte.compare(b, nack) == 0 && Byte.compare(c, ack) == 0)) {
            return true;
        }

        return false;
    }

    /**
     * Metodo que convierte en un arreglo de String con formato hexadeimal una
     * trama
     *
     * @param frame
     * @return trama covertida a String
     */
    ArrayList<String> toListStringFrame(byte[] frame) {
        ArrayList<String> result = new ArrayList<>();
        int numRead = frame.length;
        String hexa;

        if (numRead != -1) {
            for (byte b : frame) {
                hexa = Integer.toHexString(b);
                if (b < 0) {
                    int c = b + 256;
                    hexa = Integer.toHexString(c);
                }
                if (b < 10) {
                    if (b >= 0) {
                        hexa = "0" + b;
                    }
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
     * Metodo utilizado para validar si el String es hexa: A,B,C,D,E o F
     * (decimal 10 al 15)
     *
     * @param hexa --> valor hexa
     * @return true--> si es un decimal del 10 al 15, false Creado por: Silvia
     * Hernandez
     */
    private boolean validarHexaLetra(String hexa) {
        if (hexa.equalsIgnoreCase("a") || hexa.equalsIgnoreCase("b")
                || hexa.equalsIgnoreCase("c") || hexa.equalsIgnoreCase("d")
                || hexa.equalsIgnoreCase("e") || hexa.equalsIgnoreCase("f")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo utilizado para descomponer la parte fija del mensaje recibido El
     * presentation header
     *
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

    /*
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
    }*/

    public String msgOnScreen(String msg) {
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
            case Constans.SOLICITUD_INIT:
                msgOnScreen = Constans.STR_INIT;
                break;
            case Constans.SOLICITUD_CIERRE:
                msgOnScreen = Constans.STR_CLOSE;
                break;
            case Constans.CIERRE_CANTIDAD:
                msgOnScreen = Constans.STR_CLOSE;
                break;
            case Constans.DATOS_CIERRE:
                msgOnScreen = Constans.STR_DATOS_CIERRE;
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
            case Constans.RESP_ERROR:
                msgOnScreen = Constans.ERROR;
                break;
            case Constans.SEND_REF_PENDING:
                msgOnScreen = Constans.STR_SEND_REF_PENDING;
                break;
            case Constans.RESP_INIT:
                msgOnScreen = Constans.STR_INIT_OK;
                break;
            default:
                if (isTCP) {
                   Alerts.alert(true, "mensaje sobre pantalla no contemplado", 2); 
                }
                break;

        }
        return msgOnScreen;
    }

    /**
     * metodo utilizado para calcular la longitud de la trama a enviar
     * corresponde: primer byte transportHeader hasta el byte anterior al ETX
     *
     * @param size tamaño de la trama
     * (TransportHeader+PresentationHeader+Campos)
     * @return Creado por Deskin Velasquez
     */
    public static byte[] calcularLongitudMensaje(int size) {
        return Util.int2bcd(size, 2);
    }

    /**
     * Metodo utilizado para armar la parte variable de la trama Valida que tipo
     * de transacción es y procede a armar la trama
     *
     * @param presentationHeader
     * @return
     */
    public byte[] armarParteVariable(String presentationHeader) {
        byte[] retorno = null;
        switch (presentationHeader) {
            case Constans.STR_INIT:
            case Constans.SOLICITUD_CIERRE:
                break;
            case Constans.SOLICITUD_CONEXION:
            case Constans.SOLICITUD_CONEXION_QR:
            case Constans.SOLICITUD_CONEXION_CONTACTLESS:
                //aqui se debe validar si tiene id de comercio
                retorno = armarFirtsFields();
                break;
            case Constans.TRANS_REV_No:
                retorno = armarTramaVariable(Constans.STR_TRANS_REV_No);
                break;
            case Constans.TRANSACCION_ENVIO_DATOS:
                retorno = armarTramaVariable(Constans.TRANSACCION_ENVIO_DATOS);
                break;
            case Constans.TARJETA_CONTACTLESS:
                //retorno = impComunication.armarTramaVariable(Constans.TARJETA_CONTACTLESS);
                break;
        }
        return retorno;
    }

    private byte[] armarFirtsFields() {
        byte[] frame = new byte[30];
        int dinamicLen = 0;
        if (panel.checkMulti.isSelected()) {
            int idAcq = (int) panel.jSpnAcquirer.getValue();
            byte[] tramaIdAc = armarTramaIDACQ(idAcq);
            System.arraycopy(tramaIdAc, 0, frame, dinamicLen, tramaIdAc.length);
            dinamicLen += tramaIdAc.length;
        }
        String moneda = (String) panel.cbxMoneda.getSelectedItem();
        if (moneda.equals(Constans.STR_BS)) {
            moneda = "01";
        } else {
            moneda = "02";
        }
        byte[] tramaCoin = crearCampo(moneda, 68, 2);
        System.arraycopy(tramaCoin, 0, frame, dinamicLen, tramaCoin.length);
        dinamicLen += tramaCoin.length;

        byte[] etx = {Constans.ETX};
        System.arraycopy(etx, 0, frame, dinamicLen, etx.length);

        byte[] frame2 = ComunicationTools.dicardEmpty(frame);
        byte[] retorno = new byte[frame2.length - 2];
        for (int i = 0; i < frame2.length - 2; i++) {
            retorno[i] = frame2[i];
        }

        return retorno;
    }

    private byte[] armarTramaIDACQ(int idacq) {
        String dato = "";
        if (idacq < 10) {
            dato = "0" + idacq;
        } else {
            dato = String.valueOf(idacq);
        }

        byte[] retorno = crearCampo(dato, 79, 2);

        return retorno;
    }

    /**
     * Metodo que permite hacer un campo de codigo de respuesta, correcto o
     * incorrecto dependiendo del boolean enviando
     *
     * @param tipo el tipo de codigo de respuesta, true si es correcto, de lo
     * contrario false
     * @return el campo de codigo de respuesta
     */
    public static byte[] hacerCodigoRespuesta(boolean tipo) {
        byte retorno[] = new byte[7];
        retorno[0] = Constans.SEPARADOR;
        //Nombre del campo
        retorno[1] = 0x34;
        retorno[2] = 0x38;

        //Longitud
        retorno[3] = 0x00;
        retorno[4] = 0x02;
        //campo

        if (tipo) {
            retorno[5] = 0x20;
            retorno[6] = 0x20;
        } else {
            retorno[5] = 0x58;
            retorno[6] = 0x58;
        }
        return retorno;
    }

    /**
     * converts to BCD
     *
     * @param s - the number
     * @param padLeft - flag indicating left/right padding
     * @return BCD representation of the number
     */
    public static byte[] str2bcd(String s, boolean padLeft) {
        if (s == null) {
            return null;
        }
        int len = s.length();
        byte[] d = new byte[len + 1 >> 1];
        return str2bcd(s, padLeft, d, 0);
    }

    /**
     * converts to BCD
     *
     * @param s - the number
     * @param padLeft - flag indicating left/right padding
     * @param d The byte array to copy into.
     * @param offset Where to start copying into.
     * @return BCD representation of the number
     */
    public static byte[] str2bcd(String s, boolean padLeft, byte[] d, int offset) {
        char c;
        int len = s.length();
        int start = (len & 1) == 1 && padLeft ? 1 : 0;
        for (int i = start; i < len + start; i++) {
            c = s.charAt(i - start);
            if (c >= '0' && c <= '?') // 30~3f
            {
                c -= '0';
            } else {
                c &= ~0x20;
                c -= 'A' - 10;
            }
            d[offset + (i >> 1)] |= c << ((i & 1) == 1 ? 0 : 4);
        }
        return d;
    }

    //-------------------------------------------------------------------------------------------
    /**
     * Metodo utilizado para calcular el LRC de la trama LRC = XOR de todos los
     * bytes después del STX [0] (sin incluir) hasta el ETX
     * [leng-2](incluyéndolo)
     *
     * @param arreglo
     * @return
     */
    public static byte calcularLRC(byte[] arreglo) {
        byte LRC = 0x00;
        //definir nuevo arreglo sin incluir la primera y la ultima posicion del arreglo
        byte[] trama = new byte[arreglo.length - 2];

        //llenar el [] trama quitando el STX (primer elemento) y el ultimo elemento de la trama (LRC)
        for (int i = 1; i < arreglo.length - 1; i++) {
            trama[i - 1] = arreglo[i];
        }
        //calculo del LRC
        for (int i = 0; i < trama.length; i++) {
            LRC ^= trama[i];//XOR
        }
        return LRC;
    }

    /**
     * Metodo que permite crear un campo especifico de un dato
     *
     * @param campo el valor del dato Ej: 5000
     * @param idCampo el id del campo
     * @param longitud la longitud del campo
     * @return un arreglo de bytes con todos los datos del campo: separador - id
     * - longitud - valor
     */
    public static byte[] crearCampo(String campo, Integer idCampo, Integer longitud) {
        byte[] totalByte = new byte[longitud + 5];
        byte[] idCampoByte = new byte[2];
        byte[] longitudCampoByte = new byte[2];
        byte[] campoByte = converT2byteHexa(longitud, campo);
        totalByte[0] = Constans.SEPARADOR;
        switch (idCampo) {
            case 1: //Codigo de Autorizacion
                idCampoByte[0] = 0x30;
                idCampoByte[1] = 0x31;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x06;
                break;
            case 30: //Envio Bin de tarjeta
                idCampoByte[0] = 0x33;
                idCampoByte[1] = 0x30;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x0C;
                break;
            case 40: //Monto de transaccion
                idCampoByte[0] = 0x34;
                idCampoByte[1] = 0x30;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x0C;
                break;
            case 42: //Numero caja
                idCampoByte[0] = 0x34;
                idCampoByte[1] = 0x32;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x0C;
                break;
            case 43: //Numero del recibo
                idCampoByte[0] = 0x34;
                idCampoByte[1] = 0x33;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x06;
                break;
            case 44: //RNN
                idCampoByte[0] = 0x34;
                idCampoByte[1] = 0x34;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x0C;
                break;
            case 45: //Terminal ID
                idCampoByte[0] = 0x34;
                idCampoByte[1] = 0x35;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x08;
                break;
            case 46: //Fecha
                idCampoByte[0] = 0x34;
                idCampoByte[1] = 0x36;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x04;
                break;
            case 47: //Hora
                idCampoByte[0] = 0x34;
                idCampoByte[1] = 0x37;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x04;
                break;
            case 48://cod de respuesta
                idCampoByte[0] = 0x34;
                idCampoByte[1] = 0x38;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x02;
                break;
            case 75: //BIN
                idCampoByte[0] = 0x37;
                idCampoByte[1] = 0x35;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x06;
                break;
            case 77: //idcomercio
                idCampoByte[0] = 0x37;
                idCampoByte[1] = 0x37;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x17;
                break;
            case 50:
                idCampoByte[0] = 0x35;
                idCampoByte[1] = 0x30;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x02;
                break;
            case 51:
                idCampoByte[0] = 0x35;
                idCampoByte[1] = 0x31;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x02;
                break;
            case 53: //numero trx
                idCampoByte[0] = 0x35;
                idCampoByte[1] = 0x33;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x0C;
                break;
            case 54: //Ultimos 4 digitos
                idCampoByte[0] = 0x35;
                idCampoByte[1] = 0x34;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x04;
                break;
            case 61:
                Arrays.fill(totalByte, (byte) 0x20);
                idCampoByte[0] = 0x36;
                idCampoByte[1] = 0x31;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x45;
                totalByte[0] = Constans.SEPARADOR;
                break;
            case 68:
                Arrays.fill(totalByte, (byte) 0x20);
                idCampoByte[0] = 0x36;
                idCampoByte[1] = 0x38;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x02;
                totalByte[0] = Constans.SEPARADOR;
                break;
            case 79:
                Arrays.fill(totalByte, (byte) 0x20);
                idCampoByte[0] = 0x37;
                idCampoByte[1] = 0x39;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x02;
                totalByte[0] = Constans.SEPARADOR;
                break;
            case 82:
                idCampoByte[0] = 0x38;
                idCampoByte[1] = 0x32;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x0C;
                break;
            case 88:
                idCampoByte[0] = 0x38;
                idCampoByte[1] = 0x38;
                longitudCampoByte[0] = 0x00;
                longitudCampoByte[1] = 0x0C;
                break;

        }
        System.arraycopy(idCampoByte, 0, totalByte, 1, idCampoByte.length);
        System.arraycopy(longitudCampoByte, 0, totalByte, 3, longitudCampoByte.length);
        if (longitud != 0) {
            System.arraycopy(campoByte, 0, totalByte, 5, campoByte.length);
        }
        return totalByte;
    }

    /**
     * Metodo que permite convertir un String a un arreglo de bytes en
     * hexadecimal
     *
     * @param size
     * @param value
     * @return
     */
    public static byte[] converT2byteHexa(Integer size, String value) {
        //Logger.debug("Convirtiendo a hexa el valor " + value);
        byte[] retorno = new byte[size];
        if (value != null) {
            retorno = value.getBytes(StandardCharsets.US_ASCII);//convertir cadena en []ascii
        }
        return retorno;
    }

    /**
     * Metodo que permite buscar un campo en la trama,
     *
     * @param mensaje el arreglo del mensaje
     * @param campo el campo a buscar ej: 43, 48
     * @return Una lista con los datos del campo buscado, null si no encuentra
     * el campo
     */
    public static List<String> buscarDato(ArrayList<String> mensaje, Integer campo) {
        try {
            Integer contador = 20;
            if (mensaje != null) {
                while (contador < mensaje.size() - 2) {
                    if (isSeparador(mensaje.get(contador))) {
                        String tipo = Util.conversorAString(mensaje.subList(contador + 1, contador + 3));
                        Integer tipoInteger = Integer.valueOf(tipo);
                        if (campo == tipoInteger) {
                            StringBuilder juntos = new StringBuilder();
                            juntos.append(mensaje.get(contador + 3));
                            juntos.append(mensaje.get(contador + 4));
                            int longitudInteger = Util.hex2Int(juntos.toString());
                            //Logger.debug("Imprimiendo longitud " + longitudInteger);
                            return mensaje.subList(contador + 5, contador + 5 + longitudInteger);
                        }
                    }
                    contador++;
                }
                return null;
            } else {
                return null;
            }
        } catch (Exception e) {
            //Logger.error("Hubo un error recorriendo la trama para buscar un dato");
            return null;
        }
    }

    /**
     * Metodo usado para saber si un valor hexa en string es separador = 1C
     *
     * @param entrada
     * @return
     */
    public static boolean isSeparador(String entrada) {
        return entrada.equalsIgnoreCase(Constans.SEPARADOR_STRING);
    }

    /**
     * Metodo que analiza la parte de datos una trama de codigo de respuesta y
     * regresa si es un codigo de respuesta correcto o incorrecto
     *
     * @param trama
     * @return
     */
    public static boolean analizarCodigoRespuesta(List<String> trama) {
        if (trama.size() == 2) {
            if (trama.get(0).equalsIgnoreCase("20")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void finalizarCo() {
        comando = 0;
        disaableConnect();
        openConnect();
    }

    private String manejoError(ArrayList<String> mensaje) {

        int typeError = Integer.parseInt(Util.conversorAString(buscarDato(mensaje, 48)));
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

    //metodos heredados---------------------------------------------------------
    protected void iniciarProceso(String typeSend) throws InterruptedException {

        msgSend = typeSend;
        needSend = true;
        needReceived = true;

        do {

            if (needSend) {
                enviarMsg(msgSend);
            }

            if (needReceived) {
                recibir();
            }

            mensajeria();

        } while (!lastMsg);
        finalizarCo();
    }

    protected void enviarMsg(String tipoMsg) {
        byte[] trama;
        switch (tipoMsg) {
            case Constans.SOLICITUD_CONEXION_CONTACTLESS:
                trama = armarTrama(300, tipoMsg, Constans.PH_SOLICITUD_CONEXION_CTL);
                enviar(trama);
                break;
            case Constans.ACK_STRING:
                enviar(Constans.BT_ACK);
                break;
            case Constans.TRANS_REV_No:
                trama = armarTrama(300, tipoMsg, Constans.PH_TRANS_NO_REV);
                enviar(trama);
                break;
            case Constans.TRANSACCION_ENVIO_DATOS:
                trama = armarTrama(300, tipoMsg, Constans.PH_ENVIO_DATA);
                enviar(trama);
                break;
            case Constans.TARJETA_CONTACTLESS://se Debe validar este mensaje
                trama = armarTrama(300, tipoMsg, Constans.PH_TARJETA_CTL);
                enviar(trama);
                break;
            case Constans.SOLICITUD_CONEXION_QR:
                trama = armarTrama(300, tipoMsg, Constans.PH_SOLICITUD_CONEXION_QR);
                enviar(trama);
                break;
            case Constans.SOLICITUD_CONEXION:
                trama = armarTrama(300, tipoMsg, Constans.PH_SOLICITUD_CONEXION);
                enviar(trama);
                break;
            case Constans.SOLICITUD_INIT:
                trama = armarTrama(300, tipoMsg, Constans.PH_SOLICITUD_INIT);
                enviar(trama);
                break;
            case Constans.SOLICITUD_CIERRE:
                trama = armarTrama(300, tipoMsg, Constans.PH_SOLICITUD_CIERRE);
                enviar(trama);
                break;
            default:
                retorno = null;
                System.out.println("Error switch comunicationQR-armarTramaVariable");
        }
    }

    protected void enviar(byte[] frame) {
        byte[] trama = ComunicationTools.dicardEmpty(frame);
        String msgEnviado = decodeMsg(trama);
        StringBuilder stringBuilder = new StringBuilder();

        if (trama.length == 1) {
            msgEnviado = decodeMsg(trama);
        } else {
            msgEnviado = Util.hex2AsciiStr(decodeMsg(trama));
            listMsgOutput.add(msgEnviado);
        }

        ArrayList<String> msgComplete = toListStringFrame(trama);

        for (String element : msgComplete) {
            stringBuilder.append(element);
        }

        if (send(trama)) {
            panel.rspBox("CAJA ->  " + msgOnScreen(msgEnviado));
            panel.rspBox("Trama:  " + stringBuilder.toString() + "\n");
            comando++;
        } else {
            panel.rspBox("Error en el envio de la trama: " + stringBuilder.toString() + "\n");
            comando = -1;
        }

    }

    public void recibir() {

        byte[] tramaReceived = receiveRsp();
        comando++;

        if (tramaReceived == null) {
            comando = -1;
            panel.rspBox("POS ->  null");
            finalizarCo();
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        msgComplete = toListStringFrame(tramaReceived);

        for (String element : msgComplete) {
            stringBuilder.append(element);
        }

        System.out.println("rsp: " + stringBuilder.toString());
        String msgRecibido;

        if (tramaReceived.length == 1) {
            msgRecibido = decodeMsg(tramaReceived);
        } else {
            msgRecibido = Util.hex2AsciiStr(decodeMsg(tramaReceived));
            listMsgInput.add(msgRecibido);
        }
        panel.rspBox("POS ->  " + msgOnScreen(msgRecibido));
        if (msgRecibido.equals(Constans.SEND_REF_PENDING)) {
            panel.rspBox("Trama:  " + stringBuilder.toString());
            panel.rspBox("Referencia pendiente: " + Util.conversorAString(buscarDato(msgComplete, 43)) + "\n");
        } else if (msgRecibido.equals(Constans.CIERRE_CANTIDAD)) {
            panel.rspBox("Trama:  " + stringBuilder.toString());
            panel.rspBox("Cantidad Trx: " + Util.conversorAString(buscarDato(msgComplete, 90)) + "\n");
        } else if (msgRecibido.equals(Constans.DATOS_CIERRE)) {
            panel.rspBox("Trama:  " + stringBuilder.toString());
            panel.rspBox("Trx No: " + ComunicationClose.countTrx);
            desempaquetarDatos(msgComplete, false);
        } else {
            panel.rspBox("Trama:  " + stringBuilder.toString() + "\n");
        }

        if (msgRecibido.equals(Constans.RESP_ERROR) || msgRecibido.equals(Constans.NACK_STRING)) {
            panel.rspBox("POS ->  " + manejoError(msgComplete));
        }
    }

    /**
     * Metodo utilizado para armar la trama de acuerdo que será enviada a la
     * caja
     *
     * @param tamTrama tamaño que va a tener la trama completa
     * @param tipoTrans tipo de transacción realizada ASCII
     * @param presentationHeader arreglo de byte del tipo de transacción
     * @return arreglo de bytes de la trama que será enviada
     */
    protected byte[] armarTrama(int tamTrama, String tipoTrans, byte[] presentationHeader) {
        byte[] retorno = new byte[tamTrama];

        retorno[0] = Constans.STX;
        //en la pos [1]y[2] va la longitud de la trama
        //se lleva un registro de en que parte debemos escribir
        int con = 3;
        //agregar TransportHeader--desde pos [3] hasta pos[12]
        for (int i = 0; i < Constans.transportHeader.length; i++) {
            retorno[i + 3] = Constans.transportHeader[i];
            con++;
        }
        //agregar presentationHeader--desde posi [13]  hasta [19]
        for (int i = 0; i < presentationHeader.length; i++) {
            retorno[i + 13] = presentationHeader[i];
            con++;
        }
        //hasta aqui ocupó 19 bytes
        //La longitud de este campo es variable dependiendo de lo que se vaya a enviar
        //campos --desde[20] hasta [37] en ultima transaccion
        //desde --[20] hasta 34 en nueva pantalla ingreso de tarjeta y nueva pantalla ingreso de PIN
        //desde --[20] hasta el 27 en solicitud de datos
        //desde --[20] hasta el [207] en respuesta host
        byte[] parteVariable = armarParteVariable(tipoTrans);
        int longMensaje;
        if (parteVariable != null) {
            for (int i = 0; i < parteVariable.length; i++) {
                retorno[i + 20] = parteVariable[i];
                con++;
            }
            longMensaje = Constans.transportHeader.length + Constans.PH_SOLICITUD_CONEXION.length + parteVariable.length;
        } else {
            longMensaje = Constans.transportHeader.length + Constans.PH_SOLICITUD_CONEXION.length;
        }

        //int longMensaje = Constans.transportHeader.length + Constans.PH_SOLICITUD_CONEXION.length + parteVariable.length;
        byte[] longMsj = calcularLongitudMensaje(longMensaje);
        retorno[1] = longMsj[0];
        retorno[2] = longMsj[1];

        retorno[con] = Constans.ETX;
        con++;
        retorno[con] = calcularLRC(retorno);

        return retorno;
    }

    /**
     * Metodo que desempaqueta los campos que llegan a la caja
     *
     * @param mensaje El mensaje o la trama completa recibida
     */
    protected int desempaquetarDatos(ArrayList<String> mensaje, boolean addList) {

        try {
            String codigoAut = Util.conversorAString(buscarDato(mensaje, 01));
            String montoString = Util.conversorAString(buscarDato(mensaje, 40));
            String numRecibo = Util.conversorAString(buscarDato(mensaje, 43));
            String RRN = Util.conversorAString(buscarDato(mensaje, 44));
            String tid = Util.conversorAString(buscarDato(mensaje, 45));
            String dateTXR = Util.conversorAString(buscarDato(mensaje, 46));
            String timeTXR = Util.conversorAString(buscarDato(mensaje, 47));
            String codRsp = Util.conversorAString(buscarDato(mensaje, 48));
            String typeAccount = Util.conversorAString(buscarDato(mensaje, 50));
            String numCuotas = Util.conversorAString(buscarDato(mensaje, 51));
            String last4Digits = Util.conversorAString(buscarDato(mensaje, 54));
            String msgError;
            if (addList) {
                msgError = Util.conversorAString(buscarDato(mensaje, 61));
            } else {
                msgError = Util.conversorAString(buscarDato(mensaje, 75));
            }

            Trx trx = new Trx(codigoAut, montoString, numRecibo, RRN, tid, dateTXR, timeTXR, codRsp, typeAccount, numCuotas, last4Digits, msgError);
            if (addList) {
                listTrx.add(trx);
            }
            panel.rspBox(panel.dataTrx(trx));
        } catch (Exception e) {
            System.out.println("Error al desempaquetar los datos ");
            return 2;
        }
        return 0;
    }

    public static byte[] armarTrama48XX() {
        byte[] retorno = new byte[7];
        byte[] codigoResp = ComunicationTools.hacerCodigoRespuesta(false);
        System.arraycopy(codigoResp, 0, retorno, 0, codigoResp.length);

        return retorno;
    }

    protected byte[] tramaEnvioDatos() {

        String montoPadleft = Util.padleft(monto + "", 12, '0');
        int dinamicLength = 0;
        byte[] frame = new byte[100];
        byte[] campoMonto = ComunicationTools.crearCampo(montoPadleft, 40, 12);
        System.arraycopy(campoMonto, 0, frame, 0, campoMonto.length);
        dinamicLength += campoMonto.length;

        byte[] noCaja = ComunicationTools.crearCampo("123", 42, 10);//10
        System.arraycopy(noCaja, 0, frame, dinamicLength, noCaja.length);
        dinamicLength += noCaja.length;

        byte[] codigoResp = ComunicationTools.hacerCodigoRespuesta(true);
        System.arraycopy(codigoResp, 0, frame, dinamicLength, codigoResp.length);
        dinamicLength += codigoResp.length;

        if (panel.checkCuota.isSelected()) {

            int numero = (int) panel.jSpnCuotas.getValue();
            String numCuota;
            if (numero < 9) {
                numCuota = "0" + numero;
            } else {
                numCuota = String.valueOf(numero);
            }
            byte[] noCuota = ComunicationTools.crearCampo(numCuota, 51, 2);
            System.arraycopy(noCuota, 0, frame, dinamicLength, noCuota.length);
            dinamicLength += noCuota.length;

        }

        byte[] noTrx = ComunicationTools.crearCampo("123456", 53, 10);//10
        System.arraycopy(noTrx, 0, frame, dinamicLength, noTrx.length);
        dinamicLength += noTrx.length;

        byte[] typeAccount = ComunicationTools.crearCampo("1", 88, 12);//12
        System.arraycopy(typeAccount, 0, frame, dinamicLength, typeAccount.length);
        dinamicLength += typeAccount.length;

        byte[] etx = {Constans.ETX};
        System.arraycopy(etx, 0, frame, dinamicLength, etx.length);

        byte[] frame2 = ComunicationTools.dicardEmpty(frame);
        byte[] retorno = new byte[frame2.length - 2];
        for (int i = 0; i < frame2.length - 2; i++) {
            retorno[i] = frame2[i];
        }

        return retorno;
    }

    //metodos abstractos---------------------------------------------------------------------------
    public abstract byte[] armarTramaVariable(String tipo);

    public abstract void mensajeria();
}
