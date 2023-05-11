/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 *
 * @author jdemi
 */
public class Refaccion implements Serializable {
    private int id;
    private int cantidad;
    private String nombre;
    private BigDecimal precioDeCompra;
    private BigDecimal precioDeVenta;
    private String categoria;
    private String marca;

    public Refaccion(int id, int cantidad, String nombre, BigDecimal precioDeCompra, BigDecimal precioDeVenta, String categoria, String marca) {
        this.id = id;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.precioDeCompra = precioDeCompra;
        this.precioDeVenta = precioDeVenta;
        this.categoria = categoria;
        this.marca = marca;
    }
    public Refaccion(Refaccion refaccion){
        this.id=refaccion.getId();
        this.cantidad=refaccion.getCantidad();
        this.categoria=refaccion.getCategoria();
        this.marca=refaccion.getMarca();
        this.nombre=refaccion.getNombre();
        this.precioDeCompra=refaccion.precioDeCompra;
        this.precioDeVenta=refaccion.precioDeVenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioDeCompra() {
        return precioDeCompra;
    }

    public void setPrecioDeCompra(BigDecimal precioDeCompra) {
        this.precioDeCompra = precioDeCompra;
    }

    public BigDecimal getPrecioDeVenta() {
        return precioDeVenta;
    }

    public void setPrecioDeVenta(BigDecimal precioDeVenta) {
        this.precioDeVenta = precioDeVenta;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void cambiarValor(int canCambio){
        this.cantidad=this.cantidad+canCambio;
    }
    
    public BigDecimal calcularTotal(){
        return this.precioDeVenta.multiply(new BigDecimal(this.cantidad));
    }
    
}
