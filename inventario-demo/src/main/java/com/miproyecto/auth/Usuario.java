package com.miproyecto.auth;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contraseñaHash;
    private Rol rol;
    private int estatus;

    public Usuario(int idUsuario, String nombre, String correo, String contraseñaHash, Rol rol, int estatus) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseñaHash = contraseñaHash;
        this.rol = rol;
        this.estatus = estatus;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContraseñaHash() { return contraseñaHash; }
    public Rol getRol() { return rol; }
    public int getEstatus() { return estatus; }
}
