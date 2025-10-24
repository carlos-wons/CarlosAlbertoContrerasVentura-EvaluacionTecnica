package com.miproyecto.auth;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDAO {

    /**
     * Lista movimientos con opción de filtrar por tipo y por producto (0 = todos).
     * tipoFiltro: "ALL", "ENTRADA", "SALIDA"
     */
    public List<Movimiento> listHistorico(String tipoFiltro, int idProductoFiltro) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT h.idHistorico, h.idProducto, p.codigo AS producto_codigo, p.nombre AS producto_nombre, ")
          .append("h.idUsuario, u.nombre AS usuario_nombre, h.tipo, h.cantidad, h.detalle, h.referencia, h.fecha ")
          .append("FROM historico_movimientos h ")
          .append("JOIN productos p ON h.idProducto = p.idProducto ")
          .append("JOIN usuarios u ON h.idUsuario = u.idUsuario ");

        boolean whereAdded = false;
        if (!"ALL".equalsIgnoreCase(tipoFiltro)) {
            sb.append("WHERE h.tipo = ? ");
            whereAdded = true;
        }
        if (idProductoFiltro > 0) {
            if (whereAdded) sb.append("AND h.idProducto = ? ");
            else { sb.append("WHERE h.idProducto = ? "); whereAdded = true; }
        }
        sb.append("ORDER BY h.fecha DESC LIMIT 1000"); // límite por seguridad; se puede paginar

        String sql = sb.toString();
        Connection conn = DBConnection.getConnection();
        List<Movimiento> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            if (!"ALL".equalsIgnoreCase(tipoFiltro)) {
                ps.setString(idx++, tipoFiltro.toUpperCase());
            }
            if (idProductoFiltro > 0) {
                ps.setInt(idx++, idProductoFiltro);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movimiento m = new Movimiento(
                            rs.getLong("idHistorico"),
                            rs.getInt("idProducto"),
                            rs.getString("producto_codigo"),
                            rs.getString("producto_nombre"),
                            rs.getInt("idUsuario"),
                            rs.getString("usuario_nombre"),
                            rs.getString("tipo"),
                            rs.getInt("cantidad"),
                            rs.getString("detalle"),
                            rs.getString("referencia"),
                            rs.getTimestamp("fecha")
                    );
                    lista.add(m);
                }
            }
        }
        return lista;
    }

    /**
     * Opcional: busca movimientos por una referencia/texto en detalle (simple).
     */
    public List<Movimiento> searchHistoricoByText(String texto) throws SQLException {
        String sql = "SELECT h.idHistorico, h.idProducto, p.codigo AS producto_codigo, p.nombre AS producto_nombre, " +
                "h.idUsuario, u.nombre AS usuario_nombre, h.tipo, h.cantidad, h.detalle, h.referencia, h.fecha " +
                "FROM historico_movimientos h " +
                "JOIN productos p ON h.idProducto = p.idProducto " +
                "JOIN usuarios u ON h.idUsuario = u.idUsuario " +
                "WHERE h.detalle LIKE ? OR h.referencia LIKE ? OR p.nombre LIKE ? OR p.codigo LIKE ? " +
                "ORDER BY h.fecha DESC LIMIT 1000";
        Connection conn = DBConnection.getConnection();
        List<Movimiento> lista = new ArrayList<>();
        String like = "%" + texto + "%";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Movimiento m = new Movimiento(
                            rs.getLong("idHistorico"),
                            rs.getInt("idProducto"),
                            rs.getString("producto_codigo"),
                            rs.getString("producto_nombre"),
                            rs.getInt("idUsuario"),
                            rs.getString("usuario_nombre"),
                            rs.getString("tipo"),
                            rs.getInt("cantidad"),
                            rs.getString("detalle"),
                            rs.getString("referencia"),
                            rs.getTimestamp("fecha")
                    );
                    lista.add(m);
                }
            }
        }
        return lista;
    }
}
