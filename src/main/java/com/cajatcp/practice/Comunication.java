/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.practice;

import com.cajatcp.Constans;
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
import javax.swing.JOptionPane;

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
        socket = null;
        return "fallo en la conexion con el cliente";
    }
    
    public void receiveRsp(JPanelPrincipal panel) {
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
                    panel.rspBox("recibido: " + frame[0]);
                } catch (IOException ex) {
                    Logger.getLogger(Comunication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
    
    
    public boolean send(byte[] data) {
        try {
            if (socket == null) {
                JOptionPane.showMessageDialog(null, "Debe haber un cliente conectado");
                return false;
            }
            OutputStream os = socket.getOutputStream();
            os.write(data);
            os.flush();
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
        int len = data.length;

        byte b = data[0];
        byte c = data[1];
        if ((Byte.compare(b, ack) == 0 && Byte.compare(c, ack) == 0) || (Byte.compare(b, nack) == 0 && Byte.compare(c, nack) == 0)
                || (Byte.compare(b, ack) == 0 && Byte.compare(c, nack) == 0) || (Byte.compare(b, nack) == 0 && Byte.compare(c, ack) == 0) ) {
            return true;
        }

        return false;
    }
}
