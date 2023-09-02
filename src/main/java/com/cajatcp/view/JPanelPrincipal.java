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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;

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
    private JButton generico;
    private JTextField textField1;
    private JLabel jLabelMonto;
    private JLabel jLabelTitle;
    private JLabel jLabelIP;
    private JLabel jLabelPORT;
    private JPasswordField textField2;
    private JTextArea jTextArea;
    private JCheckBox jCheckBox;
    private JSlider jSlider;
    private Graphics g;
    private JCheckBoxMenuItem styleItalic;
    private JCheckBoxMenuItem styleBold;
    private JCheckBoxMenuItem stylePlain;
       
    public JPanelPrincipal(int widthScreen, int heightScreen) {
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        //se deshabilita el posicionamiento de componestes para hacerlo manualmente
        setLayout(null);
        
        //se muestran los botones UI
        showLabels();
        showMenuBar();
        showTextArea();
        showButtons();
        showTextField();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //this.g =g;
        //String[] fonts = getFonts();
       // Font font = new Font(fonts[0], Font.BOLD, 16);
       // g.setFont(font);
        //g.drawString("DemoCajas TCP", 20, 20);
        //g.drawLine(20, 25, widthScreen/2, 25);
        //font = new Font(fonts[0], Font.BOLD, 12);
        //g.setFont(font);
        //g.drawString("IP: " + obtenerIP(), 20, 40);
        //drawPort();
        //g.drawLine(20, 50, (widthScreen)-40, 50);
        //g.drawImage(getImage(), (widthScreen/2)+60, 2, null); 
         
        
    }
    
    private void showMenuBar() {
        //barra menu base-----------------------------------------
        JMenuBar barraMenu = new JMenuBar();
        
        //components--------------------------------------------------
        JMenu archivo = new JMenu(Constans.FILE);
        JMenu edit = new JMenu(Constans.EDIT);
        JMenu settings = new JMenu(Constans.SETTINGS);
        JMenu view = new JMenu(Constans.VIEW);
        
        //submenu---------------------------------------------------
        JMenu menuAppearance = new JMenu(Constans.APPEARANCE);
        JMenu font = new JMenu(Constans.STR_FONT);
        JMenu size = new JMenu(Constans.STR_SIZE);
        JMenu style = new JMenu(Constans.STR_STYLE);
        
        //items componentes-----------------------------------------------
        ArrayList<JMenuItem> listItems = new ArrayList<>();
        JMenuItem appearanceLight = new JMenuItem(Constans.APPEARANCE_LIGHT);
        JMenuItem appearanceDark = new JMenuItem(Constans.APPEARANCE_DARK);
        JMenuItem configPort = new JMenuItem(Constans.STR_CONFIG_PORT);
        
        
        //items con iconos-----------------------------------------------------
        ImageIcon iconSave = new ImageIcon("src/images/disquete.png");
        ImageIcon icScaled = new ImageIcon(iconSave.getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT));
        
        JMenuItem save = new JMenuItem(Constans.STR_SAVE, icScaled);
        JMenuItem saveAs = new JMenuItem(Constans.STR_SAVE_AS, icScaled);
        
        //para checkBoxMenuItem------------------------------------------------
        styleItalic = new JCheckBoxMenuItem(Constans.STR_STYLE_ITALIC);
        styleBold = new JCheckBoxMenuItem(Constans.STR_STYLE_BOLD);
        stylePlain = new JCheckBoxMenuItem(Constans.STR_STYLE_PLAIN);
        
        //para los radioButtons en la barra de menu-------------------------
        ButtonGroup sizeLetter = new ButtonGroup();
        JRadioButtonMenuItem size8 = new JRadioButtonMenuItem("8");
        JRadioButtonMenuItem size10 = new JRadioButtonMenuItem("10");
        JRadioButtonMenuItem size12 = new JRadioButtonMenuItem("12");
        JRadioButtonMenuItem size14 = new JRadioButtonMenuItem("14");
        JRadioButtonMenuItem size16 = new JRadioButtonMenuItem("16");
        
        sizeLetter.add(size8);
        sizeLetter.add(size10);
        sizeLetter.add(size12);
        sizeLetter.add(size14);
        sizeLetter.add(size16);
        
        //añadir action listener a los items----------------------------------
        listItems.add(appearanceLight);
        listItems.add(appearanceDark);
        listItems.add(configPort);
        listItems.add(styleItalic);
        listItems.add(styleBold);
        listItems.add(stylePlain);
        listItems.add(size8);
        listItems.add(size10);
        listItems.add(size12);
        listItems.add(size14);
        listItems.add(size16);
        
        
        String[] fonts = getFonts();
        for (int i = 0; i < fonts.length; i++) {
            JMenuItem element = new JMenuItem(fonts[i]);
            listItems.add(element);
            font.add(element);
        }
        for (JMenuItem element: listItems) {
            addActionItemMenu(element);
        }
        
        //añadir a archivo----------------------------------
        archivo.add(save);
        archivo.add(saveAs);
        
        //añadir a apariencia----------------------------------
        menuAppearance.add(appearanceLight);
        menuAppearance.add(appearanceDark);
        
        //añadir a vista----------------------------------
        view.add(menuAppearance);
        
        //añadir a configuracion----------------------------------
        settings.add(configPort);
        
        //añadir a editar----------------------------------
        edit.add(style);
        edit.add(font);
        edit.add(size);
        
        //añadir a estilo----------------------------------
        style.add(styleItalic);
        style.add(styleBold);
        style.add(stylePlain);
        
        //añadir a tamaño----------------------------------
        size.add(size8);
        size.add(size10);
        size.add(size12);
        size.add(size14);
        size.add(size16);
        
        //añadir a barra base----------------------------------
        barraMenu.add(archivo);
        barraMenu.add(edit);
        barraMenu.add(settings);
        barraMenu.add(view);
       
        barraMenu.setBounds(500, 5, 250, 20);
        
        add(barraMenu);
    }
    
    private void addActionItemMenu(JMenuItem item){
        ExtAbstractAction action = new ExtAbstractAction(item.getName(), this);
        item.addActionListener(action);
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
        //ExtAbstractAction actionGenerico = new ExtAbstractAction("generico", this);
        
        //Para el tema de multifuentes, podemos instanciar los botones de la siguiente manera. 
        btnPagoQR = new JButton(actionQR);
        btnPagoIcc = new JButton(actionIcc);
        enableConnect = new JButton(actionEnableConnect);
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
        enableConnect.setBounds(505, 32, 90, 15);
        add(btnPagoQR);
        add(btnPagoIcc);
        add(enableConnect);
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
        
        textField1 = new JTextField();
        
        //agregando el listener de texto al jtextfield
        ImpDocumentListener documentListener = new ImpDocumentListener();
        Document document = textField1.getDocument();
        document.addDocumentListener(documentListener);
        textField1.setBounds(30, 80, 70, 20);
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
    
    public void showLabels() {
        jLabelTitle = new JLabel(Constans.STR_TITLE);
        jLabelIP = new JLabel("IP: " + obtenerIP());
        jLabelPORT = new JLabel("PORT: " + Constans.getPORT());
        jLabelMonto = new JLabel("Monto");
        
        jLabelTitle.setBounds(30, 20, 150, 20);
        jLabelIP.setBounds(190, 20, 150, 20);
        jLabelPORT.setBounds(360, 20, 150, 20);
        jLabelMonto.setBounds(30, 60, 70, 20);
        
        
        
        add(jLabelTitle);
        add(jLabelIP);
        add(jLabelPORT);
        add(jLabelMonto);
    }
    
    public void darkTheme(){
        setBackground(Color.BLACK);
        jTextArea.setBackground(Color.LIGHT_GRAY);
        jLabelTitle.setForeground(Color.WHITE);
        jLabelIP.setForeground(Color.WHITE);
        jLabelPORT.setForeground(Color.WHITE);
        jLabelMonto.setForeground(Color.WHITE);
        //btnPagoIcc.set (Color.white);
        //btnPagoQR.setBackground(Color.BLACK);
        //btnPagoIcc.setBackground(Color.BLACK);
        //btnPagoQR.setForeground(Color.WHITE);
        //btnPagoIcc.setForeground(Color.WHITE);
    }
    
    public void lightTheme(){
        setBackground(Color.LIGHT_GRAY);
        jTextArea.setBackground(Color.WHITE);
        jLabelTitle.setForeground(Color.BLACK);
        jLabelIP.setForeground(Color.BLACK);
        jLabelPORT.setForeground(Color.BLACK);
        jLabelMonto.setForeground(Color.BLACK);
        //btnPagoIcc.set (Color.white);
        //btnPagoQR.setBackground(Color.BLACK);
        //btnPagoIcc.setBackground(Color.BLACK);
        //btnPagoQR.setForeground(Color.WHITE);
        //btnPagoIcc.setForeground(Color.WHITE);
    }
    
    public void fontsViewMain(String fuente) {
        Font font = new Font(fuente, Constans.getSTYLE(), Constans.getSIZE_FONT());
        Constans.setFONT(fuente);
        jTextArea.setFont(font); 
    }
    public void sizeViewMain(int size) {
        Font font = new Font(Constans.getFONT(), Constans.getSTYLE(), size);
        Constans.setSIZE_FONT(size);
        jTextArea.setFont(font); 
    }
    public void styleViewMain(int style) {
        boolean isItalic = styleItalic.isSelected();
        boolean isBold = styleBold.isSelected();
        
        if ((style == Font.ITALIC)) {
            stylePlain.setSelected(false);
            if (isItalic) {
                if (isBold) {
                    style = 3;
                }
            } else {
                if (isBold) {
                    stylePlain.setSelected(false);
                    style = Font.BOLD;
                } else {
                    style = Font.PLAIN;
                }
            }
            
        }
        
        if ((style == Font.BOLD)) {
            if (isBold) {
                if (isItalic) {
                    stylePlain.setSelected(false);
                    style = 3;
                }
            } else {
                if (isItalic) {
                    stylePlain.setSelected(false);
                    style = Font.ITALIC;
                } else {
                    style = Font.PLAIN;
                }
            }
        }
        
        if ((style == Font.PLAIN)) {
            if (isItalic) {
                styleItalic.setSelected(false);
            }
            if (isBold) {
                style = Font.BOLD;
            }
        }
        Font font = new Font(Constans.getFONT(), style, Constans.getSIZE_FONT());
        Constans.setSTYLE(style);
        jTextArea.setFont(font); 
    }
}
