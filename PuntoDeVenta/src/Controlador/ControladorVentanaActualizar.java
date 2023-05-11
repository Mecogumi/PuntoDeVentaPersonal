/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package Controlador;

import Modelo.Refaccion;
import Vista.VentanaProductoActualizar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jdemi
 */
public class ControladorVentanaActualizar implements ActionListener {
    private Vista.VentanaProductoActualizar ventanaProductoActualizar;
    private String archivo;
    private int idn;

    public VentanaProductoActualizar getVentanaProductoActualizar() {
        return ventanaProductoActualizar;
    }
    
    public ControladorVentanaActualizar(VentanaProductoActualizar ventanaProductoActualizar, String archivo,int idn) {
        this.ventanaProductoActualizar = ventanaProductoActualizar;
        this.archivo = archivo;
        this.ventanaProductoActualizar.getCancelarBoton().addActionListener(this);
        this.ventanaProductoActualizar.getActualizarBoton().addActionListener(this);
        this.idn = idn;
        try{
        FileInputStream fileIn = new FileInputStream(archivo);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        ArrayList<Refaccion> db = (ArrayList<Refaccion>) in.readObject();
        this.ventanaProductoActualizar.getCantidadTexto().setText(Integer.toString(db.get(idn).getCantidad()));
        this.ventanaProductoActualizar.getNombreTexto().setText(db.get(idn).getNombre());
        this.ventanaProductoActualizar.getCategoriaTexto().setText(db.get(idn).getCategoria());
        this.ventanaProductoActualizar.getMarcaTexto().setText(db.get(idn).getMarca());
        this.ventanaProductoActualizar.getIdTexto().setText(Integer.toString(db.get(idn).getId()));
        this.ventanaProductoActualizar.getCantidadTexto().setText(Integer.toString(db.get(idn).getCantidad()));
        this.ventanaProductoActualizar.getPrecioVentaTexto().setText(db.get(idn).getPrecioDeVenta().toString());
        this.ventanaProductoActualizar.getPrecioOriginalTexto().setText(db.get(idn).getPrecioDeCompra().toString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControladorVentanaActualizar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControladorVentanaActualizar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorVentanaActualizar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        if(this.ventanaProductoActualizar.getCancelarBoton() == evento.getSource()){
            this.ventanaProductoActualizar.dispose();
        }
        
        if (this.ventanaProductoActualizar.getAgregarBoton() == evento.getSource() ){
            try{
                int id = Integer.parseInt(this.ventanaProductoActualizar.getIdTexto().getText());
                int cantidad = Integer.parseInt(this.ventanaProductoActualizar.getCantidadTexto().getText());
                String nombre = this.ventanaProductoActualizar.getNombreTexto().getText();
                BigDecimal precioDeCompra = new BigDecimal(this.ventanaProductoActualizar.getPrecioOriginalTexto().getText());
                BigDecimal precioDeVenta = new BigDecimal(this.ventanaProductoActualizar.getPrecioVentaTexto().getText());
                String categoria = this.ventanaProductoActualizar.getCategoriaTexto().getText();
                String marca = this.ventanaProductoActualizar.getMarcaTexto().getText();
                Refaccion temporal = new Refaccion(id,cantidad,nombre,precioDeCompra,precioDeVenta,categoria,marca);
                FileInputStream fileIn = new FileInputStream(archivo);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                ArrayList<Refaccion> db = (ArrayList<Refaccion>) in.readObject();
                in.close();
                db.set(this.idn, temporal);
                FileOutputStream fileOut = new FileOutputStream("Refacciones.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(db);
                out.close();
                Inicio.Main.retorno().cargarTabla(archivo);
                Inicio.Main.retorno().retorno().cargarTabla(archivo);
            } catch (FileNotFoundException ex) {
                
            } catch (IOException ex) {
                
            } catch (ClassNotFoundException ex) {
                
            }
        }
    }
    
    
}
