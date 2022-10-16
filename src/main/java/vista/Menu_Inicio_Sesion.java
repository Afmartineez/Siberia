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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 *
 * @author Fernando
 */
public class Menu_Inicio_Sesion extends JFrame{
    
    private JTextField usuario;
    private JPasswordField contrasena;
    private JPanel p1;
    private JButton iniciar_sesion, registro;
    private int rol;
    
    public Menu_Inicio_Sesion(){
        setLayout(null);
	setTitle("Inicio de sesion");
	setSize(400,500);
	setLocationRelativeTo(null);
	setResizable(false);
        
        Color bg = new Color(218, 247, 166);
        Font letra = new Font("Espresso Dolce", Font.PLAIN, 24);
        Font letra1 = new Font("Espresso Dolce", Font.PLAIN, 21);
        
        p1 = new JPanel();
	p1.setLayout(null);
	p1.setBounds(0, 0, 400, 500);
	p1.setBackground(bg);
        
        ImageIcon imagen = new ImageIcon("src/img/usuario.png");
        
        crearLabel("Inicio de sesion",120,30,200,40,letra,null);
        crearLabel(null,120,75,150,150,null,imagen);
        crearLabel("Usuario",30,240,100,40,letra1,null);
        crearLabel("Contraseña",30,290,100,40,letra1,null);
        
        usuario = new JTextField();
        crearText(usuario,150,240,200,30,letra1);
        
        contrasena = new JPasswordField();
        crearText(contrasena,150,290,200,30,letra1);
        
        iniciar_sesion = new JButton("Iniciar sesion");
        crearBotones(iniciar_sesion, 65,345,250,30,letra1,true);
        iniciar_sesion.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verificarCampos()){
                    if (verificarUsuario(usuario.getText())){
                        verificarContrasena(usuario.getText(), String.valueOf(contrasena.getPassword()));
                    }else{
                        JOptionPane.showMessageDialog(p1, "No existe el usuario", "Error", 0);
                    }
                }else{
                    JOptionPane.showMessageDialog(p1, "No deje campos vacios", "Error", 0);
                }
            }
        
        });
        
        registro = new JButton("Registrar usuario");
        crearBotones(registro, 65,395,250,30,letra1,true);
        registro.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu_Usuarios usuarios = new Menu_Usuarios();
            }
        
        });
        
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
        
    private void crearText(JTextField cuadro, int x, int y, int x1, int y1, Font ft) {
	cuadro.setBounds(x, y, x1, y1);
	cuadro.setFont(ft);
	p1.add(cuadro);
    }
    
    private boolean verificarCampos(){
        if (usuario.getText().equals("")){
            return false;
        }else if (contrasena.getPassword().length==0){
            return false;
        }else return true;
    }
    
    private boolean verificarUsuario (String usuario){
        boolean encontrado=false;
        try {
            String query = new String ("select count(*) as cant from usuarios where usuario=?");
            ControladorBBDD verificar = new ControladorBBDD();
            verificar.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            verificar.darParametroString(1, usuario);
            ResultSet rs = verificar.EjecutarConsultaPreparada();
            rs.first();
            if(rs.getInt("cant")>0){
                encontrado = true;
            }
            verificar.cerrarOperaciónConsultaPreparada();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Inicio_Sesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Inicio_Sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encontrado;
    }
    
    private void verificarContrasena (String usuario, String contrasena){
        try {
            String query = new String ("select * from usuarios where usuario=?");
            ControladorBBDD verificar = new ControladorBBDD();
            verificar.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            verificar.darParametroString(1, usuario);
            ResultSet rs = verificar.EjecutarConsultaPreparada();
            rs.first();
            if(rs.getString("contrasena").equals(contrasena)){
                JOptionPane.showMessageDialog(p1, "Bienvenido al sistema Siberia: " + rs.getString("usuario"), "Acceso autorizado", 1);
                int rol = rs.getInt("rol");
                Menu_Inicial inicio = new Menu_Inicial(rol);
                this.dispose();
            }else {
                JOptionPane.showMessageDialog(p1, "Contraseña incorrecta. Acceso denegado", "Error", 0);
            }
            verificar.cerrarOperaciónConsultaPreparada();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Inicio_Sesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Inicio_Sesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
