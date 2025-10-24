package com.miproyecto.auth;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SalidaFrame extends JFrame {
    private Usuario usuarioActual;
    private ProductoDAO productoDAO = new ProductoDAO();

    private JTable table;
    private DefaultTableModel model;
    private JButton btnSacar, btnRefrescar, btnCerrar;

    public SalidaFrame(Usuario usuario) {
        this.usuarioActual = usuario;
        setTitle("Salida de Productos - Usuario: " + usuario.getNombre());
        setSize(900, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadTable();
    }

    private void initComponents() {
        model = new DefaultTableModel(new Object[]{"ID","Código","Nombre","Descripción","Precio","Cantidad"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);

        btnSacar = new JButton("Sacar producto (restar)");
        btnRefrescar = new JButton("Refrescar");
        btnCerrar = new JButton("Cerrar");

        btnSacar.addActionListener(e -> onSacar());
        btnRefrescar.addActionListener(e -> loadTable());
        btnCerrar.addActionListener(e -> this.dispose());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnRefrescar);
        top.add(btnSacar);
        top.add(btnCerrar);

        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void loadTable() {
        try {
            List<Producto> lista = productoDAO.listProductos("ACTIVOS"); // sólo activos
            model.setRowCount(0);
            for (Producto p : lista) {
                model.addRow(new Object[]{
                        p.getIdProducto(),
                        p.getCodigo(),
                        p.getNombre(),
                        p.getDescripcion(),
                        String.format("%.2f", p.getPrecio()),
                        p.getCantidad()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onSacar() {
        int sel = table.getSelectedRow();
        if (sel < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idProducto = (int) model.getValueAt(sel, 0);
        String nombre = (String) model.getValueAt(sel, 2);
        int cantidadActual = (int) model.getValueAt(sel, 5);

        SalidaDialog dlg = new SalidaDialog(this, nombre, cantidadActual);
        dlg.setVisible(true);
        if (!dlg.isOk()) return;

        int cantidad = dlg.getCantidad();
        String detalle = dlg.getDetalle();
        String referencia = dlg.getReferencia();

        try {
            boolean ok = productoDAO.insertSalidaHistorico(idProducto, usuarioActual.getIdUsuario(), cantidad, detalle, referencia);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Salida registrada correctamente.");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "No se registró la salida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            // puede venir del chequeo en la app o del trigger en BD
            String msg = ex.getMessage();
            JOptionPane.showMessageDialog(this, "Error al registrar salida: " + msg, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
