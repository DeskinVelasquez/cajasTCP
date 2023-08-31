/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import com.cajatcp.view.JPanelPrincipal;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 *
 * @author Deskin Velasquez
 */
public class ImpWindowFocusListener implements WindowFocusListener {
    
    @Override
    public void windowGainedFocus(WindowEvent e) {
        System.out.println("La ventana ha ganado el foco");
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        System.out.println("La ventana ha perdido el foco");
    }
    
}
