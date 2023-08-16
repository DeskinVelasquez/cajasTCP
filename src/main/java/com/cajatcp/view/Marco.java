/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view;

import com.cajatcp.view.listeners.VentanaMain;
import com.cajatcp.view.Panel;
import com.cajatcp.view.listeners.MouseMain;
import com.cajatcp.view.listeners.MouseMainListener;
import com.cajatcp.view.listeners.TecladoMain;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 * Clase para crear la ventana o marco
 * @author WPOSS
 */
public class Marco extends JFrame  {
    private int widthScreen = 0;
    private int heightScreen = 0;
    
    //constructor
    public Marco(){
        //setSize(400, 400);
        //setLocation(500, 500);
        getSizeScreen();
        //tambien se puede agregar una ventana con setbounds, que recibe cuatro parametros correspondientes al tamaÃ±o y posiciÃ³n de la pantalla
        setBounds(widthScreen/4, heightScreen/4, widthScreen/2, heightScreen/2);
        setResizable(true); //este metodo permite bloquear el redimensionamiento de la ventana
        setTitle("Cajas TCP"); // pone el titulo de la ventana
        
        //agregar un panel
        Panel panel = new Panel(widthScreen, heightScreen);
        add(panel);
        
        //se agrega listener de ventana
        addWindowListener(new VentanaMain());
        addWindowStateListener(new VentanaMain());
        
        //se agrega listener de teclado
        addKeyListener(new TecladoMain());
        
        //se agrega listener de Mouse
        addMouseListener(new MouseMain());
        
        //se agrega un mouse motion listener
        addMouseMotionListener(new MouseMainListener());
      
        
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
