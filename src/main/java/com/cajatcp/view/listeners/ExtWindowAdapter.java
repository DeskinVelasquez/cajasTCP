/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Deskin Velasquez
 */
public class ExtWindowAdapter extends WindowAdapter {

    //extendiendo de la clase WindowAdapter, se omite la sobreescritura de metodos
    //debido a que dicha clase ya implementa las interfaces. 
    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("window opened");
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        //System.out.println("window change state");
        //System.out.println(e.getNewState());
        if (e.getNewState() == Frame.MAXIMIZED_BOTH) {
            System.out.println("La ventana esta a pantalla COMPLETA");
        } else if (e.getNewState() == Frame.NORMAL) {
            System.out.println("La ventana esta a pantalla NORMAL");
        }else if (e.getNewState() == Frame.ICONIFIED) {
            System.out.println("La ventana esta a pantalla MINIMIZADA");
        }
    }

    /*
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("window closing");
    }

    @Override
    public void windowClosed(WindowEvent e) {
    System.out.println("window closed");}

    @Override
    public void windowIconified(WindowEvent e) {
    System.out.println("window iconified");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("window deiconified");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        System.out.println("window activated");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        System.out.println("window deactivated");
    }
     */
}
