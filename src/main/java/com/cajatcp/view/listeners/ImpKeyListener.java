/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view.listeners;
import java.awt.event.*;

/**
 *Key listener es la clase que contiene los metodos de eventos de teclado
 * @author Deskin Velasquez
 */
public class ImpKeyListener implements KeyListener{

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("codigo de tecla: " + e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int codigo = e.getKeyCode();
        System.out.println("codigo de tecla: " + codigo);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("codigo de tecla: " + e.getKeyCode());
    }
    
}
