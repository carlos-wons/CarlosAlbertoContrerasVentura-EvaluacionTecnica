package com.miproyecto.auth;

import javax.swing.*;
import java.awt.*;

public class AddProductDialog extends JDialog {
    private JTextField codigoField, nombreField, precioField;
    private JTextArea descripcionArea;
    private boolean ok = false;

    public AddProductDialog(Frame owner) {
        super(owner, "Agregar producto", true);
        init();
        setSize(420, 320);
        setLocationRelativeTo(owner);
    }

    private void init() {
        codigoField = new JTextField(20);
        nombreField = new JTextField(25);
        precioField = new JTextField(10);
        descripcionArea = new JTextArea(5, 25);
        JScrollPane descScroll = new JScrollPane(descripcionArea);

        JButton btnOk = new JButton("Crear");
        JButton btnCancel = new JButton("Cancelar");
        btnOk.addActionListener(e -> onOk());
        btnCancel.addActionListener(e -> onCancel());

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx=0; gbc.gridy=0; p.add(new JLabel("Código (opcional):"), gbc);
        gbc.gridx=1; p.add(codigoField, gbc);
        gbc.gridx=0; gbc.gridy=1; p.add(new JLabel("Nombre:"), gbc);
        gbc.gridx=1; p.add(nombreField, gbc);
        gbc.gridx=0; gbc.gridy=2; p.add(new JLabel("Precio:"), gbc);
        gbc.gridx=1; p.add(precioField, gbc);
        gbc.gridx=0; gbc.gridy=3; p.add(new JLabel("Descripción:"), gbc);
        gbc.gridx=1; p.add(descScroll, gbc);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bot.add(btnOk);
        bot.add(btnCancel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(bot, BorderLayout.SOUTH);
    }

    private void onOk() {
        String nombre = nombreField.getText().trim();
        String precioS = precioField.getText().trim();
        if (nombre.isEmpty() || precioS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y precio son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Double.parseDouble(precioS);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ok = true;
        setVisible(false);
    }

    private void onCancel() {
        ok = false;
        setVisible(false);
    }

    public boolean isOk() { return ok; }
    public String getCodigo() { return codigoField.getText().trim(); }
    public String getNombre() { return nombreField.getText().trim(); }
    public String getDescripcion() { return descripcionArea.getText().trim(); }
    public double getPrecio() { return Double.parseDouble(precioField.getText().trim()); }
}
