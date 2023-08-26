/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp;

/**
 *
 * @author WPOSS
 */
public class Constans {
    //Variables de tramas
    public final static String ACK_STRING = "06";
    //Ventas
    public final static String SOLICITUD_CONEXION = "02001736303030303030303030313030303030300323";
    public final static String SOLICITUD_CONEXION_QR = "1008000";
    public final static String SOLICITUD_CONEXION_TIGOMONEY = "1008000";
    public final static String SOLICITUD_CONEXION_CONTACTLESS = "1006000";
    public final static String TRANS_REV_No = "1000001";
    public final static String TRANSACCION_ENVIO_DATOS = "1000002";
    public final static String TARJETA_CONTACTLESS = "1006001";
    
    //strings
    public final static String PAGO_ICC = "pago icc";
    public final static String STR_SOLICITUD_CONEXION = "STR_SOLICITUD_CONEXION";
    public final static String STR_ENABLE_CONNECT = "conectar";
    
    
    //msg bytes venta icc
    public final static byte[] BT_SOLICITUD_CONEXION = {2, 0, 23, 54, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 48, 3, 35};
    public final static byte[] BT_ACK = {6};
    public final static byte[] BT_TRANS_NO_REV= {2, 0, 36, 54, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 49, 28, 52, 56, 0, 2, 88, 88, 3, 3};
    public final static byte[] BT_SEND_DATA = {2, 0, 36, 54, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 49, 28, 52, 56, 0, 2, 88, 88, 3, 3};
    
    //msg bytes venta ctl
    
}
