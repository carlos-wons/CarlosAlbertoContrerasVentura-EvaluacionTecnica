package com.miproyecto.auth;

import java.sql.Timestamp;

public class Producto {
    private int idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidad;
    private int estatus;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    public Producto(int idProducto, String codigo, String nombre, String descripcion,
                    double precio, int cantidad, int estatus, Timestamp creadoEn, Timestamp actualizadoEn) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.estatus = estatus;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    // getters y setters mínimos
    public int getIdProducto() { return idProducto; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public int getEstatus() { return estatus; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public Timestamp getActualizadoEn() { return actualizadoEn; }
}
