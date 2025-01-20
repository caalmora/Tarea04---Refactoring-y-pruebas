/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

/**
 *
 * @author User
 */

 public class Cine {
    
    private MenuPrincipal menu;

    public Cine(MenuPrincipal menu) {
        this.menu = menu;
    }

    public void iniciarSistema() throws CinemaException {
        menu.mostrarMenu();
    }
}
