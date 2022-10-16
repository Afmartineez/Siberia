/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package vista;

import javax.swing.JFrame;


/**
 *
 * @author Fernando
 */
public class Siberia {

    public static void main(String[] args) {
        System.out.println("Ya esta en funcionamiento el sistema");
        Menu_Inicio_Sesion iniciar = new Menu_Inicio_Sesion();
        iniciar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Menu_Inicial menu = new Menu_Inicial(2);
        //menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Menu_Usuarios registro = new Menu_Usuarios();
        //registro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
    }
}
