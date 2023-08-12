/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view;

import com.cajatcp.view.listeners.TecladoMain;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.*;

/**
 *Clase que crea los paneles o laminas que tendr� la ventana. 
 * @author WPOSS
 */
public class Panel extends JPanel implements ActionListener {
    
    private int widthScreen = 0;
    private int heightScreen = 0;
    private JButton p1;
    private JButton p2;
    private JButton p3;
       
    public Panel(int widthScreen, int heightScreen){
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        showButtons();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String[] fonts = getFonts();
        Font font = new Font(fonts[0], Font.BOLD, 16);
        g.setFont(font);
        g.drawString("DemoCajas TCP", 20, 20);
        g.drawLine(20, 25, widthScreen/4, 25);
        font = new Font(fonts[0], Font.BOLD, 12);
        g.setFont(font);
        g.drawString("IP_Caja: " + obtenerIP(), 20, 40);
        g.drawLine(20, 50, (widthScreen/2)-40, 50);
        g.drawImage(getImage(), (widthScreen/4)+60, 2, null); 
        
        //se agrega listener de teclado
        addKeyListener(new TecladoMain());
    }
    
    /**
     * Este metodo se usa para obtener el ip del dispositivo en el cual se esta corriendo el programa. 
     */
    private String obtenerIP() {
        InetAddress direccionIP;
        try {
            direccionIP = InetAddress.getLocalHost();
            String ip = direccionIP.getHostAddress();
            return ip;
        } catch (UnknownHostException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return null;
    }
    
    /**
     * Este metodo obtiene las fuentes con las que cuenta el equipo en donde esta
     * siendo ejecutado.
     * @return un arreglo de String que contiene el familyFonts del equipo. 
     */
    private String[] getFonts() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }
    
    private Image getImage() {
        try {
            File file = new File("logo_red_enlace.png");
            Image image = ImageIO.read(file);
            if (image != null) {
                return image.getScaledInstance(widthScreen/7, heightScreen/16, widthScreen);
            }
        } catch (IOException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Imagen no disponible: " + ex.getMessage());
        } 
        return null;
    }
    
    private void showButtons() {
        p1 = new JButton("Producto 1");
        p2 = new JButton("Producto 2");
        p3 = new JButton("Producto 3");
        add(p1);
        add(p2);
        add(p3);
        p1.addActionListener(this);
        p2.addActionListener(this);
        p3.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          Object btnPressed = e.getSource();
          if (btnPressed == p1) {
              setBackground(Color.red);
        } else if (btnPressed == p2) {
            setBackground(Color.blue);
        } else {
            setBackground(Color.yellow);
        }
    }
}
