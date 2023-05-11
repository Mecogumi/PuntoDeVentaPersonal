/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package Controlador;

import Modelo.Refaccion;
import Vista.VentanaAdmin;
import Vista.VentanaVenta;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author jdemi
 */
public class ControladorVentanaVenta implements ActionListener,MouseListener,KeyListener {
    private Vista.VentanaVenta ventanaVenta;
    private String archivo;
    private ControladorVentanaAdmin controladorVentanaAdmin;
    private String archivoCuenta;
    private String archivoTicket;
    
    public ControladorVentanaVenta(VentanaVenta ventanaVenta, String archivo,String archivoCuenta,String archivoTicket) {
        this.ventanaVenta = ventanaVenta;
        this.archivo=archivo;
        this.archivoCuenta=archivoCuenta;
        this.archivoTicket=archivoTicket;
        agregarListeners();
        cargarTabla(this.archivo);
        cargarTablaCarrito();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.ventanaVenta.getjTable3().getModel());
        this.ventanaVenta.getjTable3().setRowSorter(sorter);
        sorter.setSortable(0, true);
        actualizarPago();
    }
    
    public ControladorVentanaAdmin retorno(){
        return this.controladorVentanaAdmin;
    }
    public void cargarTabla(String archivo){
        DefaultTableModel modelo = (DefaultTableModel) this.ventanaVenta.getjTable3().getModel();
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
                if (db.get(i).getCantidad()==0){
                    
                }
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        modelo.fireTableDataChanged();
    }
    
    public void cargarTablaCarrito(){
        DefaultTableModel modelo = (DefaultTableModel) this.ventanaVenta.getjTable2().getModel();
        modelo.setColumnIdentifiers(new Object[] {"ID","Nombre","Marca","Categoria","Cantidad","Precio","Total"});
        modelo.setRowCount(0);
        try{
            FileInputStream fileInCuenta = new FileInputStream(archivoCuenta);
            ObjectInputStream inCuenta = new ObjectInputStream(fileInCuenta);
            ArrayList<Refaccion> dbCuenta = (ArrayList<Refaccion>) inCuenta.readObject();
            inCuenta.close();
            for (int i=0;i<dbCuenta.size();i++){
                Object [] fila = {dbCuenta.get(i).getId(),dbCuenta.get(i).getNombre(),dbCuenta.get(i).getMarca(),
                    dbCuenta.get(i).getCategoria(),dbCuenta.get(i).getCantidad(),dbCuenta.get(i).getPrecioDeVenta(),dbCuenta.get(i).calcularTotal()};
                modelo.addRow(fila);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        modelo.fireTableDataChanged();
    }
    
    public ControladorVentanaVenta() {
    }
    
    
    
    public void agregarListeners(){
        this.ventanaVenta.getTotal().setColumns(7);
        this.ventanaVenta.getjMenuItem1().addActionListener(this);
        this.ventanaVenta.getPagarBoton().addActionListener(this);
        this.ventanaVenta.getjTable2().addMouseListener(this);
        this.ventanaVenta.getjTable3().addMouseListener(this);
        this.ventanaVenta.getPago().addKeyListener(this);
        this.ventanaVenta.getBarraBuscar().addKeyListener(this);
    }
    
    public VentanaVenta getVentanaVenta() {
        return ventanaVenta;
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        
        if (this.ventanaVenta.getjMenuItem1()==evento.getSource()){
            ControladorVentanaAdmin inicio = new ControladorVentanaAdmin(new VentanaAdmin(),this.archivo);
            this.controladorVentanaAdmin=inicio;
            inicio.getVentanaAdmin().setVisible(true);
        }
        else if(this.ventanaVenta.getPagarBoton()==evento.getSource()){
            try{
                BigDecimal totalIVA = new BigDecimal(this.ventanaVenta.getTotal().getText().trim());
                BigDecimal pagado = new BigDecimal (this.ventanaVenta.getPago().getText().trim());
                if(pagado.compareTo(totalIVA)>=0){
                    ArrayList<Refaccion> dbCuenta = new ArrayList<Refaccion>();
                    FileOutputStream fileOutCuenta = new FileOutputStream(archivoCuenta);
                    ObjectOutputStream outCuenta = new ObjectOutputStream(fileOutCuenta);
                    outCuenta.writeObject(dbCuenta);
                    outCuenta.close();
                    cargarTabla(archivo);
                    cargarTablaCarrito();
                    JOptionPane.showMessageDialog(null,"Compra finalizada con exito su cambio es de: "+pagado.subtract(totalIVA).toString().trim());
                }
                else{
                    JOptionPane.showMessageDialog(null,"Favor de pagar monto completo");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    @Override
    public void mouseClicked(MouseEvent evento) {
        if (this.ventanaVenta.getjTable2()==evento.getSource()){
            int seleccion=this.ventanaVenta.getjTable2().getSelectedRow();
            if (seleccion!=-1){
                try{
                    FileInputStream fileIn = new FileInputStream(archivo);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    ArrayList<Refaccion> db = (ArrayList<Refaccion>) in.readObject();
                    in.close();
                    ArrayList<Refaccion> dbCuenta = new ArrayList<Refaccion>();
                    FileInputStream fileInCuenta = new FileInputStream(archivoCuenta);
                    ObjectInputStream inCuenta = new ObjectInputStream(fileInCuenta);
                    dbCuenta = (ArrayList<Refaccion>) inCuenta.readObject();
                    inCuenta.close();
                    if(dbCuenta.get(seleccion).getCantidad()>0){
                        dbCuenta.get(seleccion).setCantidad(dbCuenta.get(seleccion).getCantidad()-1);
                        for (int i=0; i<db.size();i++){
                            if(dbCuenta.get(seleccion).getId()==db.get(i).getId()){
                                db.get(i).setCantidad(db.get(i).getCantidad()+1);
                            }
                        }
                        if (dbCuenta.get(seleccion).getCantidad()==0){
                            dbCuenta.remove(seleccion);
                        }
                    }
                    FileOutputStream fileOutCuenta = new FileOutputStream(archivoCuenta);
                    ObjectOutputStream outCuenta = new ObjectOutputStream(fileOutCuenta);
                    outCuenta.writeObject(dbCuenta);
                    outCuenta.close();
                    FileOutputStream fileOut = new FileOutputStream(archivo);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(db);
                    out.close();
                    cargarTabla(archivo);
                    cargarTablaCarrito();
                    actualizarPago();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (this.ventanaVenta.getjTable3()==evento.getSource()){
            int seleccion=this.ventanaVenta.getjTable3().getSelectedRow();
            if (seleccion!=-1){
                try{
                    FileInputStream fileIn = new FileInputStream(archivo);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    ArrayList<Refaccion> db = (ArrayList<Refaccion>) in.readObject();
                    in.close();
                    ArrayList<Refaccion> dbCuenta = new ArrayList<Refaccion>();
                    FileInputStream fileInCuenta = new FileInputStream(archivoCuenta);
                    ObjectInputStream inCuenta = new ObjectInputStream(fileInCuenta);
                    dbCuenta = (ArrayList<Refaccion>) inCuenta.readObject();
                    inCuenta.close();
                    if(db.get(seleccion).getCantidad()>0){
                        db.get(seleccion).setCantidad(db.get(seleccion).getCantidad()-1);
                        
                        boolean existe=false;
                        for (int i=0; i<dbCuenta.size();i++){
                            
                            if(db.get(seleccion).getId()==dbCuenta.get(i).getId()){
                                dbCuenta.get(i).setCantidad(dbCuenta.get(i).getCantidad()+1);
                                
                                existe=true;
                            }
                        }
                        
                        if(!existe){
                            
                            dbCuenta.add(new Refaccion(db.get(seleccion)));
                            dbCuenta.get(dbCuenta.size()-1).setCantidad(1);
                            
                        }
                        
                    }
                    
                    FileOutputStream fileOutCuenta = new FileOutputStream(archivoCuenta);
                    ObjectOutputStream outCuenta = new ObjectOutputStream(fileOutCuenta);
                    outCuenta.writeObject(dbCuenta);
                    outCuenta.close();
                    FileOutputStream fileOut = new FileOutputStream(archivo);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(db);
                    out.close();
                    cargarTabla(archivo);
                    cargarTablaCarrito();
                    actualizarPago();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void actualizarPago(){
        try{
            ArrayList<Refaccion> dbCuenta = new ArrayList<Refaccion>();
            FileInputStream fileInCuenta = new FileInputStream(archivoCuenta);
            ObjectInputStream inCuenta = new ObjectInputStream(fileInCuenta);
            dbCuenta = (ArrayList<Refaccion>) inCuenta.readObject();
            inCuenta.close();
            BigDecimal total = new BigDecimal("0");
            
            if(dbCuenta.size()>0){
                for (int i=0;i<dbCuenta.size();i++){
                    total = total.add(dbCuenta.get(i).calcularTotal());
                }
            }
            this.ventanaVenta.getTotalSinIva().setText(total.toString());
            this.ventanaVenta.getIva().setText(total.multiply(new BigDecimal("0.16")).toString());
            this.ventanaVenta.getTotal().setText(total.multiply(new BigDecimal("1.16")).toString());
            try{
                BigDecimal pago = new BigDecimal(this.ventanaVenta.getPago().getText().trim());
                this.ventanaVenta.getCambioText().setText(pago.subtract(total).toString());
            }
            catch (java.lang.NumberFormatException e1){
                
            }
            
            
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public void keyTyped(KeyEvent evento) {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent evento) {
        if (this.ventanaVenta.getBarraBuscar()==evento.getSource()){
            String texto = this.ventanaVenta.getBarraBuscar().getText().trim();
            
            if (texto.equals("")){
                cargarTabla(archivo);
            }
            else{
                cargarTablaBuscar(texto);
            }
        }
        else if (this.ventanaVenta.getPago()==evento.getSource()){
            actualizarPago();
        }
    }
    
    public void cargarTablaBuscar(String texto){
        DefaultTableModel modelo = (DefaultTableModel) this.ventanaVenta.getjTable3().getModel();
        modelo.setColumnIdentifiers(new Object[] {"ID","Nombre","Marca","Categoria","Cantidad","Precio"});
        modelo.setRowCount(0);
        try{
            FileInputStream fileIn = new FileInputStream(archivo);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<Refaccion> db = (ArrayList<Refaccion>) in.readObject();
            in.close();
            for (int i=0;i<db.size();i++){
                if(db.get(i).getNombre().startsWith(texto)||db.get(i).getCategoria().startsWith(texto)||db.get(i).getMarca().startsWith(texto)||Integer.toString(db.get(i).getId()).startsWith(texto)){
                    Object [] fila = {db.get(i).getId(),db.get(i).getNombre(),db.get(i).getMarca(),
                        db.get(i).getCategoria(),db.get(i).getCantidad(),db.get(i).getPrecioDeVenta()};
                    modelo.addRow(fila);
                    if (db.get(i).getCantidad()==0){
                        
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControladorVentanaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        modelo.fireTableDataChanged();
    }
    
    
    
    
}
