/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 *
 * @author WPOSS
 */
public class FocusMain implements FocusListener {

    @Override
    public void focusGained(FocusEvent e) {
        System.out.println("He ganado el foco");
    }

    @Override
    public void focusLost(FocusEvent e) {
         System.out.println("He perdido el foco");
    }
    
}
