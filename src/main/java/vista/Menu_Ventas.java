/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.ControladorBBDD;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fernando
 */
public class Menu_Ventas extends JFrame{
    
    private JPanel p1;
    private final JTextField cantidad;
    private JComboBox menus;
    private final JButton anadir, cancelar_venta, cancelar_item,terminar, dame_total;
    private final DefaultTableModel modelo;
    private final JTable ventas;
    private int precio, total=0;
    private JLabel imp_total;
    
    public Menu_Ventas(){
        setLayout(null);
	setTitle("Ventas");
	setSize(1200,650);
	setLocationRelativeTo(null);
	setResizable(false);
        
        Color bg = new Color(235, 236, 249);
        Font letra = new Font("Espresso Dolce", Font.PLAIN, 24);
        Font letra1 = new Font("Espresso Dolce", Font.PLAIN, 21);
        
        p1 = new JPanel();
	p1.setLayout(null);
	p1.setBounds(0, 0, 1200, 650);
	p1.setBackground(bg);
        
        crearLabel("Módulo Ventas",485,30,300,40,letra,null);
        crearLabel("Menu",50,120,120,30,letra1,null);
        crearLabel("Cantidad",50,170,120,30,letra1,null);
        crearLabel("Total",50,220,120,30,letra1,null);
        
        menus = new JComboBox();
        crearCombo(menus,180,120,200,30,letra1,Color.BLACK);
        menus.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                precio = damePrecio(String.valueOf(menus.getSelectedItem()));
                System.out.print(damePrecio(String.valueOf(menus.getSelectedItem())));
            }
        
        });
        
        cantidad = new JTextField();
        cantidad.setForeground(Color.BLACK);
	crearText(cantidad,180,170,200,30,letra1);
        
        imp_total = new JLabel();
        crearLabel2(imp_total,180,220,200,30,letra1,null);
        
        anadir = new JButton("Añadir al carrito");
        crearBotones(anadir, 50,300,330,30,letra1,true);
        anadir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCantidad(cantidad.getText())){
                    int cant = Integer.parseInt(cantidad.getText());
                    int subtotal = cant * precio;
                    total = total + subtotal;
                    imp_total.setText("$" + total);
                    String datos [] = new String [] {
                                    String.valueOf(menus.getSelectedItem()),
                                    cantidad.getText(),
                                    String.valueOf(subtotal)
				};
                    modelo.addRow(datos);		
                    cancelar_venta.setEnabled(true);
                    cancelar_item.setEnabled(true);
                    terminar.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(p1, "Por favor escriba un valor numerico valido en el campo "
                            + "Cantidad", "Error", 0);
                }
            }
        
        });
        
        cancelar_venta = new JButton("Cancelar venta");
        crearBotones(cancelar_venta, 50,350,330,30,letra1,true);
        cancelar_venta.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(p1, "¿Está seguro que desea cancelar la venta actual?"
			, "Cancelar venta", 0, 3);
                    if (opcion==0) {
			total = 0;
                        cantidad.setText("");
                        menus.setSelectedIndex(0);
                        cancelar_venta.setEnabled(false);
                        terminar.setEnabled(false);
                        cancelar_item.setEnabled(false);
                        limpiarTabla();
                        imp_total.setText("$0");
                    }
            }
        
        });
        
        
        terminar = new JButton("Terminar venta");
        crearBotones(terminar, 50,400,330,30,letra1,true);
        terminar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(p1, "¿Está seguro que desea terminar la venta actual?"
			, "Terminar venta", 0, 3);
                    if (opcion==0) {
                        guardarVenta(total);
			total = 0;
                        cantidad.setText("");
                        menus.setSelectedIndex(0);
                        cancelar_venta.setEnabled(false);
                        terminar.setEnabled(false);
                        cancelar_item.setEnabled(false);
                        limpiarTabla();
                        imp_total.setText("$0");
                    }
            }
        
        });
        
        dame_total = new JButton("Total del dia de hoy");
        crearBotones(dame_total, 50,450,330,30,letra1,true);
        dame_total.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(p1, "El total acumulado del dia de hoy es de " + dameTotalDeHoy(), "Total de hoy", 1);
            }
            
        });
        
        cancelar_item = new JButton("Cancelar item");
        crearBotones(cancelar_item, 50,500,330,30,letra1,true);
        cancelar_item.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = ventas.getSelectedRow();
		if (fila!=-1) {
                    
                    int opcion = JOptionPane.showConfirmDialog(p1, "¿Está seguro que desea eliminar el elemento"
			+ " seleccionado?", "Eliminar elemento", 0, 3);
                    if (opcion==0) {
			int subtotal = Integer.parseInt((String)ventas.getValueAt(fila, 2));
                        total = total-subtotal;
                        modelo.removeRow(fila);
                        imp_total.setText("$" + total);
                        if (total==0){
                            cancelar_venta.setEnabled(false);
                            terminar.setEnabled(false);
                            cancelar_item.setEnabled(false);
                        }
                    }
		}else {
                    JOptionPane.showMessageDialog(p1, "Por favor seleccione un elemento de la tabla", "Error", 0);
		}
            }
        
        });
        
        
        cancelar_item.setEnabled(false);
        cancelar_venta.setEnabled(false);
        terminar.setEnabled(false);
        
        String columnas[] = new String [] {
				"Menu","Cantidad","Subtotal"
		};
	modelo = new DefaultTableModel();
	modelo.setColumnIdentifiers(columnas);
        
        ventas = new JTable(modelo);
	ventas.setBounds(450, 120, 670, 410);
	p1.add(ventas);
		
	JScrollPane barras = new JScrollPane(ventas,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
		,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);	
	barras.setBounds(450, 120, 670, 410);
	add(barras);
        
        dameMenus();
        
        
        add(p1);
        setVisible(true);
        
    }
    
    private void crearLabel(String texto, int x, int y, int x1, int y1, Font ft, Icon ic) {
	JLabel et = new JLabel(texto);
	et.setBounds(x, y, x1, y1);
	et.setFont(ft);
	et.setIcon(ic);
	p1.add(et);
    }
    
    private void crearLabel2(JLabel et, int x, int y, int x1, int y1, Font ft, Icon ic) {
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
        cantidad.setText("");
        menus.setSelectedIndex(0);
    }
    
    private void limpiarTabla (){
        int filas = ventas.getRowCount();
	for (int i = filas-1; i>=0; i--) {
            modelo.removeRow(i);
	}
    }
    
    private boolean validarCantidad (String pre){
        try {
		Integer.valueOf(pre);
		if (cantidad.getText().equals("")) {
                    return false;
                }else return true;
	} catch (NumberFormatException nfe){
		return false;
	}
    }
    
    private void dameMenus(){
        try {
            String query = new String ("select descripcion from menus order by descripcion");
            ControladorBBDD dameMenus = new ControladorBBDD();
            dameMenus.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dameMenus.EjecutarConsultaNormal(query);
            while (rs.next()){
                menus.addItem(rs.getString("descripcion"));
            }
            dameMenus.cerrarConsultaNormal();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int damePrecio(String descripcion){
        int precio = 0;
        try {
            String query = new String("select precio from menus where descripcion=?");
            ControladorBBDD damePrecio = new ControladorBBDD();
            damePrecio.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            damePrecio.darParametroString(1, descripcion);
            ResultSet rs = damePrecio.EjecutarConsultaPreparada();
            rs.first();
            precio = rs.getInt("precio");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return precio;
    }
    
    private void guardarVenta (int total){
        try {
            String query = new String ("insert into ventas values (null, curdate(), curtime(),?)");
            ControladorBBDD guardarVenta = new ControladorBBDD ();
            guardarVenta.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            guardarVenta.darParametroInt(1, total);
            guardarVenta.ExecuteCreateUpdateOrDelete();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int dameTotalDeHoy (){
        int total_de_hoy = 0;
        try {
            String query = new String ("select sum(total) as total_de_hoy from ventas where fecha=curdate()");
            ControladorBBDD dtdh = new ControladorBBDD();
            dtdh.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dtdh.EjecutarConsultaNormal(query);
            rs.first();
            total_de_hoy = rs.getInt("total_de_hoy");
            dtdh.cerrarConsultaNormal();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total_de_hoy;
    
    }
}
