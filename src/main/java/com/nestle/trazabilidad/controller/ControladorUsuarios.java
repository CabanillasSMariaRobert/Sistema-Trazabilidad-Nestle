package com.nestle.trazabilidad.controller;

import com.nestle.trazabilidad.entity.PermisoEntity;
import com.nestle.trazabilidad.entity.RolEntity;
import com.nestle.trazabilidad.entity.UsuarioEntity;
import com.nestle.trazabilidad.service.PermisoService;
import com.nestle.trazabilidad.service.RolService;
import com.nestle.trazabilidad.service.ServiceFactory;
import com.nestle.trazabilidad.service.UsuarioService;
import com.nestle.trazabilidad.view.paneles.PanelUsuarios;

import javax.swing.event.ListSelectionEvent;

/** Controller de la administración de usuarios, roles y permisos (CU-12). */
public class ControladorUsuarios extends ControladorBase {

    private final PanelUsuarios vista;
    private final UsuarioService usuarioService = ServiceFactory.usuario();
    private final RolService rolService = ServiceFactory.rol();
    private final PermisoService permisoService = ServiceFactory.permiso();

    public ControladorUsuarios(PanelUsuarios vista) {
        this.vista = vista;

        vista.getBtnActualizar().addActionListener(e -> cargarDatos());
        vista.getBtnRegistrarUsuario().addActionListener(e -> registrarUsuario());
        vista.getBtnGuardarUsuario().addActionListener(e -> guardarUsuario());
        vista.getBtnCambiarEstado().addActionListener(e -> cambiarEstadoUsuario());
        vista.getBtnRegistrarRol().addActionListener(e -> registrarRol());
        vista.getBtnAsignarPermisos().addActionListener(e -> asignarPermisos());
        vista.getBtnRegistrarPermiso().addActionListener(e -> registrarPermiso());

        vista.getTablaRoles().getSelectionModel().addListSelectionListener(this::rolSeleccionado);
    }

    public void cargarDatos() {
        try {
            vista.setUsuarios(usuarioService.listarTodos());
            vista.setRoles(rolService.listarTodos());
            vista.setPermisos(permisoService.listarTodos());
        } catch (Exception e) {
            mostrarError(vista, "Error al cargar administración", e);
        }
    }

    private void registrarUsuario() {
        Integer idRol = vista.getIdRolFormulario();
        if (idRol == null) {
            mostrarInformacion(vista, "Seleccione un rol.");
            return;
        }
        try {
            RolEntity rol = new RolEntity();
            rol.setIdRol(idRol);

            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setNombres(vista.getTxtNombres().getText().trim());
            usuario.setApellidos(vista.getTxtApellidos().getText().trim());
            usuario.setUserName(vista.getTxtUserName().getText().trim());
            usuario.setPasswordHash(vista.getTxtPassword().getText());
            usuario.setRol(rol);
            usuario.setEstado("ACTIVO".equals(vista.getCbEstadoUsuario().getSelectedItem()));

            Integer id = usuarioService.insertar(usuario);
            mostrarInformacion(vista, "Usuario registrado con ID " + id);
            cargarDatos();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar usuario", e);
        }
    }

    private void guardarUsuario() {
        UsuarioEntity usuario = vista.getUsuarioSeleccionado();
        Integer idRol = vista.getIdRolFormulario();
        if (usuario == null || idRol == null) {
            mostrarInformacion(vista, "Seleccione un usuario y un rol.");
            return;
        }
        try {
            RolEntity rol = new RolEntity();
            rol.setIdRol(idRol);

            usuario.setNombres(vista.getTxtNombres().getText().trim());
            usuario.setApellidos(vista.getTxtApellidos().getText().trim());
            usuario.setUserName(vista.getTxtUserName().getText().trim());
            String password = vista.getTxtPassword().getText();
            if (!password.isBlank()) {
                usuario.setPasswordHash(password);
            }
            usuario.setRol(rol);
            usuario.setEstado("ACTIVO".equals(vista.getCbEstadoUsuario().getSelectedItem()));

            if (usuarioService.actualizar(usuario)) {
                mostrarInformacion(vista, "Usuario actualizado.");
                cargarDatos();
            } else {
                mostrarInformacion(vista, "No se pudo actualizar el usuario.");
            }
        } catch (Exception e) {
            mostrarError(vista, "Error al actualizar usuario", e);
        }
    }

    private void cambiarEstadoUsuario() {
        UsuarioEntity usuario = vista.getUsuarioSeleccionado();
        if (usuario == null) {
            mostrarInformacion(vista, "Seleccione un usuario.");
            return;
        }
        try {
            boolean activo = !usuario.isEstado();
            if (usuarioService.cambiarEstado(usuario.getIdUsuario(), activo)) {
                mostrarInformacion(vista, "Estado cambiado a " + (activo ? "ACTIVO" : "INACTIVO"));
                cargarDatos();
            } else {
                mostrarInformacion(vista, "No se pudo cambiar el estado.");
            }
        } catch (Exception e) {
            mostrarError(vista, "Error al cambiar estado", e);
        }
    }

    private void registrarRol() {
        try {
            RolEntity rol = new RolEntity();
            rol.setNombre(vista.getTxtNombreRol().getText().trim());
            rol.setDescripcion(vista.getTxtDescripcionRol().getText().trim());
            rol.setEstado((String) vista.getCbEstadoRol().getSelectedItem());

            Integer id = rolService.insertar(rol);
            mostrarInformacion(vista, "Rol registrado con ID " + id);
            cargarDatos();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar rol", e);
        }
    }

    private void rolSeleccionado(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
        RolEntity rol = vista.getRolSeleccionado();
        if (rol == null) return;
        try {
            vista.marcarPermisos(rolService.listarPermisosPorRol(rol.getIdRol()));
        } catch (Exception ex) {
            mostrarError(vista, "Error al cargar permisos del rol", ex);
        }
    }

    private void asignarPermisos() {
        RolEntity rol = vista.getRolSeleccionado();
        if (rol == null) {
            mostrarInformacion(vista, "Seleccione un rol en la tabla.");
            return;
        }
        try {
            boolean ok = rolService.asignarPermisos(rol.getIdRol(), vista.getIdsPermisosSeleccionados());
            if (ok) {
                mostrarInformacion(vista, "Permisos del rol actualizados.");
            } else {
                mostrarInformacion(vista, "No se pudieron asignar los permisos.");
            }
        } catch (Exception e) {
            mostrarError(vista, "Error al asignar permisos", e);
        }
    }

    private void registrarPermiso() {
        try {
            PermisoEntity permiso = new PermisoEntity();
            permiso.setCodigo(vista.getTxtCodigoPermiso().getText().trim());
            permiso.setNombre(vista.getTxtNombrePermiso().getText().trim());
            permiso.setDescripcion(vista.getTxtDescripcionPermiso().getText().trim());

            Integer id = permisoService.insertar(permiso);
            mostrarInformacion(vista, "Permiso registrado con ID " + id);
            cargarDatos();
        } catch (Exception e) {
            mostrarError(vista, "Error al registrar permiso", e);
        }
    }
}
