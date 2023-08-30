/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Deskin Velasquez
 */
public class ImpMouseMotionListener implements MouseMotionListener{

  
     @Override
    public void mouseDragged(MouseEvent e) {
       System.out.println("estas arrastranso el ratón");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // System.out.println("estas moviendo el ratón");
    }
}
