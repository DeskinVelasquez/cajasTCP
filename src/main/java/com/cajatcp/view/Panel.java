/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view;

import com.cajatcp.view.listeners.FocusMain;
import com.cajatcp.view.listeners.MultiFuenteMain;
import java.awt.BorderLayout;
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
import java.security.KeyStore;
import javax.naming.Context;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *Clase que crea los paneles o laminas que tendr� la ventana. 
 * @author WPOSS
 */
public class Panel extends JPanel /*implements ActionListener*/ {
    
    private int widthScreen = 0;
    private int heightScreen = 0;
    private JButton p1;
    private JButton p2;
    private JButton p3;
    private JButton p4;
    private JTextField textField1;
    private JTextField textField2;
       
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
        
        //agregando cuadros de texto, para el tema del evento foco
        //primero invalidamos el layout: el layuot es la disposicion que tienen los componentes en el panel (orden de componentes), por defecto java los ubica automaticamente por ello lo invalidamos
        setLayout(null);
        textField1 = new JTextField();
        textField2 = new JTextField();
        
        textField1.setBounds(250, 80, 150, 20);
        textField2.setBounds(250, 100, 150, 20);
        
        add(textField1);
        add(textField2);
        
        //agregando cuadros de texto, para el tema del evento foco
        textField1.addFocusListener(new FocusMain());
        textField2.addFocusListener(new FocusMain());
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
            File file = new File("src/images/logo_red_enlace.png");
            Image image = ImageIO.read(file);
            if (image != null) {
                return image.getScaledInstance(widthScreen/7, heightScreen/20, widthScreen);
            }
        } catch (IOException ex) {
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Imagen no disponible: " + ex.getMessage());
        } 
        return null;
    }
    
    private void showButtons() {
        
        //Se crea el icono de la imagen para redimensionarlo
         ImageIcon imageIcon = redimensionarIcono(new ImageIcon("src/images/ic_logo.png").getImage());
        //ImageIcon imageIcon = new ImageIcon("src/images/ic_logo.png");
        
        //se crean las instancias de multifuente para cada boton
        MultiFuenteMain actionP1 = new MultiFuenteMain("producto 1", imageIcon, Color.yellow, this);
        MultiFuenteMain actionP2 = new MultiFuenteMain("producto 2", imageIcon, Color.blue, this);
        MultiFuenteMain actionP3 = new MultiFuenteMain("producto 3",imageIcon, Color.red, this);
        MultiFuenteMain actionP4 = new MultiFuenteMain("producto 4",imageIcon, Color.green, this);
        
        //Para el tema de multifuentes, podemos instanciar los botones de la siguiente manera. 
        p1 = new JButton(actionP1);
        p2 = new JButton(actionP2);
        p3 = new JButton(actionP3);
        p4 = new JButton(actionP4);
        
        /*
        //se instancian los botones
        p1 = new JButton("Producto 1");
        p2 = new JButton("Producto 2");
        p3 = new JButton("Producto 3");
        */
        
               
        /* comentamos la action listener de cada boton, ya que la agregamos 
        previamente por multifuente, en este caso se ejecutará el metodo Action
        performed de la clase multiFuenteMain y no la de esta clase.
        //se agrega ActionListener a los botones
        p1.addActionListener(this);
        p2.addActionListener(this);
        p3.addActionListener(this);
        */
        
        //Se rehubican los botones
        /*setLayout(null);
        p1.setBounds(30, 80, 120, 20);
        p2.setBounds(30, 110, 120, 20);
        p3.setBounds(30, 130, 120, 20);*/
        
        //trabajando con posicionamientos de componentes del panel.
        setLayout(new BorderLayout());
        //se agregan los botones al panel
        add(p1, BorderLayout.SOUTH);
        add(p2, BorderLayout.NORTH);
        add(p3, BorderLayout.EAST);
        add(p4, BorderLayout.WEST);
        
        //InputMap proporciona un vinculo ante un evento y un objeto, normalmente es usado con un actionMap.
        InputMap mapaEntrada =getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        //creamos la clave con el control de teclas
        KeyStroke keyBackGroundYellow = KeyStroke.getKeyStroke("ctrl Y");
        KeyStroke keyBackGroundBlue = KeyStroke.getKeyStroke("ctrl B");
        KeyStroke keyBackGroundRed = KeyStroke.getKeyStroke("ctrl R");
        KeyStroke keyBackGroundRGreen = KeyStroke.getKeyStroke("ctrl G");
        
        //asignamos la combinacion de teclas al siguiente objeto
        mapaEntrada.put(keyBackGroundYellow, "fondo_amarillo");
        mapaEntrada.put(keyBackGroundBlue, "fondo_azul");
        mapaEntrada.put(keyBackGroundRed, "fondo_rojo");
        mapaEntrada.put(keyBackGroundRGreen, "fondo_verde");
        
        //se asigna el objeto a la action
        ActionMap actionMap = getActionMap();
        actionMap.put("fondo_amarillo", actionP1);
        actionMap.put("fondo_azul", actionP2);
        actionMap.put("fondo_rojo", actionP3);;
        actionMap.put("fondo_verde", actionP4);;
    }
    
    private ImageIcon redimensionarIcono(Image image){
        int newSize = image.getWidth(null)/25;
        return new ImageIcon(image.getScaledInstance(newSize, newSize, Image.SCALE_DEFAULT));
    }
    
    /*
    @Override
    public void actionPerformed(ActionEvent e) {
          Object btnPressed = e.getSource();
          if (btnPressed == p1) {
             //lógica botón 1
             
        } else if (btnPressed == p2) {
            //lógica botón 2
        } else {
            //lógica botón 3
        }
    }
*/
}
