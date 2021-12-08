/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package picotoorganizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.FileWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    public DefaultTableModel modelo, modelo2;
    private Connection conn = null;
    private Clipboard portapapeles;

    public ArrayList<Persona> listaPersonas;
    private final String CADENA_CONEXION = "jdbc:mysql://localhost:3306/organizer";
    JButton botonNuevo, botonEditar, botonEliminar, botonPDF, botonImprimir, botonBuscar, botonBuscarWiki;

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();

        //Ocultamos de inicio los comandos de la pestaña de Grupos
        jRadioSoltero.setVisible(false);
        jRadioCasado.setVisible(false);
        jRadioDivorciado.setVisible(false);
        jRadioViudo.setVisible(false);
        jLabelResultado.setVisible(false);

        //Cargamos la tabla al inicio
        refrescarTabla();
        //Lo mismo con la tabla de grupos
        refrescarTablaGrupos();

        //Inicializamos los botones
        botonNuevo = new JButton();
        botonEditar = new JButton();
        botonEliminar = new JButton();
        botonPDF = new JButton();
        botonImprimir = new JButton();
        botonBuscar = new JButton();
        botonBuscarWiki = new JButton();

        //Creamos la barra de herramientas
        jToolBar1.add(botonNuevo);
        jToolBar1.add(botonEditar);
        jToolBar1.add(botonEliminar);
        jToolBar1.add(new javax.swing.JToolBar.Separator());
        jToolBar1.add(botonPDF);
        jToolBar1.add(botonImprimir);
        jToolBar1.add(new javax.swing.JToolBar.Separator());
        jToolBar1.add(botonBuscar);
        jToolBar1.add(botonBuscarWiki);

        //Establecemos el modo de selección
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Cargamos iconos
        botonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picotoorganizer/icons/new.png")));
        botonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picotoorganizer/icons/edit.png")));
        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picotoorganizer/icons/FIRE.png")));

        botonPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picotoorganizer/icons/save.png")));
        botonImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picotoorganizer/icons/printer.png")));
        botonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picotoorganizer/icons/agent.png")));
        botonBuscarWiki.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picotoorganizer/icons/helpX.png")));

        //Para cada botón establecemos acciones que llevarán a métodos
        botonNuevo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //your actions
                AsistenteCreacion estancia = new AsistenteCreacion(jTable1);
                estancia.setVisible(true);

            }

        });
        botonEditar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //your actions
                editarRegistro();
            }

        });

        botonEliminar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarRegistro();
                refrescarTabla();
            }
        });

        botonPDF.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //your actions
                guardarRegistros();
            }
        });

        botonImprimir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                imprimirTabla(jTable1, getTitle(), "Cargando", true);

            }
        });

        botonBuscar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //your actions
                buscarRegistro();
            }
        });

        botonBuscarWiki.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarRegistroWiki();
            }
        });

    }

    public void refrescarTablaGrupos() {
        try {
            conn = DriverManager.getConnection(CADENA_CONEXION, "root", "");
            String sql = "SELECT EC.ESTADO, P.NOMBRE, P.APELLIDOS, P.DNI FROM PERSONAS AS P, ESTADOS_CIVILES AS EC WHERE EC.ID=P.estado_civil";

            PreparedStatement ps = conn.prepareStatement(sql);
            try {
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

                modelo2 = new javax.swing.table.DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            rsmd.getColumnLabel(1), rsmd.getColumnLabel(2), rsmd.getColumnLabel(3), rsmd.getColumnLabel(4)
                        }
                );

                jTable2.setModel(modelo2);
            } catch (Exception et) {
                JOptionPane.showMessageDialog(rootPane, "Ha habido un error: " + et);
            }
        } catch (SQLException e) {

        }
    }

    public void refrescarTabla() {
        try {
            conn = DriverManager.getConnection(CADENA_CONEXION, "root", "");

            String sql = "SELECT * FROM PERSONAS";
            listaPersonas = new ArrayList<>();
            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
                modelo = new javax.swing.table.DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            rsmd.getColumnLabel(1), rsmd.getColumnLabel(2), rsmd.getColumnLabel(3), rsmd.getColumnLabel(4),
                            rsmd.getColumnLabel(5), rsmd.getColumnLabel(6), rsmd.getColumnLabel(7), rsmd.getColumnLabel(8),
                            rsmd.getColumnLabel(9), rsmd.getColumnLabel(10), rsmd.getColumnLabel(11), rsmd.getColumnLabel(12),
                            rsmd.getColumnLabel(13), rsmd.getColumnLabel(14), rsmd.getColumnLabel(15), rsmd.getColumnLabel(16),
                            rsmd.getColumnLabel(17)
                        }
                ) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                jTable1.setModel(modelo);

                int i = 0;
                while (rs.next()) {
                    int id = Integer.parseInt(rs.getString("id"));
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String dni = rs.getString("dni");
                    String fecha_nacimiento = rs.getString("fecha_nacimiento");
                    String fecha_fallecimiento = rs.getString("fecha_fallecimiento");
                    String genero = rs.getString("genero");
                    String email = rs.getString("email");
                    int telefono = rs.getInt("telefono");
                    String direccion = rs.getString("direccion");
                    String poblacion = rs.getString("poblacion");
                    String provincia = rs.getString("provincia");
                    String region = rs.getString("region");
                    String cod_postal = rs.getString("cod_postal");
                    String pais = rs.getString("pais");
                    int id_estado = rs.getInt("estado_civil");
                    String estado_laboral = rs.getString("estado_laboral");

                    Persona persona = new Persona(nombre, apellidos, dni, fecha_nacimiento, fecha_fallecimiento, genero, email,
                            telefono, direccion, poblacion, provincia, region, cod_postal, pais, id_estado, estado_laboral);

                    listaPersonas.add(persona);
                    modelo.addRow(new Object[]{id, nombre, apellidos, dni, fecha_nacimiento, fecha_fallecimiento,
                        genero, email, telefono, direccion, poblacion, provincia, region, cod_postal, pais,
                        id_estado, estado_laboral});
                    i++;
                }

                if (i < 1) {
                    JOptionPane.showMessageDialog(null, "No se ha encontrado ningún registro", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "Ha habido un error con la BD, revise su conexión: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Ha habido un error con la BD, revise su conexión: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarRegistro() {
        if (jTable1.getSelectedRow() != -1) {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Se buscará a la persona tomando el nombre, apellidos y ciudad", "Está seguro?",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                int id_row = jTable1.getSelectedRow();
                String name_person = jTable1.getModel().getValueAt(id_row, 1).toString().replace(" ", "+");

                String surname_person = jTable1.getModel().getValueAt(id_row, 2).toString().replace(" ", "+");

                String city_person = jTable1.getModel().getValueAt(id_row, 10).toString().replace(" ", "+");

                //Cogemos la url y la limpiamos
                String url = "https://www.google.com/search?q=" + name_person + "+" + surname_person + "+" + city_person;
                Desktop desktop = java.awt.Desktop.getDesktop();

                try {
                    desktop.browse(new URI(url));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (confirmed == JOptionPane.NO_OPTION) {

            }

        } else {
            JOptionPane.showMessageDialog(null, "Elija una persona para buscar, por favor");
        }

    }

    public void buscarRegistroWiki() {
        //your actions
        if (jTable1.getSelectedRow() != -1) {
            int id_row = jTable1.getSelectedRow();
            JOptionPane.showMessageDialog(null, "Se buscará info de la localidad de la persona seleccionada");

            //Reemplazamos los espacios por "+" para que no presente error de parseado si son nombres con espacios
            String city_person = jTable1.getModel().getValueAt(id_row, 10).toString().replace(" ", "+");

            String url = "https://es.wikipedia.org/wiki/" + city_person;

            Desktop desktop = java.awt.Desktop.getDesktop();

            try {
                desktop.browse(new URI(url));
            } catch (URISyntaxException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Elija una persona para buscar, por favor");
        }
    }

    public void editarRegistro() {
        if (jTable1.getSelectedRow() != -1) {
            int id_row = jTable1.getSelectedRow();
            int id_persona = Integer.parseInt(jTable1.getModel().getValueAt(id_row, 0).toString());
            String nombre_persona = jTable1.getModel().getValueAt(id_row, 1).toString();
            String apellidos_persona = jTable1.getModel().getValueAt(id_row, 2).toString();
            String dni_persona = jTable1.getModel().getValueAt(id_row, 3).toString();
            String fecha_nacimiento_pers = jTable1.getModel().getValueAt(id_row, 4).toString();
            String fecha_fallecimiento_pers;
            if (jTable1.getModel().getValueAt(id_row, 5) == null) {
                fecha_fallecimiento_pers = "";
            } else {
                fecha_fallecimiento_pers = jTable1.getModel().getValueAt(id_row, 5).toString();
            }
            String genero_pers = jTable1.getModel().getValueAt(id_row, 6).toString();
            String email_pers = jTable1.getModel().getValueAt(id_row, 7).toString();
            int telefono_pers = Integer.parseInt(jTable1.getModel().getValueAt(id_row, 8).toString());
            String direccion_pers = jTable1.getModel().getValueAt(id_row, 9).toString();
            String poblacion_pers = jTable1.getModel().getValueAt(id_row, 10).toString();
            String provincia_pers = jTable1.getModel().getValueAt(id_row, 11).toString();
            String region_pers = jTable1.getModel().getValueAt(id_row, 12).toString();
            String cod_postal_pers = jTable1.getModel().getValueAt(id_row, 13).toString();
            String pais_pers = jTable1.getModel().getValueAt(id_row, 14).toString();
            int est_civil_pers = (int) jTable1.getModel().getValueAt(id_row, 15);
            String est_laboral_pers = jTable1.getModel().getValueAt(id_row, 16).toString();

            //Llamamos a la ventana AsistenteEdicion pasando todos los parámetros del objeto
            AsistenteEdicion estancia = new AsistenteEdicion(jTable1, id_persona, nombre_persona, apellidos_persona, dni_persona,
                    fecha_nacimiento_pers, fecha_fallecimiento_pers, genero_pers, email_pers, telefono_pers, direccion_pers,
                    poblacion_pers, provincia_pers, region_pers, cod_postal_pers, pais_pers, est_civil_pers,
                    est_laboral_pers);
            estancia.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Elija una persona para editar por favor");
        }
    }

    public void copiarRegistro() {
        int numColumns = jTable1.getSelectedColumnCount();
        int numFilas = jTable1.getSelectedRowCount();
        int[] filasSelected = jTable1.getSelectedRows();
        int[] columnasSelected = jTable1.getSelectedColumns();
        StringBuffer excelStr;
        //Inicializamos el portapapeles
        portapapeles = Toolkit.getDefaultToolkit().getSystemClipboard();

        if (numFilas != filasSelected[filasSelected.length - 1] - filasSelected[0] + 1 || numFilas != filasSelected.length
                || numColumns != columnasSelected[columnasSelected.length - 1] - columnasSelected[0] + 1 || numColumns != columnasSelected.length) {

            JOptionPane.showMessageDialog(null, "Selección errónea para copiar", "Selección errónea para copiar", JOptionPane.ERROR_MESSAGE);
            return;
        }

        excelStr = new StringBuffer();
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumns; j++) {
                excelStr.append(reemplazarCaracteresEscape(jTable1.getValueAt(filasSelected[i], columnasSelected[j])));

                if (j < numColumns - 1) {
                    excelStr.append("\t");
                }
            }
            excelStr.append("\n");
        }

        StringSelection sel = new StringSelection(excelStr.toString());
        portapapeles.setContents(sel, sel);
    }

    private String reemplazarCaracteresEscape(Object cell) {
        return cell.toString().replace("\n", " ").replace("\t", " ");
    }

    public void eliminarRegistro() {
        if (jTable1.getSelectedRow() != -1) {
            int confirmed = JOptionPane.showConfirmDialog(null,
                    "Va a eliminar a la persona seleccionada de la tabla y de la BD, desea continuar?", "Está seguro?",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                int id_row = jTable1.getSelectedRow();
                int id_person = (int) jTable1.getModel().getValueAt(id_row, 0);
                String sql = "DELETE FROM PERSONAS WHERE ID=" + id_person + "";

                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                    modelo.removeRow(id_row);

                    JOptionPane.showMessageDialog(rootPane, "Persona eliminada correctamente");
                } catch (SQLException ee) {
                    ee.printStackTrace();
                }
            } else if (confirmed == JOptionPane.NO_OPTION) {

            }

        } else {
            JOptionPane.showMessageDialog(null, "Elija una persona para eliminar por favor");
        }
    }

    public void imprimirTabla(JTable jTable, String cabecera, String pie, boolean showPrintDialog) {
        boolean fitWidth = true;
        boolean interactive = true;
        // We define the print mode (Definimos el modo de impresión)
        JTable.PrintMode mode = fitWidth ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
        try {
            // Print the table (Imprimo la tabla)             
            boolean complete = jTable.print(mode, new MessageFormat(cabecera), new MessageFormat(pie), showPrintDialog, null, interactive);
            if (complete) {
                // Mostramos el mensaje de impresión existosa
                JOptionPane.showMessageDialog(jTable, "Impresión completa!", "Resultado de la impresión: ", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mostramos un mensaje indicando que la impresión fue cancelada                 
                JOptionPane.showMessageDialog(jTable, "Impresión cancelada", "Resultado de la impresión: ", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(jTable, "Lo sentimos ha habido un fallo en la impresión: " + pe.getMessage(), "Resultado: ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void realizarConsultaGrupos(int id_estado) {
        try {
            conn = DriverManager.getConnection(CADENA_CONEXION, "root", "");
            System.out.println("La Conexión ha sido realizada con éxito!");
            System.out.println("Se iniciará el programa en breve...");

            String sql = "SELECT EC.ESTADO, P.NOMBRE, P.APELLIDOS, P.DNI FROM PERSONAS AS P, ESTADOS_CIVILES AS EC WHERE EC.ID=" + id_estado + " AND EC.ID=P.estado_civil";

            PreparedStatement ps = conn.prepareStatement(sql);

            try {
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

                modelo2 = new javax.swing.table.DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            rsmd.getColumnLabel(1), rsmd.getColumnLabel(2), rsmd.getColumnLabel(3), rsmd.getColumnLabel(4)
                        }
                );

                jTable2.setModel(modelo2);

                int i = 0;
                while (rs.next()) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String estado = rs.getString("estado");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String dni = rs.getString("dni");

                    modelo2.addRow(new Object[]{estado, nombre, apellidos, dni});
                    i++;
                }
                if (i < 1) {
                    JOptionPane.showMessageDialog(null, "No se ha encontrado ninguna referencia", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (i == 1) {
                    jLabelResultado.setText(i + " registros encontrados");
                } else {
                    jLabelResultado.setText(i + " registros encontrados");
                }
                jTable2.setToolTipText(sql);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "Ha habido un error de la BD: " + e);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Ha habido un error: " + e);
        }
    }

    public void exportarACSV() {
        try {
            final JFileChooser selector = new JFileChooser();   //Creo el selector
            selector.setApproveButtonText("Guardar");       //Botón guardar

            MyFileFilterCSV filtro_csv = new MyFileFilterCSV();
            selector.setFileFilter(filtro_csv);
            selector.addChoosableFileFilter(filtro_csv);

            int returnVal = selector.showSaveDialog(this);
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File archivo = selector.getSelectedFile();
            if (!archivo.getName().endsWith(".csv")) {
                archivo = new File(archivo.getAbsolutePath() + ".csv");
            }
            FileWriter csv = new FileWriter(archivo);
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                csv.write(modelo.getColumnName(i) + ",");
            }

            csv.write("\n");

            for (int i = 0; i < modelo.getRowCount(); i++) {
                for (int j = 0; j < modelo.getColumnCount(); j++) {
                    if (modelo.getValueAt(i, j) == null) {
                        csv.write("N/D, ");
                    } else {
                        csv.write(modelo.getValueAt(i, j).toString() + ",");
                    }

                }
                csv.write("\n");
            }

            csv.close();

            Desktop desktop = java.awt.Desktop.getDesktop();

            try {
                desktop.open(archivo);
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPane, "Ha habido un error con la I/O", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void guardarRegistros() {
        final JFileChooser selector = new JFileChooser();   //Creo el selector
        selector.setApproveButtonText("Guardar");       //Botón guardar
        MyFileFilter filtro_pdf = new MyFileFilter();
        selector.setFileFilter(filtro_pdf);
        selector.addChoosableFileFilter(filtro_pdf);

        int returnVal = selector.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();
        if (!archivo.getName().endsWith(".pdf")) {
            archivo = new File(archivo.getAbsolutePath() + ".pdf");
        }

        Document document;
        try {
            document = new Document(PageSize.A2.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(archivo));

            document.open();
            //Maquetamos el documento PDF a generar
            int contador = jTable1.getRowCount();
            PdfPTable tab = new PdfPTable(17);
            PdfPCell id = new PdfPCell(new Phrase("ID"));
            id.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(id);
            PdfPCell nombre = new PdfPCell(new Phrase("Nombre"));
            nombre.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(nombre);
            PdfPCell apellidos = new PdfPCell(new Phrase("Apellidos"));
            apellidos.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(apellidos);
            PdfPCell dni = new PdfPCell(new Phrase("DNI"));
            dni.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(dni);
            PdfPCell fecha_nacimiento = new PdfPCell(new Phrase("Fecha nacimiento"));
            fecha_nacimiento.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(fecha_nacimiento);
            PdfPCell fecha_fallecimiento = new PdfPCell(new Phrase("Fecha fallecimiento"));
            fecha_fallecimiento.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(fecha_fallecimiento);
            PdfPCell genero = new PdfPCell(new Phrase("Género"));
            genero.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(genero);
            PdfPCell email = new PdfPCell(new Phrase("Email"));
            email.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(email);
            PdfPCell telefono = new PdfPCell(new Phrase("Teléfono"));
            telefono.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(telefono);
            PdfPCell direccion = new PdfPCell(new Phrase("Dirección"));
            direccion.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(direccion);
            PdfPCell poblacion = new PdfPCell(new Phrase("Población"));
            poblacion.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(poblacion);
            PdfPCell provincia = new PdfPCell(new Phrase("Provincia"));
            provincia.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(provincia);
            PdfPCell region = new PdfPCell(new Phrase("Región"));
            region.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(region);
            PdfPCell cod_postal = new PdfPCell(new Phrase("Cód. postal"));
            cod_postal.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(cod_postal);
            PdfPCell pais = new PdfPCell(new Phrase("País"));
            pais.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(pais);
            PdfPCell estado_civil = new PdfPCell(new Phrase("Estado civil"));
            estado_civil.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(estado_civil);
            PdfPCell estado_laboral = new PdfPCell(new Phrase("Estado laboral"));
            estado_laboral.setBackgroundColor(Color.LIGHT_GRAY);
            tab.addCell(estado_laboral);
            tab.setWidthPercentage(90);

            for (int i = 0; i < contador; i++) {
                Object obj1 = CogerDatos(jTable1, i, 0);
                Object obj2 = CogerDatos(jTable1, i, 1);
                Object obj3 = CogerDatos(jTable1, i, 2);
                Object obj4 = CogerDatos(jTable1, i, 3);
                Object obj5 = CogerDatos(jTable1, i, 4);
                Object obj6 = CogerDatos(jTable1, i, 5);
                Object obj7 = CogerDatos(jTable1, i, 6);
                Object obj8 = CogerDatos(jTable1, i, 7);
                Object obj9 = CogerDatos(jTable1, i, 8);
                Object obj10 = CogerDatos(jTable1, i, 9);
                Object obj11 = CogerDatos(jTable1, i, 10);
                Object obj12 = CogerDatos(jTable1, i, 11);
                Object obj13 = CogerDatos(jTable1, i, 12);
                Object obj14 = CogerDatos(jTable1, i, 13);
                Object obj15 = CogerDatos(jTable1, i, 14);
                Object obj16 = CogerDatos(jTable1, i, 15);
                Object obj17 = CogerDatos(jTable1, i, 16);

                PdfPCell value1 = new PdfPCell(new Phrase(obj1.toString()));
                PdfPCell value2 = new PdfPCell(new Phrase(obj2.toString()));
                PdfPCell value3 = new PdfPCell(new Phrase(obj3.toString()));
                PdfPCell value4 = new PdfPCell(new Phrase(obj4.toString()));
                PdfPCell value5 = new PdfPCell(new Phrase(obj5.toString()));
                PdfPCell value6;
                if (obj6 == null) {
                    value6 = new PdfPCell(new Phrase("N/A"));
                } else {
                    value6 = new PdfPCell(new Phrase(obj6.toString()));
                }
                PdfPCell value7 = new PdfPCell(new Phrase(obj7.toString()));
                PdfPCell value8 = new PdfPCell(new Phrase(obj8.toString()));
                PdfPCell value9 = new PdfPCell(new Phrase(obj9.toString()));
                PdfPCell value10 = new PdfPCell(new Phrase(obj10.toString()));
                PdfPCell value11 = new PdfPCell(new Phrase(obj11.toString()));
                PdfPCell value12 = new PdfPCell(new Phrase(obj12.toString()));
                PdfPCell value13 = new PdfPCell(new Phrase(obj13.toString()));
                PdfPCell value14 = new PdfPCell(new Phrase(obj14.toString()));
                PdfPCell value15 = new PdfPCell(new Phrase(obj15.toString()));
                PdfPCell value16;
                if (obj16 == null) {
                    value16 = new PdfPCell(new Phrase("N/A"));
                } else {
                    value16 = new PdfPCell(new Phrase(obj16.toString()));
                }
                PdfPCell value17;
                if (obj17 == null) {
                    value17 = new PdfPCell(new Phrase("N/A"));
                } else {
                    value17 = new PdfPCell(new Phrase(obj17.toString()));
                }

                tab.addCell(value1);
                tab.addCell(value2);
                tab.addCell(value3);
                tab.addCell(value4);
                tab.addCell(value5);
                tab.addCell(value6);
                tab.addCell(value7);
                tab.addCell(value8);
                tab.addCell(value9);
                tab.addCell(value10);
                tab.addCell(value11);
                tab.addCell(value12);
                tab.addCell(value13);
                tab.addCell(value14);
                tab.addCell(value15);
                tab.addCell(value16);
                tab.addCell(value17);

            }
            document.add(tab);
            document.close();
            Desktop desktop = java.awt.Desktop.getDesktop();

            try {
                desktop.open(archivo);
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jMenuItemGuardar.getInputMap().put(KeyStroke.getKeyStroke("control S"), "Save");
    }

    private static class MyFileFilter extends javax.swing.filechooser.FileFilter {

        public boolean accept(File f) {

            return f.isDirectory() || (f.isFile() && f.getName().toLowerCase().endsWith(".pdf"));
        }

        public String getDescription() {

            return "Documento de formato portátil (.pdf)";
        }
    }

    private static class MyFileFilterCSV extends javax.swing.filechooser.FileFilter {

        public boolean accept(File f) {

            return f.isDirectory() || (f.isFile() && f.getName().toLowerCase().endsWith(".csv"));
        }

        public String getDescription() {

            return "Archivo separado por comas (.csv)";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jRadioSoltero = new javax.swing.JRadioButton();
        jRadioCasado = new javax.swing.JRadioButton();
        jRadioDivorciado = new javax.swing.JRadioButton();
        jRadioViudo = new javax.swing.JRadioButton();
        jLabelResultado = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemNuevo = new javax.swing.JMenuItem();
        jMenuItemGuardar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuCSV = new javax.swing.JMenuItem();
        jMenuItemPrint = new javax.swing.JMenuItem();
        jMenuItemClose = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemEdit = new javax.swing.JMenuItem();
        jMenuItemDelete = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemSelectAll = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItemCopiar = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuBuscar = new javax.swing.JMenuItem();
        jMenuBuscarWiki = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuPersonasConsola = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItemOptions = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Picoto Organizer v0.7");

        jTabbedPane1.setAutoscrolls(true);
        jTabbedPane1.setName("General"); // NOI18N

        jScrollPane1.setToolTipText("");
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setInheritsPopupMenu(true);
        jScrollPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jScrollPane1ComponentShown(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setDoubleBuffered(true);
        jScrollPane1.setViewportView(jTable1);

        jTabbedPane1.addTab("General", jScrollPane1);

        jScrollPane2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jScrollPane2ComponentShown(evt);
            }
        });

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);
        jTable2.getAccessibleContext().setAccessibleName("");

        jTabbedPane1.addTab("Vista por grupos", null, jScrollPane2, "");

        jToolBar1.setRollover(true);

        jLabel1.setText("Codificación: UTF-8");
        jLabel1.setToolTipText("");

        jLabel2.setText("Listo para editar");
        jLabel2.setToolTipText("");

        buttonGroup1.add(jRadioSoltero);
        jRadioSoltero.setText("Soltero/a");
        jRadioSoltero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioSolteroActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioCasado);
        jRadioCasado.setText("Casado/a");
        jRadioCasado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioCasadoActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioDivorciado);
        jRadioDivorciado.setText("Divorciado/a");
        jRadioDivorciado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioDivorciadoActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioViudo);
        jRadioViudo.setText("Viudo/a");
        jRadioViudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioViudoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioSoltero)
                .addGap(18, 18, 18)
                .addComponent(jRadioCasado)
                .addGap(18, 18, 18)
                .addComponent(jRadioDivorciado)
                .addGap(18, 18, 18)
                .addComponent(jRadioViudo)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(305, 305, 305)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(96, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jRadioSoltero)
                    .addComponent(jRadioCasado)
                    .addComponent(jRadioDivorciado)
                    .addComponent(jRadioViudo))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jLabelResultado.setText("Busque una de las opciones");
        jLabelResultado.setToolTipText("");

        jMenu1.setText("Archivo");

        jMenuItemNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemNuevo.setLabel("Nuevo...");
        jMenuItemNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNuevoActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemNuevo);

        jMenuItemGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemGuardar.setText("Guardar como");
        jMenuItemGuardar.setToolTipText("");
        jMenuItemGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemGuardarActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemGuardar);
        jMenu1.add(jSeparator1);

        jMenuCSV.setText("Exportar a CSV");
        jMenuCSV.setToolTipText("");
        jMenuCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCSVActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuCSV);

        jMenuItemPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemPrint.setLabel("Imprimir");
        jMenuItemPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPrintActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemPrint);

        jMenuItemClose.setLabel("Salir");
        jMenuItemClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCloseActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemClose);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edición");

        jMenuItemEdit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemEdit.setLabel("Editar persona...");
        jMenuItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemEdit);

        jMenuItemDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        jMenuItemDelete.setLabel("Eliminar persona");
        jMenuItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDeleteActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemDelete);
        jMenu2.add(jSeparator2);

        jMenuItemSelectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemSelectAll.setLabel("Seleccionar todo");
        jMenuItemSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSelectAllActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemSelectAll);
        jMenu2.add(jSeparator4);

        jMenuItemCopiar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemCopiar.setLabel("Copiar");
        jMenuItemCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCopiarActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemCopiar);
        jMenu2.add(jSeparator5);

        jMenuBuscar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuBuscar.setText("Buscar persona...");
        jMenuBuscar.setToolTipText("");
        jMenuBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBuscarActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuBuscar);

        jMenuBuscarWiki.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuBuscarWiki.setText("Buscar ciudad en Wikipedia");
        jMenuBuscarWiki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBuscarWikiActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuBuscarWiki);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Herramientas");

        jMenuPersonasConsola.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuPersonasConsola.setText("Mostrar listado de personas por consola");
        jMenuPersonasConsola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPersonasConsolaActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuPersonasConsola);
        jMenu4.add(jSeparator3);

        jMenuItemOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItemOptions.setLabel("Comprobar conexión con BD");
        jMenuItemOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOptionsActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemOptions);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Ayuda");

        jMenuItemAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItemAbout.setText("Acerca de...");
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItemAbout);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelResultado)))
        );

        getAccessibleContext().setAccessibleName("Picoto Organizer v0.7");
        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOptionsActionPerformed
        // TODO add your handling code here:
        jLabel3.setText("Propiedades...");
        VentanaPropiedades estancia = new VentanaPropiedades();
        estancia.setVisible(true);
        jLabel3.setText("");
    }//GEN-LAST:event_jMenuItemOptionsActionPerformed

    private void jMenuItemNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNuevoActionPerformed
        // TODO add your handling code here:
        AsistenteCreacion estancia = new AsistenteCreacion(jTable1);
        estancia.setVisible(true);
    }//GEN-LAST:event_jMenuItemNuevoActionPerformed

    private void jMenuItemGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemGuardarActionPerformed
        // TODO add your handling code here:
        guardarRegistros();
    }//GEN-LAST:event_jMenuItemGuardarActionPerformed

    public Object CogerDatos(JTable table, int row_index, int col_index) {
        return table.getModel().getValueAt(row_index, col_index);
    }

    private void jMenuItemPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPrintActionPerformed
        // TODO add your handling code here:
        imprimirTabla(jTable1, getTitle(), "Cargando", true);
    }//GEN-LAST:event_jMenuItemPrintActionPerformed

    private void jMenuItemCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCloseActionPerformed
        // Salimos del programa
        System.exit(0);
    }//GEN-LAST:event_jMenuItemCloseActionPerformed

    private void jMenuItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDeleteActionPerformed
        // Eliminar registro
        eliminarRegistro();
    }//GEN-LAST:event_jMenuItemDeleteActionPerformed

    private void jMenuItemSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSelectAllActionPerformed
        // Seleccionamos todo
        jTable1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setRowSelectionInterval(0, jTable1.getRowCount() - 1);
    }//GEN-LAST:event_jMenuItemSelectAllActionPerformed

    private void jScrollPane2ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jScrollPane2ComponentShown
        // Mostramos accesibles los RadioButtons de filtro de la vista por Grupos
        jRadioSoltero.setVisible(true);
        jRadioViudo.setVisible(true);
        jRadioCasado.setVisible(true);
        jRadioDivorciado.setVisible(true);
        jLabelResultado.setVisible(true);
    }//GEN-LAST:event_jScrollPane2ComponentShown

    private void jScrollPane1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jScrollPane1ComponentShown
        // Si salimos de la pestaña de Grupos dejarán de estar visibles los RadioButtons
        jRadioSoltero.setVisible(false);
        jRadioViudo.setVisible(false);
        jRadioCasado.setVisible(false);
        jRadioDivorciado.setVisible(false);
        jLabelResultado.setVisible(false);
    }//GEN-LAST:event_jScrollPane1ComponentShown

    private void jMenuItemEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditActionPerformed
        // Editar el registro seleccionado
        editarRegistro();
    }//GEN-LAST:event_jMenuItemEditActionPerformed

    private void jMenuItemCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCopiarActionPerformed
        // Copiará la celda seleccionada
        copiarRegistro();
    }//GEN-LAST:event_jMenuItemCopiarActionPerformed

    private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutActionPerformed
        // Abrirá la ventana Acerca de...
        Acerca estancia = new Acerca();
        estancia.setVisible(true);
    }//GEN-LAST:event_jMenuItemAboutActionPerformed

    private void jMenuBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBuscarActionPerformed
        // Buscará nombre, apellidos y localidad del registro en Google
        buscarRegistro();
    }//GEN-LAST:event_jMenuBuscarActionPerformed

    private void jMenuBuscarWikiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBuscarWikiActionPerformed
        // Buscará la localidad del registro en Wikipedia
        buscarRegistroWiki();
    }//GEN-LAST:event_jMenuBuscarWikiActionPerformed

    private void jRadioSolteroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioSolteroActionPerformed
        // Realizará una consulta en la BD cuyo ID sea 2 "Soltero"
        realizarConsultaGrupos(2);
    }//GEN-LAST:event_jRadioSolteroActionPerformed

    private void jRadioCasadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioCasadoActionPerformed
        // Realizará una consulta en la BD cuyo ID sea 1 "Casado"
        realizarConsultaGrupos(1);
    }//GEN-LAST:event_jRadioCasadoActionPerformed

    private void jRadioDivorciadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioDivorciadoActionPerformed
        // Realizará una consulta en la BD cuyo ID sea 3 "Divorciado"
        realizarConsultaGrupos(3);
    }//GEN-LAST:event_jRadioDivorciadoActionPerformed

    private void jRadioViudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioViudoActionPerformed
        // Realizará una consulta en la BD cuyo ID sea 0 "Viudo"
        realizarConsultaGrupos(0);
    }//GEN-LAST:event_jRadioViudoActionPerformed

    private void jMenuPersonasConsolaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPersonasConsolaActionPerformed
        //Se mostrará la lista de objetos Persona por consola
        if (listaPersonas == null) {
            JOptionPane.showMessageDialog(rootPane, "Se mostrará la lista de Personas por consola");
        } else {
            System.out.println("######## LISTADO DE PERSONAS REGISTRADAS ########");
            for (Persona pos : listaPersonas) {
                System.out.print(pos.toString() + "\n");
            }
        }

    }//GEN-LAST:event_jMenuPersonasConsolaActionPerformed

    private void jMenuCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCSVActionPerformed
        // TODO add your handling code here:
        exportarACSV();

    }//GEN-LAST:event_jMenuCSVActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new VentanaPrincipal().setVisible(true);

            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelResultado;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuBuscar;
    private javax.swing.JMenuItem jMenuBuscarWiki;
    private javax.swing.JMenuItem jMenuCSV;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemClose;
    private javax.swing.JMenuItem jMenuItemCopiar;
    private javax.swing.JMenuItem jMenuItemDelete;
    private javax.swing.JMenuItem jMenuItemEdit;
    private javax.swing.JMenuItem jMenuItemGuardar;
    private javax.swing.JMenuItem jMenuItemNuevo;
    private javax.swing.JMenuItem jMenuItemOptions;
    private javax.swing.JMenuItem jMenuItemPrint;
    private javax.swing.JMenuItem jMenuItemSelectAll;
    private javax.swing.JMenuItem jMenuPersonasConsola;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioCasado;
    private javax.swing.JRadioButton jRadioDivorciado;
    private javax.swing.JRadioButton jRadioSoltero;
    private javax.swing.JRadioButton jRadioViudo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

}
