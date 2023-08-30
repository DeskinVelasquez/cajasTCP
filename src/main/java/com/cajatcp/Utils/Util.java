/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils;

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
    
    public static String setColor(String string, int color) {
        String newString = string.set
    }
}
