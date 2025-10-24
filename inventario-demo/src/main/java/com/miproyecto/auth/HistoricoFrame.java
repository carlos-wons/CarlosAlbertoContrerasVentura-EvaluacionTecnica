package com.miproyecto.auth;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoricoFrame extends JFrame {
    private HistoricoDAO dao = new HistoricoDAO();
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> tipoCombo;
    private JTextField productoIdField;
    private JButton btnFiltrar, btnBuscar, btnCerrar;

    public HistoricoFrame() {
        setTitle("Histórico de Movimientos");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadTable("ALL", 0);
    }

    private void initComponents() {
        model = new DefaultTableModel(new Object[]{
                "ID","Prod ID","Código","Producto","Tipo","Cantidad","Detalle","Referencia","Usuario","Fecha"
        }, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        tipoCombo = new JComboBox<>(new String[]{"ALL","ENTRADA","SALIDA"});
        productoIdField = new JTextField(6); // si pasas un id de producto lo filtra
        btnFiltrar = new JButton("Filtrar");
        btnBuscar = new JButton("Buscar texto");
        btnCerrar = new JButton("Cerrar");

        btnFiltrar.addActionListener(e -> {
            String tipo = (String) tipoCombo.getSelectedItem();
            int idProd = 0;
            try {
                if (!productoIdField.getText().trim().isEmpty()) idProd = Integer.parseInt(productoIdField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID de producto inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            loadTable(tipo, idProd);
        });

        btnBuscar.addActionListener(e -> {
            String texto = JOptionPane.showInputDialog(this, "Texto a buscar (detalle/referencia/producto):");
            if (texto != null && !texto.trim().isEmpty()) {
                try {
                    List<Movimiento> lista = dao.searchHistoricoByText(texto.trim());
                    fillTable(lista);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error buscando: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCerrar.addActionListener(e -> this.dispose());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Tipo:"));
        top.add(tipoCombo);
        top.add(new JLabel("Prod ID:"));
        top.add(productoIdField);
        top.add(btnFiltrar);
        top.add(btnBuscar);
        top.add(btnCerrar);

        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void loadTable(String tipo, int idProducto) {
        try {
            List<Movimiento> lista = dao.listHistorico(tipo, idProducto);
            fillTable(lista);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error cargando histórico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillTable(List<Movimiento> lista) {
        model.setRowCount(0);
        for (Movimiento m : lista) {
            model.addRow(new Object[]{
                    m.getIdHistorico(),
                    m.getIdProducto(),
                    m.getProductoCodigo(),
                    m.getProductoNombre(),
                    m.getTipo(),
                    m.getCantidad(),
                    m.getDetalle(),
                    m.getReferencia(),
                    m.getUsuarioNombre(),
                    m.getFecha()
            });
        }
    }
}
