/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils;

/**
 *
 * @author WPOSS
 */
public class Constans {
    //Variables de tramas
    public final static String ACK_STRING = "06";
    public final static String NACK_STRING = "15";
    
    //Strings Variables de tramas
    public final static String STR_ACK_STRING = "ACK";
    public final static String STR_NACK_STRING = "NACK";
    
    //Ventas tramas enviadas
    public final static String SOLICITUD_CONEXION = "1000000";
    public final static String SOLICITUD_CONEXION_QR = "1008000";
    public final static String SOLICITUD_CONEXION_TIGOMONEY = "1009000";
    public final static String SOLICITUD_CONEXION_CONTACTLESS = "1006000";
    public final static String TRANS_REV_No = "1000001";
    public final static String TRANSACCION_ENVIO_DATOS = "1000002";
    public final static String TARJETA_CONTACTLESS = "1006001";
    
    //Strings ventas tramas enviadas
    public final static String STR_SOLICITUD_CONEXION = "SOLICITUD CONEXION";
    public final static String STR_SOLICITUD_CONEXION_QR = "SOLICITUD CONEXION QR";
    public final static String STR_SOLICITUD_CONEXION_TIGOMONEY = "SOLICITUD CONEXION TIGOMONEY";
    public final static String STR_SOLICITUD_CONEXION_CONTACTLESS = "SOLICITUD CONEXION CONTACTLESS";
    public final static String STR_TRANS_REV_No = "TRANS REV No";
    public final static String STR_TRANSACCION_ENVIO_DATOS = "TRANSACCION ENVIO DATOS";
    public final static String STR_TARJETA_CONTACTLESS = "TARJETA CONTACTLESS";
    
    //strings programa
    public final static String PAGO_ICC = "PAGO ICC";
    public final static String PAGO_QR = "PAGO QR";
    public final static String STR_ENABLE_CONNECT = "CONECTAR";
    public final static String STR_DISABLE_CONNECT = "DESCONECTAR";
    
    
    //Transacciones que salen del POS la CAJA
    //################### POS -> CAJA #############################
    //Ventas tramas recibidas
    public final static String ULTIMA_TRANS = "1000  1";
    public final static String NUEVA_PANTALLA = "1004  0";
    public final static String NUEVA_PANTALLA_PIN = "1004  0";
    public final static String SOLICITUD_DATOS = "1000  2";
    public final static String RESP_HOST = "1000  3";
    public final static String RESP_HOST_CONTACTLESS = "1006  0";
    
    //Strings Ventas tramas recibidas
    public final static String STR_ULTIMA_TRANS = "ULTIMA TRANS";
    public final static String STR_NUEVA_PANTALLA = "NUEVA PANTALLA";
    public final static String STR_NUEVA_PANTALLA_PIN = "NUEVA PANTALLA PIN";
    public final static String STR_SOLICITUD_DATOS = "SOLICITUD DATOS";
    public final static String STR_RESP_HOST = "RESP HOST";
    public final static String STR_RESP_HOST_CONTACTLESS = "RESP HOST CONTACTLESS";
    
    //msg bytes venta icc
    public final static byte[] BT_SOLICITUD_CONEXION = {2, 0, 23, 54, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 48, 3, 35};
    public final static byte[] BT_ACK = {6};
    public final static byte[] BT_TRANS_NO_REV= {2, 0, 36, 54, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 49, 28, 52, 56, 0, 2, 88, 88, 3, 3};
    public final static byte[] BT_SEND_DATA = {2, 0, 119, 54, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 50, 28, 52, 48, 0, 12, 48, 48, 48, 48, 48, 48, 48, 48, 50, 55, 48, 48, 28, 52, 50, 0, 10, 49, 50, 51, 32, 32, 32, 32, 32, 32, 32, 28, 52, 56, 0, 2, 32, 32, 28, 53, 51, 0, 10, 49, 50, 51, 52, 53, 54, 32, 32, 32, 32, 28, 56, 56, 0, 1, 49, 3, 121};
    
    //msg bytes venta QR
    public final static byte[] BT_SOLICITUD_CONEXION_QR = {2, 0, 23, 54, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 56, 48, 48, 48, 3, 43};
    
    
    //CONSTANTES DE COMPONENTES
    private static String MONTO = "0";

    public static String getMONTO() {
        return MONTO;
    }
    public static void setMONTO(String newMonto) {
        MONTO = newMonto;
    }
    
    
}

