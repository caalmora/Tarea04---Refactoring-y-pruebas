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

public interface IPersistenciaUsuarios {

    public List<Usuario> cargarUsuarios();

    public void guardarUsuarios(List<Usuario> usuarios);
}

