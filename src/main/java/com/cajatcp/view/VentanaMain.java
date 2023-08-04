/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author WPOSS
 */
public class VentanaMain implements WindowListener {

    @Override
    public void windowOpened(WindowEvent e) {
       System.out.println("window opened");
    }

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
    
}
