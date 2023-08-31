/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view;

import com.cajatcp.Utils.Alerts;
import com.cajatcp.Utils.Constans;
import com.cajatcp.view.listeners.ImpFocusListener;
import com.cajatcp.view.listeners.ExtAbstractAction;
import com.cajatcp.view.listeners.ImpDocumentListener;
import com.cajatcp.view.listeners.ImpWindowFocusListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;

/**
 *Clase que crea los paneles o laminas que tendr� la ventana. 
 * @author Deskin Velasquez
 */
public class JPanelPrincipal extends JPanel /*implements ActionListener*/ {
    
    private int widthScreen = 0;
    private int heightScreen = 0;
    private JButton btnPagoQR;
    private JButton btnPagoIcc;
    private JButton enableConnect;
    private JButton configPort;
    private JButton generico;
    private JTextField textField1;
    private JLabel jLabelMonto;
    private JPasswordField textField2;
    private JTextArea jTextArea;
    private JCheckBox jCheckBox;
    private JSlider jSlider;
    private Graphics g;
       
    public JPanelPrincipal(int widthScreen, int heightScreen) {
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        //se deshabilita el posicionamiento de componestes para hacerlo manualmente
        setLayout(null);
        
        //se muestran los botones UI
        showTextArea();
        showButtons();
        showTextField();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g =g;
        String[] fonts = getFonts();
        Font font = new Font(fonts[0], Font.BOLD, 16);
        g.setFont(font);
        g.drawString("DemoCajas TCP", 20, 20);
        g.drawLine(20, 25, widthScreen/2, 25);
        font = new Font(fonts[0], Font.BOLD, 12);
        g.setFont(font);
        g.drawString("IP: " + obtenerIP(), 20, 40);
        drawPort();
        g.drawLine(20, 50, (widthScreen)-40, 50);
        //g.drawImage(getImage(), (widthScreen/2)+60, 2, null); 
         
        
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
                return image.getScaledInstance(widthScreen/2, heightScreen/10, widthScreen);
            }
        } catch (IOException ex) {
            Logger.getLogger(JPanelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Imagen no disponible: " + ex.getMessage());
        } 
        return null;
    }
    
    private void showButtons() {
        
        //Se crea el icono de la imagen para redimensionarlo
        // ImageIcon imageIcon = redimensionarIcono(new ImageIcon("src/images/ic_logo.png").getImage());
        //ImageIcon imageIcon = new ImageIcon("src/images/ic_logo.png");
        
        //se crean las instancias de multifuente para cada boton
        ExtAbstractAction actionQR = new ExtAbstractAction(Constans.PAGO_QR, redimensionarIcono(new ImageIcon("src/images/ic_qr.png").getImage()), this);
        ExtAbstractAction actionIcc = new ExtAbstractAction(Constans.PAGO_ICC, redimensionarIcono(new ImageIcon("src/images/ic_icc.png").getImage()), this);
        ExtAbstractAction actionEnableConnect = new ExtAbstractAction(Constans.STR_ENABLE_CONNECT, this);
        ExtAbstractAction actionConfigPort = new ExtAbstractAction(Constans.STR_CONFIG_PORT, this);
        //ExtAbstractAction actionGenerico = new ExtAbstractAction("generico", this);
        
        //Para el tema de multifuentes, podemos instanciar los botones de la siguiente manera. 
        btnPagoQR = new JButton(actionQR);
        btnPagoIcc = new JButton(actionIcc);
        enableConnect = new JButton(actionEnableConnect);
        configPort = new JButton(actionConfigPort);
       // generico = new JButton(actionGenerico);
        
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
        btnPagoQR.setBounds(30, 110, 130, 20);
        btnPagoIcc.setBounds(30, 140, 130, 20);
        enableConnect.setBounds(500, 5, 90, 15);
        configPort.setBounds(650, 5, 100, 15);
        
        add(btnPagoQR);
        add(btnPagoIcc);
        add(enableConnect);
        add(configPort);
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
        /*ActionMap actionMap = getActionMap();
        actionMap.put("fondo_amarillo", actionP1);
        actionMap.put("fondo_azul", actionP2);
        actionMap.put("fondo_rojo", actionP3);;*/
    }
    
    private ImageIcon redimensionarIcono(Image image){
        int newSizeX = image.getWidth(null)/30;
        int newSizeY = image.getHeight(null)/30;
        return new ImageIcon(image.getScaledInstance(newSizeX, newSizeY, Image.SCALE_DEFAULT));
    }
    
    private void showTextArea() {
        jTextArea = new JTextArea();

        //para que no se ensanche, sino que de un salto de linea al escribir texto
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);

        //para que no crezca a lo alto, sino que tnga barras de desplazamiento
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setBounds(30, 170, widthScreen-70, heightScreen/2);
        
        //para que el JScrollPane siempre se desplace al final
        DefaultCaret defaultCaret = (DefaultCaret) jTextArea.getCaret();
        defaultCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        add(jScrollPane);
    }
    
    public void rspBox(String rsp){
        jTextArea.append(rsp+"\n");
    }
    
    public void cambiarNombreBtnConecct(String newName){
        enableConnect.setText(newName);
        if (newName.equals(Constans.STR_ENABLE_CONNECT)) {
           enableConnect.setSize(100, 15); 
        } else {
            enableConnect.setSize(120, 15); 
        }
        
    }

    private void showTextField (){
        
        //agregando cuadros de texto, para el tema del evento foco
        //primero invalidamos el layout: el layuot es la disposicion que tienen los componentes en el panel (orden de componentes), por defecto java los ubica automaticamente por ello lo invalidamos
        setLayout(null);
        jLabelMonto = new JLabel("Monto");
        textField1 = new JTextField();
        
        //agregando el listener de texto al jtextfield
        ImpDocumentListener documentListener = new ImpDocumentListener();
        Document document = textField1.getDocument();
        document.addDocumentListener(documentListener);
        
        jLabelMonto.setBounds(30, 50, 70, 20);
        textField1.setBounds(30, 70, 70, 20);
        add(jLabelMonto);
        add(textField1);
        
        //agregando cuadros de texto, para el tema del evento foco
        textField1.addFocusListener(new ImpFocusListener(textField1));
    }
    
    public void configPort() {
        String strPort = Alerts.inputAlert("ingrese nuevo puerto");
        int port = 0;
        if (strPort != null) {
            try {
            port = Integer.parseInt(strPort);
            } catch (Exception e) {
                Alerts.alert(true, "puerto invalido");
                return;
            }
            Constans.setPORT(port);
            drawPort();
        }
    }
    public  void drawPort() {
        g.drawString("PORT: " + Constans.getPORT(), 150, 40);
    }
    
    private void showOthersComponetsSwing() {
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
        
        //comboBOx
        JComboBox jComboBox = new JComboBox();
        String[] itemsComboBox = {"element 1", "element 2","element 3"};
        jComboBox.addItem(itemsComboBox[0]);
        jComboBox.addItem(itemsComboBox[1]);
        jComboBox.addItem(itemsComboBox[2]);
        jComboBox.setBounds(300, 270, 130, 20);
        jComboBox.setEditable(true); //para que sea editable
        add(jComboBox);
        
        //JSlider
        jSlider = new JSlider();
        jSlider.setBounds(20, 270, 130, 100);
        jSlider.setMajorTickSpacing(25);
        jSlider.setMinorTickSpacing(5);
        jSlider.setPaintLabels(true);
        jSlider.setPaintTicks(true);
        jSlider.setPaintTrack(true);
        jSlider.setSnapToTicks(true); //para que solo tome valores de la barra
        
        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("deslizante en: " + jSlider.getValue());
            }
        });
        add(jSlider);
        
        //JSpinner
        JSpinner jSpinner;
       // JSpinner jSpinner = new JSpinner(new SpinnerDateModel()); //para representar fechas
       // JSpinner jSpinner = new JSpinner(new SpinnerListModel(new String[]{"Enero", "Febrero", "Marzo", "Abril"})); //para representar Listas
       // jSpinner.setBounds(500, 270, 150, 20);
       String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
       SpinnerListModel spinnerListModel = new SpinnerListModel(fonts);
       jSpinner = new JSpinner(spinnerListModel);
       Dimension dimension = new Dimension(200, 20);
       jSpinner.setLocation(500, 270);
       jSpinner.setPreferredSize(dimension);
       add(jSpinner);
        

    }
}
