/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cajatcp.Utils;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    
    /**
     * pad to the left
     *
     * @param s   - original string
     * @param len - desired len
     * @param c   - padding char
     * @return padded string
     * @throws ISOException on error
     */
    public static String padleft(String s, int len, char c) {
        s = s.trim();
        if (s.length() > len) {
            return null;
        }
        StringBuilder d = new StringBuilder(len);
        int fill = len - s.length();
        while (fill-- > 0) {
            d.append(c);
        }
        d.append(s);
        return d.toString();
    }
    
    /**
     * Metodo que convierte una lista en un string continuo con los datos
     * @param porcion lista a convertir
     * @return el string equivalente a la lista
     */
    public static String conversorAString(List<String> porcion) {
        StringBuilder sb = new StringBuilder();
        for (String s : porcion) {
            sb.append(s);
        }
        return hexToAscii(sb.toString());
    }
    
    /**
     * Metodo utilizado para convetir una cadena hexadecimal a ASCII
     *
     * @param hexStr-->Cadena con la trama  en hexadecimal
     * @return Cadena convertida a ASCII
     * Creado por: Deskin Velasquez
     */
    public static String hexToAscii(String hexStr) {
        StringBuilder retorno = new StringBuilder();

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            retorno.append((char) Integer.parseInt(str, 16));
        }

        return retorno.toString();
    }
    
    /**
     * Metodo que convierte un String de hex (0A0C) a integer, se usa para tomar la longitud de un campo
     * de una trama 0x00 0x12 = 12
     * @param hex el String de hex
     * @return el resultado
     */
    public static int hex2Int(String hex) {
        int resultado = 0;
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            resultado += decimal;
        }
        return resultado;
    }
    
    /**
     * Metodo generico que verifica la nulabilidad de un objeto
     * @param <T>
     * @param object
     * @return 
     */
    public static final <T> boolean isNull(T object){
        
        if (object == null) {
            System.out.println("El objeto es nulo");
            return true;
            
        }
        return false;
    }
    
    public static String hex2AsciiStr(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);
            temp.append(decimal);
        }

        return sb.toString();
    }
}
