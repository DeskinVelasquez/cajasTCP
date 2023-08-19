/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.practice;

import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author WPOSS
 */
public class MarcoCalculadora extends JFrame {
    
    public MarcoCalculadora() {
        setTitle("Calculadora");
        setBounds(500, 300, 450, 300);
        PanelCalculadora panelCalculadora = new PanelCalculadora();
        add(panelCalculadora);
        //pack();
    }

    
}
