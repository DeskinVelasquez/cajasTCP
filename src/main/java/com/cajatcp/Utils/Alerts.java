/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils;

import javax.swing.JOptionPane;

/**
 *
 * @author deskin velasquez
 */
public class Alerts {

    public static boolean alert(boolean b, String msg, int typeAlert){
        if (b) {
            JOptionPane.showMessageDialog(null, msg,"Alerta", typeAlert);
            return true;
        }
        return false;
    }
    public static String inputAlert(String msg){
        String inputValue = JOptionPane.showInputDialog(msg);
        if (inputValue != null && !inputValue.isEmpty()) {
            return inputValue;    
        }
       return null;
    }
    
}
