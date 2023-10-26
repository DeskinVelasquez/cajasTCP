/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.cajatcp;

import com.cajatcp.Utils.Alerts;
import com.cajatcp.Utils.Comunication.ComunicationTools;
import com.cajatcp.view.JFramePrincipal;
import com.cajatcp.view.listeners.ImpWindowFocusListener;
import javax.swing.JFrame;

/**
 * Esta clase contiene el metodo main
 *
 * @author Deskin Velasquez
 */
public class Cajatcp {

    public static void main(String[] args) {

        int tipoCo = Alerts.showCommunicationChoice();

        if (tipoCo == 1) {
            ComunicationTools.isTCP = true;
        } else {
            ComunicationTools.isTCP = false;
        }

        JFramePrincipal marco = new JFramePrincipal();
        marco.getColorModel();
        marco.setVisible(true);
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cancelar ejecución del programa al cerrar la ventana
        marco.setResizable(false);//bloquear la maximizacion de la pantalla

        //se agrega el focus listener a la ventana principal.
        marco.addWindowFocusListener(new ImpWindowFocusListener());

    }
}

