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

public interface IPersistenciaFunciones {

    public List<Funcion> cargarFunciones();

    public void guardarFunciones(List<Funcion> funciones);
}

