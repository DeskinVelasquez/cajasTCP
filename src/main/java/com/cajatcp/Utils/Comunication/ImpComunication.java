/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cajatcp.Utils.Comunication;

/**
 *
 * @author deskin
 */
public interface ImpComunication {
    
   public void enviarMsg(String tipoMsg);
   
   public void recibir();
   
   public byte[] armarTramaVariable(String tipo);
}
