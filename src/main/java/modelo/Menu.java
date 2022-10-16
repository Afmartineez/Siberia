/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Fernando
 */
public class Menu {
    
    private int clave, precio;
    private String descripcion;
    
    public Menu(String descripcion, int precio){
        this.descripcion = descripcion;
        this.precio = precio;
    }
    
    public Menu(int clave, String descripcion, int precio){
        this.clave = clave;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    
    public int getClave(){
        return clave;
    } 
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public int getPrecio (){
        return precio;
    }
}
