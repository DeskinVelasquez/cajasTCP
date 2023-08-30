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

    public static boolean alert(boolean b, String msg){
        if (b) {
            JOptionPane.showMessageDialog(null, msg);
            return true;
        }
        return false;
    }
    
}
