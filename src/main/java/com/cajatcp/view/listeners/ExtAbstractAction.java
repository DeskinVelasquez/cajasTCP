/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;

import com.cajatcp.Utils.Alerts;
import com.cajatcp.Utils.Constans;
import static com.cajatcp.Utils.Constans.APPEARANCE_DARK;
import static com.cajatcp.Utils.Constans.APPEARANCE_LIGHT;
import static com.cajatcp.Utils.Constans.BT_SOLICITUD_CONEXION;
import static com.cajatcp.Utils.Constans.BT_SOLICITUD_CONEXION_QR;
import static com.cajatcp.Utils.Constans.PAGO_ICC;
import static com.cajatcp.Utils.Constans.PAGO_QR;
import static com.cajatcp.Utils.Constans.STR_CONFIG_PORT;
import static com.cajatcp.Utils.Constans.STR_DISABLE_CONNECT;
import static com.cajatcp.Utils.Constans.STR_ENABLE_CONNECT;
import static com.cajatcp.Utils.Constans.STR_SIZE_10;
import static com.cajatcp.Utils.Constans.STR_SIZE_12;
import static com.cajatcp.Utils.Constans.STR_SIZE_14;
import static com.cajatcp.Utils.Constans.STR_SIZE_16;
import static com.cajatcp.Utils.Constans.STR_SIZE_8;
import static com.cajatcp.Utils.Constans.STR_STYLE_BOLD;
import static com.cajatcp.Utils.Constans.STR_STYLE_ITALIC;
import static com.cajatcp.Utils.Constans.STR_STYLE_PLAIN;
import com.cajatcp.Utils.Comunication.Comunication;
import static com.cajatcp.Utils.Constans.STR_CLEAR;
import static com.cajatcp.Utils.Constans.STR_READ_FILE;
import static com.cajatcp.Utils.Constans.STR_SAVE;
import static com.cajatcp.Utils.Constans.STR_SAVE_TRX;
import static com.cajatcp.Utils.Constans.STR_VIEW_TRXS;
import com.cajatcp.view.JPanelPrincipal;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

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
                if (/*!Alerts.alert(Constans.getMONTO().equals("0"), "Debe haber un monto", 2)
                        &&*/ co.send(BT_SOLICITUD_CONEXION)) {
                    System.out.println("Envio exitoso");
                }
                break;
            case STR_ENABLE_CONNECT:
                panel.cambiarNombreBtnConecct(STR_DISABLE_CONNECT);
                co.openConnect();
                co.receiveRsp();
                break;
            case PAGO_QR:
                if (/*!Alerts.alert(Constans.getMONTO().equals("0"), "Debe haber un monto", 2)
                        &&*/co.send(BT_SOLICITUD_CONEXION_QR)) {
                    System.out.println("Envio exitoso");
                }
                break;
            case STR_DISABLE_CONNECT:
                System.out.println("desconectado");
                panel.cambiarNombreBtnConecct(STR_ENABLE_CONNECT);
                panel.rspBox(co.disaableConnect());
                break;
            case STR_CONFIG_PORT:
                panel.configPort();
                panel.rspBox("Nuevo puerto: " + Constans.getPORT());
                break;
            case APPEARANCE_LIGHT:
                System.out.println(APPEARANCE_LIGHT);
                panel.lightTheme();
                break;
            case APPEARANCE_DARK:
                System.out.println(APPEARANCE_DARK);
                panel.darkTheme();
                break;
            case STR_STYLE_BOLD:
                panel.styleViewMain2(Font.BOLD);
                break;
            case STR_STYLE_ITALIC:
                panel.styleViewMain2(Font.ITALIC);
                break;
            case STR_STYLE_PLAIN:
                panel.styleViewMain2(Font.PLAIN);
                break;
            case STR_SIZE_8:
            case STR_SIZE_10:
            case STR_SIZE_12:
            case STR_SIZE_14:
            case STR_SIZE_16:
                panel.sizeViewMain(Integer.parseInt(e.getActionCommand()));
                break;
            case STR_READ_FILE:
                panel.leerBufferFichero();
                break;
            case STR_SAVE:
                panel.escribirBufferFichero();
                break;
            case STR_SAVE_TRX:
                panel.saveTrxs();
                break;
            case STR_CLEAR:
                panel.clearRegistry();
                break;
            case STR_VIEW_TRXS:
                panel.viewTrxs();
                break;
            default:
                System.out.println(e.getActionCommand());
                panel.fontsViewMain(e.getActionCommand());
                break;
        }
    }
    
   

}
