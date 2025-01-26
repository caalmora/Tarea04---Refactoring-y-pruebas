/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Sistema_CinemaSeat {

    public static void main(String[] args) {
        try {
            IPersistenciaUsuarios repositorioUsuarios = new RepositorioUsuariosArchivo();
            IPersistenciaFunciones repositorioFunciones = new RepositorioFuncionesArchivo();
            INotificador notificador = new NotificadorCorreo();
            IGestorFunciones gestorFunciones = new GestorFunciones(repositorioFunciones);
            List<Usuario> usuarios = repositorioUsuarios.cargarUsuarios();
            RepositorioSalasArchivo.cargarSalas();
            Scanner scanner = new Scanner(System.in);
            ContextoAccion contexto = new ContextoAccion(scanner, gestorFunciones, usuarios, repositorioUsuarios);
            MenuPrincipal menu = new MenuPrincipal(scanner, repositorioUsuarios, gestorFunciones, notificador, usuarios);
            Cine cine = new Cine(menu);
            cine.iniciarSistema();
            repositorioUsuarios.guardarUsuarios(usuarios);
            RepositorioSalasArchivo.guardarSalas();
            repositorioFunciones.guardarFunciones(gestorFunciones.obtenerFunciones());
        } catch (CinemaException e) {
            System.err.println("Error en el sistema (CinemaException): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general en el sistema: " + e.getMessage());
        }
    }
}
