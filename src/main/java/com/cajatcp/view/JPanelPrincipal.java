/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view;

import com.cajatcp.view.listeners.ImpFocusListener;
import com.cajatcp.view.listeners.ExtAbstractAction;
import com.cajatcp.view.listeners.ImpDocumentListener;
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
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Document;

/**
 *Clase que crea los paneles o laminas que tendr� la ventana. 
 * @author WPOSS
 */
public class JPanelPrincipal extends JPanel /*implements ActionListener*/ {
    
    private int widthScreen = 0;
    private int heightScreen = 0;
    private JButton p1;
    private JButton p2;
    private JButton p3;
    private JTextField textField1;
    private JPasswordField textField2;
    private JTextArea jTextArea;
    private JCheckBox jCheckBox;
       
    public JPanelPrincipal(int widthScreen, int heightScreen) {
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        //se deshabilita el posicionamiento de componestes para hacerlo manualmente
        setLayout(null);
        
        //se muestran los botones UI
        showBoxRadio();
        showButtons();
        showTextField();
        showTextArea();
                
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
         
        
    }
        /**
         * Este metodo se usa para obtener el ip del dispositivo en el cual se
         * esta corriendo el programa.
         */
    private String obtenerIP() {
        InetAddress direccionIP;
        try {
            direccionIP = InetAddress.getLocalHost();
            String ip = direccionIP.getHostAddress();
            return ip;
        } catch (UnknownHostException ex) {
            Logger.getLogger(JPanelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(JPanelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Imagen no disponible: " + ex.getMessage());
        } 
        return null;
    }
    
    private void showButtons() {
        
        //Se crea el icono de la imagen para redimensionarlo
         ImageIcon imageIcon = redimensionarIcono(new ImageIcon("src/images/ic_logo.png").getImage());
        //ImageIcon imageIcon = new ImageIcon("src/images/ic_logo.png");
        
        //se crean las instancias de multifuente para cada boton
        ExtAbstractAction actionP1 = new ExtAbstractAction("producto 1", imageIcon, Color.yellow, this);
        ExtAbstractAction actionP2 = new ExtAbstractAction("producto 2", imageIcon, Color.blue, this);
        ExtAbstractAction actionP3 = new ExtAbstractAction("producto 3",imageIcon, Color.red, this);
        
        //Para el tema de multifuentes, podemos instanciar los botones de la siguiente manera. 
        p1 = new JButton(actionP1);
        p2 = new JButton(actionP2);
        p3 = new JButton(actionP3);
        
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
        p1.setBounds(30, 80, 120, 20);
        p2.setBounds(30, 110, 120, 20);
        p3.setBounds(30, 140, 120, 20);
        
        add(p1);
        add(p2);
        add(p3);
        //add(p4);
        /*
        //trabajando con posicionamientos de componentes del panel.
        setLayout(new BorderLayout());
        //se agregan los botones al panel
        add(p1, BorderLayout.SOUTH);
        add(p2, BorderLayout.NORTH);
        add(p3, BorderLayout.EAST);
        add(p4, BorderLayout.WEST);
        */
        
        //InputMap proporciona un vinculo ante un evento y un objeto, normalmente es usado con un actionMap.
        InputMap mapaEntrada =getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        //creamos la clave con el control de teclas
        KeyStroke keyBackGroundYellow = KeyStroke.getKeyStroke("ctrl Y");
        KeyStroke keyBackGroundBlue = KeyStroke.getKeyStroke("ctrl B");
        KeyStroke keyBackGroundRed = KeyStroke.getKeyStroke("ctrl R");
        
        //asignamos la combinacion de teclas al siguiente objeto
        mapaEntrada.put(keyBackGroundYellow, "fondo_amarillo");
        mapaEntrada.put(keyBackGroundBlue, "fondo_azul");
        mapaEntrada.put(keyBackGroundRed, "fondo_rojo");
        
        //se asigna el objeto a la action
        ActionMap actionMap = getActionMap();
        actionMap.put("fondo_amarillo", actionP1);
        actionMap.put("fondo_azul", actionP2);
        actionMap.put("fondo_rojo", actionP3);;
    }
    
    private ImageIcon redimensionarIcono(Image image){
        int newSize = image.getWidth(null)/25;
        return new ImageIcon(image.getScaledInstance(newSize, newSize, Image.SCALE_DEFAULT));
    }
    
    private void showTextArea() {
        jTextArea = new JTextArea();

        //para que no se ensanche, sino que de un salto de linea al escribir texto
        jTextArea.setLineWrap(true);

        //para que no crezca a lo alto, sino que tnga barras de desplazamiento
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setBounds(30, 170, 150, 60);
        add(jScrollPane);
    }

    private void showTextField (){
        
        //agregando cuadros de texto, para el tema del evento foco
        //primero invalidamos el layout: el layuot es la disposicion que tienen los componentes en el panel (orden de componentes), por defecto java los ubica automaticamente por ello lo invalidamos
        setLayout(null);
        textField1 = new JTextField();
        textField2 = new JPasswordField();
        
        //agregando el listener de texto al jtextfield
        ImpDocumentListener documentListener = new ImpDocumentListener();
        Document document = textField1.getDocument();
        document.addDocumentListener(documentListener);
        
        textField1.setBounds(250, 80, 150, 20);
        textField2.setBounds(250, 100, 150, 20);
        
        add(textField1);
        add(textField2);
        
        //agregando cuadros de texto, para el tema del evento foco
        textField1.addFocusListener(new ImpFocusListener());
        textField2.addFocusListener(new ImpFocusListener());
    }
    
    private void showBoxRadio() {
        jCheckBox = new JCheckBox();
        jCheckBox.setBounds(500, 170, 150, 60);

        //asi se verifica si esta seleccionada la casilla. 
        jCheckBox.isSelected();

        //asi se le da una seleccion por defecto
        jCheckBox.setSelected(true);

        //para eventos de click
        //jCheckBox.addActionListener(l);
        
        //JRadioButton y ButtonGroup
        ButtonGroup buttonGroup = new ButtonGroup();

        //se instancian los botones
        JRadioButton btn1 = new JRadioButton();
        JRadioButton btn2 = new JRadioButton("boton 2");
        JRadioButton btn3 = new JRadioButton("boton 3", true);
        
        //se hubican
        btn1.setBounds(300, 170, 130, 20);
        btn2.setBounds(300, 200, 130, 20);
        btn3.setBounds(300, 230, 130, 20);

        //agregar los botones a grupo
        buttonGroup.add(btn1);
        buttonGroup.add(btn2);
        buttonGroup.add(btn3);
        
        //agregar botones a la lamina. nota: no se puede agregar un ButtonGroup al panel
        add(btn1);
        add(btn2);
        add(btn3);
        add(jCheckBox);

    }
}
