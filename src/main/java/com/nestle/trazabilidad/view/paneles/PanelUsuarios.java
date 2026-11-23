package com.nestle.trazabilidad.view.paneles;

import com.nestle.trazabilidad.entity.PermisoEntity;
import com.nestle.trazabilidad.entity.RolEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;
import com.nestle.trazabilidad.view.VistaUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** CU-12: administración de usuarios, roles y permisos. */
public class PanelUsuarios extends JPanel {

    private final JTextField txtNombres = new JTextField(14);
    private final JTextField txtApellidos = new JTextField(14);
    private final JTextField txtUserName = new JTextField(14);
    private final JTextField txtPassword = new JTextField(14);
    private final JComboBox<String> cbRol = new JComboBox<>();
    private final JComboBox<String> cbEstadoUsuario = new JComboBox<>(new String[]{"ACTIVO", "INACTIVO"});
    private final JButton btnRegistrarUsuario = new JButton("Registrar usuario");
    private final JButton btnGuardarUsuario = new JButton("Guardar cambios");
    private final JButton btnCambiarEstado = new JButton("Activar / desactivar");
    private final JButton btnActualizar = new JButton("Actualizar lista");

    private final JTextField txtNombreRol = new JTextField(16);
    private final JTextField txtDescripcionRol = new JTextField(18);
    private final JComboBox<String> cbEstadoRol = new JComboBox<>(new String[]{"ACTIVO", "INACTIVO"});
    private final JButton btnRegistrarRol = new JButton("Registrar rol");
    private final JButton btnAsignarPermisos = new JButton("Asignar permisos");
    private final DefaultListModel<String> modeloPermisos = new DefaultListModel<>();
    private final JList<String> listaPermisos = new JList<>(modeloPermisos);

    private final JTextField txtCodigoPermiso = new JTextField(14);
    private final JTextField txtNombrePermiso = new JTextField(14);
    private final JTextField txtDescripcionPermiso = new JTextField(18);
    private final JButton btnRegistrarPermiso = new JButton("Registrar permiso");

    private final DefaultTableModel modeloUsuarios = VistaUtil.modeloTabla(new String[]{
            "ID", "Nombres", "Apellidos", "Usuario", "Rol", "Estado"
    });
    private final DefaultTableModel modeloRoles = VistaUtil.modeloTabla(new String[]{
            "ID", "Nombre", "Descripción", "Estado"
    });
    private final DefaultTableModel modeloPermisosTabla = VistaUtil.modeloTabla(new String[]{
            "ID", "Código", "Nombre", "Descripción"
    });
    private final JTable tablaUsuarios = new JTable(modeloUsuarios);
    private final JTable tablaRoles = new JTable(modeloRoles);
    private final JTable tablaPermisos = new JTable(modeloPermisosTabla);

    private List<UsuarioEntity> usuarios = List.of();
    private List<RolEntity> roles = List.of();

