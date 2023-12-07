/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils;

/**
 *
 * @author deskin
 */
public class Constans {
    //Variables de tramas
    public final static String ACK_STRING = "06";
    public final static String NACK_STRING = "15";
    public final static String SEPARADOR_STRING = "1C";
    
    //Strings Variables de tramas
    public final static String STR_ACK_STRING = "ACK";
    public final static String STR_NACK_STRING = "NACK";
    public final static String ERROR = "ERROR";
    
    //Ventas tramas enviadas
    public final static String SOLICITUD_CONEXION = "1000000";
    public final static String SOLICITUD_CONEXION_QR = "1008000";
    public final static String SOLICITUD_CONEXION_TIGOMONEY = "1009000";
    public final static String SOLICITUD_CONEXION_CONTACTLESS = "1006000";
    public final static String TRANS_REV_No = "1000001";
    public final static String TRANSACCION_ENVIO_DATOS = "1000002";
    public final static String TARJETA_CONTACTLESS = "1006001";
    
    //Inicializacion
    public final static String SOLICITUD_INIT = "1002000";
    //trama de inicializacion recibida
    public final static String RESP_INIT = "1002  0";
    
    //Cierre
    public final static String SOLICITUD_CIERRE = "1001000";
    //Tramas de cierre recibidas. 
    public final static String CIERRE_CANTIDAD = "1001  0";//-->cantidad de transacciones inicial
    public final static String DATOS_CIERRE = "1001  1"; //la transaccion
    public final static String RESP_CIERRE = "1001  2";//-->respuesta final
    
    //Anulacion
    public final static String SOLICITUD_ANULACION = "1005000";
    public final static String REFERENCIA_TRANSACCION_ANULACION = "1005001";
    public final static String CONFIRMACION_ANULACION = "1005002";
    //tramas de anulacion recibidas
    public final static String SOLICITUD_ANULACION_REFERENCIA = "1005  0";
    public final static String RESULTADO_BUSQUEDA_REFERENCIA = "1005  1";
    public final static String RESPUESA_HOST_ANULACION = "1005  2";
    
    //Strings ventas tramas enviadas
    public final static String STR_SOLICITUD_CONEXION = "SOLICITUD CONEXION";
    public final static String STR_SOLICITUD_CONEXION_QR = "SOLICITUD CONEXION QR";
    public final static String STR_SOLICITUD_CONEXION_TIGOMONEY = "SOLICITUD CONEXION TIGOMONEY";
    public final static String STR_SOLICITUD_CONEXION_CONTACTLESS = "SOLICITUD CONEXION CONTACTLESS";
    public final static String STR_SOLICITUD_INIT = "SOLICITUD INICIALIZACION";
    public final static String STR_SOLICITUD_ANULACION = "SOLICITUD ANULACION";
    public final static String STR_REFERENCIA_TRANSACCION_ANULACION = "REFERENCIA TRANSACCION ANULACION";
    public final static String STR_CONFIRMACION_ANULACION = "CONFIRMACION ANULACION";
    public final static String STR_SOLICITUD_ANULACION_REFERENCIA = "SOLICITUD ANULACION REFERENCIA";
    public final static String STR_RESULTADO_BUSQUEDA_REFERENCIA = "RESULTADO_BUSQUEDA_REFERENCIA";
    public final static String STR_RESPUESA_HOST_ANULACION = "RESPUESA HOST ANULACION";
    public final static String STR_TRANS_REV_No = "TRANS REV No";
    public final static String STR_TRANSACCION_ENVIO_DATOS = "TRANSACCION ENVIO DATOS";
    public final static String STR_TARJETA_CONTACTLESS = "TARJETA CONTACTLESS";
    public final static String STR_SEND_REF_PENDING = "REFERENCIA PENDIENTE";
    public final static String STR_CIERRE_CANTIDAD = "CANTIDAD TRXS";
    public final static String STR_DATOS_CIERRE = "DATOS DE LA TRX"; //la transaccion
    public final static String STR_RESP_CIERRE = "RESPUESTA FINAL"; 
    
