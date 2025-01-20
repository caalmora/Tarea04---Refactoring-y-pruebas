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
public class GestorFunciones implements IGestorFunciones {

    private IPersistenciaFunciones repositorioFunciones;
    private List<Funcion> funciones;

    public GestorFunciones(IPersistenciaFunciones repositorioFunciones) {
        this.repositorioFunciones = repositorioFunciones;
        this.funciones = repositorioFunciones.cargarFunciones();
    }

    @Override
    public void agregarFuncion(Funcion funcion) {
        funciones.add(funcion);
        repositorioFunciones.guardarFunciones(funciones);
    }

    @Override
    public List<Funcion> obtenerFunciones() {
        return funciones;
    }
}
