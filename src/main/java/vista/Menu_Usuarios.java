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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
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
public class Menu_Usuarios extends JFrame{
    
    private JTextField usuario;
    private JPasswordField contrasena;
    private JPanel p1;
    private JButton registrar;
    private int rol;
    public Menu_Usuarios(){
        setLayout(null);
	setTitle("Registro de usuarios");
	setSize(400,300);
	setLocationRelativeTo(null);
	setResizable(false);
        
        Color bg = new Color(218, 247, 166);
        Font letra = new Font("Espresso Dolce", Font.PLAIN, 24);
        Font letra1 = new Font("Espresso Dolce", Font.PLAIN, 21);
        
        p1 = new JPanel();
	p1.setLayout(null);
	p1.setBounds(0, 0, 400, 300);
	p1.setBackground(bg);
        
        crearLabel("Registro de usuarios",100,30,220,40,letra,null);
        crearLabel("Usuario",30,100,100,40,letra1,null);
        crearLabel("Contraseña",30,150,100,40,letra1,null);
        
        usuario = new JTextField();
        crearText(usuario,150,100,200,30,letra1);
        
        contrasena = new JPasswordField();
        crearText(contrasena,150,150,200,30,letra1);
        
        
        registrar = new JButton("Registrar usuario");
        crearBotones(registrar, 80,210,220,30,letra1,true);
        registrar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(verificarCampos()){
                    if(verificarAdministrador()){
                        rol = 2;
                    }else{
                        rol = 1;
                    }
                    
                    if(verificarUsuario(usuario.getText())){
                        
                        registrarUsuario(usuario.getText(), String.valueOf(contrasena.getPassword()),rol );
                    }else{
                        JOptionPane.showMessageDialog(p1, "usuario repetido. Elija otro nombre de usuario" , "Error", 0);
                    }
                }else{
                    JOptionPane.showMessageDialog(p1, "No deje campos vacios" , "Error", 0);
                }
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
    
    private boolean verificarAdministrador(){
        boolean encontrado = false;
        try {
            String query = new String ("select count(*) as cant from usuarios");
            ControladorBBDD verificar = new ControladorBBDD();
            verificar.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = verificar.EjecutarConsultaNormal(query);
            rs.first();
            if (rs.getInt("cant")==0){
                encontrado = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encontrado;
    }
    
    private boolean verificarUsuario (String usuario){
        boolean encontrado = false;
        try {
            String query = new String ("select count(*) as cant from usuarios where usuario=?");
            ControladorBBDD verificar = new ControladorBBDD();
            verificar.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            verificar.darParametroString(1, usuario);
            ResultSet rs = verificar.EjecutarConsultaPreparada();
            rs.first();
            if(rs.getInt("cant")==0){
                encontrado = true;
            }
            verificar.cerrarOperaciónConsultaPreparada();
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return encontrado;
    }
    
    private void registrarUsuario(String usu, String contra, int rol){
        try {
            String query = new String("insert into usuarios values (?,?,?)");
            ControladorBBDD registrar = new ControladorBBDD();
            registrar.prepararConsulta(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            registrar.darParametroString(1, usu);
            registrar.darParametroString(2, contra);
            registrar.darParametroInt(3, rol);
            registrar.ExecuteCreateUpdateOrDelete();
            usuario.setText("");
            contrasena.setText("");
            JOptionPane.showMessageDialog(p1, "Usuario dado de alta correctamente", "Operación exitosa", 1);
            if (rol==2){
                JOptionPane.showMessageDialog(p1, "Usuario dado de alta como administrador", "Operación exitosa", 1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
