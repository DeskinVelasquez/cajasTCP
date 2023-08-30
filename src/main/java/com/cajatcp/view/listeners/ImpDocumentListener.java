/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 *Esta clase gestiona los eventos de los cuadros de texto como cambios en el mismo
 * @author Deskin Velasquez
 */
public class ImpDocumentListener implements DocumentListener{

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            String text = e.getDocument().getText(0, e.getDocument().getLength());
            System.out.println("haz insertado texto: " + text);
        } catch (BadLocationException ex) {
            Logger.getLogger(ImpDocumentListener.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        System.out.println("haz borrado texto");
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
    
}
