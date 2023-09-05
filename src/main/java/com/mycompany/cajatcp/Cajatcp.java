/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.cajatcp;

import com.cajatcp.view.ImpLayoutManager;
import com.cajatcp.view.JFramePrincipal;
import com.cajatcp.view.listeners.ImpWindowFocusListener;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;

/**
 * Esta clase contiene el metodo main
 *
 * @author Deskin Velasquez
 */
public class Cajatcp {

    public static void main(String[] args) {
       
       JFramePrincipal marco = new JFramePrincipal();
       marco.getColorModel();
       marco.setVisible(true);
       marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cancelar ejecución del programa al cerrar la ventana
       marco.setResizable(false);//bloquear la maximizacion de la pantalla
       
       //se agrega el focus listener a la ventana principal.
       marco.addWindowFocusListener(new ImpWindowFocusListener());
       
        
       /*
       MarcoCalculadora marcoCalculadora = new MarcoCalculadora();
       marcoCalculadora.setVisible(true);
       marcoCalculadora.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cancelar ejecución del programa al cerrar la ventana
       */

        //Disposiciones avanzadas --------------------------------------------
        //MarcoPractica marco1 = new MarcoPractica("Disposicion en caja");
        //marco1.setVisible(true);
        //marco1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //MarcoPractica marco2 = new MarcoPractica("Disposicion en muelle");
        //marco2.setVisible(true);
        //marco2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //MarcoPractica marco3 = new MarcoPractica("Disposicion libre");
        //marco3.setVisible(true);
        //marco3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

/**
 * clase de practica
 *
 * @author Deskin
 */
class MarcoPractica extends JFrame {

    public MarcoPractica(String typeBox) {
        setTitle(typeBox);

        setLocation(500, 500);

        switch (typeBox) {
            case "Disposicion en caja":
                setSize(250, 250);
                JLabel rotulo1 = new JLabel("Nombre");
                JTextField texto1 = new JTextField(10);
                texto1.setMaximumSize(texto1.getPreferredSize());

                Box cajaH1 = Box.createHorizontalBox();
                cajaH1.add(rotulo1);
                cajaH1.add(Box.createHorizontalStrut(10));
                cajaH1.add(texto1); //caja con alineacion horizaontal 1

                JLabel rotulo2 = new JLabel("Contraseña");
                JTextField texto2 = new JTextField(10);
                texto2.setMaximumSize(texto2.getPreferredSize());

                Box cajaH2 = Box.createHorizontalBox();
                cajaH2.add(rotulo2);
                cajaH2.add(Box.createHorizontalStrut(10));
                cajaH2.add(texto2); //caja con alineacion horizaontal 2

                JButton btn1 = new JButton("Ok");
                JButton btn2 = new JButton("Cancelar");

                Box cajaH3 = Box.createHorizontalBox();
                cajaH3.add(btn1);
                cajaH3.add(Box.createHorizontalGlue());
                cajaH3.add(btn2); //caja con alineacion horizaontal 2

                Box cajaVertical = Box.createVerticalBox();
                cajaVertical.add(cajaH1);
                cajaVertical.add(cajaH2);
                cajaVertical.add(cajaH3);

                add(cajaVertical);
                break;
            case "Disposicion en muelle":

                setSize(400, 250);

                JPanel panelMuelle = new JPanel();
                JButton btnMuelle = new JButton("btnMuelle");
                JButton btnMuelle2 = new JButton("btnMuelle2");
                JButton btnMuelle3 = new JButton("btnMuelle3");

                SpringLayout springLayout = new SpringLayout();

                panelMuelle.setLayout(springLayout);

                panelMuelle.add(btnMuelle);
                panelMuelle.add(btnMuelle2);
                panelMuelle.add(btnMuelle3);

                add(panelMuelle);

                //construir el muelle
                Spring muelle = Spring.constant(0, 10, 100);
                Spring muelleRigido = Spring.constant(10);

                springLayout.putConstraint(SpringLayout.WEST, btnMuelle, muelle, SpringLayout.WEST, panelMuelle);

                springLayout.putConstraint(SpringLayout.WEST, btnMuelle2, muelleRigido, SpringLayout.EAST, btnMuelle);

                springLayout.putConstraint(SpringLayout.WEST, btnMuelle3, muelleRigido, SpringLayout.EAST, btnMuelle2);

                springLayout.putConstraint(SpringLayout.EAST, panelMuelle, muelle, SpringLayout.EAST, btnMuelle3);
                break;

            case "Disposicion libre":

                setSize(400, 250);

                /*
                 JPanel panelLibre = new JPanel();
                 
                 panelLibre.setLayout(null);
                 
                 JButton btnLibre = new JButton("btnLibre");
                 JButton btnLibre2 = new JButton("btnLibre2");
                 JButton btnLibre3 = new JButton("btnLibre3");
                 
                 btnLibre.setBounds(20, 20, 120, 20);
                 btnLibre2.setBounds(40, 40, 120, 20);
                 btnLibre3.setBounds(60, 60, 120, 20);
                 
                 panelLibre.add(btnLibre);
                 panelLibre.add(btnLibre2);
                 panelLibre.add(btnLibre3);
                 
                 add(panelLibre);
                 */
                //usando layout container
                JPanel panelLibre = new JPanel();
                
                panelLibre.setLayout(new ImpLayoutManager()); //layoutManager que creamos
                
                JLabel rotuloLibre = new JLabel("Nombre");
                JTextField textoLibre = new JTextField(10);
                textoLibre.setMaximumSize(textoLibre.getPreferredSize());

                JLabel rotuloLibre2 = new JLabel("Contraseña");
                JTextField textoLibre2 = new JTextField(10);
                textoLibre2.setMaximumSize(textoLibre2.getPreferredSize());
                
                panelLibre.add(rotuloLibre);
                panelLibre.add(textoLibre);
                panelLibre.add(rotuloLibre2);
                panelLibre.add(textoLibre2);
                
                add(panelLibre);

                break;
            default:
                throw new AssertionError();

        }

    }
}
