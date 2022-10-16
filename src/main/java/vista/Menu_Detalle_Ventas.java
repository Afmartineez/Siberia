/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import controlador.ControladorBBDD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import java.sql.*;


/**
 *
 * @author Fernando
 */
public class Menu_Detalle_Ventas extends JFrame{
    
    private JPanel p1;
    private JButton ejecutar;
    private int clave_insumo;
    private JDateChooser calendario1, calendario2, calendario3;
    private JRadioButton operacion1, operacion2, periodo1, periodo2;
    private ButtonGroup operaciones, periodos;
    
    public Menu_Detalle_Ventas (){
        setLayout(null);
	setTitle("Detalle de ventas");
	setSize(700,580);
	setLocationRelativeTo(null);
	setResizable(false);
        
        Color bg = new Color(182, 254, 209 );
        java.awt.Font letra = new java.awt.Font("Espresso Dolce", java.awt.Font.PLAIN, 24);
        java.awt.Font letra1 = new java.awt.Font("Espresso Dolce", java.awt.Font.PLAIN, 21);
        
        p1 = new JPanel();
	p1.setLayout(null);
	p1.setBounds(0, 0, 700, 580);
	p1.setBackground(bg);
        
        crearLabel("Detalle de ventas",250,30,200,30,letra,null);
        crearLabel("Operacion a realizar",50,100,250,30,letra1,null);
        
        operacion1 = new JRadioButton("Solo generar total acumulado");
        crearBotonesRadio(operacion1,50,150,300,30,letra1,bg);
        
        operacion2 = new JRadioButton("Generar reporte de ventas");
        crearBotonesRadio(operacion2,370,150,300,30,letra1,bg);
        
        operaciones = new ButtonGroup();
        operaciones.add(operacion1);
        operaciones.add(operacion2);
        
        crearLabel("Periodo de tiempo",50,200,250,30,letra1,null);
        
        periodo1 = new JRadioButton("De una fecha hasta hoy");
        crearBotonesRadio(periodo1,50,250,300,30,letra1,bg);
        
        periodo2 = new JRadioButton("Rango personalizado");
        crearBotonesRadio(periodo2,50,350,300,30,letra1,bg);
        
        periodos = new ButtonGroup();
        periodos.add(periodo1);
        periodos.add(periodo2);
        
        calendario1 = new JDateChooser();
       
        crearJDate(calendario1, 50,300,150,30,letra1);
        
        calendario2 = new JDateChooser();
        crearJDate(calendario2, 50,400,150,30,letra1);
        
        calendario3 = new JDateChooser();
        crearJDate(calendario3, 250,400,150,30,letra1);
        
        ejecutar = new JButton("Ejecutar operación");
        crearBotones(ejecutar,250,470,200,30,letra1,true);
        ejecutar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (operacion1.isSelected()){
                    if (periodo1.isSelected()){
                        if (calendario1.getDate()== null){
                            JOptionPane.showMessageDialog(p1, "No deje ningun campo de calendario vacio o con mal formato. Utilize "
                                    + "los calendarios de referencia", "Error", 0);
                        }else {
                            String mes = null;
                            Date fecha = calendario1.getDate();
                            int anio = fecha.getYear() + 1900;
                            String anio2 = String.valueOf(anio);
                            int mesParseado = fecha.getMonth() + 1;
                            if (fecha.getMonth()<9){
                                mes = new String ("0" + String.valueOf(mesParseado));
                            }else {
                                mes = new String (String.valueOf(mesParseado));
                            }
                            String dia = String.valueOf(fecha.getDate());   
                            String fechaCompleta = new String(anio2 + "-" + mes + "-" + dia);
                            String fechaLarga = new String(dia + " de " + dameMes(mesParseado) + " de " + anio2);
                            //System.out.print(fechaCompleta);
                            JOptionPane.showMessageDialog(p1, "El total generado desde " + fechaLarga + " hasta el dia de hoy es de " + 
                                    dameTotalVentasEntreFechas(fechaCompleta), "Informa de ventas", 1);
                        }
                    }else if (periodo2.isSelected()){
                        
                        if (calendario2.getDate()== null || calendario3.getDate()== null){
                            JOptionPane.showMessageDialog(p1, "Por favor seleccione un formato de fecha valido. Utilize "
                                    + "el calendario de referencia", "Error", 0);
                        }else {
                            String mes2 = null;
                            Date fecha2 = calendario2.getDate();
                            int anio = fecha2.getYear() + 1900;
                            String anio2 = String.valueOf(anio);
                            int mesParseado2 = fecha2.getMonth() + 1;
                            if (fecha2.getMonth()<9){
                                mes2 = new String ("0" + String.valueOf(mesParseado2));
                            }else {
                                mes2 = new String (String.valueOf(mesParseado2));
                            }
                            String dia2 = String.valueOf(fecha2.getDate());   
                            String fechaCompleta2 = new String(anio2 + "-" + mes2 + "-" + dia2);
                            String fechaLarga2 = new String(dia2 + " de " + dameMes(mesParseado2) + " de " + anio2);
                            
                            String mes3 = null;
                            Date fecha3 = calendario3.getDate();
                            int anio4 = fecha3.getYear() + 1900;
                            String anio3 = String.valueOf(anio4);
                            int mesParseado3 = fecha3.getMonth() + 1;
                            if (fecha3.getMonth()<9){
                                mes3 = new String ("0" + String.valueOf(mesParseado2));
                            }else {
                                mes3 = new String (String.valueOf(mesParseado2));
                            }
                            String dia3 = String.valueOf(fecha3.getDate());   
                            String fechaCompleta3 = new String(anio3 + "-" + mes3 + "-" + dia3);
                            String fechaLarga3 = new String(dia3 + " de " + dameMes(mesParseado3) + " de " + anio3);
                            
                            JOptionPane.showMessageDialog(p1, "El total generado desde " + fechaLarga2 + " hasta " + fechaLarga3 + " es de " +
                                    dameTotalVentasEntreFechas2(fechaCompleta2,fechaCompleta3), "Informa de ventas", 1);
                        }
                    }else {
                        JOptionPane.showMessageDialog(p1, "Por favor seleccione un periodo de tiempo"
                                , "Error", 0);
                    }
                }else if(operacion2.isSelected()){
                    
                    if (periodo1.isSelected()){
                        
                        if (calendario1.getDate()== null){
                            JOptionPane.showMessageDialog(p1, "No deje ningun campo de calendario vacio o con mal formato. Utilize "
                                    + "los calendarios de referencia", "Error", 0);
                        }else {
                            String mes = null;
                            Date fecha = calendario1.getDate();
                            int ani = fecha.getYear() + 1900;
                            String anio = String.valueOf(ani);
                            int mesParseado = fecha.getMonth() + 1;
                            if (fecha.getMonth()<9){
                                mes = new String ("0" + String.valueOf(mesParseado));
                            }else {
                                mes = new String (String.valueOf(mesParseado));
                            }
                            String dia = String.valueOf(fecha.getDate());   
                            String fechaCompleta = new String(anio + "-" + mes + "-" + dia);
                            String fechaLarga = new String(dia + " de " + dameMes(mesParseado) + " de " + anio);
                            //System.out.print(fechaCompleta);
                            
                            
                            long miliseconds = System.currentTimeMillis();
                            java.sql.Date hoy = new java.sql.Date(miliseconds);
                            String fechaActual = String.valueOf(hoy);
                            
                            String mes2=null;
                            int ani2 = hoy.getYear() + 1900;
                            String anio2 = String.valueOf(ani2);
                            int mesParseado2 = hoy.getMonth() + 1;
                            if (hoy.getMonth()<9){
                                mes2 = new String ("0" + String.valueOf(mesParseado));
                            }else {
                                mes2 = new String (String.valueOf(mesParseado));
                            }
                            String dia2 = String.valueOf(hoy.getDate());
                            String fechaLarga2 = new String(dia2 + " de " + dameMes(mesParseado2) + " de " + anio2);
                            
                            dameReporteVentasEntreFechas(fechaCompleta,fechaActual,fechaLarga,fechaLarga2); 
                            
                        }
                        
                    }else if (periodo2.isSelected()){
                        
                        if (calendario2.getDate()== null || calendario3.getDate()==null){
                            JOptionPane.showMessageDialog(p1, "No deje ningun campo de calendario vacio o con mal formato. Utilize "
                                    + "los calendarios de referencia", "Error", 0);
                        }else {
                            String mes = null;
                            Date fecha = calendario2.getDate();
                            int ani = fecha.getYear() + 1900;
                            String anio = String.valueOf(ani);
                            int mesParseado = fecha.getMonth() + 1;
                            if (fecha.getMonth()<9){
                                mes = new String ("0" + String.valueOf(mesParseado));
                            }else {
                                mes = new String (String.valueOf(mesParseado));
                            }
                            String dia = String.valueOf(fecha.getDate());   
                            String fechaCompleta = new String(anio + "-" + mes + "-" + dia);
                            String fechaLarga = new String(dia + " de " + dameMes(mesParseado) + " de " + anio);
                            //System.out.print(fechaCompleta);
                            
                            
                            Date fecha2 = calendario3.getDate();
                            
                            String mes2=null;
                            int ani2 = fecha2.getYear() + 1900;
                            String anio2 = String.valueOf(ani2);
                            int mesParseado2 = fecha2.getMonth() + 1;
                            if (fecha2.getMonth()<9){
                                mes2 = new String ("0" + String.valueOf(mesParseado));
                            }else {
                                mes2 = new String (String.valueOf(mesParseado));
                            }
                            String dia2 = String.valueOf(fecha2.getDate());
                            String fechaCompleta2 = new String(anio2 + "-" + mes2 + "-" + dia2);
                            String fechaLarga2 = new String(dia2 + " de " + dameMes(mesParseado2) + " de " + anio2);
                            
                            dameReporteVentasEntreFechas(fechaCompleta,fechaCompleta2,fechaLarga,fechaLarga2); 
                            
                        }
                        
                        
                    }else {
                        JOptionPane.showMessageDialog(p1, "Por favor seleccione un periodo de tiempo"
                                , "Error", 0);
                    }
                    
                }else {
                    JOptionPane.showMessageDialog(p1, "Por favor seleccione una operaci[on a realizar"
                                , "Error", 0);
                }
               // System.out.print(fecha);
            }
        
        });
        
        //System.out.print(dameTotalVentasEntreFechas("2022-09-15","2022-09-17"));
        add(p1);
        setVisible(true);
    }
    
    private void crearLabel(String texto, int x, int y, int x1, int y1, java.awt.Font ft, Icon ic) {
	JLabel et = new JLabel(texto);
	et.setBounds(x, y, x1, y1);
	et.setFont(ft);
	et.setIcon(ic);
	p1.add(et);
    }
	
    private void crearBotones(JButton boton, int x, int y, int x1, int y1, java.awt.Font ft, boolean activo) {
	boton.setBounds(x, y, x1, y1);
	boton.setFont(ft);
	boton.setEnabled(activo);
	p1.add(boton);
    }
    
    private void crearBotonesRadio (JRadioButton boton,int x, int y, int x1, int y1, java.awt.Font ft, Color bg){
        boton.setBounds(x, y, x1, y1);
	boton.setFont(ft);
        boton.setBackground(bg);
	p1.add(boton);
    }
    
    private void crearJDate (JDateChooser jdc,int x, int y, int x1, int y1, java.awt.Font ft){
        jdc.setBounds(x, y, x1, y1);
	jdc.setFont(ft);
	p1.add(jdc);
    }
    
    private int dameTotalVentasEntreFechas (String fecha1){
    int total = 0;
        try {
            String query = new String("select sum(total) as total_fechas from ventas where fecha BETWEEN '"+ fecha1 + "' and curtime()");
            ControladorBBDD dtvef = new ControladorBBDD();
            dtvef.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dtvef.EjecutarConsultaNormal(query);
            rs.first();
            total = rs.getInt("total_fechas");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Detalle_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Detalle_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    return total;
    }
    
    private int dameTotalVentasEntreFechas2 (String fecha1, String fecha2){
    int total = 0;
        try {
            String query = new String("select sum(total) as total_fechas from ventas where fecha BETWEEN '"+ fecha1 + "' and '" + fecha2 + "'");
            ControladorBBDD dtvef = new ControladorBBDD();
            dtvef.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = dtvef.EjecutarConsultaNormal(query);
            rs.first();
            total = rs.getInt("total_fechas");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Detalle_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Detalle_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    return total;
    }
    
    private String dameMes (int numero_de_mes){
        String mes = null;
        switch (numero_de_mes){
            case 1:
                mes = new String ("enero");
                break;
            case 2:
                mes = new String ("febrero");
                break;
            case 3:
                mes = new String ("marzo");
                break;
            case 4:
                mes = new String ("abril");
                break;
            case 5:
                mes = new String ("mayo");
                break;
            case 6:
                mes = new String ("junio");
                break;
            case 7:
                mes = new String ("julio");
                break;    
            case 8:
                mes = new String ("agosto");
                break;
            case 9:
                mes = new String ("septiembre");
                break;
            case 10:
                mes = new String ("octubre");
                break;
            case 11:
                mes = new String ("noviembre");
                break;
            case 12:
                mes = new String ("diciembre");
                break;   
        }
        return mes;
    }
    
    private void dameReporteVentasEntreFechas (String fecha1, String fecha2, String fechaLarga1, String fechaLarga2){
        try {
            String query = new String ("select * from ventas where fecha BETWEEN '"+ fecha1 + "' and '" + fecha2 + "'");
            ControladorBBDD drvef = new ControladorBBDD();
            drvef.crearConsultaNormal(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = drvef.EjecutarConsultaNormal(query);
            
            generarReporte(rs, fechaLarga1, fechaLarga2, dameTotalVentasEntreFechas2(fecha1,fecha2));
            drvef.cerrarConsultaNormal();
        } catch (SQLException ex) {
            Logger.getLogger(Menu_Detalle_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Menu_Detalle_Ventas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void generarReporte(ResultSet rs, String fechaLarga1, String fechaLarga2, int total){
        com.itextpdf.text.Document pdf = new com.itextpdf.text.Document();
        String ruta=System.getProperty("user.home");
        try {
            PdfWriter.getInstance(pdf, new FileOutputStream(ruta + "/Desktop/reporte_ventas.pdf"));
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.setFont(FontFactory.getFont("Arial", 18, com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
            parrafo.add("Reporte de ventas - Taqueria Siberia" + "\n\n\n");
            parrafo.add("Periodo de " + fechaLarga1 + " al " + fechaLarga2 + "\n\n\n");
            pdf.open();
            
            PdfPTable detalle = new PdfPTable(4);
            detalle.addCell("Clave:");
            detalle.addCell("Fecha ");
            detalle.addCell("Hora");
            detalle.addCell("Total");
            
            while (rs.next()){
                detalle.addCell(String.valueOf(rs.getInt("clave_venta")));
                detalle.addCell(String.valueOf(rs.getDate("fecha")));
                detalle.addCell(String.valueOf(rs.getTime("hora")));
                detalle.addCell(String.valueOf(rs.getInt("total")));
            }
            
            Paragraph parrafo2 = new Paragraph();
            parrafo2.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo2.setFont(FontFactory.getFont("Arial", 14, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));
            parrafo2.add("\n\n\n" + "Total acumulado: " +  total);
            
            
            pdf.add(parrafo);
            pdf.add(detalle);
            pdf.add(parrafo2);
            pdf.close();
            
            JOptionPane.showMessageDialog(p1, "Reporte generado correctamente. Revise el archivo en el escritorio", "Operación exitosa", 1);
            
            //abrirReporte("reporte_proveedores.pdf");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(Menu_Insumos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
