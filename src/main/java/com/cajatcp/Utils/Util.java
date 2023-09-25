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
    
}
