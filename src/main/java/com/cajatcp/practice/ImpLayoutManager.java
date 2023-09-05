/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.practice;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;


/**
 * clase que gestiona la layout
 * @author Deskin
 */
public class ImpLayoutManager implements LayoutManager {
    private int x = 20;
    private int y = 20;

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container miContenedor) {
        
        int d = miContenedor.getWidth();
        
        x = d/2;
        
        int count = 0;
        
        int n = miContenedor.getComponentCount(); //cantidad de componentes agregados
        
        for (int i = 0; i < n; i++) {
            
            count++;
            
            Component cmp = miContenedor.getComponent(i);
            
            cmp.setBounds(x-100, y, 100, 20);
            
            x+=100;
            
            if (count % 2 == 0) {
                x = d/2;
                y+=40;
            }
        }
    }
    
}
