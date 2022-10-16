/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Fernando
 */
public class Proveedor {
    
    private int id;
    private String nombre,telefono, direccion;
    
    public Proveedor(int id, String nombre, String telefono, String direccion){
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
    public Proveedor( String nombre, String telefono, String direccion){
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
    public int getId() {
	return id;
    }

    public String getNombre() {
	return nombre;
    }

    public String getTelefono() {
	return telefono;
    }

    public String getDireccion() {
	return direccion;
    }
    
}
