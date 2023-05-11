package Inicio;


import Controlador.ControladorVentanaProductoNuevo;
import Controlador.ControladorVentanaVenta;
import Modelo.Refaccion;
import Vista.VentanaProductoNuevo;
import Vista.VentanaVenta;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/

/**
 *
 * @author jdemi
 */
public class Main {
    static String archivo = "Refacciones.ser";
    static String archivoCuenta = "Cuenta.ser";
    static String archivoTicket = "Historial.ser";
    static ControladorVentanaVenta inicio = new ControladorVentanaVenta(new VentanaVenta(),archivo,archivoCuenta,archivoTicket);
    public static ControladorVentanaVenta retorno (){
        return inicio;
    }
    public static void main(String[] args) {
        
        try {
            ArrayList<Refaccion> db = new ArrayList<Refaccion>();
            FileInputStream fileIn = new FileInputStream(archivo);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            db = (ArrayList<Refaccion>) in.readObject();
            in.close();
            
        }
        catch (Exception ex){
            try{
                ArrayList<Refaccion> db = new ArrayList<Refaccion>();
                FileOutputStream fileOut = new FileOutputStream(archivo);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(db);
                out.close();
            } catch (FileNotFoundException ex1) {
            } catch (IOException ex1) {
            }
        }
        try{
            ArrayList<Refaccion> dbCuenta = new ArrayList<Refaccion>();
            FileInputStream fileInCuenta = new FileInputStream(archivoCuenta);
            ObjectInputStream inCuenta = new ObjectInputStream(fileInCuenta);
            dbCuenta = (ArrayList<Refaccion>) inCuenta.readObject();
            inCuenta.close();
        }
        catch(Exception ex){
            try{
                ArrayList<Refaccion> dbCuenta = new ArrayList<Refaccion>();
                FileOutputStream fileOutCuenta = new FileOutputStream(archivoCuenta);
                ObjectOutputStream outCuenta = new ObjectOutputStream(fileOutCuenta);
                outCuenta.writeObject(dbCuenta);
                outCuenta.close();
            } catch (FileNotFoundException ex1) {
            } catch (IOException ex1) {
            }
        }
        try {
            ArrayList<Refaccion> dbTicket = new ArrayList<Refaccion>();
            FileInputStream fileInTicket = new FileInputStream(archivoTicket);
            ObjectInputStream inTicket = new ObjectInputStream(fileInTicket);
            dbTicket = (ArrayList<Refaccion>) inTicket.readObject();
            inTicket.close();
            
        }
        catch (Exception ex){
            try{
                ArrayList<Refaccion> dbTicket = new ArrayList<Refaccion>();
                FileOutputStream fileOutTicket = new FileOutputStream(archivoTicket);
                ObjectOutputStream outTicket = new ObjectOutputStream(fileOutTicket);
                outTicket.writeObject(dbTicket);
                outTicket.close();
            } catch (FileNotFoundException ex1) {
            } catch (IOException ex1) {
            }
        }
        inicio.getVentanaVenta().setVisible(true);
        
    }
}
