/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * @author jdemi
 */
public class Ticket {
    private Fecha fecha;
    private ArrayList<Refaccion> refacciones;
    private BigDecimal total,totalGanado;

    public Ticket(Fecha fecha, ArrayList<Refaccion> refacciones, BigDecimal total, BigDecimal totalGanado) {
        this.fecha = fecha;
        this.refacciones = refacciones;
        this.total=calcularTotal(refacciones);
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Refaccion> getRefacciones() {
        return refacciones;
    }

    public void setRefacciones(ArrayList<Refaccion> refacciones) {
        this.refacciones = refacciones;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalGanado() {
        return totalGanado;
    }

    public void setTotalGanado(BigDecimal totalGanado) {
        this.totalGanado = totalGanado;
    }
    
    public BigDecimal calcularTotal(ArrayList<Refaccion> refacciones){
        BigDecimal retorno = new BigDecimal(0);
        for (int i=0;i<refacciones.size();i++){
            retorno.add(refacciones.get(i).getPrecioDeVenta());
        }
        return retorno;
    }
}
