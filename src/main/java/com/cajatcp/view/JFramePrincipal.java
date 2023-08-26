/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view;

import com.cajatcp.view.listeners.ExtWindowAdapter;
import com.cajatcp.view.JPanelPrincipal;
import com.cajatcp.view.listeners.ExtMouseAdapter;
import com.cajatcp.view.listeners.ImpMouseMotionListener;
import com.cajatcp.view.listeners.ImpKeyListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 * Clase para crear la ventana o marco
 * @author WPOSS
 */
public class JFramePrincipal extends JFrame  {
    private int widthScreen = 0;
    private int heightScreen = 0;
    
    //constructor
    public JFramePrincipal(){
        //setSize(400, 400);
        //setLocation(500, 500);
        //dimensiones de la ventana+
        getSizeScreen();
        int ScreenX = widthScreen/2;
        int ScreenY = heightScreen/2;
        
        //tambien se puede agregar una ventana con setbounds, que recibe cuatro parametros correspondientes al tamaÃ±o y posiciÃ³n de la pantalla
        setBounds(ScreenX/2, ScreenY/2, ScreenX, ScreenY);
        setResizable(true); //este metodo permite bloquear el redimensionamiento de la ventana
        setTitle("Cajas TCP"); // pone el titulo de la ventana
        
        //agregar un panel
        JPanelPrincipal panel = new JPanelPrincipal(ScreenX, ScreenY);
        
        //trabajando con posicionamientos de componentes del panel.
        //FlowLayout flowLayout = new FlowLayout(FlowLayout.TRAILING);
        // panel.setLayout(flowLayout);
      
        add(panel);
        
        //se agrega listener de ventana
        addWindowListener(new ExtWindowAdapter());
        addWindowStateListener(new ExtWindowAdapter());
        
        //se agrega listener de teclado
        addKeyListener(new ImpKeyListener());
        
        //se agrega listener de Mouse
        addMouseListener(new ExtMouseAdapter());
        
        //se agrega un mouse motion listener
        addMouseMotionListener(new ImpMouseMotionListener());
      
        
    }
    
    
    
    //metodos de la clase Marco
    /**
     * Este metodo se usa para capturar las dimensiones de la pantalla en las 
     * variable globales, y cambia el icono de la ventana. 
     */
    private void getSizeScreen() {
       Toolkit toolKit = Toolkit.getDefaultToolkit();
       Dimension dimension = toolKit.getScreenSize();
       widthScreen = dimension.width;
       heightScreen = dimension.height;
       
       //Cambiar el icono de la ventana
       Image img = toolKit.getImage("ic_logo.png");
        setIconImage(img);
    }
    
    //getters y setters
    public int setWidthScreen(){
        return  widthScreen;
    }
    
}
