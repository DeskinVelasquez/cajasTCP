/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataManager.DataClasses;

import java.io.Serializable;

/**
 *
 * @author Deskin
 */
public class Trx implements Serializable {
    private String codigoAutorizacion; //Campo 01 Codigo de Autorizacion, 6
    private String montoCompra;    //Campo 40 Monto Compra, 10
    private String numeroRecibo;  //Campo 43 Numero de Recibo, 6
    private String RRN; //Campo 44 RRN, 6
    private String terminalID; //Campo 45 Terminal ID, 8
    private String dateTRX; //Campo 46 Fecha Transaccion, (6 en documento) 2 en la trama
    private String timeTRX; //Campo 47 Hora Transaccion, (4 en documento) 2 en la trama
    private String codRSP; //Campo 48 Codigo de Respuesta, (2 en documento) 2 en la trama // 0x30 0x30 si correcto, 0x39 0x39 si no
    private String tipeAccount; //Campo 50 Tipo de Cuenta,
    private String NumeroCuotas; //Campo 51 Numero de Cuotas
    private String last4Digits; //Campo 54 Ultimos 4 Digitos, 4
    private String msgError; //Campo 61 Mensaje de Error, 45
    
    
    public Trx() {}
    
    public Trx(String codigoAutorizacion, String montoCompra, String numeroRecibo, String RRN, String terminalID, String dateTRX, String timeTRX, String codRSP, String tipeAccount, String NumeroCuotas, String last4Digits, String msgError) {
        this.codigoAutorizacion = codigoAutorizacion;
        this.montoCompra = montoCompra;
        this.numeroRecibo = numeroRecibo;
        this.RRN = RRN;
        this.terminalID = terminalID;
        this.dateTRX = dateTRX;
        this.timeTRX = timeTRX;
        this.codRSP = codRSP;
        this.tipeAccount = tipeAccount;
        this.NumeroCuotas = NumeroCuotas;
        this.last4Digits = last4Digits;
        this.msgError = msgError;
    }
    
    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

    public String getMontoCompra() {
        return montoCompra;
    }

    public void setMontoCompra(String montoCompra) {
        this.montoCompra = montoCompra;
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public String getRRN() {
        return RRN;
    }

    public void setRRN(String RRN) {
        this.RRN = RRN;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getDateTRX() {
        return dateTRX;
    }

    public void setDateTRX(String dateTRX) {
        this.dateTRX = dateTRX;
    }

    public String getTimeTRX() {
        return timeTRX;
    }

    public void setTimeTRX(String timeTRX) {
        this.timeTRX = timeTRX;
    }

    public String getCodRSP() {
        return codRSP;
    }

    public void setCodRSP(String codRSP) {
        this.codRSP = codRSP;
    }

    public String getTipeAccount() {
        return tipeAccount;
    }

    public void setTipeAccount(String tipeAccount) {
        this.tipeAccount = tipeAccount;
    }

    public String getNumeroCuotas() {
        return NumeroCuotas;
    }

    public void setNumeroCuotas(String NumeroCuotas) {
        this.NumeroCuotas = NumeroCuotas;
    }

    public String getLast4Digits() {
        return last4Digits;
    }

    public void setLast4Digits(String last4Digits) {
        this.last4Digits = last4Digits;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }


}
