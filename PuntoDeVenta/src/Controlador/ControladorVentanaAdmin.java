/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package Controlador;

import Modelo.Refaccion;
import Vista.VentanaAdmin;
import Vista.VentanaProductoActualizar;
import Vista.VentanaProductoNuevo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jdemi
 */
public class ControladorVentanaAdmin implements ActionListener {
    private VentanaAdmin ventanaAdmin;
    private String archivo;
    
    public ControladorVentanaAdmin(VentanaAdmin ventanaAdmin, String archivo) {
        this.ventanaAdmin = ventanaAdmin;
        this.archivo=archivo;
        this.ventanaAdmin.getAnadirBoton().addActionListener(this);
        this.ventanaAdmin.getCambiarCantBoton().addActionListener(this);
        cargarTabla(this.archivo);
    }
    
    
    
    public void cargarTabla(String archivo){
        DefaultTableModel modelo=(DefaultTableModel) this.ventanaAdmin.getjTable1().getModel();
        modelo.setColumnIdentifiers(new Object[] {"ID","Nombre","Marca","Categoria","Cantidad","Precio"});
        modelo.setRowCount(0);
        try{
            FileInputStream fileIn = new FileInputStream(archivo);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<Refaccion> db = (ArrayList<Refaccion>) in.readObject();
            in.close();
            for (int i=0;i<db.size();i++){
                Object [] fila = {db.get(i).getId(),db.get(i).getNombre(),db.get(i).getMarca(),
                    db.get(i).getCategoria(),db.get(i).getCantidad(),db.get(i).getPrecioDeVenta()};
                modelo.addRow(fila);
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControladorVentanaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControladorVentanaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorVentanaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        modelo.fireTableDataChanged();
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        if (this.ventanaAdmin.getAnadirBoton()==evento.getSource()){
            ControladorVentanaProductoNuevo inicio = new ControladorVentanaProductoNuevo(new VentanaProductoNuevo(),this.archivo);
            inicio.getVentanaProductoNuevo().setVisible(true);
        }
        if (this.ventanaAdmin.getCambiarCantBoton()==evento.getSource()){
            try{
                FileInputStream fileIn = new FileInputStream(archivo);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                ArrayList<Refaccion> db = (ArrayList<Refaccion>) in.readObject();
                in.close();
                int idn=0;
                int idfinder=(int)this.ventanaAdmin.getjTable1().getValueAt(this.ventanaAdmin.getjTable1().getSelectedRow(), 0);
                for (int i=0;i<db.size();i++){
                  if (db.get(i).getId()==idfinder){
                      idn=i;
                  }  
                }
                ControladorVentanaActualizar inicio = new ControladorVentanaActualizar(new VentanaProductoActualizar(),this.archivo,idn);
                inicio.getVentanaProductoActualizar().setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(ControladorVentanaAdmin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ControladorVentanaAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public VentanaAdmin getVentanaAdmin() {
        return ventanaAdmin;
    }
    
}
