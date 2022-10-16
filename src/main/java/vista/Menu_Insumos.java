/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import modelo.Insumo;

/**
 *
 * @author Fernando
 */
public class Menu_Insumos extends JFrame{
    
    private JPanel p1;
    private final JTextField descripcion, existencia, valor;
    private JComboBox proveedores;
    private final JButton anadir, editar, actualizar, cancelar, eliminar, reporte;
    private final DefaultTableModel modelo;
    private final JTable insumos;
    private int clave_insumo;
    
    public Menu_Insumos(){
        setLayout(null);
	setTitle("Insumos");
	setSize(1200,700);
	setLocationRelativeTo(null);
	setResizable(false);
        
        Color bg = new Color(252, 243, 207);
        Font letra = new Font("Espresso Dolce", Font.PLAIN, 24);
        Font letra1 = new Font("Espresso Dolce", Font.PLAIN, 21);
        
        p1 = new JPanel();
	p1.setLayout(null);
	p1.setBounds(0, 0, 1200, 700);
	p1.setBackground(bg);
        
        crearLabel("Módulo Insumos",485,30,300,40,letra,null);
        crearLabel("Descripcion",50,120,120,30,letra1,null);
        crearLabel("Existencia",50,170,120,30,letra1,null);
        crearLabel("Proveedor",50,220,120,30,letra1,null);
        crearLabel("Buscar por descripcion",550,120,200,30,letra1,null);
        
        descripcion = new JTextField();
        descripcion.setForeground(Color.BLACK);
	crearText(descripcion,180,120,200,30,letra1);
        
        existencia = new JTextField();
        existencia.setForeground(Color.BLACK);
	crearText(existencia,180,170,200,30,letra1);
        
        proveedores = new JComboBox();
        crearCombo(proveedores,180,220,200,30,letra1,Color.BLACK);
        
        valor = new JTextField();
        valor.setForeground(Color.BLUE);
        Document miValor = valor.getDocument();
	miValor.addDocumentListener(new dameInsumos());
        crearText(valor,790,120,230,30,letra1);
        
        anadir = new JButton("Añadir");
        crearBotones(anadir, 50,300,330,30,letra1,true);
        anadir.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) {
               if (validarCampos()){
                   if (validarExistencia(existencia.getText())){
                        String desc = descripcion.getText();
                        int exis = Integer.valueOf(existencia.getText());
                        int clave_prov = dameClaveProveedor(String.valueOf(proveedores.getSelectedItem()));
                        anadirInsumo(new Insumo(desc, exis, clave_prov));
                   }else{
                       JOptionPane.showMessageDialog(p1, "Escriba una cantidad numerica valida en el "
                               + "campo Existencia" , "Error", 0);
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
                int fila = insumos.getSelectedRow();
		if (fila!=-1) {
                    int clave = Integer.parseInt((String)insumos.getValueAt(fila, 0));
                    Insumo ins = dameInsumo(clave);
                    descripcion.setText(ins.getDescripcion());
                    //existencia.setText(String.valueOf(ins.getExistencia()));
                    proveedores.setSelectedItem(ins.getNombreProveedor());
                    anadir.setEnabled(false);
                    editar.setEnabled(false);
                    actualizar.setEnabled(true);
                    cancelar.setEnabled(true);
                    eliminar.setEnabled(false);
                    clave_insumo = ins.getId();
		}else {
                    JOptionPane.showMessageDialog(p1, "Por favor seleccione un registro de la fila", "Error", 0);
		}
            }
            
        });
        
