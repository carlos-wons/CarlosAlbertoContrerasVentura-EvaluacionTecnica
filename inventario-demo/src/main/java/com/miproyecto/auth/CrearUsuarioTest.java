package com.miproyecto.auth;

public class CrearUsuarioTest {
    public static void main(String[] args) throws Exception {
        AuthService auth = new AuthService();
        UsuarioDAO dao = new UsuarioDAO();

        String nombre = "Admin Demo";
        String correo = "admin@demo.local";
        String plain = "Admin123!";
        String hash = AuthService.hashPassword(plain); // bcrypt

        boolean ok = dao.createUsuario(nombre, correo, hash, 1); // rol 1 = administrador
        System.out.println("Usuario creado? " + ok);
    }
}
