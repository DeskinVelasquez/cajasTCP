/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.practice;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Deskin Velasquez
 */
public class JPanelCalculadora extends JPanel implements ActionListener{
    private JButton pantalla;
    private JPanel botonera;
    private StringBuilder textScreen = new StringBuilder();
    public JPanelCalculadora(){
        setLayout(new BorderLayout()); 
        pantalla = new JButton("0");
        pantalla.setEnabled(false);
        add(pantalla, BorderLayout.NORTH);
        
        botonera= new JPanel();
        botonera.setLayout(new GridLayout(4, 4));
        for (int i = 0; i < 12; i++) {
            putDigits(String.valueOf(i));
        }
      
        add(botonera, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (!action.equals("=")) {
            textScreen.append(e.getActionCommand());
        } else {

            String[] numeros = textScreen.toString().split("\\+");
            if (numeros.length == 1) {
               textScreen.replace(0, textScreen.length(), "");
               pantalla.setText("0");
               return;
            }
            textScreen.replace(0, textScreen.length(), "");
            int result = 0;
            for (String n : numeros) {
                result += Integer.parseInt(n);
            }
            textScreen.append(result);
  
        }
        pantalla.setText(textScreen.toString());
    }
    
    public void putDigits(String value) {
        JButton btn;
        if(value.equals("10")) {
            btn = new JButton("+");
        }else if(value.equals("11")) {
            btn = new JButton("=");
        }else {
        btn = new JButton(value);
        }
        btn.addActionListener(this);
        botonera.add(btn);
        
    }
}