        actualizar = new JButton("Actualizar");
        crearBotones(actualizar, 50,400,330,30,letra1,true);
        actualizar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()){
                    if (validarExistencia(existencia.getText())){
                        actualizarInsumo(clave_insumo);
                    }else{
                        JOptionPane.showMessageDialog(p1, "Escriba una cantidad numerica valida en el "
                               + "campo Existencia" , "Error", 0);
                    }
                }else{
                    JOptionPane.showMessageDialog(p1, "No deje campos vacios", "Error", 0);
                }
            }
            
        });
        
        cancelar = new JButton("Cancelar");
        crearBotones(cancelar, 50,450,330,30,letra1,true);
        cancelar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                descripcion.setText("");
                existencia.setText("");
                proveedores.setSelectedIndex(0);
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
                
                int fila = insumos.getSelectedRow();
		if (fila!=-1) {
                    
                    int opcion = JOptionPane.showConfirmDialog(p1, "¿Está seguro que desea borrar el elemento"
			+ " seleccionado?", "Eliminar insumo", 0, 3);
                    if (opcion==0) {
			int clave = Integer.parseInt((String)insumos.getValueAt(fila, 0));
                        eliminarInsumo(clave);
                    }
		}else {
                    JOptionPane.showMessageDialog(p1, "Por favor seleccione un registro de la tabla", "Error", 0);
		}
            }
        });
        
        
        reporte = new JButton("Generar reporte de insumos");
        crearBotones(reporte, 50,550,330,30,letra1,true);
        reporte.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dameReporteDeInsumos();
            }
        
        });
        
        String columnas[] = new String [] {
				"Clave","Descripcion","Existencia","Proveedor","Telefono"
		};
	modelo = new DefaultTableModel();
	modelo.setColumnIdentifiers(columnas);
        
        insumos = new JTable(modelo);
	insumos.setBounds(450, 170, 670, 410);
	p1.add(insumos);
		
	JScrollPane barras = new JScrollPane(insumos,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
		,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);	
	barras.setBounds(450, 170, 670, 410);
	add(barras);
        
        cargarProveedores();
        
        dameInsumos();
        
        actualizar.setEnabled(false);
        cancelar.setEnabled(false);
        
        add(p1);
        setVisible(true);
        
    }
    
    private class dameInsumos implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            dameInsumoPorDescripcion(valor.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (valor.getText().equals("")){
               dameInsumos();
           }else{
               dameInsumoPorDescripcion(valor.getText());
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
    
    private void crearCombo(JComboBox combo, int x, int y, int x1, int y1, java.awt.Font ft, Color fg ) {
	combo.setBounds(x, y, x1, y1);
	combo.setFont(ft);
	combo.setForeground(fg);
	p1.add(combo);
    }
    
    private void limpiarCampos (){
        descripcion.setText("");
        existencia.setText("");
        proveedores.setSelectedIndex(0);
    }
    
    private boolean validarCampos (){
        
        if (descripcion.getText().equals("")){
            return false;
        }else if (existencia.getText().equals("")){
            return false;
        }else return true;
        
    }
    
    private boolean validarExistencia(String numero){
       try {
		Integer.valueOf(numero);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
    
    private void limpiarTabla (){
        int filas = insumos.getRowCount();
	for (int i = filas-1; i>=0; i--) {
            modelo.removeRow(i);
	}
    }
    
    private void llenarTabla (ResultSet rs){
        limpiarTabla();
		try {
			while(rs.next()) {
				String datos [] = new String [] {
                                    String.valueOf(rs.getInt("clave_insumo")), rs.getString("descripcion"),
                                    String.valueOf(rs.getInt("existencia")),rs.getString("nombre"), 
                                    rs.getString("telefono")
				};
				modelo.addRow(datos);		
			}
			
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
    }
    
    private void dameInsumos (){
        try {
            String query = new String ("select clave_insumo, descripcion, existencia, nombre, telefono"
                    + " from insumos inner join proveedores on insumos.clave_proveedor = "
                    + " proveedores.clave_proveedor");
            ControladorBBDD dameInsumos = new ControladorBBDD();
            dameInsumos.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dameInsumos.EjecutarConsultaNormal(query);
            llenarTabla(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int dameClaveProveedor (String nombre){
        int clave_proveedor =0;
        try {
            String query = new String("select clave_proveedor from proveedores where nombre=?");
            ControladorBBDD dameClaveProv = new ControladorBBDD();
            dameClaveProv.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            dameClaveProv.darParametroString(1, nombre);
            ResultSet rs = dameClaveProv.EjecutarConsultaPreparada();
            rs.first();
            clave_proveedor = rs.getInt("clave_proveedor");
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clave_proveedor;
    }
    
    private void cargarProveedores(){
        try {
            String query = new String ("select nombre from proveedores order by nombre");
            ControladorBBDD dameProv = new ControladorBBDD();
            dameProv.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dameProv.EjecutarConsultaNormal(query);
            while (rs.next()){
                proveedores.addItem(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void anadirInsumo (Insumo insumo){
        try {
            String query = new String("insert into insumos values (null,?,?,?)");
            ControladorBBDD anadir = new ControladorBBDD();
            anadir.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            anadir.darParametroString(1, insumo.getDescripcion());
            anadir.darParametroInt(2, insumo.getExistencia());
            anadir.darParametroInt(3, insumo.getClaveProveedor());
            anadir.ExecuteCreateUpdateOrDelete();
            limpiarCampos();
            dameInsumos();
            JOptionPane.showMessageDialog(p1, "Insumo dado de alta corretamente", "Operación exitosa", 1);
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private Insumo dameInsumo (int clave){
        Insumo insumo = null;
        try {
            String query = new String ("select clave_insumo, descripcion, existencia, insumos.clave_proveedor,nombre, telefono"
                    + " from insumos inner join proveedores on insumos.clave_proveedor = "
                    + " proveedores.clave_proveedor where clave_insumo=?");
            ControladorBBDD dameInsumo = new ControladorBBDD();
            dameInsumo.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            dameInsumo.darParametroInt(1, clave);
            ResultSet rs = dameInsumo.EjecutarConsultaPreparada();
            rs.first();
            insumo = new Insumo(rs.getInt("clave_insumo"),rs.getString("descripcion"),rs.getInt("existencia")
            ,rs.getString("nombre"), rs.getString("telefono"));
            existencia.setText(String.valueOf(rs.getInt("existencia")));
            dameInsumo.cerrarOperaciónConsultaPreparada();
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return insumo;
    };
    
    private void actualizarInsumo(int clave){
        try {
            String query = new String ("update insumos set descripcion=?, existencia=?, clave_proveedor=? where clave_insumo=?");
            ControladorBBDD actualizarInsumo = new ControladorBBDD();
            actualizarInsumo.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            actualizarInsumo.darParametroString(1, descripcion.getText());
            actualizarInsumo.darParametroInt(2, Integer.parseInt(existencia.getText()));
            actualizarInsumo.darParametroInt(3, dameClaveProveedor(String.valueOf(proveedores.getSelectedItem())));
            actualizarInsumo.darParametroInt(4, clave);
            actualizarInsumo.ExecuteCreateUpdateOrDelete();
            anadir.setEnabled(true);
            editar.setEnabled(true);
            actualizar.setEnabled(false);
            cancelar.setEnabled(false);
            eliminar.setEnabled(true);
            limpiarCampos();
            JOptionPane.showMessageDialog(p1, "Información actualizada exitosamente", "Operación exitosa", 1);
            dameInsumos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private void eliminarInsumo(int clave){
        try {
            String query = new String ("delete from insumos where clave_insumo=?");
            ControladorBBDD eliminarInsumo = new ControladorBBDD();
            eliminarInsumo.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            eliminarInsumo.darParametroInt(1, clave);
            eliminarInsumo.ExecuteCreateUpdateOrDelete();
            JOptionPane.showMessageDialog(p1, "Insumo dado de baja correctamente", "Operación exitosa", 1);
            dameInsumos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void dameInsumoPorDescripcion(String descripcion){
        try {
            String query = new String("SELECT * FROM insumos inner join proveedores on insumos.clave_proveedor "
                    + "= proveedores.clave_proveedor WHERE descripcion LIKE '%" + descripcion
					+ "%' ");
            ControladorBBDD dipd = new ControladorBBDD();
            dipd.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dipd.EjecutarConsultaNormal(query);
            llenarTabla(rs);
            dipd.cerrarConsultaNormal();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void dameReporteDeInsumos (){
        try {
            String query = new String("SELECT * FROM insumos inner join proveedores on insumos.clave_proveedor "
                    + "= proveedores.clave_proveedor order by existencia");
            ControladorBBDD drdi = new ControladorBBDD();
            drdi.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = drdi.EjecutarConsultaNormal(query);
            generarReporte(rs);
            drdi.cerrarConsultaNormal();
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
            PdfWriter.getInstance(pdf, new FileOutputStream(ruta + "/Desktop/reporte_insumos.pdf"));
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.setFont(FontFactory.getFont("Arial", 18, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
            parrafo.add("Reporte de insumos - Taqueria Siberia" + "\n\n\n");
            pdf.open();
            
            PdfPTable detalle = new PdfPTable(5);
            detalle.addCell("Clave:");
            detalle.addCell("Descripción: ");
            detalle.addCell("Existencia");
            detalle.addCell("Proveedor:");
            detalle.addCell("Telefono");
            
            while (rs.next()){
                detalle.addCell(String.valueOf(rs.getInt("clave_insumo")));
                detalle.addCell(rs.getString("descripcion"));
                detalle.addCell(String.valueOf(rs.getInt("existencia")));
                detalle.addCell(rs.getString("nombre"));
                detalle.addCell(rs.getString("telefono"));
            }
            
            pdf.add(parrafo);
            pdf.add(detalle);
            pdf.close();
            
            JOptionPane.showMessageDialog(p1, "Reporte generado correctamente. Revise el archivo en el escritorio", "Operación exitosa", 1);
            
            //abrirReporte("reporte_proveedores.pdf");
            
        } catch (Exception ex) {
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}


