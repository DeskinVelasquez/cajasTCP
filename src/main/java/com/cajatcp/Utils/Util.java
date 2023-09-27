/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Deskin Velasquez
 */
public class Util {
    
    public static String convertToHex(int asciiValue){
        String hexString = Integer.toHexString(asciiValue);
        return hexString;
    }
    
    public static String byte2Hex(int b){
        return String.format("%02X", b);
    }
    
    public static char byte2Char(int b){
        return (char) b;
    }
    
    public static int convertToDecimal(String hexValue){
        return Integer.parseInt(hexValue, 16);
    }
    
    public static String getDate(){
        
        LocalDate date = LocalDate.now();
        
        String fecha = "" + date.getDayOfMonth()  + date.getMonthValue() + date.getYear();
        return fecha;
    }
    
    public static byte[] int2bcd(int data, int len) {
        byte[] bb = null;
        if (len == 1) {
            data = data % 100;
            bb = new byte[1];
            bb[0] = (byte) (((data / 10) << 4) + (data % 10));
            return bb;
        } else if (len == 2) {
            bb = new byte[2];
            bb[0] = (byte) (data / 100);

            bb[1] = (byte) ((((data / 10) % 10) << 4) + (data % 10));
            return bb;
        } else
            return null;
    }
    
    /**
     * Devuelve true si el string es nulo o vacio
     * @param str
     * @return 
     */
    public static boolean isNullWithTrim(String str) {
        return str == null || str.trim().equals("")||str.trim().equals("null");
    }
}
