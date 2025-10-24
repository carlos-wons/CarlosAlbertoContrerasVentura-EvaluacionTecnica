package com.miproyecto.auth;

import java.sql.Timestamp;

public class Movimiento {
    private long idHistorico;
    private int idProducto;
    private String productoCodigo;
    private String productoNombre;
    private int idUsuario;
    private String usuarioNombre;
    private String tipo; // "ENTRADA" | "SALIDA"
    private int cantidad;
    private String detalle;
    private String referencia;
    private Timestamp fecha;

    public Movimiento(long idHistorico, int idProducto, String productoCodigo, String productoNombre,
                      int idUsuario, String usuarioNombre, String tipo, int cantidad,
                      String detalle, String referencia, Timestamp fecha) {
        this.idHistorico = idHistorico;
        this.idProducto = idProducto;
        this.productoCodigo = productoCodigo;
        this.productoNombre = productoNombre;
        this.idUsuario = idUsuario;
        this.usuarioNombre = usuarioNombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.detalle = detalle;
        this.referencia = referencia;
        this.fecha = fecha;
    }

    // getters
    public long getIdHistorico() { return idHistorico; }
    public int getIdProducto() { return idProducto; }
    public String getProductoCodigo() { return productoCodigo; }
    public String getProductoNombre() { return productoNombre; }
    public int getIdUsuario() { return idUsuario; }
    public String getUsuarioNombre() { return usuarioNombre; }
    public String getTipo() { return tipo; }
    public int getCantidad() { return cantidad; }
    public String getDetalle() { return detalle; }
    public String getReferencia() { return referencia; }
    public Timestamp getFecha() { return fecha; }
}
