/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.toedter.calendar.JCalendar;


/**
 *
 * @author Fernando
 */
public class Menu_Inicial extends JFrame{
    
    private JPanel p1;
    
    public Menu_Inicial(int rol){
        setLayout(null);
	setTitle("Menu Inicial");
	setSize(690,600);
	setLocationRelativeTo(null);
	setResizable(false);
        
        Color bg = new Color(218, 247, 166);
        Font letra = new Font("Espresso Dolce", Font.PLAIN, 24);
        
        p1 = new JPanel();
	p1.setLayout(null);
	p1.setBounds(0, 0, 690, 600);
	p1.setBackground(bg);
        
        crearLabel("Bienvenido al sistema Siberia",195,40,300,40,letra,null);
        
        JButton insumos = new JButton();
        crearLabel("Inventario",80,280,100,30,letra,null);
        crearBotonesMenu(insumos,50,120,150,150,null, new ImageIcon("src/img/insumos.png"));
        insumos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Insumos insumos = new Menu_Insumos();
            }
        
        });
        
        JButton proveedores = new JButton();
        crearLabel("Proveedores",275,280,120,30,letra,null);
        crearBotonesMenu(proveedores,260,120,150,150,null, new ImageIcon("src/img/proveedores.png"));
        proveedores.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Proveedores proveedores = new Menu_Proveedores();
            }
            
        });
        
        JButton menus = new JButton();
        crearLabel("Menú",520,280,100,30,letra,null);
        crearBotonesMenu(menus,470,120,150,150,null, new ImageIcon("src/img/menu.png"));
        menus.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Menus menus = new Menu_Menus();
            }
        
        });
        
        JButton ventas = new JButton();
        crearLabel("Nueva venta",175,500,120,30,letra,null);
        crearBotonesMenu(ventas,160,340,150,150,null, new ImageIcon("src/img/ventas.png"));
        ventas.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Ventas ventas = new Menu_Ventas();
            }
        
        });
        
        JButton detalle_ventas = new JButton();
        crearLabel("Reporte de ventas",370,500,200,30,letra,null);
        crearBotonesMenu(detalle_ventas,380,340,150,150,null, new ImageIcon("src/img/detalle_ventas.png"));
        detalle_ventas.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Detalle_Ventas detalle_ventas = new Menu_Detalle_Ventas();
            }
        
        });
        
        if (rol==1){
            proveedores.setEnabled(false);
            insumos.setEnabled(false);
            menus.setEnabled(false);
            detalle_ventas.setEnabled(false);
        }
        
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
	
	private void crearBotones(JButton boton, int x, int y, int x1, int y1, Font ft, boolean activo) {
		boton.setBounds(x, y, x1, y1);
		boton.setFont(ft);
		boton.setEnabled(activo);
		p1.add(boton);
	}
        
        private void crearBotonesMenu(JButton boton, int x, int y, int x1, int y1, Font ft, Icon ic) {
		boton.setBounds(x, y, x1, y1);
		boton.setFont(ft);
		boton.setIcon(ic);
		boton.setBorderPainted(false);
		boton.setFocusPainted(true);
		boton.setContentAreaFilled(false);
		p1.add(boton);
	}
	
	private void crearText(JTextField cuadro, int x, int y, int x1, int y1, Font ft, Color fg, Color bg) {
		cuadro.setBounds(x, y, x1, y1);
		cuadro.setFont(ft);
		cuadro.setForeground(fg);
		cuadro.setBackground(bg);
		p1.add(cuadro);
	}
    
}