    //strings programa
    public final static String TCP = "TCP";
    public final static String USB = "USB";
    public final static String PAGO_ICC = "PAGO ICC";
    public final static String PAGO_CTL = "PAGO CTL";
    public final static String PAGO_QR = "PAGO QR";
    public final static String PAGO_TG = "PAGO TG";
    public final static String STR_INIT = "INICIALIZAR";
    public final static String STR_CLOSE = "CIERRE";
    public final static String STR_VOID = "ANULAR";
    public final static String STR_ENABLE_CONNECT = "conectar";
    public final static String STR_DISABLE_CONNECT = "desconectar";
    public final static String STR_TIPO_CO = "Tipo Comunicacion";
    public final static String STR_CLEAR = "Limpiar registro";
    public final static String STR_CONFIG_PORT = "config port";
    public final static String STR_STYLE_PLAIN = "Normal";
    public final static String STR_STYLE_BOLD = "Negrita";
    public final static String STR_STYLE_ITALIC = "Cursiva";
    public final static String STR_SIZE_8 = "8";
    public final static String STR_SIZE_10 = "10";
    public final static String STR_SIZE_12 = "12";
    public final static String STR_SIZE_14 = "14";
    public final static String STR_SIZE_16 = "16";
    
    
    //Transacciones que salen del POS la CAJA
    //################### POS -> CAJA #############################
    //Ventas tramas recibidas
    public final static String ULTIMA_TRANS = "1000  1";
    public final static String NUEVA_PANTALLA = "1004  0";
    public final static String NUEVA_PANTALLA_PIN = "1004  0";
    public final static String SOLICITUD_DATOS = "1000  2";
    public final static String RESP_HOST = "1000  3";
    public final static String RESP_HOST_CONTACTLESS = "1006  0";
    public final static String RESP_ERROR = "1007  0";
    public final static String SEND_REF_PENDING = "1008  0";
    
    //Strings Ventas tramas recibidas
    public final static String STR_ULTIMA_TRANS = "ULTIMA TRANS";
    public final static String STR_NUEVA_PANTALLA = "NUEVA PANTALLA";
    public final static String STR_NUEVA_PANTALLA_PIN = "NUEVA PANTALLA PIN";
    public final static String STR_SOLICITUD_DATOS = "SOLICITUD DATOS";
    public final static String STR_RESP_HOST = "RESP HOST";
    public final static String STR_RESP_HOST_CONTACTLESS = "RESP HOST CONTACTLESS";
    public final static String STR_INIT_OK = "INICIALIZACION EXITOSA";
    
    //msg bytes venta icc
    public final static byte[] BT_SOLICITUD_CONEXION = {2, 0, 23, 54, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 48, 3, 35};
    public final static byte[] BT_ACK = {6};
    public final static byte[] BT_TRANS_NO_REV= {2, 0, 36, 54, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 49, 28, 52, 56, 0, 2, 88, 88, 3, 3};
    public final static byte[] BT_SEND_DATA = {2, 0, 119, 54, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 48, 50, 28, 52, 48, 0, 12, 48, 48, 48, 48, 48, 48, 48, 48, 50, 55, 48, 48, 28, 52, 50, 0, 10, 49, 50, 51, 32, 32, 32, 32, 32, 32, 32, 28, 52, 56, 0, 2, 32, 32, 28, 53, 51, 0, 10, 49, 50, 51, 52, 53, 54, 32, 32, 32, 32, 28, 56, 56, 0, 1, 49, 3, 121};//monto: 27.00
    
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
    
    private static int PORT = 1234;
    public static int getPORT() {
        return PORT;
    }
    public static void setPORT(int port) {
        PORT = port;
    }
    
    private static String FONT = "0";
    public static String getFONT() {
        return FONT;
    }
    public static void setFONT(String FONT) {
        Constans.FONT = FONT;
    }
   
    private static int STYLE = 0;
    public static int getSTYLE() {
        return STYLE;
    }
     public static void setSTYLE(int STYLE) {
        Constans.STYLE = STYLE;
    }
    
    private static int SIZE_FONT = 12;
    public static int getSIZE_FONT() {
        return SIZE_FONT;
    }
    public static void setSIZE_FONT(int SIZE_FONT) {
        Constans.SIZE_FONT = SIZE_FONT;
    }
    
    private static boolean BOLD = false;
    public static boolean getBOLD() {
        return BOLD;
    }
    public static void setBOLD(boolean bold) {
        Constans.BOLD = bold;
    }
   
