/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.view;

import DataManager.DataClasses.Trx;
import DataManager.Fichero;
import com.cajatcp.Utils.Alerts;
import com.cajatcp.Utils.Comunication.ComunicationTools;
import com.cajatcp.Utils.Constans;
import com.cajatcp.Utils.Util;
import com.cajatcp.view.listeners.ImpFocusListener;
import com.cajatcp.view.listeners.ExtAbstractAction;
import com.cajatcp.view.listeners.ImpDocumentListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.Document;

/**
 *Clase que crea los paneles o laminas que tendr� la ventana. 
 * @author Deskin Velasquez
 */
public final class JPanelPrincipal extends JPanel /*implements ActionListener*/ {
    
    private int widthScreen = 0;
    private int heightScreen = 0;
    private JButton btnPagoQR;
    private JButton btnPagoIcc;
    private JButton btnPagoCtl;
    private JButton btnPagoTigo;
    private JButton btnInit;
    private JButton btnClose;
    private JButton btnVoid;
    private JButton enableConnect;
    private JButton clearTextArea;
    private JTextField textField1;
    private JTextField tfVoid;
    private JLabel jLabelMonto;
    private JLabel jLabelMulti;
    private JLabel jLabelIP;
    private JLabel jLabelPORT;
    private final JTextArea jTextArea = new JTextArea();
    public static  JCheckBox checkMulti;
    public static  JSpinner jSpnAcquirer;
    private JSlider jSlider;
    private Graphics g;
    private JMenuItem styleItalic;
    private JMenuItem styleBold;
    private JMenuItem stylePlain;
    private JPanel jpBtns;
       
    public JPanelPrincipal(int widthScreen, int heightScreen) {
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        //se deshabilita el posicionamiento de componestes para hacerlo manualmente
        setLayout(null);
        
        //se muestran loscomponentes
        showComponents();
        
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
    
    public JMenuBar showMenuBar() {
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
        ImageIcon iconFile = new ImageIcon("src/images/ic_text.png");
        ImageIcon icScaled = new ImageIcon(iconSave.getImage().getScaledInstance(10, 10, Image.SCALE_DEFAULT));
        ImageIcon icFileScaled = new ImageIcon(iconFile.getImage().getScaledInstance(iconFile.getIconWidth()/35, iconFile.getIconHeight()/35, Image.SCALE_DEFAULT));
        
        JMenuItem save = new JMenuItem(Constans.STR_SAVE, icScaled);
        JMenuItem saveTrx = new JMenuItem(Constans.STR_SAVE_TRX, icScaled);
        JMenuItem readFile = new JMenuItem(Constans.STR_READ_FILE, icFileScaled);
        
        
        JMenuItem viewTrxs = new JMenuItem(Constans.STR_VIEW_TRXS);
        
        //para checkBoxMenuItem------------------------------------------------
        //styleItalic = new JCheckBoxMenuItem(Constans.STR_STYLE_ITALIC);
        //styleBold = new JCheckBoxMenuItem(Constans.STR_STYLE_BOLD);
        //stylePlain = new JCheckBoxMenuItem(Constans.STR_STYLE_PLAIN);
        
        //refactorizado el checkBox (ya no es checkBox------------------
        styleItalic = new JMenuItem(Constans.STR_STYLE_ITALIC);
        styleBold = new JMenuItem(Constans.STR_STYLE_BOLD);
        stylePlain = new JMenuItem(Constans.STR_STYLE_PLAIN);
        
        //se le agregan atajos de teclado a los items
        styleItalic.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_DOWN_MASK));
        styleBold.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        stylePlain.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        
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
        listItems.add(save);
        listItems.add(saveTrx);
        listItems.add(readFile);
        listItems.add(viewTrxs);

        String[] fonts = getFonts();
        for (String font1 : fonts) {
            JMenuItem element = new JMenuItem(font1);
            listItems.add(element);
            font.add(element);
        }
        for (JMenuItem element : listItems) {
            addActionItemMenu(element);
        }

        //añadir a archivo----------------------------------
        archivo.add(save);
        archivo.add(saveTrx);
        archivo.add(readFile);

        //añadir a apariencia----------------------------------
        menuAppearance.add(appearanceLight);
        menuAppearance.add(appearanceDark);

        //añadir a vista----------------------------------
        view.add(menuAppearance);
        view.add(viewTrxs);

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

