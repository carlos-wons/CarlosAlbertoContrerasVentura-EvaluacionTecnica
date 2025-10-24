package com.miproyecto.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField correoField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Inicio de Sesión - Inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JLabel correoLabel = new JLabel("Correo:");
        correoField = new JTextField(25);

        JLabel passLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField(25);

        loginButton = new JButton("Iniciar sesión");
        loginButton.addActionListener(this::onLogin);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panel.add(correoLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        panel.add(correoField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        panel.add(passLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);
        

        add(panel);
    }

    private void onLogin(ActionEvent evt) {
        String correo = correoField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if (correo.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa correo y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = authService.authenticate(correo, pass);
        if (usuario != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getNombre() + " — Rol: " + usuario.getRol().getNombre());
            abrirModuloSegunRol(usuario);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas o usuario inactivo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirModuloSegunRol(Usuario u) {
        int idRol = u.getRol().getIdRol();
        if (idRol == 1 || idRol == 2) {
            InventoryFrame inv = new InventoryFrame(u);
            inv.setVisible(true);
        } else if (idRol == 3) {
            JOptionPane.showMessageDialog(this, "Abrir módulo... (pendiente implementación)");
        } else {
            JOptionPane.showMessageDialog(this, "No tienes permisos para abrir módulos (rol sin permiso).");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
