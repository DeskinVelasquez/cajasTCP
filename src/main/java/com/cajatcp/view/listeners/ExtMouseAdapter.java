/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Esta clase gestiona los eventos de ratón
 * @author Deskin Velasquez
 */
public class ExtMouseAdapter extends MouseAdapter implements MouseMotionListener{

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("haz hecho click, posición (x,Y): " + e.getX() + ", " + e.getY());
       // System.out.println("count click: " + e.getClickCount()); //cantidad de clicks
    }


    @Override
    public void mousePressed(MouseEvent e) {
        
        if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
          System.out.println("haz pulsado el boton izquierdo");  
        } else if (e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
            System.out.println("haz pulsado el boton derecho");  
        } else {
            System.out.println("haz pulsado la rueda del ratón");  
        }
        
    }
 /*
    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }
*/
   
    
}