        //add(barraMenu);
        
        return barraMenu;
        
    }
    
    public JButton showBtnConect () {
        return enableConnect;
    }
    public JButton showBtnClear () {
        return clearTextArea;
    }
    
    public void clearRegistry(){
        jTextArea.setText("");
    }
    
    private void insertarMenuEmergente(){
        String[] fonts = getFonts();
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenu font2 = new JMenu(Constans.STR_FONT);
        for (String font : fonts) {
            JMenuItem element = new JMenuItem(font);
            addActionItemMenu(element);
            font2.add(element);
        }

        JMenu size2 = new JMenu(Constans.STR_SIZE);
        JMenuItem size8_2 = new JMenuItem("8");
        JMenuItem size10_2 = new JMenuItem("10");
        JMenuItem size12_2 = new JMenuItem("12");
        JMenuItem size14_2 = new JMenuItem("14");
        JMenuItem size16_2 = new JMenuItem("16");
        addActionItemMenu(size8_2);
        addActionItemMenu(size10_2);
        addActionItemMenu(size12_2);
        addActionItemMenu(size14_2);
        addActionItemMenu(size16_2);
        size2.add(size8_2);
        size2.add(size10_2);
        size2.add(size12_2);
        size2.add(size14_2);
        size2.add(size16_2);
        
        JMenu style = new JMenu(Constans.STR_STYLE);
        JMenuItem italic = new JMenuItem(Constans.STR_STYLE_ITALIC);
        JMenuItem bold = new JMenuItem(Constans.STR_STYLE_BOLD);
        JMenuItem plain = new JMenuItem(Constans.STR_STYLE_PLAIN);
        
        addActionItemMenu(italic);
        addActionItemMenu(bold);
        addActionItemMenu(plain);
        style.add(italic);
        style.add(bold);
        style.add(plain);
        jPopupMenu.add(font2);
        jPopupMenu.add(size2);
        jPopupMenu.add(style);
        jTextArea.setComponentPopupMenu(jPopupMenu);
    }
    
    public void addActionItemMenu(JMenuItem item){
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
    public String[] getFonts() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }
    /*
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
    */
    private void showButtons() {
        jpBtns = new JPanel(new GridLayout(2, 3, 5, 5));
        //Se crea el icono de la imagen para redimensionarlo
        // ImageIcon imageIcon = redimensionarIcono(new ImageIcon("src/images/ic_logo.png").getImage());
        //ImageIcon imageIcon = new ImageIcon("src/images/ic_logo.png");
        
        //se crean las instancias de multifuente para cada boton
        ExtAbstractAction actionQR = new ExtAbstractAction(Constans.PAGO_QR, redimensionarIcono(new ImageIcon("src/images/ic_qr.png").getImage(), 120), this);
        ExtAbstractAction actionIcc = new ExtAbstractAction(Constans.PAGO_ICC, redimensionarIcono(new ImageIcon("src/images/ic_icc.png").getImage(), 30), this);
        ExtAbstractAction actionCtl = new ExtAbstractAction(Constans.PAGO_CTL, redimensionarIcono(new ImageIcon("src/images/ic_ctl.png").getImage(), 20), this);
        ExtAbstractAction actionTigo = new ExtAbstractAction(Constans.PAGO_TG, redimensionarIcono(new ImageIcon("src/images/ic_tigo.png").getImage(), 30), this);
        ExtAbstractAction actionInit = new ExtAbstractAction(Constans.STR_INIT, redimensionarIcono(new ImageIcon("src/images/ic_init.png").getImage(), 60), this);
        ExtAbstractAction actionClose = new ExtAbstractAction(Constans.STR_CLOSE, redimensionarIcono(new ImageIcon("src/images/ic_close.png").getImage(), 60), this);
        ExtAbstractAction actionVoid = new ExtAbstractAction(Constans.STR_VOID, this);
        ExtAbstractAction actionEnableConnect = new ExtAbstractAction(Constans.STR_ENABLE_CONNECT, this);
        ExtAbstractAction actionClearTextArea = new ExtAbstractAction(Constans.STR_CLEAR, this);
        //ExtAbstractAction actionGenerico = new ExtAbstractAction("generico", this);
        
        //Para el tema de multifuentes, podemos instanciar los botones de la siguiente manera. 
        btnPagoQR = new JButton(actionQR);
        btnPagoIcc = new JButton(actionIcc);
        btnPagoCtl = new JButton(actionCtl);
        btnPagoTigo = new JButton(actionTigo);
        btnInit = new JButton(actionInit);
        btnClose = new JButton(actionClose);
        btnVoid = new JButton(actionVoid);
        enableConnect = new JButton(actionEnableConnect);
        clearTextArea = new JButton(actionClearTextArea);
       // generico = new JButton(actionGenerico);
       
       jpBtns.add(btnPagoQR);
       jpBtns.add(btnPagoIcc);
       jpBtns.add(btnInit);
       jpBtns.add(btnPagoCtl);
       jpBtns.add(btnPagoTigo);
       jpBtns.add(btnClose);
       
       jpBtns.setBounds(210, 50, 500, 50);
       
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
        //30, 60, 70, 20
        //btnPagoQR.setBounds(120, 80, 130, 20);
        /*
        btnPagoQR.setBounds(210, 50, 130, 20);
        btnPagoIcc.setBounds(210, 80, 130, 20);
        btnPagoCtl.setBounds(370, 50, 130, 20);
        btnPagoTigo.setBounds(370, 80, 130, 20);
        btnInit.setBounds(530, 50, 130, 20);
        btnClose.setBounds(530, 80, 130, 20);
         */
        btnVoid.setBounds(330, 20, 85, 20);
        
        setEnableButtons(false);
       
        //enableConnect.setBounds(505, 32, 90, 15);
        //clearTextArea.setSize(150, 15); 
        
        /*
        add(btnPagoQR);
        add(btnPagoIcc);
        add(btnPagoCtl);
        add(btnPagoTigo);
        add(btnInit);
        add(btnClose);
        */
        add(btnVoid);
        //add(enableConnect);
        //add(p4);
        
        add(jpBtns);
        /*
        //trabajando con posicionamientos de componentes del panel.
        setLayout(new BorderLayout());
        //se agregan los botones al panel
        add(p1, BorderLayout.SOUTH);
        add(p2, BorderLayout.NORTH);
        add(p3, BorderLayout.EAST);
        add(p4, BorderLayout.WEST);
        */
        /*
        //InputMap proporciona un vinculo ante un evento y un objeto, normalmente es usado con un actionMap.
        InputMap mapaEntrada =getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        //creamos la clave con el control de teclas
        KeyStroke keyBackGroundYellow = KeyStroke.getKeyStroke("ctrl Y");
        KeyStroke keyBackGroundBlue = KeyStroke.getKeyStroke("ctrl B");
        KeyStroke keyBackGroundRed = KeyStroke.getKeyStroke("ctrl R");
        
        //asignamos la combinacion de teclas al siguiente objeto
        mapaEntrada.put(keyBackGroundYellow, "fondo_amarillo");
        mapaEntrada.put(keyBackGroundBlue, "fondo_azul");
        mapaEntrada.put(keyBackGroundRed, "fondo_rojo");*/
        
        //se asigna el objeto a la action
        /*ActionMap actionMap = getActionMap();
        actionMap.put("fondo_amarillo", actionP1);
        actionMap.put("fondo_azul", actionP2);
        actionMap.put("fondo_rojo", actionP3);;*/
    }
    
    public void setEnableButtons(boolean isEnable) {
        Component[] components = jpBtns.getComponents();

        for (Component component : components) {
            component.setEnabled(isEnable); // Deshabilitar el botón
        }

        btnVoid.setEnabled(isEnable);
    }
    
    private ImageIcon redimensionarIcono(Image image, int div){
        int newSizeX = image.getWidth(null)/div;
        int newSizeY = image.getHeight(null)/div;
        return new ImageIcon(image.getScaledInstance(newSizeX, newSizeY, Image.SCALE_DEFAULT));
    }
    
    private void showTextArea() {
       
        //para que no se ensanche, sino que de un salto de linea al escribir texto
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);

        //para que no crezca a lo alto, sino que tnga barras de desplazamiento
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setBounds(30, 110, widthScreen-70, heightScreen/2);
        
        //para que el JScrollPane siempre se desplace al final
        DefaultCaret defaultCaret = (DefaultCaret) jTextArea.getCaret();
        defaultCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        add(jScrollPane);
        
        insertarMenuEmergente();
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
        
        textField1 = new JTextField("");
        
        //agregando el listener de texto al jtextfield
        ImpDocumentListener documentListener = new ImpDocumentListener();
        Document document = textField1.getDocument();
        document.addDocumentListener(documentListener);
        textField1.setBounds(90, 20, 95, 20);
        add(textField1);
        
        //agregando cuadros de texto, para el tema del evento foco
        textField1.addFocusListener(new ImpFocusListener(textField1));
        
        //textfield de anulacion
        tfVoid = new JTextField("");
        
        tfVoid.setBounds(430, 20, 100, 20);
        add(tfVoid);
    }
    
    public String getMonto() {
        return textField1.getText();
    }
    
    public void configPort() {
        String strPort = Alerts.inputAlert("ingrese nuevo puerto");
        int port = 0;
        if (strPort != null) {
            try {
            port = Integer.parseInt(strPort);
            } catch (NumberFormatException e) {
                Alerts.alert(true, "puerto invalido", 2);
                return;
            }
            Constans.setPORT(port);
            drawPort();
        }
    }
    public  void drawPort() {
        //g.drawString("PORT: " + Constans.getPORT(), 150, 40);
        if (jLabelPORT == null) {
           jLabelPORT = new JLabel("PORT: " + Constans.getPORT()); 
        } else {
            jLabelPORT.removeAll();
            jLabelPORT.setText("PORT: " + Constans.getPORT());
        }
        
    }
    /*
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
    */
    
    public void showComponents() {
        showLabels();
        showTextArea();
        showButtons();
        showTextField();
        showMulticomercio();
        showMoneda();
        showCuotas();
    }
    
    public void showLabels() {
        jLabelIP = new JLabel("IP: " + obtenerIP());
        drawPort();
        jLabelMonto = new JLabel("Monto");
        jLabelIP.setBounds(550, 3, 150, 20);
        jLabelPORT.setBounds(660, 3, 150, 20);
        jLabelMonto.setBounds(30, 20, 70, 20);
        
        add(jLabelIP);
        add(jLabelPORT);
        add(jLabelMonto);
    }
    
    public void showMulticomercio() {
        jLabelMulti = new JLabel(Constans.STR_MULTI_ACQ);
        jLabelMulti.setBounds(30, 80, 150, 20);

        checkMulti = new JCheckBox();
        checkMulti.setBounds(110, 80, 35, 20);

        jSpnAcquirer = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        jSpnAcquirer.setBounds(145, 80, 40, 20);
        JComponent editor = jSpnAcquirer.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
            JTextField textField = defaultEditor.getTextField();
            textField.setEditable(false);
        }
        jSpnAcquirer.setEnabled(false);
        
        checkMulti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jSpnAcquirer.setEnabled(checkMulti.isSelected());
            }
        });

        add(jLabelMulti);
        add(checkMulti);
        add(jSpnAcquirer);
    }
    
    public void showMoneda() {
        //30, 20, 70, 20
        JLabel jlMoneda = new JLabel(Constans.STR_COIN); 
        jlMoneda.setBounds(200, 20, 150, 20);
        
        JComboBox jComboBox = new JComboBox();
        jComboBox.setBounds(250, 20, 53, 20);
        String[] itemsComboBox = {"BS", "USD"};
        jComboBox.addItem(itemsComboBox[0]);
        jComboBox.addItem(itemsComboBox[1]);
        
        add(jComboBox);
        add(jlMoneda);
        
    }
 
    public void showCuotas() {
        JLabel jlCuota = new JLabel(Constans.STR_CUOTAS);
        jlCuota.setBounds(30, 50, 150, 20);

        JCheckBox checkCuota = new JCheckBox();
        checkCuota.setBounds(80, 50, 35, 20);

        JSpinner jSpnCuotas = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
        jSpnCuotas.setBounds(145, 50, 40, 20);
        JComponent editor = jSpnCuotas.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor) editor;
            JTextField textField = defaultEditor.getTextField();
            textField.setEditable(false);
        }

        jSpnCuotas.setEnabled(false);

        checkCuota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jSpnCuotas.setEnabled(checkCuota.isSelected());
            }
        });

        add(jlCuota);
        add(jSpnCuotas);
        add(checkCuota);
    }
    
    public void darkTheme(){
        setBackground(Color.DARK_GRAY);
        jTextArea.setBackground(Color.LIGHT_GRAY);
        jLabelMulti.setForeground(Color.WHITE);
        jLabelIP.setForeground(Color.WHITE);
        jLabelPORT.setForeground(Color.WHITE);
        jLabelMonto.setForeground(Color.WHITE);;
    }
    
    public void lightTheme(){
        setBackground(new Color(238, 238, 238));
        jTextArea.setBackground(Color.WHITE);
        jLabelMulti.setForeground(Color.BLACK);
        jLabelIP.setForeground(Color.BLACK);
        jLabelPORT.setForeground(Color.BLACK);
        jLabelMonto.setForeground(Color.BLACK);
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
    
     public void styleViewMain2(int style) {
        Font font = jTextArea.getFont();
        
        if ((style == Font.ITALIC)) {
            if (font.getStyle() == Font.BOLD) {
                style = 3;
            }
        }
        if ((style == Font.BOLD)) {
            if (font.getStyle() == Font.ITALIC) {
                style = 3;
            }
        }
        Constans.setSTYLE(style);
        jTextArea.setFont(new Font(Constans.getFONT(), style, Constans.getSIZE_FONT())); 

    }
     
     public void leerFichero(){
         Fichero fichero = new Fichero(false);
         rspBox(fichero.leerFichero());
     }
     
     public void escribirFichero() {
         Fichero fichero = new Fichero(false);
         fichero.escribirFichero(jTextArea.getText(), true);
     }
     
     public void escribirBufferFichero() {
         String nameFile = Alerts.inputAlert("Nombre del archivo");
         if (nameFile == null) {
             Alerts.alert(true, "Cancelo guardado de archivo", 1);
             return;
         }
         Fichero fichero = new Fichero(false);
         fichero.escribirBufferFichero(jTextArea.getText(),nameFile, true);
     }
     
     public void leerBufferFichero(){
         Fichero fichero = new Fichero(false);
         rspBox(fichero.leerBufferFichero());
     }
     
     public void leerByteFichero(){
         Fichero fichero = new Fichero(true);
         rspBox(fichero.leerByteFichero());
     }
     
     public void escribirByteFichero() {
         Fichero fichero = new Fichero(true);
         byte[] bs = jTextArea.getText().getBytes();
         int[] ibs = new int[bs.length];
         for (int i = 0; i < bs.length; i++) {
             ibs[i] = bs[i];
         }
         fichero.escribirByteFichero(ibs, true);
     }

    public void saveTrxs() {
        String name = Alerts.inputAlert("Nombre de lote");
         if (name == null) {
             Alerts.alert(true, "Cancelo guardado de lote", 1);
             return;
         }
        Fichero fichero = new Fichero(false);
        boolean b = fichero.escribirTrxSerealizado(name);
        if (b) {
            Alerts.alert(true, "guardo correctamente", 1);
        } else {
            Alerts.alert(true, "no guardo", 2);
        }  
    }
    
    public void viewTrxs() {
        Fichero fichero = new Fichero(false);
        List<Trx> listTrxs = fichero.leerTrxSerealizado();
        if (listTrxs != null && !listTrxs.isEmpty()) {
            
            for (Trx trx : listTrxs) {
                rspBox(dataTrx(trx));
            }
            
        } else {
            Alerts.alert(true, "no se encontro datos trxs", 2);
        }  
    }
    
    public String dataTrx(Trx trx) {
        String data;
        if (trx != null) {
            data
                    = "DATOS DE LA TRANSACCIÓN" + "\n"
                    + "codigoAut:     " + trx.getCodigoAutorizacion() + "\n"
                    + "monto:     " + trx.getMontoCompra() + "\n"
                    + "numRecibo:     " + trx.getNumeroRecibo() + "\n"
                    + "RRN:   " + trx.getRRN() + "\n"
                    + "terminalId:    " + trx.getTerminalID() + "\n"
                    + "dateTXR:   " + trx.getDateTRX() + "\n"
                    + "timeTXR:   " + trx.getTimeTRX() + "\n"
                    + "codRsp:    " + trx.getCodRSP() + "\n"
                    + "typeAccount:   " + trx.getCodRSP() + "\n"
                    + "msgError:  " + trx.getMsgError() + "\n";
            return data;
        }
        return null;
    }
    
}
