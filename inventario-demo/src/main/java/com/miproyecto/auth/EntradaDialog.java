package com.miproyecto.auth;

import javax.swing.*;
import java.awt.*;

public class EntradaDialog extends JDialog {
    private JSpinner cantidadSpinner;
    private JTextField referenciaField;
    private JTextArea detalleArea;
    private boolean ok = false;

    public EntradaDialog(Frame owner, String nombreProducto) {
        super(owner, "Entrada para: " + nombreProducto, true);
        init();
        setSize(380, 300);
        setLocationRelativeTo(owner);
    }

    private void init() {
        cantidadSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        referenciaField = new JTextField(20);
        detalleArea = new JTextArea(4, 22);
        JScrollPane detailScroll = new JScrollPane(detalleArea);

        JButton btnOk = new JButton("Registrar");
        JButton btnCancel = new JButton("Cancelar");
        btnOk.addActionListener(e -> onOk());
        btnCancel.addActionListener(e -> onCancel());

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx=0; gbc.gridy=0; p.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx=1; p.add(cantidadSpinner, gbc);
        gbc.gridx=0; gbc.gridy=1; p.add(new JLabel("Referencia:"), gbc);
        gbc.gridx=1; p.add(referenciaField, gbc);
        gbc.gridx=0; gbc.gridy=2; p.add(new JLabel("Detalle:"), gbc);
        gbc.gridx=1; p.add(detailScroll, gbc);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bot.add(btnOk);
        bot.add(btnCancel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(bot, BorderLayout.SOUTH);
    }

    private void onOk() {
        int cantidad = (int) cantidadSpinner.getValue();
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0.", "Error", JOptionPane.ERROR_MESSAGE);
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
    public int getCantidad() { return (int) cantidadSpinner.getValue(); }
    public String getReferencia() { return referenciaField.getText().trim(); }
    public String getDetalle() { return detalleArea.getText().trim(); }
}
