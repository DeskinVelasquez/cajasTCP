/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.practice;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WPOSS
 */
public class Comunication {
    InputStream inputStream;
    OutputStream outputStream;
    private static Socket socket;
    ServerSocket serverSocket;

    public Comunication() {
      
    }
    
    public String enableConnect(){
        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Esperando una conexión...");
            socket = serverSocket.accept();
            if (socket != null && socket.isConnected()) {
                System.out.println("cliente conectado");
                return "cliente conectado";
            }
        } catch (IOException ex) {
            Logger.getLogger(Comunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fallo en la conexion con el cliente";
    }
    
    public String receiveRsp() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String receive = entrada.readLine();
            System.out.println("rsp: " + receive);
            return receive;
        } catch (IOException ex) {
            Logger.getLogger(Comunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean send(String data) { 
        try {
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println(hex2byte(data));
            salida.flush();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Comunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    /**
     * Converts a hex string into a byte array
     *
     * @param s source string (with Hex representation)
     * @return byte array
     */
    public byte[] hex2byte(String s) {
        if (s.length() % 2 == 0) {
            return hex2byte(s.getBytes(), 0, s.length() >> 1);
        } else {
            // Padding left zero to make it even size #Bug raised by tommy
            return hex2byte("0" + s);
        }
    }
    
    public byte[] hex2byte(byte[] b, int offset, int len) {
        byte[] d = new byte[len];
        for (int i = 0; i < len * 2; i++) {
            // Buginfo when i oddness then this line won't be work
            // but in the for judge i>0 & i++ so i absolutely won't be oddness
            int shift = ((i % 2 == 1) ? 0 : 4);
            d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
        }
        return d;
    }
    
      /**
     * Metodo para descartar los espacios sobrantes del arreglo temporal donde se recibe la trama
     * (SOLO MODO CAJA)
     *
     * @param data
     * @return arreglo de bytes con los datos exactos de la trama
     */
    private byte[] dicardEmpty(byte[] data) {
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
    
    private boolean manyAckAndNack(byte[] data) {
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
    public ArrayList<String> toListStringFrame(byte[] frame) {
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
    public static boolean validarHexaLetra(String hexa) {
        if (hexa.equalsIgnoreCase("a") || hexa.equalsIgnoreCase("b")
                || hexa.equalsIgnoreCase("c") || hexa.equalsIgnoreCase("d")
                || hexa.equalsIgnoreCase("e") || hexa.equalsIgnoreCase("f")) {
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Converts a byte array into a hex string
     *
     * @param bs source byte array
     * @return hexadecimal representation of bytes
     */
    public static String byte2hex(byte[] bs) {
        return byte2hex(bs, 0, bs.length);
    }
    
     /**
     * Converts a byte array into a string of lower case hex chars.
     *
     * @param bs     A byte array
     * @param off    The index of the first byte to read
     * @param length The number of bytes to read.
     * @return the string of hex chars.
     */
    public static String byte2hex(byte[] bs, int off, int length) {
        if (bs.length <= off || bs.length < off + length)
            throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder(length * 2);
        byte2hexAppend(bs, off, length, sb);
        return sb.toString().toUpperCase();
    }

    private static void byte2hexAppend(byte[] bs, int off, int length,
                                       StringBuilder sb) {
        if (bs.length <= off || bs.length < off + length)
            throw new IllegalArgumentException();
        sb.ensureCapacity(sb.length() + length * 2);
        for (int i = off; i < off + length; i++) {
            sb.append(Character.forDigit(bs[i] >>> 4 & 0xf, 16));
            sb.append(Character.forDigit(bs[i] & 0xf, 16));
        }
    }

}
