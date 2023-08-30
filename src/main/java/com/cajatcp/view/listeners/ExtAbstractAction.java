/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import com.cajatcp.Utils.Alerts;
import com.cajatcp.Utils.Constans;
import static com.cajatcp.Utils.Constans.BT_SEND_DATA;
import static com.cajatcp.Utils.Constans.BT_SOLICITUD_CONEXION;
import static com.cajatcp.Utils.Constans.BT_SOLICITUD_CONEXION_QR;
import static com.cajatcp.Utils.Constans.PAGO_ICC;
import static com.cajatcp.Utils.Constans.PAGO_QR;
import static com.cajatcp.Utils.Constans.STR_DISABLE_CONNECT;
import static com.cajatcp.Utils.Constans.STR_ENABLE_CONNECT;
import com.cajatcp.Utils.Util;
import com.cajatcp.practice.Comunication;
import com.cajatcp.view.JPanelPrincipal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JPanel;
import java.util.ArrayList;

/**
 *
 * @author Deskin Velasquez
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
        final Comunication co = new Comunication();
        co.setPanel(panel);
        
        switch (e.getActionCommand()) {
            case PAGO_ICC:
                if (!Alerts.alert(Constans.getMONTO().equals("0"), "Debe haber un monto")
                        && co.send(BT_SOLICITUD_CONEXION)) {
                    System.out.println("Envio exitoso");
                }
                break;
            case STR_ENABLE_CONNECT:
                panel.cambiarNombreBtnConecct(STR_DISABLE_CONNECT);
                panel.rspBox(co.enableConnect());
                co.receiveRsp();
                break;
            case PAGO_QR:
                if (!Alerts.alert(Constans.getMONTO().equals("0"), "Debe haber un monto")
                        &&co.send(BT_SOLICITUD_CONEXION_QR)) {
                    System.out.println("Envio exitoso");
                }
                break;
            case STR_DISABLE_CONNECT:
                System.out.println("desconectado");
                panel.cambiarNombreBtnConecct(STR_ENABLE_CONNECT);
                panel.rspBox(co.disaableConnect());
                break;
            default:
                break;
        }
    }
    
   

}