    //componentes de la barra de menu
    public static final String FILE = "Archivo";
    public static final String SETTINGS = "Configuración";
    public static final String EDIT = "Editar";
    public static final String VIEW = "Vista";
    public static final String APPEARANCE = "Apariencia";
    public static final String APPEARANCE_LIGHT = "Claro";
    public static final String APPEARANCE_DARK = "Obscuro";
    public static final String STR_STYLE = "Estilo";
    public static final String STR_FONT = "Fuente";
    public static final String STR_SIZE = "Tamaño";
    public static final String STR_SAVE = "Guardar";
    public static final String STR_SAVE_TRX = "Guardar datos Trxs";
    public static final String STR_READ_FILE = "Leer fichero";
    public static final String STR_VIEW_TRXS = "Ver trxs";
    
    //labels
    public static final String STR_TITLE = "DemoCajas TCP";
    public static final String STR_MULTI_ACQ = "Multicomercio";
    public static final String STR_COIN = "Moneda";
    public static final String STR_CUOTAS = "Cuotas";
    public static final String STR_INTERES = "Interes";
    
    //moneda
    public static final String STR_BS = "BS";
    public static final String STR_USD = "USD";
    
    //bytes para armado de tramas-----------------------------------------------------------------
    public final static byte STX = 0x02;
    public final static byte ETX = 0x03;
    public final static byte SEPARADOR = 0x1C;
    
    //Transport Header
    public final static  byte []transportHeader={0x36, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30 };//(10Bytes)
    
    //Presentation Header para las tramas enviadas al MPK en formato Bytes
    //venta ICC
    public final static byte[] PH_NUEVA_PANTALLA_BYTE ={ 0x31, 0x30, 0x30, 0x34, 0x20, 0x20, 0x30 };
    public final static byte[] PH_ENVIO_DATOS_BYTE ={ 0x31, 0x30, 0x30, 0x30, 0x30, 0x30, 0x32 };
    public final static byte[] PH_RESP_HOST_BYTES ={ 0x31, 0x30, 0x30, 0x30, 0x20, 0x20, 0x33 };
    
    
    public final static byte[] PH_SOLICITUD_CONEXION ={ 0x31, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30};
    public final static byte[] PH_TRANS_NO_REV ={ 0x31, 0x30, 0x30, 0x30, 0x30, 0x30, 0x31};
    public final static byte[] PH_ENVIO_DATA ={ 0x31, 0x30, 0x30, 0x30, 0x30, 0x30, 0x32};
    
    //venta CTL
    public final static byte[] PH_SOLICITUD_CONEXION_CTL ={ 0x31, 0x30, 0x30, 0x36, 0x30, 0x30, 0x30};
    public final static byte[] PH_TARJETA_CTL ={ 0x31, 0x30, 0x30, 0x36, 0x30, 0x30, 0x31};
    public final static byte[] PH_RESP_HOST_CONTACTLESS_BYTES ={ 0x31, 0x30, 0x30, 0x36, 0x20, 0x20, 0x30 };
    //qr
    public final static byte[] PH_SOLICITUD_CONEXION_QR ={ 0x31, 0x30, 0x30, 0x38, 0x30, 0x30, 0x30};
    //TG
    public final static byte[] PH_SOLICITUD_CONEXION_TG ={ 0x31, 0x30, 0x30, 0x39, 0x30, 0x30, 0x30};
    //init
    public final static byte[] PH_SOLICITUD_INIT ={ 0x31, 0x30, 0x30, 0x32, 0x30, 0x30, 0x30};
    //cierre
    public final static byte[] PH_SOLICITUD_CIERRE ={ 0x31, 0x30, 0x30, 0x31, 0x30, 0x30, 0x30};
    //anulacion
    public final static byte[] PH_SOLICITUD_ANULACION={ 0x31, 0x30, 0x30, 0x35, 0x30, 0x30, 0x30};
    public final static byte[] PH_REFERENCIA_TRANSACCION_ANULACION={ 0x31, 0x30, 0x30, 0x35, 0x30, 0x30, 0x31};
    public final static byte[] PH_CONFIRMACION_ANULACION ={ 0x31, 0x30, 0x30, 0x35, 0x30, 0x30, 0x32};
    
}

