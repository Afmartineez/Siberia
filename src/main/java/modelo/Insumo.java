/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Fernando
 */
public class Insumo {
    
    private int id, existencia, clave_proveedor;
    private String descripcion, nombre_proveedor, telefono_proveedor;
    
    public Insumo (String descripcion, int existencia, int clave_proveedor){
        this.descripcion = descripcion;
        this.existencia = existencia;
        this.clave_proveedor = clave_proveedor;
    }
    
    public Insumo (int id, String descripcion, int existencia, int clave_proveedor){
        this.id = id;
        this.descripcion = descripcion;
        this.existencia = existencia;
        this.clave_proveedor = clave_proveedor;
    }
    
    public Insumo (int id, String descripcion, int existencia, String nombre_proveedor, String telefono_proveedor){
        this.id = id;
        this.descripcion = descripcion;
        this.nombre_proveedor = nombre_proveedor;
        this.telefono_proveedor = telefono_proveedor;
    }
    
    public int getId(){
        return id;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    
    public int getExistencia (){
        return existencia;
    }
    
    public int getClaveProveedor (){
        return clave_proveedor;
    }
    
    public String getNombreProveedor (){
        return nombre_proveedor;
    }
    
    public String getTelefonoProveedor (){
        return telefono_proveedor;
    }
}
