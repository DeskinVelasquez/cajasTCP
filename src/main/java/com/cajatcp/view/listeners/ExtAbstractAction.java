/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import com.cajatcp.Constans;
import com.cajatcp.practice.Comunication;
import com.cajatcp.view.JPanelPrincipal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JPanel;

/**
 *
 * @author WPOSS
 */
public class ExtAbstractAction /*implements Action*/ extends AbstractAction {
    public static final String COLOR_OBJETO_OYENTE = "COLOR_OBJETO_OYENTE";
    private JPanelPrincipal panel;
    
    public ExtAbstractAction(String nombre, Icon icono, JPanelPrincipal panel){
        //se guardan los parametro en clave valor, esto se guardara en el objeto evento del tipo ActionEvent
        putValue(Action.NAME, nombre);
        putValue(Action.SMALL_ICON, icono);
        this.panel = panel;
    } 
    
    public ExtAbstractAction(String nombre, JPanelPrincipal jpanel) {

        //se guardan los parametro en clave valor, esto se guardara en el objeto evento del tipo ActionEvent
        putValue(Action.NAME, nombre);
        panel = jpanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nombre = (String) getValue(NAME);
        final Comunication co = new Comunication();
        String rsp = "cliente no conectado\n";

        switch (nombre) {
            case Constans.PAGO_ICC:
                
                if(co.send(Constans.BT_SOLICITUD_CONEXION)){
                    System.out.println("Envio exitoso"); 
                }; 
                break;
            case Constans.STR_ENABLE_CONNECT:
                panel.rspBox(co.enableConnect());
                co.receiveRsp(panel);
                break;
        default:
            break;
        }
    }

}
