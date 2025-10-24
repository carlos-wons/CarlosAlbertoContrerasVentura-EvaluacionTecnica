package com.miproyecto.auth;

public class CrearUsuarioTestAlmacenista {
    public static void main(String[] args) throws Exception {
        AuthService auth = new AuthService();
        UsuarioDAO dao = new UsuarioDAO();

        String nombre = "Almacenista Demo";
        String correo = "almacenistapro@demo.local";
        String plain = "Admin123!";
        String hash = AuthService.hashPassword(plain); // bcrypt

        boolean ok = dao.createUsuario(nombre, correo, hash, 2); // rol 2 = almacenista
        System.out.println("Usuario creado? " + ok);
    }
}
