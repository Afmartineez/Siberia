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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
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
import modelo.Menu;

/**
 *
 * @author Fernando
 */
public class Menu_Menus extends JFrame{
    
    private JPanel p1;
    private final JTextField descripcion, precio, valor;
    private final JButton anadir, editar, actualizar, cancelar, eliminar, reporte;
    private final DefaultTableModel modelo;
    private final JTable menus;
    private int clave_menu;
    
    public Menu_Menus(){
        
        setLayout(null);
	setTitle("Menus");
	setSize(1200,700);
	setLocationRelativeTo(null);
	setResizable(false);
        
        Color bg = new Color(244, 236, 247 );
        Font letra = new Font("Espresso Dolce", Font.PLAIN, 24);
        Font letra1 = new Font("Espresso Dolce", Font.PLAIN, 21);
        
        p1 = new JPanel();
	p1.setLayout(null);
	p1.setBounds(0, 0, 1200, 700);
	p1.setBackground(bg);
        
        crearLabel("Módulo Menús",485,30,300,40,letra,null);
        crearLabel("Descripcion",50,120,120,30,letra1,null);
        crearLabel("Precio",50,220,120,30,letra1,null);
        crearLabel("Buscar por descripcion",550,120,200,30,letra1,null);
        
        descripcion = new JTextField();
        descripcion.setForeground(Color.RED);
	crearText(descripcion,180,120,200,30,letra1);
        
        precio = new JTextField();
        precio.setForeground(Color.RED);
	crearText(precio,180,220,200,30,letra1);
        
        valor = new JTextField();
        valor.setForeground(Color.BLUE);
        Document miValor = valor.getDocument();
	miValor.addDocumentListener(new dameMenus());
        crearText(valor,790,120,230,30,letra1);
        
        anadir = new JButton("Añadir");
        crearBotones(anadir, 50,300,330,30,letra1,true);
        anadir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()){
                    if (validarPrecio(precio.getText())){
                        String desc = descripcion.getText();
                        int pre = Integer.parseInt(precio.getText());
                        anadirMenu(new Menu(desc,pre));
                    }else{
                         JOptionPane.showMessageDialog(p1, "Escriba una cantidad numerica valida en el "
                               + "campo Precio" , "Error", 0);
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
                int fila = menus.getSelectedRow();
		if (fila!=-1) {
                    int clave = Integer.parseInt((String)menus.getValueAt(fila, 0));
                    Menu men = dameMenu(clave);
                    descripcion.setText(men.getDescripcion());
                    precio.setText(String.valueOf(men.getPrecio()));
                    anadir.setEnabled(false);
                    editar.setEnabled(false);
                    actualizar.setEnabled(true);
                    cancelar.setEnabled(true);
                    eliminar.setEnabled(false);
                    System.out.print(men.getClave());
                    clave_menu = men.getClave();
		}else {
                    JOptionPane.showMessageDialog(p1, "Por favor seleccione un registro de la fila", "Error", 0);
		}
            }
        
        });
        
        actualizar = new JButton("Actualizar");
        crearBotones(actualizar, 50,400,330,30,letra1,true);
        actualizar.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()){
                    if (validarPrecio(precio.getText())){
                        Menu menu = new Menu(clave_menu, descripcion.getText(), Integer.parseInt(precio.getText()));
                        actualizarMenu(menu);
                    }else{
                        JOptionPane.showMessageDialog(p1, "Escriba una cantidad numerica valida en el "
                               + "campo Precio" , "Error", 0);
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
                precio.setText("");
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
                int fila = menus.getSelectedRow();
		if (fila!=-1) {
                    
                    int opcion = JOptionPane.showConfirmDialog(p1, "¿Está seguro que desea borrar el elemento"
			+ " seleccionado?", "Eliminar insumo", 0, 3);
                    if (opcion==0) {
			int clave = Integer.parseInt((String)menus.getValueAt(fila, 0));
                        eliminarMenu(clave);
                    }
		}else {
                    JOptionPane.showMessageDialog(p1, "Por favor seleccione un registro de la tabla", "Error", 0);
		}
            }
        
        });
        
        reporte = new JButton("Generar menu completo");
        crearBotones(reporte, 50,550,330,30,letra1,true);
        reporte.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dameReporteDeMenu();
            }
            
        });
        
        String columnas[] = new String [] {
				"Clave","Descripcion","Precio"
		};
	modelo = new DefaultTableModel();
	modelo.setColumnIdentifiers(columnas);
        
        menus = new JTable(modelo);
	menus.setBounds(450, 170, 670, 410);
	p1.add(menus);
		
	JScrollPane barras = new JScrollPane(menus,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
		,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);	
	barras.setBounds(450, 170, 670, 410);
	add(barras);
        
        actualizar.setEnabled(false);
        cancelar.setEnabled(false);
        
        dameMenus();
        
        add(p1);
        setVisible(true);
    
    }
    
    private class dameMenus implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            dameMenuPorDescripcion(valor.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (valor.getText().equals("")){
               dameMenus();
           }else{
               dameMenuPorDescripcion(valor.getText());
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
    
    private boolean validarCampos(){
        if (descripcion.getText().equals("")){
            return false;
        }else if (precio.getText().equals("")){
            return false;
        }else return true;
    }
    
    private boolean validarPrecio (String pre){
    try {
		Integer.valueOf(pre);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
    
        private void limpiarTabla (){
        int filas = menus.getRowCount();
	for (int i = filas-1; i>=0; i--) {
            modelo.removeRow(i);
	}
    }
    
    private void llenarTabla (ResultSet rs){
        limpiarTabla();
		try {
			while(rs.next()) {
				String datos [] = new String [] {
                                    String.valueOf(rs.getInt("clave_menu")), rs.getString("descripcion"),
                                    String.valueOf(rs.getInt("precio"))
				};
				modelo.addRow(datos);		
			}
			
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
    }
    
    private void dameMenus(){
        try {
            String query = new String ("select * from menus");
            ControladorBBDD dameMenus = new ControladorBBDD();
            dameMenus.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dameMenus.EjecutarConsultaNormal(query);
            llenarTabla(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void anadirMenu (Menu menu){
        try {
            String query = new String("insert into menus values (null,?,?)");
            ControladorBBDD anadirMenu = new ControladorBBDD();
            anadirMenu.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            anadirMenu.darParametroString(1, menu.getDescripcion());
            anadirMenu.darParametroInt(2, menu.getPrecio());
            anadirMenu.ExecuteCreateUpdateOrDelete();
            descripcion.setText("");
            precio.setText("");
            dameMenus();
            JOptionPane.showMessageDialog(p1, "Menu dado de alta corretamente", "Operación exitosa", 1);   
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Menu dameMenu (int clave){
        Menu men = null;
        try {
            String query = new String("select * from menus where clave_menu=?");
            ControladorBBDD dameMenu = new ControladorBBDD();
            dameMenu.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            dameMenu.darParametroInt(1, clave);
            ResultSet rs = dameMenu.EjecutarConsultaPreparada();
            rs.first();
            clave_menu=rs.getInt("clave_menu");
            men = new Menu(rs.getInt("clave_menu"), rs.getString("descripcion"),
                    rs.getInt("precio"));
            dameMenu.cerrarOperaciónConsultaPreparada();
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return men;
    }
    
    private void actualizarMenu (Menu menu){
        try {
            String query = new String ("update menus set descripcion=?, precio=? where clave_menu=?");
            ControladorBBDD actualizarMenu = new ControladorBBDD ();
            actualizarMenu.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            actualizarMenu.darParametroString(1, menu.getDescripcion());
            actualizarMenu.darParametroInt(2, menu.getPrecio());
            actualizarMenu.darParametroInt(3, menu.getClave());
            actualizarMenu.ExecuteCreateUpdateOrDelete();
            anadir.setEnabled(true);
            editar.setEnabled(true);
            actualizar.setEnabled(false);
            cancelar.setEnabled(false);
            eliminar.setEnabled(true);
            descripcion.setText("");
            precio.setText("");
            JOptionPane.showMessageDialog(p1, "Información actualizada exitosamente", "Operación exitosa", 1);
            dameMenus();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void eliminarMenu (int clave){
        try {
            String query = new String("delete from menus where clave_menu=?");
            ControladorBBDD eliminarMenu = new ControladorBBDD();
            eliminarMenu.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            eliminarMenu.darParametroInt(1, clave);
            eliminarMenu.ExecuteCreateUpdateOrDelete();
            JOptionPane.showMessageDialog(p1, "Menu dado de baja correctamente", "Operación exitosa", 1);
            dameMenus();
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void dameMenuPorDescripcion (String descripcion){
        try {
            String query = new String ("select * from menus where descripcion like '%"  + descripcion
					+ "%' ");
            ControladorBBDD dmpd = new ControladorBBDD();
            dmpd.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dmpd.EjecutarConsultaNormal(query);
            llenarTabla(rs);
            dmpd.cerrarConsultaNormal();
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Menus.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private void dameReporteDeMenu(){
        try {
            String query = new String("select * from menus order by descripcion");
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
            PdfWriter.getInstance(pdf, new FileOutputStream(ruta + "/Desktop/reporte_menu.pdf"));
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.setFont(FontFactory.getFont("Arial", 18, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
            parrafo.add("Menú - Taqueria Siberia" + "\n\n\n");
            pdf.open();
            
            PdfPTable detalle = new PdfPTable(3);
            detalle.addCell("Clave:");
            detalle.addCell("Descripcion ");
            detalle.addCell("Precio");
            
            while (rs.next()){
                detalle.addCell(String.valueOf(rs.getInt("clave_menu")));
                detalle.addCell(rs.getString("descripcion"));
                detalle.addCell(String.valueOf(rs.getInt("precio")));
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
