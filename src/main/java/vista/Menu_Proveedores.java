/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controlador.ControladorBBDD;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import modelo.Proveedor;

/**
 *
 * @author Fernando
 */
public class Menu_Proveedores extends JFrame{
    
    private JPanel p1;
    private final JTextField nombre, telefono, direccion, valor;
    private final JButton anadir, editar, actualizar, cancelar, eliminar, reporte;
    private final DefaultTableModel modelo;
    private final JTable proveedores;
    private int clave_proveedor;
    
    public Menu_Proveedores(){
        setLayout(null);
	setTitle("Proveedores");
	setSize(1200,700);
	setLocationRelativeTo(null);
	setResizable(false);
        
        Color bg = new Color(248, 249, 249 );
        Font letra = new Font("Espresso Dolce", Font.PLAIN, 24);
        Font letra1 = new Font("Espresso Dolce", Font.PLAIN, 21);
        
        p1 = new JPanel();
	p1.setLayout(null);
	p1.setBounds(0, 0, 1200, 700);
	p1.setBackground(bg);
        
        crearLabel("Módulo Proveedores",485,30,300,40,letra,null);
        crearLabel("Nombre",50,120,80,30,letra1,null);
        crearLabel("Telefono",50,170,80,30,letra1,null);
        crearLabel("Direccion",50,220,80,30,letra1,null);
        crearLabel("Buscar por nombre",550,120,200,30,letra1,null);
        
        nombre = new JTextField();
        nombre.setForeground(Color.BLUE);
	crearText(nombre,180,120,200,30,letra1);
        
        telefono = new JTextField();
        telefono.setForeground(Color.BLUE);
	crearText(telefono,180,170,200,30,letra1);
        
        direccion = new JTextField();
        direccion.setForeground(Color.BLUE);
	crearText(direccion,180,220,200,30,letra1);
        
        valor = new JTextField();
        valor.setForeground(Color.BLUE);
        Document miValor = valor.getDocument();
	miValor.addDocumentListener(new dameProveedores());
        crearText(valor,770,120,250,30,letra1);
        
        anadir = new JButton("Añadir");
        crearBotones(anadir, 50,300,330,30,letra1,true);
        anadir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()){
                    if (validarTelefono(telefono.getText())){
                        //System.out.print("\nEl telefono es correcto!!!");
                        String nom = nombre.getText();
                        String tel = telefono.getText();
                        String dir = direccion.getText();
                        anadirProveedor(new Proveedor(nom,tel,dir));
                    }else{
                        System.out.print("\nNo sea estupido y escriba bien el telefono");
                        JOptionPane.showMessageDialog(p1, "Escriba un numero telefónico válido."
                                + " Numero de 10 d[igitos", "Error", 0);
                    }
                }else{
                    JOptionPane.showMessageDialog(p1, "No deje campos vacios", "Error", 0);
                }
                    
            }
        
        });
        
        editar = new JButton("Editar");
        crearBotones(editar, 50,350,330,30,letra1,true);
        editar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = proveedores.getSelectedRow();
		if (fila!=-1) {
                    int clave = Integer.parseInt((String)proveedores.getValueAt(fila, 0));
                    Proveedor prov = dameProveedor(clave);
                    nombre.setText(prov.getNombre());
                    telefono.setText(prov.getTelefono());
                    direccion.setText (prov.getDireccion());
                    anadir.setEnabled(false);
                    editar.setEnabled(false);
                    actualizar.setEnabled(true);
                    cancelar.setEnabled(true);
                    eliminar.setEnabled(false);
                    clave_proveedor = prov.getId();
		}else {
                    JOptionPane.showMessageDialog(p1, "Por favor seleccione un registro de la fila", "Error", 0);
		}
            }
            
        });
        
        actualizar = new JButton("Actualizar");
        crearBotones(actualizar, 50,400,330,30,letra1,false);
        actualizar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()){
                    if (validarTelefono(telefono.getText())){
                        actualizarProveedor(clave_proveedor);
                    }else{
                        JOptionPane.showMessageDialog(p1, "Escriba un numero telefónico válido."
                                + " Numero de 10 d[igitos", "Error", 0);
                    }
                }else{
                    JOptionPane.showMessageDialog(p1, "No deje campos vacios", "Error", 0);
                }
            }
            
        });
        
        cancelar = new JButton("Cancelar");
        crearBotones(cancelar, 50,450,330,30,letra1,false);
        cancelar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                nombre.setText("");
                telefono.setText("");
                direccion.setText("");
                anadir.setEnabled(true);
                editar.setEnabled(true);
                cancelar.setEnabled(false);
                actualizar.setEnabled(false);
                eliminar.setEnabled(true);
            }
            
        });
        
        eliminar = new JButton("Eliminar");
        crearBotones(eliminar, 50,500,330,30,letra1,true);
        eliminar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = proveedores.getSelectedRow();
		if (fila!=-1) {
                    
                    int clave = Integer.parseInt((String)proveedores.getValueAt(fila, 0));
                    eliminarProveedor(clave);
                    dameProveedores();
		}else {
                    JOptionPane.showMessageDialog(p1, "Por favor seleccione un registro de la fila", "Error", 0);
		}
            }
            
        });
        
        reporte = new JButton("Generar reporte de proveedores");
        crearBotones(reporte, 50,550,330,30,letra1,true);
        reporte.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dameReporteDeProveedores();
            }
        
        });
        
        String columnas[] = new String [] {
				"Clave","Nombre","Telefono","Dirección"
		};
	modelo = new DefaultTableModel();
	modelo.setColumnIdentifiers(columnas);
        
        proveedores = new JTable(modelo);
	proveedores.setBounds(450, 170, 670, 410);
	p1.add(proveedores);
		
	JScrollPane barras = new JScrollPane(proveedores,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
		,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);	
	barras.setBounds(450, 170, 670, 410);
	add(barras);
        
        
        dameProveedores();
        add(p1);
        setVisible(true);
        
        
        
    }
    
    private class dameProveedores implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
           dameProveedorPorNombre(valor.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
           if (valor.getText().equals("")){
               dameProveedores();
           }else{
               dameProveedorPorNombre (valor.getText());
           }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            
        }
    
    }
    
    
    private void crearLabel(String texto, int x, int y, int x1, int y1, Font ft, Icon ic) {
	JLabel et = new JLabel(texto);
	et.setBounds(x, y, x1, y1);
	et.setFont(ft);
	et.setIcon(ic);
	p1.add(et);
    }
	
    private void crearBotones(JButton boton, int x, int y, int x1, int y1, Font ft, boolean activo) {
	boton.setBounds(x, y, x1, y1);
	boton.setFont(ft);
	boton.setEnabled(activo);
	p1.add(boton);
    }
	
    private void crearText(JTextField cuadro, int x, int y, int x1, int y1, Font ft) {
	cuadro.setBounds(x, y, x1, y1);
	cuadro.setFont(ft);
	p1.add(cuadro);
    }
    
    private boolean validarCampos (){
        
        if (nombre.getText().equals("")){
            return false;
        }else if (telefono.getText().equals("")){
            return false;
        }else if (direccion.getText().equals("")){
            return false;
        }else return true;
        
    }
    
    private void limpiarCampos (){
        nombre.setText("");
        telefono.setText("");
        direccion.setText("");
    }
    
    private boolean validarTelefono (String tel){
        Pattern patron = Pattern.compile("^[\\d]{10}$");
        Matcher m = patron.matcher(tel);
        return m.find();
    }
    
    private void limpiarTabla (){
        int filas = proveedores.getRowCount();
	for (int i = filas-1; i>=0; i--) {
            modelo.removeRow(i);
	}
    }
    
    private void llenarTabla (ResultSet rs){
        limpiarTabla();
		try {
			while(rs.next()) {
				String datos [] = new String [] {
                                    String.valueOf(rs.getInt("clave_proveedor")), rs.getString("nombre"),
                                    rs.getString("telefono"), rs.getString("direccion")
				};
				modelo.addRow(datos);		
			}
			
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
    }
    
    private Proveedor dameProveedor(int clave){
        Proveedor prov = null;
        try {
            String query = new String ("select * from proveedores where clave_proveedor=?");
            ControladorBBDD dameProv = new ControladorBBDD();
            dameProv.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            dameProv.darParametroInt(1, clave);
            ResultSet rs = dameProv.EjecutarConsultaPreparada();
            rs.first();
            prov = new Proveedor (rs.getInt("clave_proveedor"), rs.getString("nombre"), 
                rs.getString("telefono"), rs.getString("direccion"));
            dameProv.cerrarOperaciónConsultaPreparada();
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return prov;
    }
    
    private void dameProveedores (){
        try {
            String query = new String ("select * from proveedores");
            ControladorBBDD dameProv = new ControladorBBDD();
            dameProv.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dameProv.EjecutarConsultaNormal(query);
            llenarTabla(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void anadirProveedor(Proveedor prov){
        try {
            String query = new String("insert into proveedores values (null,?,?,?)");
            ControladorBBDD anadir = new ControladorBBDD();
            anadir.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            anadir.darParametroString(1, prov.getNombre());
            anadir.darParametroString(2, prov.getTelefono());
            anadir.darParametroString(3, prov.getDireccion());
            anadir.ExecuteCreateUpdateOrDelete();
            limpiarCampos();
            JOptionPane.showMessageDialog(p1, "Proveedor dado de alta correctamente", "Nuevo proveedor", 1);
            dameProveedores();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void actualizarProveedor (int clave){
        try {
            String query = new String ("update proveedores set nombre=?, telefono=?, direccion=? where clave_proveedor=?");
            ControladorBBDD actualizarProv = new ControladorBBDD();
            actualizarProv.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            actualizarProv.darParametroString(1, nombre.getText());
            actualizarProv.darParametroString(2, telefono.getText());
            actualizarProv.darParametroString(3, direccion.getText());
            actualizarProv.darParametroInt(4, clave);
            actualizarProv.ExecuteCreateUpdateOrDelete();
            anadir.setEnabled(true);
            editar.setEnabled(true);
            actualizar.setEnabled(false);
            cancelar.setEnabled(false);
            eliminar.setEnabled(true);
            limpiarCampos();
            JOptionPane.showMessageDialog(p1, "Información actualizada exitosamente", "Operación exitosa", 1);
            dameProveedores();
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
    }
    
    private void eliminarProveedor(int clave){
        try {
            String query = new String ("delete from proveedores where clave_proveedor=?");
            ControladorBBDD eliminarProv = new ControladorBBDD();
            eliminarProv.prepararConsulta(query, ResultSet.CONCUR_UPDATABLE, ResultSet.CONCUR_UPDATABLE);
            eliminarProv.darParametroInt(1, clave);
            eliminarProv.ExecuteCreateUpdateOrDelete();
            JOptionPane.showMessageDialog(p1, "Proveedor dado de baja correctamente", "Operación exitosa", 1);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void dameProveedorPorNombre(String nombre){
        try {
            String query = new String("SELECT * FROM proveedores WHERE nombre LIKE '%" + nombre
					+ "%' ");
            ControladorBBDD dppn = new ControladorBBDD();
            dppn.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dppn.EjecutarConsultaNormal(query);
            llenarTabla(rs);
            dppn.cerrarConsultaNormal();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Proveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void dameReporteDeProveedores(){
        try {
            String query = new String("select * from proveedores order by nombre");
            ControladorBBDD drdi = new ControladorBBDD();
            drdi.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = drdi.EjecutarConsultaNormal(query);
            generarReporte(rs);
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void generarReporte(ResultSet rs){
        com.itextpdf.text.Document pdf = new com.itextpdf.text.Document();
        String ruta=System.getProperty("user.home");
        try {
            PdfWriter.getInstance(pdf, new FileOutputStream(ruta + "/Desktop/reporte_proveedores.pdf"));
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.setFont(FontFactory.getFont("Arial", 18, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
            parrafo.add("Reporte de proveedores - Taqueria Siberia" + "\n\n\n");
            pdf.open();
            
            PdfPTable detalle = new PdfPTable(4);
            detalle.addCell("Clave:");
            detalle.addCell("Nombre: ");
            detalle.addCell("Telefono");
            detalle.addCell("Dirección:");
            
            while (rs.next()){
                detalle.addCell(String.valueOf(rs.getInt("clave_proveedor")));
                detalle.addCell(rs.getString("nombre"));
                detalle.addCell(rs.getString("telefono"));
                detalle.addCell(rs.getString("direccion"));
            }
            
            pdf.add(parrafo);
            pdf.add(detalle);
            pdf.close();
            
            JOptionPane.showMessageDialog(p1, "Reporte generado correctamente. Revise el archivo en el escritorio", "Operación exitosa", 1);
            
            //abrirReporte("reporte_proveedores.pdf");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
