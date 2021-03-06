/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package picotoorganizer;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo
 */
public class AsistenteEdicion extends javax.swing.JFrame {

    JTable jtable1;
    String CADENA_CONEXION = "jdbc:mysql://localhost:3306/organizer";
    public DefaultTableModel modelo;
    boolean presionadoCheckJubilado = false;
    String nombre, apellidos, dni, fecha_nacimiento, fecha_fallecimiento, genero,
            email, direccion, poblacion, provincia, region, cod_postal, pais, estado_laboral;
    int id_persona, telefono;
    int estado_civil;

    /**
     * Creates new form AsistenteCreacion
     */
    public AsistenteEdicion() {
        initComponents();

    }

    public AsistenteEdicion(JTable jtable1, int id_persona, String nombre, String apellidos, String dni, String fecha_nacimiento, String fecha_fallecimiento, String genero, String email, int telefono, String direccion, String poblacion, String provincia, String region,
            String cod_postal, String pais, int estado_civil, String estado_laboral) {

        this.jtable1 = jtable1;
        initComponents();
        this.id_persona = id_persona;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.fecha_fallecimiento = fecha_fallecimiento;
        this.genero = genero;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.region = region;
        this.cod_postal = cod_postal;
        this.pais = pais;
        this.estado_civil = estado_civil;
        this.estado_laboral = estado_laboral;

        campoRegion.setEnabled(false);
        campoPais.setEnabled(false);
         jCheckJubilado.setSelected(false);
        if(fecha_fallecimiento.equals("")){
             campoFallecimiento.setEnabled(false);
             jCheckFallecido.setSelected(false);
        }else{
            jCheckFallecido.setSelected(true);
            campoFallecimiento.setEnabled(true);
            campoFallecimiento.setText(fecha_fallecimiento);
        }
        //Variables de los datos de la persona a editar:
        jCheckNacional.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    campoRegion.setEnabled(false);
                    campoPais.setEnabled(false);
                    jComboComunidad.setEnabled(true);
                    jComboProvincia.setEnabled(true);

                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    campoRegion.setEnabled(true);
                    campoPais.setEnabled(true);
                    jComboComunidad.setEnabled(false);
                    jComboProvincia.setEnabled(false);
                }

