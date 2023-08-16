/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import com.cajatcp.view.Panel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

/**
 *
 * @author WPOSS
 */
public class MultiFuenteMain /*implements Action*/ extends AbstractAction {
    public static final String COLOR_OBJETO_OYENTE = "COLOR_OBJETO_OYENTE";
    private Panel panel;
    
    public MultiFuenteMain(String nombre, Icon icono, Color color, Panel panel){
        
        //se guardan los parametro en clave valor, esto se guardara en el objeto evento del tipo ActionEvent
        putValue(Action.NAME, nombre);
        putValue(Action.SMALL_ICON, icono);
        putValue(Action.SHORT_DESCRIPTION, "Descripcion de prueba que indica el color: " + color.toString());
        putValue(COLOR_OBJETO_OYENTE, color);
        this.panel = panel;
    } 
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Color color = (Color) getValue(COLOR_OBJETO_OYENTE);

        panel.setBackground(color);
        System.out.println("Nombre: " + getValue(Action.NAME));
        System.out.println("Descripción: " + getValue(Action.SHORT_DESCRIPTION));
        
    }
    
}
