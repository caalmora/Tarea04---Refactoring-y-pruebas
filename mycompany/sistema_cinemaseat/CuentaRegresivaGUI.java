/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author User
 */

public class CuentaRegresivaGUI {

    private JFrame frame;
    private JLabel label;

    public CuentaRegresivaGUI() {
        frame = new JFrame("Cuenta Regresiva");
        label = new JLabel("Tiempo restante: 15 segundos", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        frame.add(label);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actualizarTiempo(int segundos) {
        label.setText("Tiempo restante: " + segundos + " segundos");
    }

    public void cerrarVentana() {
        frame.dispose();
    }
}
