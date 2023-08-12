/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.cajatcp;

import com.cajatcp.view.Marco;
import com.cajatcp.view.listeners.FocusListenerWindowMain;
import com.sun.java.accessibility.util.AWTEventMonitor;
import javax.swing.JFrame;

/**
 *Esta clase contiene el metodo main
 * @author WPOSS
 */
public class Cajatcp {

    public static void main(String[] args) {
       Marco marco = new Marco();
       marco.setVisible(true);
       marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cancelar ejecución del programa al cerrar la ventana
       
       marco.addWindowFocusListener(new FocusListenerWindowMain());
    }
}
