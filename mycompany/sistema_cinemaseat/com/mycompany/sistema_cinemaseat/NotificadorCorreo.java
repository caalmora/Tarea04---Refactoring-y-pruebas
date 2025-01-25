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

public class NotificadorCorreo implements INotificador {

    @Override
    public void enviarNotificacion(String correoDestino, String mensaje) {
        if (correoDestino.toLowerCase().contains("admin")) {
            ArchivoCorreo.escribirAdministrador(correoDestino, mensaje);
        } else {
            ArchivoCorreo.escribirCliente(correoDestino, mensaje);
        }
    }

    public void notificar(List<Cliente> clientes, String mensaje) {
        ArchivoCorreo.notificarAClientes(clientes, mensaje);
    }
}
