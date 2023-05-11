/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package Controlador;

import Modelo.Refaccion;
import Vista.VentanaProductoNuevo;
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
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

/**
 *
 * @author jdemi
 */
public class ControladorVentanaProductoNuevo implements ActionListener {
    private Vista.VentanaProductoNuevo ventanaProductoNuevo;
    private String archivo;
    
    
    public ControladorVentanaProductoNuevo(VentanaProductoNuevo ventanaProductoNuevo,String archivo) {
        this.ventanaProductoNuevo = ventanaProductoNuevo;
        this.ventanaProductoNuevo.getCancelarBoton().addActionListener(this);
        this.ventanaProductoNuevo.getAgregarBoton().addActionListener(this);
        this.archivo=archivo;
    }
    
    public VentanaProductoNuevo getVentanaProductoNuevo() {
        return ventanaProductoNuevo;
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        if(this.ventanaProductoNuevo.getCancelarBoton() == evento.getSource()){
            this.ventanaProductoNuevo.dispose();
        }
        
        if (this.ventanaProductoNuevo.getAgregarBoton() == evento.getSource() ){
            try{
                FileInputStream fileIn = new FileInputStream(archivo);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                ArrayList<Refaccion> db = (ArrayList<Refaccion>) in.readObject();
                in.close();
                boolean unico=false;
                int id = Integer.parseInt(this.ventanaProductoNuevo.getIdTexto().getText());
                for (int i=0;i<db.size();i++){
                    if (id==db.get(i).getId()){
                        unico=true;
                    }
                }
                if (!unico){
                int cantidad = Integer.parseInt(this.ventanaProductoNuevo.getCantidadTexto().getText());
                String nombre = this.ventanaProductoNuevo.getNombreTexto().getText();
                BigDecimal precioDeCompra = new BigDecimal(this.ventanaProductoNuevo.getPrecioOriginalTexto().getText());
                BigDecimal precioDeVenta = new BigDecimal(this.ventanaProductoNuevo.getPrecioVentaTexto().getText());
                String categoria = this.ventanaProductoNuevo.getCategoriaTexto().getText();
                String marca = this.ventanaProductoNuevo.getMarcaTexto().getText();
                Refaccion temporal = new Refaccion(id,cantidad,nombre,precioDeCompra,precioDeVenta,categoria,marca);
                db.add(temporal);
                FileOutputStream fileOut = new FileOutputStream("Refacciones.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(db);
                out.close();
                Inicio.Main.retorno().cargarTabla(archivo);
                Inicio.Main.retorno().retorno().cargarTabla(archivo);
                }
                else{
                    JOptionPane.showMessageDialog(null, "ID repetido, por favor cambia id");
                }
            } catch (FileNotFoundException ex) {
                
            } catch (IOException ex) {
                
            } catch (ClassNotFoundException ex) {
                
            }
        }
    }
    
    
}
