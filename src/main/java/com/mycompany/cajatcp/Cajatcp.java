/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.cajatcp;

import com.cajatcp.practice.JFrameCalculadora;
import com.cajatcp.view.JFramePrincipal;
import com.cajatcp.view.listeners.ImpWindowFocusListener;
import com.sun.java.accessibility.util.AWTEventMonitor;
import javax.swing.JFrame;

/**
 *Esta clase contiene el metodo main
 * @author WPOSS
 */
public class Cajatcp {

    public static void main(String[] args) {
        
       JFramePrincipal marco = new JFramePrincipal();
       marco.setVisible(true);
       marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cancelar ejecución del programa al cerrar la ventana
       marco.setResizable(false);//bloquear la maximizacion de la pantalla
       
       //se agrega el focus listener a la ventana principal.
       marco.addWindowFocusListener(new ImpWindowFocusListener());
       
      /*
       MarcoCalculadora marcoCalculadora = new MarcoCalculadora();
       marcoCalculadora.setVisible(true);
       marcoCalculadora.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cancelar ejecución del programa al cerrar la ventana
      */
    }
}
