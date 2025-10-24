package com.miproyecto.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario findByCorreo(String correo) throws SQLException {
        String sql = "SELECT u.idUsuario, u.nombre AS usuario_nombre, u.correo, u.contraseña, u.estatus, r.idRol, r.nombre AS rol_nombre " +
                "FROM usuarios u JOIN roles r ON u.idRol = r.idRol WHERE u.correo = ? LIMIT 1";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idUsuario = rs.getInt("idUsuario");
                    String nombre = rs.getString("usuario_nombre");
                    String correoDb = rs.getString("correo");
                    String contraseñaHash = rs.getString("contraseña");
                    int estatus = rs.getInt("estatus");
                    int idRol = rs.getInt("idRol");
                    String nombreRol = rs.getString("rol_nombre");
                    Rol rol = new Rol(idRol, nombreRol);
                    return new Usuario(idUsuario, nombre, correoDb, contraseñaHash, rol, estatus);
                } else {
                    return null;
                }
            }
        }
    }

    public boolean createUsuario(String nombre, String correo, String contraseñaHash, int idRol) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, idRol, estatus) VALUES (?, ?, ?, ?, 1)";
        Connection conn = DBConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contraseñaHash);
            ps.setInt(4, idRol);
            return ps.executeUpdate() == 1;
        }
    }
}
