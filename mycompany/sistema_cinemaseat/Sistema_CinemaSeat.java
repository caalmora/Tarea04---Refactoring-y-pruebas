/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import java.util.List;

/**
 *
 * @author User
 */
public class Sistema_CinemaSeat {

    public static void main(String[] args) {
        try {
            // Repositorios de persistencia
            IPersistenciaUsuarios repositorioUsuarios = new RepositorioUsuariosArchivo();
            IPersistenciaFunciones repositorioFunciones = new RepositorioFuncionesArchivo();

            // Notificador y gestor de funciones
            INotificador notificador = new NotificadorCorreo();
            IGestorFunciones gestorFunciones = new GestorFunciones(repositorioFunciones);

            // Cargar usuarios y salas
            List<Usuario> usuarios = repositorioUsuarios.cargarUsuarios();
            RepositorioSalasArchivo.cargarSalas();

            // Iniciar el sistema (menú principal)
            Cine cine = new Cine(repositorioUsuarios, gestorFunciones, notificador, usuarios);
            cine.iniciarSistema();

            // AL FINAL, ANTES DE SALIR, GUARDAMOS:
            repositorioUsuarios.guardarUsuarios(usuarios);
            // (Reescribe usuarios.txt con la lista actual de 'usuarios')

            RepositorioSalasArchivo.guardarSalas();
            // (Reescribe salas.txt con los cambios hechos en la lista estática)

            // Si tienes funciones en memoria:
            repositorioFunciones.guardarFunciones(gestorFunciones.obtenerFunciones());
            // (Reescribe funciones.txt con los cambios)

        } catch (CinemaException e) {
            System.err.println("\nError en el sistema (CinemaException): " + e.getMessage() + "\n");
        } catch (Exception e) {
            System.err.println("\nError general en el sistema: " + e.getMessage() + "\n");
        }
    }
}