                validate();
                repaint();
            }
        });

        jCheckFallecido.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    campoFallecimiento.setEnabled(true);

                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    campoFallecimiento.setEnabled(false);
                }

                validate();
                repaint();
            }
        });

        jComboProvincia.addItem("??lava");
        jComboProvincia.addItem("Albacete");
        jComboProvincia.addItem("Alicante");
        jComboProvincia.addItem("Almer??a");
        jComboProvincia.addItem("Asturias, Principado de");
        jComboProvincia.addItem("??vila");
        jComboProvincia.addItem("Badajoz");
        jComboProvincia.addItem("Baleares, Islas");
        jComboProvincia.addItem("Burgos");
        jComboProvincia.addItem("C??ceres");
        jComboProvincia.addItem("C??diz");
        jComboProvincia.addItem("Cantabria");
        jComboProvincia.addItem("Castell??n");
        jComboProvincia.addItem("Ceuta, Ciudad Aut. de");
        jComboProvincia.addItem("Ciudad Real");
        jComboProvincia.addItem("C??rdoba");
        jComboProvincia.addItem("Cuenca");
        jComboProvincia.addItem("Gerona");
        jComboProvincia.addItem("Granada");
        jComboProvincia.addItem("Guadalajara");
        jComboProvincia.addItem("Guipuzcoa");
        jComboProvincia.addItem("Huelva");
        jComboProvincia.addItem("Huesca");
        jComboProvincia.addItem("Ja??n");
        jComboProvincia.addItem("La Coru??a");
        jComboProvincia.addItem("Las Palmas");
        jComboProvincia.addItem("Le??n");
        jComboProvincia.addItem("Lugo");
        jComboProvincia.addItem("Madrid, Comunidad de");
        jComboProvincia.addItem("M??laga");
        jComboProvincia.addItem("Melilla, Ciudad Aut. de");
        jComboProvincia.addItem("Murcia, Regi??n de");
        jComboProvincia.addItem("Navarra, C. Foral de");
        jComboProvincia.addItem("Orense");
        jComboProvincia.addItem("Palencia");
        jComboProvincia.addItem("Pontevedra");
        jComboProvincia.addItem("Salamanca");
        jComboProvincia.addItem("Tenerife, Sta. Cruz de");
        jComboProvincia.addItem("Segovia");
        jComboProvincia.addItem("Sevilla");
        jComboProvincia.addItem("Soria");
        jComboProvincia.addItem("Tarragona");
        jComboProvincia.addItem("Teruel");
        jComboProvincia.addItem("Toledo");
        jComboProvincia.addItem("Valencia");
        jComboProvincia.addItem("Valladolid");
        jComboProvincia.addItem("Vizcaya");
        jComboProvincia.addItem("Zaragoza");

        jComboComunidad.addItem("Andaluc??a");
        jComboComunidad.addItem("Arag??n");
        jComboComunidad.addItem("Asturias, Principado de");
        jComboComunidad.addItem("Baleares, Islas");
        jComboComunidad.addItem("Canarias, Islas");
        jComboComunidad.addItem("Cantabria");
        jComboComunidad.addItem("Castilla-La Mancha");
        jComboComunidad.addItem("Castilla y Le??n");
        jComboComunidad.addItem("Catalu??a");
        jComboComunidad.addItem("Ceuta, Ciudad Aut. de");
        jComboComunidad.addItem("Extremadura");
        jComboComunidad.addItem("Galicia");
        jComboComunidad.addItem("Madrid, Comunidad de");
        jComboComunidad.addItem("Melilla, Ciudad. Aut. de");
        jComboComunidad.addItem("Murcia, Regi??n de");
        jComboComunidad.addItem("Navarra, C. Foral de");
        jComboComunidad.addItem("Pa??s Vasco");
        jComboComunidad.addItem("Valenciana, Comunidad");

        jComboGenero.addItem("Hombre");
        jComboGenero.addItem("Mujer");
        jComboGenero.addItem("Otro");

        //Rellenamos los campos de la persona seleccionada
        campoNombre.setText(nombre);
        campoApellidos.setText(apellidos);
        campoDNI.setText(dni);
        campoDireccion.setText(direccion);
        campoPoblacion.setText(poblacion);
        campoPostal.setText(cod_postal);
        campoTelefono.setText(String.valueOf(telefono));
        campoEmail.setText(email);
        jComboGenero.getModel().setSelectedItem(genero);
        campoNacimiento.setText(fecha_nacimiento);
        if (pais.equals("Espa??a")) {
            jComboProvincia.getModel().setSelectedItem(provincia);
            jComboComunidad.getModel().setSelectedItem(region);
            jCheckNacional.setSelected(true);
        } else {
            jCheckNacional.setSelected(false);
            campoRegion.setText(region);
            campoPais.setText(pais);
        }

        switch (estado_civil) {
            case 0: 
                jRadioViudo.setSelected(true);
                
                break;
            case 1:
                jRadioCasado.setSelected(true);
                break;
            case 2:
                jRadioSoltero.setSelected(true);
                break;
            case 3:
               jRadioDivorciado.setSelected(true);
                break;
            default:
                break;
        }
        
        if (estado_laboral.equals("Jubilado/a")){
            jCheckParo.setEnabled(false);
            jCheckJubilado.setSelected(true);
        }else {
            if (estado_laboral.equals("En paro")) {
            jCheckParo.setSelected(true);
        } else {
            jCheckParo.setSelected(false);
        }
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
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        label6 = new java.awt.Label();
        label7 = new java.awt.Label();
        label8 = new java.awt.Label();
        label9 = new java.awt.Label();
        label10 = new java.awt.Label();
        label11 = new java.awt.Label();
        label12 = new java.awt.Label();
        label13 = new java.awt.Label();
        label14 = new java.awt.Label();
        label15 = new java.awt.Label();
        campoNombre = new java.awt.TextField();
        campoApellidos = new java.awt.TextField();
        campoDNI = new java.awt.TextField();
        campoTelefono = new java.awt.TextField();
        campoEmail = new java.awt.TextField();
        campoNacimiento = new java.awt.TextField();
        campoFallecimiento = new java.awt.TextField();
        campoPoblacion = new java.awt.TextField();
        campoDireccion = new java.awt.TextField();
        campoRegion = new java.awt.TextField();
        campoPostal = new java.awt.TextField();
        campoPais = new java.awt.TextField();
        buttonCrear = new java.awt.Button();
        jComboComunidad = new javax.swing.JComboBox<>();
        jCheckNacional = new javax.swing.JCheckBox();
        jComboProvincia = new javax.swing.JComboBox<>();
        label16 = new java.awt.Label();
        label17 = new java.awt.Label();
        jRadioSoltero = new javax.swing.JRadioButton();
        jRadioCasado = new javax.swing.JRadioButton();
        jRadioDivorciado = new javax.swing.JRadioButton();
        jRadioViudo = new javax.swing.JRadioButton();
        jCheckParo = new javax.swing.JCheckBox();
        jCheckFallecido = new javax.swing.JCheckBox();
        jComboGenero = new javax.swing.JComboBox<>();
        jCheckJubilado = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Asistente de creaci??n");
        setLocation(new java.awt.Point(100, 100));
        setResizable(false);

        label1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        label1.setText("Edici??n de persona");

        label2.setText("Nombre:");

        label3.setText("Apellidos:");

        label4.setText("DNI:");

        label5.setText("Telefono:");

        label6.setText("email:");

        label7.setText("Genero:");

        label8.setText("Fecha de nacimiento:");

        label9.setText("Fecha de fallecimiento:");

        label10.setText("Direcci??n:");

        label11.setText("Poblaci??n:");

        label12.setText("Provincia:");

        label13.setText("CP:");

        label14.setText("Regi??n:");

        label15.setText("Pa??s:");

        campoNombre.setName(""); // NOI18N

        buttonCrear.setActionCommand("Editar");
        buttonCrear.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        buttonCrear.setLabel("Editar");
        buttonCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCrearActionPerformed(evt);
            }
        });

        jCheckNacional.setSelected(true);
        jCheckNacional.setText("Nacional");
        jCheckNacional.setToolTipText("");

        label16.setText("Comunidad Aut??noma:");

        label17.setText("Estado civil:");

        buttonGroup1.add(jRadioSoltero);
        jRadioSoltero.setText("Soltero/a");
        jRadioSoltero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioSolteroActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioCasado);
        jRadioCasado.setText("Casado/a");
        jRadioCasado.setToolTipText("");

        buttonGroup1.add(jRadioDivorciado);
        jRadioDivorciado.setText("Divorciado/a");
        jRadioDivorciado.setToolTipText("");

        buttonGroup1.add(jRadioViudo);
        jRadioViudo.setText("Viudo/a");
        jRadioViudo.setToolTipText("");

        jCheckParo.setSelected(true);
        jCheckParo.setText("La persona est?? en paro laboral");

        jCheckFallecido.setText("La persona ha fallecido");

        jCheckJubilado.setSelected(true);
        jCheckJubilado.setText("La persona pas?? a estado de jubilaci??n");
        jCheckJubilado.setToolTipText("");
        jCheckJubilado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckJubiladoActionPerformed(evt);
            }
        });

        jLabel1.setText("Las fechas deben de ser: yyyy-MM-dd");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoDNI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(115, 115, 115))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jComboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(jCheckFallecido))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(label16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(4, 4, 4)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(campoPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoPais, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(label14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2)
                                                .addComponent(campoRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1)
                                                .addComponent(jComboProvincia, 0, 176, Short.MAX_VALUE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jComboComunidad, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCheckNacional)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(label17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioCasado)
                                    .addComponent(jRadioSoltero))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioDivorciado)
                                    .addComponent(jRadioViudo)
                                    .addComponent(jCheckParo, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckJubilado, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(73, 73, 73)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoFallecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCheckNacional)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(campoPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(label14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(campoRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jComboComunidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRadioSoltero)
                                        .addComponent(jRadioDivorciado))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(label17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioCasado)
                            .addComponent(jRadioViudo))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jCheckParo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckFallecido)
                                .addComponent(jComboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckJubilado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoFallecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonCrear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 public void refrescarTabla() {
        try {
            Connection conn = DriverManager.getConnection(CADENA_CONEXION, "root", "");


            String sql = "SELECT * FROM PERSONAS";

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
                ){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                jtable1.setModel(modelo);

                int i = 0;
                while (rs.next()) {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
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
                    int estado_civil = rs.getInt("estado_civil");
                    String estado_laboral = rs.getString("estado_laboral");

                    modelo.addRow(new Object[]{id, nombre, apellidos, dni, fecha_nacimiento, fecha_fallecimiento,
                        genero, email, telefono, direccion, poblacion, provincia, region, cod_postal, pais,
                        estado_civil, estado_laboral});
                    i++;
                }
                if (i < 1) {
                    JOptionPane.showMessageDialog(null, "No Record Found", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (i == 1) {
                    System.out.println(i + " Record Found");
                } else {
                    System.out.println(i + " Records Found");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void buttonCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCrearActionPerformed
        // TODO add your handling code here:

        Date fechaNacic, fechaFallec;
        if (campoNombre.getText().isEmpty() && campoApellidos.getText().isEmpty() && campoDNI.getText().isEmpty() && campoDireccion.getText().isEmpty()
                        && campoTelefono.getText().isEmpty() && campoPostal.getText().isEmpty() && campoNacimiento.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, "No puede dejar en blanco los campos b??sicos (Nombre, Apellidos, DNI, Direccion, Telefono, cod_postal, Fecha_Nacimiento)");
        } else {
            
            if(jCheckNacional.isSelected()){
            if(!jComboComunidad.isEnabled() || !jComboProvincia.isEnabled()){
            
            
            }
            
            else{
            if(jComboProvincia.getSelectedItem().toString().equals("Pontevedra") || jComboProvincia.getSelectedItem().toString().equals("La Coru??a")
                || jComboProvincia.getSelectedItem().toString().equals("Orense") || jComboProvincia.getSelectedItem().toString().equals("Lugo")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Galicia")){
                    
                }else{
                  jComboComunidad.getModel().setSelectedItem("Galicia");
                  JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
              
                }
            }else if (jComboProvincia.getSelectedItem().toString().equals("Asturias, Principado de")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Asturias, Principado de")){
                   
                }else{
                  jComboComunidad.getModel().setSelectedItem("Asturias, Principado de");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
                }
                
                
            }else if(jComboProvincia.getSelectedItem().toString().equals("??vila") || jComboProvincia.getSelectedItem().toString().equals("Le??n") ||
                    jComboProvincia.getSelectedItem().toString().equals("Salamanca") || jComboProvincia.getSelectedItem().toString().equals("Zamora") || 
                    jComboProvincia.getSelectedItem().toString().equals("Valladolid") || jComboProvincia.getSelectedItem().toString().equals("Soria") ||
                    jComboProvincia.getSelectedItem().toString().equals("Palencia") || jComboProvincia.getSelectedItem().toString().equals("Burgos")){
            if(jComboComunidad.getModel().getSelectedItem().toString().equals("Castilla y Le??n")){
                
            }else{
               jComboComunidad.getModel().setSelectedItem("Castilla y Le??n");
            JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
            
            }
                
                
            }else if(jComboProvincia.getSelectedItem().toString().equals("Madrid, Comunidad de")){
                
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Madrid, Comunidad de")){
                    
                }else{
                   jComboComunidad.getModel().setSelectedItem("Madrid, Comunidad de");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
             
                }
               
            }else if(jComboProvincia.getSelectedItem().toString().equals("Cantabria")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Cantabria")){
                    
                }else{
                   jComboComunidad.getModel().setSelectedItem("Cantabria");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
            
                }
                
            }else if (jComboProvincia.getSelectedItem().toString().equals("Vizcaya") || jComboProvincia.getSelectedItem().toString().equals("Guipuzcoa")||
                    jComboProvincia.getSelectedItem().toString().equals("??lava")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Pa??s Vasco")){
                    
                }else{
                   jComboComunidad.getModel().setSelectedItem("Pa??s Vasco");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
             
                }
               
            }else if(jComboProvincia.getSelectedItem().toString().equals("Navarra, C. Foral de")){
                if (jComboComunidad.getModel().getSelectedItem().toString().equals("Navarra, C.Foral de")){
                    
                }else{
                  jComboComunidad.getModel().setSelectedItem("Navarra, C. Foral de");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
              
                }
               
            }else if(jComboProvincia.getSelectedItem().toString().equals("Huesca") || jComboProvincia.getSelectedItem().toString().equals("Zaragoza") ||
                    jComboProvincia.getSelectedItem().toString().equals("Teruel")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Arag??n")){
                    
                }else{
                   jComboComunidad.getModel().setSelectedItem("Arag??n");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
             
                }
               
            }else if(jComboProvincia.getSelectedItem().toString().equals("Cuenca") || jComboProvincia.getSelectedItem().toString().equals("Albacete")||
                    jComboProvincia.getSelectedItem().toString().equals("Guadalajara") || jComboProvincia.getSelectedItem().toString().equals("Toledo") ||
                    jComboProvincia.getSelectedItem().toString().equals("Ciudad Real")){
                if (jComboComunidad.getModel().getSelectedItem().toString().equals("Castilla-La Mancha")){
                    
                }else{
                 jComboComunidad.getModel().setSelectedItem("Castilla-La Mancha");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
              
                }
                
            }else if(jComboProvincia.getSelectedItem().toString().equals("Barcelona")|| jComboProvincia.getSelectedItem().toString().equals("Gerona")||
                    jComboProvincia.getSelectedItem().toString().equals("Tarragona")||jComboProvincia.getSelectedItem().toString().equals("L??rida")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Catalu??a")){
                    
                }else{
                   jComboComunidad.getModel().setSelectedItem("Catalu??a");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
             
                }
                
               
            }else if(jComboProvincia.getSelectedItem().toString().equals("Baleares, Islas")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Baleares, Islas")){
                    
                }else{
                    jComboComunidad.getModel().setSelectedItem("Baleares, Islas");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
             
                }
              
            }else if(jComboProvincia.getSelectedItem().toString().equals("Alicante")||jComboProvincia.getSelectedItem().toString().equals("Valencia")||
                    jComboProvincia.getSelectedItem().toString().equals("Castell??n")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Valenciana, Comunidad")){
                    
                }else{
                 jComboComunidad.getModel().setSelectedItem("Valenciana, Comunidad");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
              
                }
                
            }else if(jComboProvincia.getSelectedItem().toString().equals("Murcia, Regi??n de")){
                if (jComboComunidad.getModel().getSelectedItem().toString().equals("Murcia, Regi??n de")){
                    
                }else{
                  jComboComunidad.getModel().setSelectedItem("Murcia, Regi??n de");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
             
                }
                
            }else if(jComboProvincia.getSelectedItem().toString().equals("Las Palmas") || jComboProvincia.getSelectedItem().toString().equals("Tenerife, Sta. Cruz de")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Canarias, Islas")){
                    
                }else{
                   jComboComunidad.getModel().setSelectedItem("Canarias, Islas");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
             
                }
                
               
            }else if(jComboProvincia.getSelectedItem().toString().equals("Ceuta, Ciudad Aut. de")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Ceuta, Ciudad Aut. de")){
                    
                }else{
                   jComboComunidad.getModel().setSelectedItem("Ceuta, Ciudad Aut. de");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
             
                }
               
            }else if (jComboProvincia.getSelectedItem().toString().equals("Melilla, Ciudad Aut. de")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Melilla, Ciudad Aut. de")){
                    
                }else{
                 jComboComunidad.getModel().setSelectedItem("Melilla, Ciudad Aut. de");
            JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
               
                }
           
            }else if(jComboProvincia.getSelectedItem().toString().equals("C??ceres") || jComboProvincia.getSelectedItem().toString().equals("Badajoz")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Extremadura")){
                    
                }else{
                 jComboComunidad.getModel().setSelectedItem("Extremadura");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
              
                }
                
            }else if(jComboProvincia.getSelectedItem().toString().equals("Almer??a") || jComboProvincia.getSelectedItem().toString().equals("Huelva") ||
                    jComboProvincia.getSelectedItem().toString().equals("C??diz")||jComboProvincia.getSelectedItem().toString().equals("Ja??n")||
                    jComboProvincia.getSelectedItem().toString().equals("C??rdoba")||jComboProvincia.getSelectedItem().toString().equals("Granada")||
                    jComboProvincia.getSelectedItem().toString().equals("Sevilla")||jComboProvincia.getSelectedItem().toString().equals("M??laga")){
                if(jComboComunidad.getModel().getSelectedItem().toString().equals("Andaluc??a")){
                    
                }else{
                  jComboComunidad.getModel().setSelectedItem("Andaluc??a");
                JOptionPane.showMessageDialog(rootPane, "La provincia "+jComboProvincia.getSelectedItem().toString()+" forma parte de "+jComboComunidad.getSelectedItem().toString());
              
                }
                
               
            }else{
                
            }
            }
            }
        try {
            int x = (int) Integer.parseInt(campoTelefono.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (x != (int) x) {
                JOptionPane.showMessageDialog(rootPane, "Telefono no puede ser String");

            }

            try {
                //Variables de prueba para comprobar el parse
                Date nacimiento = sdf.parse(campoNacimiento.getText());
                if (jCheckFallecido.isSelected()) {
                    Date fallecimiento = sdf.parse(campoFallecimiento.getText());
                }
               
                    int estadoCivil;
                    String estadoLaboral;
                    if (jRadioSoltero.isSelected()) {
                        estadoCivil = 2;
                    } else if (jRadioViudo.isSelected()) {
                        estadoCivil = 0;
                    } else if (jRadioCasado.isSelected()) {
                        estadoCivil = 1;
                    } else if (jRadioDivorciado.isSelected()) {
                        estadoCivil = 3;
                    } else {
                        //Sino se pondr?? por defecto Soltero
                        estadoCivil = 2;
                    }

                    if (jCheckJubilado.isSelected()){
                        
                        estadoLaboral = "Jubilado/a";
                        
                    }else {
                       if (jCheckParo.isSelected()) {
                        estadoLaboral = "En paro";
                    } else {
                        estadoLaboral = "Trabajando";
                    }  
                    }
                    
                    try {
                        Connection conn = DriverManager.getConnection(CADENA_CONEXION, "root", "");
                        Statement sta = conn.createStatement();

                        String query;

                        if (jCheckNacional.isSelected() & !jCheckFallecido.isSelected()) {
                            query = "UPDATE personas SET nombre='" + campoNombre.getText() + "',"
                                    + "apellidos='" + campoApellidos.getText() + "',"
                                    + "dni='" + campoDNI.getText() + "',"
                                    + "fecha_nacimiento='" + campoNacimiento.getText() + "',"
                                    + "fecha_fallecimiento=" + "null" + ","
                                    + "genero='" + jComboGenero.getSelectedItem().toString() + "',"
                                    + "email='" + campoEmail.getText() + "',"
                                    + "telefono='" + campoTelefono.getText() + "',"
                                    + "direccion='" + campoDireccion.getText() + "',"
                                    + "poblacion='" + campoPoblacion.getText() + "',"
                                    + "provincia='" + jComboProvincia.getSelectedItem().toString() + "',"
                                    + "region='" + jComboComunidad.getSelectedItem().toString() + "',"
                                    + "cod_postal='" + campoPostal.getText() + "',"
                                    + "pais='" + "Espa??a" + "',"
                                    + "estado_civil='" + estadoCivil + "',"
                                    + "estado_laboral='" + estadoLaboral + "'"
                                    + "WHERE id=" + id_persona;

                        } else if (jCheckNacional.isSelected() & jCheckFallecido.isSelected()) {
                            query = "UPDATE personas SET nombre='" + campoNombre.getText() + "',"
                                    + "apellidos='" + campoApellidos.getText() + "',"
                                    + "dni='" + campoDNI.getText() + "',"
                                    + "fecha_nacimiento='" + campoNacimiento.getText() + "',"
                                    + "fecha_fallecimiento='" + campoFallecimiento.getText() + "',"
                                    + "genero='" + jComboGenero.getSelectedItem().toString() + "',"
                                    + "email='" + campoEmail.getText() + "',"
                                    + "telefono='" + campoTelefono.getText() + "',"
                                    + "direccion='" + campoDireccion.getText() + "',"
                                    + "poblacion='" + campoPoblacion.getText() + "',"
                                    + "provincia='" + jComboProvincia.getSelectedItem().toString() + "',"
                                    + "region='" + jComboComunidad.getSelectedItem().toString() + "',"
                                    + "cod_postal='" + campoPostal.getText() + "',"
                                    + "pais='" + "Espa??a" + "',"
                                    + "estado_civil='" + estadoCivil + "',"
                                    + "estado_laboral='" + estadoLaboral + "'"
                                    + "WHERE id=" + id_persona;

                        } else if (!jCheckNacional.isSelected() & jCheckFallecido.isSelected()) {
                            query = "UPDATE personas SET nombre='" + campoNombre.getText() + "',"
                                    + "apellidos='" + campoApellidos.getText() + "',"
                                    + "dni='" + campoDNI.getText() + "',"
                                    + "fecha_nacimiento='" + campoNacimiento.getText() + "',"
                                    + "fecha_fallecimiento='" + campoFallecimiento.getText() + "',"
                                    + "genero='" + jComboGenero.getSelectedItem().toString() + "',"
                                    + "email='" + campoEmail.getText() + "',"
                                    + "telefono='" + campoTelefono.getText() + "',"
                                    + "direccion='" + campoDireccion.getText() + "',"
                                    + "poblacion='" + campoPoblacion.getText() + "',"
                                    + "provincia='" + jComboProvincia.getSelectedItem().toString() + "',"
                                    + "region='" + campoRegion.getText() + "',"
                                    + "cod_postal='" + campoPostal.getText() + "',"
                                    + "pais='" + campoPais.getText() + "',"
                                    + "estado_civil='" + estadoCivil + "',"
                                    + "estado_laboral='" + estadoLaboral + "'"
                                    + "WHERE id=" + id_persona;

                        } else if (!jCheckNacional.isSelected() & !jCheckFallecido.isSelected()) {
                            query = "UPDATE personas SET nombre='" + campoNombre.getText() + "',"
                                    + "apellidos='" + campoApellidos.getText() + "',"
                                    + "dni='" + campoDNI.getText() + "',"
                                    + "fecha_nacimiento='" + campoNacimiento.getText() + "',"
                                    + "fecha_fallecimiento=" + "null" + ","
                                    + "genero='" + jComboGenero.getSelectedItem().toString() + "',"
                                    + "email='" + campoEmail.getText() + "',"
                                    + "telefono='" + campoTelefono.getText() + "',"
                                    + "direccion='" + campoDireccion.getText() + "',"
                                    + "poblacion='" + campoPoblacion.getText() + "',"
                                    + "provincia='" + "N/D" + "',"
                                    + "region='" + campoRegion.getText() + "',"
                                    + "cod_postal='" + campoPostal.getText() + "',"
                                    + "pais='" + campoPais.getText() + "',"
                                    + "estado_civil='" + estadoCivil + "',"
                                    + "estado_laboral='" + estadoLaboral + "'"
                                    + "WHERE id=" + id_persona;
                        } else {
                            query = "";
                        }

                        int resultado = sta.executeUpdate(query);
                        if (resultado == 0) {
                            JOptionPane.showMessageDialog(rootPane, "Ha habido un error");
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "La edici??n ha sido un ??xito!");
                            refrescarTabla();
                            dispose();
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(rootPane, "Revise el formato de la fecha!");
                    }
                
                //Si fallamos a la hora de meter la fecha
            }catch(ParseException i){
                JOptionPane.showMessageDialog(rootPane, "Formato de fecha incorrecta, debe ser yyyy-MM-dd");
            }
            //Si fallamos a la hora de meter letras en el campo del Telefono
        }catch (NumberFormatException t) {
            JOptionPane.showMessageDialog(rootPane, "Tel??fono no puede ser String!");    
        }

        }
    }//GEN-LAST:event_buttonCrearActionPerformed

    private void jRadioSolteroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioSolteroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioSolteroActionPerformed

    private void jCheckJubiladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckJubiladoActionPerformed
        // TODO add your handling code here:
         if (presionadoCheckJubilado == false){
            jCheckParo.setEnabled(false);
            presionadoCheckJubilado = true;
        }else {
            jCheckParo.setEnabled(true);
            presionadoCheckJubilado = false;
        }
    }//GEN-LAST:event_jCheckJubiladoActionPerformed

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
            java.util.logging.Logger.getLogger(AsistenteEdicion

.class  


.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AsistenteEdicion

.class  


.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AsistenteEdicion

.class  


.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AsistenteEdicion

.class  


.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AsistenteEdicion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button buttonCrear;
    private javax.swing.ButtonGroup buttonGroup1;
    private java.awt.TextField campoApellidos;
    private java.awt.TextField campoDNI;
    private java.awt.TextField campoDireccion;
    private java.awt.TextField campoEmail;
    private java.awt.TextField campoFallecimiento;
    private java.awt.TextField campoNacimiento;
    private java.awt.TextField campoNombre;
    private java.awt.TextField campoPais;
    private java.awt.TextField campoPoblacion;
    private java.awt.TextField campoPostal;
    private java.awt.TextField campoRegion;
    private java.awt.TextField campoTelefono;
    private javax.swing.JCheckBox jCheckFallecido;
    private javax.swing.JCheckBox jCheckJubilado;
    private javax.swing.JCheckBox jCheckNacional;
    private javax.swing.JCheckBox jCheckParo;
    private javax.swing.JComboBox<String> jComboComunidad;
    private javax.swing.JComboBox<String> jComboGenero;
    private javax.swing.JComboBox<String> jComboProvincia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioCasado;
    private javax.swing.JRadioButton jRadioDivorciado;
    private javax.swing.JRadioButton jRadioSoltero;
    private javax.swing.JRadioButton jRadioViudo;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label13;
    private java.awt.Label label14;
    private java.awt.Label label15;
    private java.awt.Label label16;
    private java.awt.Label label17;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    // End of variables declaration//GEN-END:variables
}
