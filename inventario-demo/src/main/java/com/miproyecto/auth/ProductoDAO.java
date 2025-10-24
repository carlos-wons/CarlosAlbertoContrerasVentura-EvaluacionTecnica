package com.miproyecto.auth;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    /**
     * Lista productos.
     * filtro: "ALL" | "ACTIVOS" | "INACTIVOS"
     */
    public List<Producto> listProductos(String filtro) throws SQLException {
        String sqlBase = "SELECT idProducto, codigo, nombre, descripcion, precio, cantidad, estatus, creado_en, actualizado_en FROM productos";
        String where = "";
        if ("ACTIVOS".equalsIgnoreCase(filtro)) where = " WHERE estatus = 1";
        else if ("INACTIVOS".equalsIgnoreCase(filtro)) where = " WHERE estatus = 0";

        String sql = sqlBase + where + " ORDER BY nombre";
        List<Producto> lista = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("idProducto"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad"),
                        rs.getInt("estatus"),
                        rs.getTimestamp("creado_en"),
                        rs.getTimestamp("actualizado_en")
                );
                lista.add(p);
            }
        }
        return lista;
    }

    /**
     * Crea un producto con cantidad inicial 0.
     */
    public boolean createProducto(String codigo, String nombre, String descripcion, double precio) throws SQLException {
        String sql = "INSERT INTO productos (codigo, nombre, descripcion, precio, cantidad, estatus) VALUES (?, ?, ?, ?, 0, 1)";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setDouble(4, precio);
            return ps.executeUpdate() == 1;
        }
    }

    /**
     * Inserta registro de entrada en historico_movimientos.
     * Confiamos en el trigger de la BD para actualizar productos.cantidad.
     */
    public boolean insertEntradaHistorico(int idProducto, int idUsuario, int cantidad, String detalle, String referencia) throws SQLException {
        String sql = "INSERT INTO historico_movimientos (idProducto, idUsuario, tipo, cantidad, detalle, referencia) VALUES (?, ?, 'ENTRADA', ?, ?, ?)";
        Connection conn = DBConnection.getConnection();
        boolean ok = false;
        boolean previousAutoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idProducto);
                ps.setInt(2, idUsuario);
                ps.setInt(3, cantidad);
                ps.setString(4, detalle);
                ps.setString(5, referencia);
                ps.executeUpdate();
            }
            // Si el trigger o la inserción falla, la excepción hará rollback
            conn.commit();
            ok = true;
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(previousAutoCommit);
        }
        return ok;
    }

    /**
     * Inserta un registro de salida en historico_movimientos.
     * Valida en la capa de aplicación que la cantidad a sacar no supere el stock actual.
     * Confiamos en el trigger para consistencia final (y para bloquear si existe carrera).
     */
    public boolean insertSalidaHistorico(int idProducto, int idUsuario, int cantidad, String detalle, String referencia) throws SQLException {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que 0");

        Connection conn = DBConnection.getConnection();
        boolean previousAutoCommit = conn.getAutoCommit();
        try {
            // 1) verificar stock actual
            Producto p = findById(idProducto);
            if (p == null) throw new SQLException("Producto no encontrado");
            if (p.getCantidad() < cantidad) {
                throw new SQLException("Stock insuficiente. Stock actual: " + p.getCantidad());
            }

            // 2) insertar historico dentro de transacción
            conn.setAutoCommit(false);
            String sql = "INSERT INTO historico_movimientos (idProducto, idUsuario, tipo, cantidad, detalle, referencia) " +
                        "VALUES (?, ?, 'SALIDA', ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idProducto);
                ps.setInt(2, idUsuario);
                ps.setInt(3, cantidad);
                ps.setString(4, detalle);
                ps.setString(5, referencia);
                ps.executeUpdate();
            }

            // commit (el trigger que actualiza productos.cantidad actúa como parte de la misma transacción)
            conn.commit();
            return true;
        } catch (SQLException ex) {
            try { conn.rollback(); } catch (SQLException e) { /* ignora rollback error */ }
            throw ex;
        } finally {
            conn.setAutoCommit(previousAutoCommit);
        }
    }

    /**
     * Cambia estatus (0 o 1)
     */
    public boolean setEstatusProducto(int idProducto, int estatus) throws SQLException {
        String sql = "UPDATE productos SET estatus = ? WHERE idProducto = ?";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, estatus);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() == 1;
        }
    }

    public Producto findById(int idProducto) throws SQLException {
        String sql = "SELECT idProducto, codigo, nombre, descripcion, precio, cantidad, estatus, creado_en, actualizado_en FROM productos WHERE idProducto = ? LIMIT 1";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                            rs.getInt("idProducto"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getInt("cantidad"),
                            rs.getInt("estatus"),
                            rs.getTimestamp("creado_en"),
                            rs.getTimestamp("actualizado_en")
                    );
                }
            }
        }
        return null;
    }
}
