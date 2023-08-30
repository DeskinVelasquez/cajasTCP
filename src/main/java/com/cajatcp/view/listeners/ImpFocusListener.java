/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import com.cajatcp.Utils.Constans;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author Deskin Velasquez
 */
public class ImpFocusListener implements FocusListener {
    private JTextField textField;
    
    public ImpFocusListener(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void focusGained(FocusEvent e) {
        System.out.println("He ganado el foco");
    }

    @Override
    public void focusLost(FocusEvent e) {
         System.out.println("He perdido el foco");
         Constans.setMONTO(textField.getText());
    }
    
}
