package com.miproyecto.auth;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class InventoryFrame extends JFrame {
    private Usuario usuarioActual;
    private ProductoDAO productoDAO = new ProductoDAO();

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> filtroCombo;
    private JButton btnAgregar, btnEntrada, btnToggleStatus, btnRefrescar, btnVerHistorico, btnSalida;

    public InventoryFrame(Usuario usuario) {
        this.usuarioActual = usuario;
        setTitle("Inventario - Usuario: " + usuario.getNombre() + " (Rol: " + usuario.getRol().getNombre() + ")");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadTable("ACTIVOS"); // por default mostrar activos
    }

    private void initComponents() {
        model = new DefaultTableModel(new Object[]{"ID","Código","Nombre","Descripción","Precio","Cantidad","Estatus"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);

        filtroCombo = new JComboBox<>(new String[]{"ACTIVOS","INACTIVOS","ALL"});
        filtroCombo.addActionListener(e -> loadTable((String)filtroCombo.getSelectedItem()));

        btnAgregar = new JButton("Agregar producto");
        btnEntrada = new JButton("Entrada (+)");
        btnToggleStatus = new JButton("Activar/Desactivar");
        btnRefrescar = new JButton("Refrescar");

        btnVerHistorico = new JButton("Ver Histórico");
        btnVerHistorico.setEnabled(usuarioActual.getRol().getIdRol() == 1); // solo admin
        btnVerHistorico.addActionListener(e -> {
            HistoricoFrame hf = new HistoricoFrame();
            hf.setVisible(true);
        });

        btnSalida = new JButton("Salida de productos");
        btnSalida.setEnabled(usuarioActual.getRol().getIdRol() == 2); // solo almacenista
        btnSalida.addActionListener(e -> {
            SalidaFrame sf = new SalidaFrame(usuarioActual);
            sf.setVisible(true);
        });

        //Aquí mostramos botones según rol:
        int idRol = usuarioActual.getRol().getIdRol();
        // Permisos: admin (1) y almacenista (2) pueden operar. Otros solo ver.
        boolean puedeOperar = (idRol == 1 ); //|| idRol == 2
        btnAgregar.setEnabled(puedeOperar);
        btnEntrada.setEnabled(puedeOperar);
        btnToggleStatus.setEnabled(puedeOperar);

        btnAgregar.addActionListener(e -> onAgregar());
        btnEntrada.addActionListener(e -> onEntrada());
        btnToggleStatus.addActionListener(e -> onToggleStatus());
        btnRefrescar.addActionListener(e -> loadTable((String)filtroCombo.getSelectedItem()));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Filtro:"));
        topPanel.add(filtroCombo);
        topPanel.add(btnRefrescar);
        topPanel.add(btnAgregar);
        topPanel.add(btnEntrada);
        topPanel.add(btnToggleStatus);
        topPanel.add(btnVerHistorico);
        topPanel.add(btnSalida);
        

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void loadTable(String filtro) {
        try {
            List<Producto> lista = productoDAO.listProductos(filtro);
            model.setRowCount(0);
            for (Producto p : lista) {
                model.addRow(new Object[]{
                        p.getIdProducto(),
                        p.getCodigo(),
                        p.getNombre(),
                        p.getDescripcion(),
                        String.format("%.2f", p.getPrecio()),
                        p.getCantidad(),
                        p.getEstatus() == 1 ? "Activo" : "Inactivo"
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAgregar() {
        AddProductDialog dlg = new AddProductDialog(this);
        dlg.setVisible(true);
        if (dlg.isOk()) {
            try {
                boolean ok = productoDAO.createProducto(dlg.getCodigo(), dlg.getNombre(), dlg.getDescripcion(), dlg.getPrecio());
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Producto creado (cantidad inicial 0).");
                    loadTable((String)filtroCombo.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo crear el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error creando producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onEntrada() {
        int sel = table.getSelectedRow();
        if (sel < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idProducto = (int) model.getValueAt(sel, 0);
        String nombre = (String) model.getValueAt(sel, 2);

        EntradaDialog dlg = new EntradaDialog(this, nombre);
        dlg.setVisible(true);
        if (dlg.isOk()) {
            int cantidad = dlg.getCantidad();
            String detalle = dlg.getDetalle();
            String referencia = dlg.getReferencia();
            try {
                boolean ok = productoDAO.insertEntradaHistorico(idProducto, usuarioActual.getIdUsuario(), cantidad, detalle, referencia);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Entrada registrada correctamente.");
                    loadTable((String)filtroCombo.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(this, "No se guardó la entrada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                // si el trigger falla por alguna razón, lo mostramos
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error registrando entrada: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onToggleStatus() {
        int sel = table.getSelectedRow();
        if (sel < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idProducto = (int) model.getValueAt(sel, 0);
        String est = (String) model.getValueAt(sel, 6);
        int nuevo = "Activo".equalsIgnoreCase(est) ? 0 : 1;
        String accion = nuevo == 0 ? "dar de baja" : "activar";
        int confirm = JOptionPane.showConfirmDialog(this, "¿Confirma " + accion + " el producto seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            boolean ok = productoDAO.setEstatusProducto(idProducto, nuevo);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Estatus actualizado.");
                loadTable((String)filtroCombo.getSelectedItem());
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar estatus.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error actualizando estatus: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
