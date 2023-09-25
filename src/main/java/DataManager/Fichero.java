/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataManager;

import com.cajatcp.Utils.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Deskin
 */
public class Fichero {
    String carpeta, archivo;
    

    public Fichero() {
        this.carpeta = "src/docs";
        this.archivo = Util.getDate()+"_comunication.txt";
        File carp = new File(carpeta);
        if (!carp.exists()) {
            carp.mkdirs();
        }
    }

    public String leerFichero() {
        StringBuilder stringBuilder = new StringBuilder();
        
        File file = new File(carpeta, archivo);
        try {
            FileReader fileReader = new FileReader(file);

            int c = 0;
            while (c != -1) {
                c = fileReader.read();
                char letra = (char) c;
                System.out.println("(" + c + ")" + letra + " ");
                stringBuilder.append(letra);
            }
            fileReader.close();

        } catch (FileNotFoundException e) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, e);
            return "No se encuentra el archivo";
        } catch (IOException e) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, e);
            return "Error al leer el archivo";
        }
        return stringBuilder.toString();
    }

    public void escribirFichero(String s, boolean noSobreEscribir) {
        try {

            File file = new File(carpeta, archivo);
            FileWriter fileReader = new FileWriter(file, noSobreEscribir);
            
            fileReader.write(s);
            
            fileReader.close();
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se encuentra el archivo");
        }
    }
}
