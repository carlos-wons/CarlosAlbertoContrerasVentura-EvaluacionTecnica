package com.miproyecto.auth;

import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Intenta autenticar; devuelve el Usuario si OK, o null si falla.
     */
    public Usuario authenticate(String correo, String contraseñaPlain) {
        try {
            Usuario usuario = usuarioDAO.findByCorreo(correo);
            if (usuario == null) return null;
            if (usuario.getEstatus() != 1) return null; // usuario inactivo

            String storedHash = usuario.getContraseñaHash();
            if (BCrypt.checkpw(contraseñaPlain, storedHash)) {
                return usuario;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para hashear contraseñas al registrarlas
    public static String hashPassword(String plain) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(plain, salt);
    }
}