    public PanelUsuarios() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formUsuario = new JPanel(new GridBagLayout());
        formUsuario.setBorder(BorderFactory.createTitledBorder("Datos del usuario"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        VistaUtil.agregarCampo(formUsuario, gbc, 0, 0, "Nombres:", txtNombres);
        VistaUtil.agregarCampo(formUsuario, gbc, 0, 2, "Apellidos:", txtApellidos);
        VistaUtil.agregarCampo(formUsuario, gbc, 1, 0, "Usuario:", txtUserName);
        VistaUtil.agregarCampo(formUsuario, gbc, 1, 2, "Contraseña:", txtPassword);
        VistaUtil.agregarCampo(formUsuario, gbc, 2, 0, "Rol:", cbRol);
        VistaUtil.agregarCampo(formUsuario, gbc, 2, 2, "Estado:", cbEstadoUsuario);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        formUsuario.add(VistaUtil.panelBotones(
                btnRegistrarUsuario, btnGuardarUsuario, btnCambiarEstado, btnActualizar), gbc);

        JPanel tabUsuarios = new JPanel(new BorderLayout());
        tabUsuarios.add(formUsuario, BorderLayout.NORTH);
        tabUsuarios.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

        JPanel formRol = new JPanel(new GridBagLayout());
        formRol.setBorder(BorderFactory.createTitledBorder("Nuevo rol"));
        GridBagConstraints gbr = new GridBagConstraints();
        gbr.insets = new Insets(4, 4, 4, 4);
        gbr.anchor = GridBagConstraints.WEST;
        VistaUtil.agregarCampo(formRol, gbr, 0, 0, "Nombre:", txtNombreRol);
        VistaUtil.agregarCampo(formRol, gbr, 0, 2, "Descripción:", txtDescripcionRol);
        VistaUtil.agregarCampo(formRol, gbr, 1, 0, "Estado:", cbEstadoRol);
        gbr.gridx = 0;
        gbr.gridy = 2;
        gbr.gridwidth = 4;
        formRol.add(VistaUtil.panelBotones(btnRegistrarRol), gbr);

        JPanel panelRoles = new JPanel(new BorderLayout(8, 8));
        panelRoles.add(formRol, BorderLayout.NORTH);
        panelRoles.add(new JScrollPane(tablaRoles), BorderLayout.CENTER);
        JPanel panelPermisosRol = new JPanel(new BorderLayout(4, 4));
        panelPermisosRol.setBorder(BorderFactory.createTitledBorder(
                "Permisos asignados al rol seleccionado (Ctrl + clic para varios)"));
        listaPermisos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        panelPermisosRol.add(new JScrollPane(listaPermisos), BorderLayout.CENTER);
        panelPermisosRol.add(btnAsignarPermisos, BorderLayout.SOUTH);
        panelRoles.add(panelPermisosRol, BorderLayout.EAST);

        JPanel formPermiso = new JPanel(new GridBagLayout());
        formPermiso.setBorder(BorderFactory.createTitledBorder("Nuevo permiso"));
        GridBagConstraints gbp = new GridBagConstraints();
        gbp.insets = new Insets(4, 4, 4, 4);
        gbp.anchor = GridBagConstraints.WEST;
        VistaUtil.agregarCampo(formPermiso, gbp, 0, 0, "Código:", txtCodigoPermiso);
        VistaUtil.agregarCampo(formPermiso, gbp, 0, 2, "Nombre:", txtNombrePermiso);
        VistaUtil.agregarCampo(formPermiso, gbp, 1, 0, "Descripción:", txtDescripcionPermiso);
        gbp.gridx = 0;
        gbp.gridy = 2;
        gbp.gridwidth = 4;
        formPermiso.add(VistaUtil.panelBotones(btnRegistrarPermiso), gbp);

        JPanel tabPermisos = new JPanel(new BorderLayout());
        tabPermisos.add(formPermiso, BorderLayout.NORTH);
        tabPermisos.add(new JScrollPane(tablaPermisos), BorderLayout.CENTER);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Usuarios", tabUsuarios);
        pestanas.addTab("Roles y permisos", panelRoles);
        pestanas.addTab("Permisos", tabPermisos);
        add(pestanas, BorderLayout.CENTER);

        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRoles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
        modeloUsuarios.setRowCount(0);
        for (UsuarioEntity u : usuarios) {
            modeloUsuarios.addRow(new Object[]{
                    u.getIdUsuario(), u.getNombres(), u.getApellidos(), u.getUserName(),
                    u.getRol() != null ? u.getRol().getNombre() : "",
                    u.isEstado() ? "ACTIVO" : "INACTIVO"
            });
        }
    }

    public void setRoles(List<RolEntity> roles) {
        this.roles = roles;
        modeloRoles.setRowCount(0);
        cbRol.removeAllItems();
        for (RolEntity r : roles) {
            modeloRoles.addRow(new Object[]{
                    r.getIdRol(), r.getNombre(), r.getDescripcion(), r.getEstado()
            });
            cbRol.addItem(r.getIdRol() + " - " + r.getNombre());
        }
    }

    public void setPermisos(List<PermisoEntity> permisos) {
        modeloPermisosTabla.setRowCount(0);
        modeloPermisos.clear();
        for (PermisoEntity p : permisos) {
            modeloPermisosTabla.addRow(new Object[]{
                    p.getIdPermiso(), p.getCodigo(), p.getNombre(), p.getDescripcion()
            });
            modeloPermisos.addElement(p.getIdPermiso() + " - " + p.getNombre());
        }
    }

    public void marcarPermisos(List<Integer> idsPermisos) {
        listaPermisos.clearSelection();
        for (int i = 0; i < modeloPermisos.size(); i++) {
            Integer id = VistaUtil.idDeCombo(modeloPermisos.get(i));
            if (id != null && idsPermisos.contains(id)) {
                listaPermisos.addSelectionInterval(i, i);
            }
        }
    }

    public UsuarioEntity getUsuarioSeleccionado() {
        int fila = tablaUsuarios.getSelectedRow();
        return fila < 0 || fila >= usuarios.size() ? null : usuarios.get(fila);
    }

    public RolEntity getRolSeleccionado() {
        int fila = tablaRoles.getSelectedRow();
        return fila < 0 || fila >= roles.size() ? null : roles.get(fila);
    }

    public Integer getIdRolFormulario() { return VistaUtil.idDeCombo(cbRol.getSelectedItem()); }

    public List<Integer> getIdsPermisosSeleccionados() {
        List<Integer> ids = new ArrayList<>();
        for (String valor : listaPermisos.getSelectedValuesList()) {
            Integer id = VistaUtil.idDeCombo(valor);
            if (id != null) ids.add(id);
        }
        return ids;
    }

    public JTextField getTxtNombres() { return txtNombres; }
    public JTextField getTxtApellidos() { return txtApellidos; }
    public JTextField getTxtUserName() { return txtUserName; }
    public JTextField getTxtPassword() { return txtPassword; }
    public JComboBox<String> getCbRol() { return cbRol; }
    public JComboBox<String> getCbEstadoUsuario() { return cbEstadoUsuario; }
    public JTextField getTxtNombreRol() { return txtNombreRol; }
    public JTextField getTxtDescripcionRol() { return txtDescripcionRol; }
    public JComboBox<String> getCbEstadoRol() { return cbEstadoRol; }
    public JTextField getTxtCodigoPermiso() { return txtCodigoPermiso; }
    public JTextField getTxtNombrePermiso() { return txtNombrePermiso; }
    public JTextField getTxtDescripcionPermiso() { return txtDescripcionPermiso; }
    public JButton getBtnRegistrarUsuario() { return btnRegistrarUsuario; }
    public JButton getBtnGuardarUsuario() { return btnGuardarUsuario; }
    public JButton getBtnCambiarEstado() { return btnCambiarEstado; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnRegistrarRol() { return btnRegistrarRol; }
    public JButton getBtnAsignarPermisos() { return btnAsignarPermisos; }
    public JButton getBtnRegistrarPermiso() { return btnRegistrarPermiso; }
    public JTable getTablaRoles() { return tablaRoles; }
}
