/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataManager;

import DataManager.DataClasses.Trx;
import com.cajatcp.Utils.Comunication.ComunicationICC;
import com.cajatcp.Utils.Comunication.ComunicationTools;
import com.cajatcp.Utils.Util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Deskin
 */
public class Fichero {

    String carpeta, archivo;

    public Fichero(boolean isByte) {

        if (isByte) {
            this.carpeta = "src/docs/bytes";
            this.archivo = Util.getDate() + "_bt_comunication.txt";
        } else {
            this.carpeta = "src/docs";
            this.archivo = Util.getDate() + "_comunication.txt";
        }
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
            FileWriter fileWriter = new FileWriter(file, noSobreEscribir);
            //para escritura de pocos caracteres, hasta el tamaño del tipo de variable string
            fileWriter.write(s);

            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se encuentra el archivo");
        }
    }

    public String leerBufferFichero() {
        StringBuilder stringBuilder = new StringBuilder();

        File file = new File(carpeta, archivo);
        try {
            FileReader fileReader = new FileReader(file);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linea = "";

            while (linea != null) {
                linea = bufferedReader.readLine();
                if (linea != null) {
                    System.out.println(linea + "\n");
                    stringBuilder.append(linea + "\n");
                }
            }
            fileReader.close();
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, e);
            return "No se encuentra el archivo";
        } catch (IOException e) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, e);
            return "Error al leer el archivo";
        }
        return stringBuilder.toString();
    }

    public void escribirBufferFichero(String s, boolean noSobreEscribir) {
        try {

            File file = new File(carpeta, archivo);
            FileWriter fileWriter = new FileWriter(file, noSobreEscribir);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(s);
            bufferedWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se encuentra el archivo");
        }
    }

    public String leerByteFichero() {
        StringBuilder stringBuilder = new StringBuilder();

        File file = new File(carpeta, archivo);
        try {
            FileInputStream fis = new FileInputStream(file);

            int c = 0;
            while (c != -1) {
                c = fis.read();

                if (c != -1) {
                    System.out.println("(" + c + ")");
                    stringBuilder.append(c + "\n");
                }
            }
            fis.close();

        } catch (FileNotFoundException e) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, e);
            return "No se encuentra el archivo";
        } catch (IOException e) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, e);
            return "Error al leer el archivo";
        }
        return stringBuilder.toString();
    }

    public void escribirByteFichero(int[] bs, boolean noSobreEscribir) {
        try {
            File file = new File(carpeta, archivo);
            FileOutputStream fos = new FileOutputStream(file, noSobreEscribir);
            for (int i = 0; i < bs.length; i++) {
                fos.write(bs[i]);
            }
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se encuentra el archivo");
        }
    }

    public boolean escribirTrxSerealizado() {
        ArrayList<String> listTrxs = ComunicationICC.listMsgInput;
        if (listTrxs == null || listTrxs.isEmpty()) {
            return false;
        }
        carpeta = "src/datatrx";
        File carp = new File(carpeta);
        if (!carp.exists()) {
            carp.mkdirs();
        }
        archivo = Util.getDate() + "_trxs.dat";

        File file = new File(carpeta, archivo);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("No se creo el archivo trxs");
                return false;
            }
        }

        //uso se try with resource, cierra los objetos que necesitan ser cerrados automaticamente
        try ( FileOutputStream fos = new FileOutputStream(file, true);  ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(listTrxs);

            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se encuentra el archivo");
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al leer el archivo");

        }

        return false;
    }
    
    public List<Trx> leerTrxSerealizado() {
         List<Trx> listTrxs = null;
        carpeta = "src/datatrx";
        File carp = new File(carpeta);
        if (!carp.exists()) {
            carp.mkdirs();
        }
        archivo = Util.getDate() + "_trxs.dat";
        
        File file = new File(carpeta, archivo);
        
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listTrxs = (List<Trx>) ois.readObject();
            
            fis.close();
            ois.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se encuentra el archivo");
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al leer el archivo");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ClassNotFoundException");
        }
        
        
        return listTrxs;
    }

}
