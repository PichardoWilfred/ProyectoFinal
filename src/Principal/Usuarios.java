/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Usuarios extends javax.swing.JFrame {

   //Conexion para la base de Datos 
    Connection connDBC = null;
    PreparedStatement pst = null;
    ResultSet resultado = null;
    //Objeto  
    Conexionar dbc = new Conexionar();
    
    
    
    //String para recolectar la informacion en usuarios
    String[] usuarioString = new String[5];
    
   
    
    public Usuarios() {
        
        //Aspectos de toodo el Jframe
        initComponents();
//        Image icon = new ImageIcon(getClass().getResource("../Imagenes/Iconos/SunflowerIcon.png")).getImage();
//         setIconImage(icon);
         getRootPane().setBorder(BorderFactory.createLineBorder(Color.gray));
        
        this.setLocationRelativeTo(null);
//        getRootPane().setBorder(BorderFactory.createLineBorder(Color.gray));
//        
          //TM model
          usuarios.setModel(TMusuarios);
          
          //Usuariosllenar
          VaciarUsuarios();
          LlenarUsers();
//          usuarios.setEnabled(false);

    //Deshabilitar edicion en la Jtable
   
    //Fin del constructor     
    }
    
    
     //TableModels
     DefaultTableModel TMusuarios = new DefaultTableModel();
     
    
    
        public void LlenarUsers(){
            VaciarUsuarios();
        try {
            connDBC = dbc.conectar();
            
            TMusuarios.addColumn("ID");
            TMusuarios.addColumn("Nombre");
            TMusuarios.addColumn("Contraseña");
            TMusuarios.addColumn("Telefono");
            TMusuarios.addColumn("ADMIN");
            
            String SQLselect = "SELECT id_usuario, nombre, contraseña, telefono, admin FROM usuarios ORDER BY id_usuario";
            pst = connDBC.prepareStatement(SQLselect);
            resultado = pst.executeQuery();
            while( resultado.next() ){
                String Users[] = new String[5];
                Users[0] = resultado.getString("id_usuario");
                Users[1] = resultado.getString("nombre");
                Users[2] = resultado.getString("contraseña");
                Users[3] = resultado.getString("telefono");
                Users[4] = resultado.getString("admin");
                
                TMusuarios.addRow(Users);
                connDBC.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
    //Eliminar toda la tabla para poder actualizarla
    public void VaciarUsuarios(){
    TMusuarios.setColumnCount(0);
    TMusuarios.setRowCount(0);
    usuarios.revalidate();
    
    }
    

        
        public void EliminarThisUsuarios(){
            
            //Ver si se seleccion algo en primer lugar
           int fila =  usuarios.getSelectedRow();
                            if(fila > -1){
                                
                            int id_us = Integer.parseInt((String) usuarios.getValueAt(fila,0)); 

                            String SQL = "DELETE FROM usuarios WHERE id_usuario = ?";
                         try {
                             connDBC = dbc.conectar();
                             pst = connDBC.prepareStatement(SQL);
                             pst.setInt(1, id_us);
                             pst.executeUpdate();
                             connDBC.close();
                         } catch (SQLException ex) {
                             System.out.println(ex.getMessage());
                         }
                         LlenarUsers();
        //final query
           }else {
           JOptionPane.showMessageDialog(this, "Porfavor seleccione algun usuario");
           }
    }      
        
    //_________________________________________________________________________________________________________________________________________________________________________
    
        //Recopilar los datos del usuario que se quiere modificar
    public void btn_EditarUsuario(){
    int fila = usuarios.getSelectedRow();
    
            if(fila > -1){
                
                
                for(int i = 0; i < usuarioString.length; i++){
                    usuarioString[i] = (String) usuarios.getValueAt(fila,i);
                }
                //Se le asignan los valores a los txt
                txt_name.setText(usuarioString[1]);
                txt_pass.setText(usuarioString[2]);
                txt_tel.setText(usuarioString[3]);
                txt_admin.setText(usuarioString[4]);
} }
    
    
    //Metodo que edita el usuario antes mencionado
    public void Editar_Usuario(String nombre, String contraseña,String telefono, String admin, int id) {
       try {       
                   connDBC = dbc.conectar();
                   String SQL = "UPDATE usuarios SET nombre = ? , contraseña= ?, telefono = ?, admin = ? WHERE id_usuario = ?";   
                   pst = connDBC.prepareStatement(SQL);

                   pst.setString(1, nombre);                        
                   pst.setString(2, contraseña);
                   pst.setString(3, telefono);
                   pst.setString(4, admin);
                   pst.setInt(5, id);

                   pst.executeUpdate();
                   connDBC.close();
                   JOptionPane.showMessageDialog(null, "El Usuario "+ nombre + " ha sido editado");
               } catch (SQLException ex) {
                   Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                       JOptionPane.showMessageDialog(null, "Error al actualizar el Producto:" + ex);

               } }
    
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form
     * Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        usuarios = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_name = new javax.swing.JTextField();
        txt_pass = new javax.swing.JTextField();
        txt_tel = new javax.swing.JTextField();
        txt_admin = new javax.swing.JTextField();
        EliminarUs_pnl = new javax.swing.JPanel();
        EliminarUs = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        usuarios.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        usuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Contraseña", "Telefono", "Admin"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        usuarios.setGridColor(new java.awt.Color(204, 204, 204));
        usuarios.setSelectionBackground(new java.awt.Color(82, 179, 217));
        usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(usuarios);
        if (usuarios.getColumnModel().getColumnCount() > 0) {
            usuarios.getColumnModel().getColumn(2).setResizable(false);
            usuarios.getColumnModel().getColumn(3).setResizable(false);
            usuarios.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel4.setText("Nombre:");

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setText("Contraseña:");

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setText("Teléfono:");

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel7.setText("Admin");

        txt_name.setFont(new java.awt.Font("Yu Gothic UI", 0, 11)); // NOI18N
        txt_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameActionPerformed(evt);
            }
        });

        txt_pass.setFont(new java.awt.Font("Yu Gothic UI", 0, 11)); // NOI18N
        txt_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passActionPerformed(evt);
            }
        });

        txt_tel.setFont(new java.awt.Font("Yu Gothic UI", 0, 11)); // NOI18N
        txt_tel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telActionPerformed(evt);
            }
        });

        txt_admin.setFont(new java.awt.Font("Yu Gothic UI", 0, 11)); // NOI18N
        txt_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_adminActionPerformed(evt);
            }
        });

        EliminarUs_pnl.setBackground(new java.awt.Color(241, 169, 160));
        EliminarUs_pnl.setToolTipText("Eliminar usuario.");
        EliminarUs_pnl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EliminarUs_pnl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EliminarUs_pnlMouseClicked(evt);
            }
        });

        EliminarUs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/Eliminar Usuario.png"))); // NOI18N
        EliminarUs.setToolTipText("Eliminar usuario.");
        EliminarUs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        EliminarUs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EliminarUsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout EliminarUs_pnlLayout = new javax.swing.GroupLayout(EliminarUs_pnl);
        EliminarUs_pnl.setLayout(EliminarUs_pnlLayout);
        EliminarUs_pnlLayout.setHorizontalGroup(
            EliminarUs_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EliminarUs_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EliminarUs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        EliminarUs_pnlLayout.setVerticalGroup(
            EliminarUs_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EliminarUs, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(162, 222, 208));
        jPanel13.setToolTipText("Guardar cambios");
        jPanel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/Guardar.png"))); // NOI18N
        jLabel9.setToolTipText("Guardar cambios");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(0, 44, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EliminarUs_pnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EliminarUs_pnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_tel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_admin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(218, 223, 225));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/Cerrar.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/Minimizar.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setToolTipText("Atrás.");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
this.setVisible(false);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
       
         this.setExtendedState(JFrame.ICONIFIED);
        
    }//GEN-LAST:event_jLabel2MouseClicked

    private void txt_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nameActionPerformed

    private void txt_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passActionPerformed

    private void txt_telActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telActionPerformed

    private void txt_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_adminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_adminActionPerformed

    private void EliminarUsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EliminarUsMouseClicked
      
        int Decision =  JOptionPane.showConfirmDialog(null, "¿Está seguro/a de que quiere eliminar este usuario?", "...",
                JOptionPane.YES_NO_OPTION);
        
        if(Decision == 0){
        EliminarThisUsuarios();
        }else {
        
        }
        
    }//GEN-LAST:event_EliminarUsMouseClicked

    private void EliminarUs_pnlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EliminarUs_pnlMouseClicked
       
        int Decision =  JOptionPane.showConfirmDialog(null, "¿Está seguro/a de que quiere eliminar este producto?", "...",
                JOptionPane.YES_NO_OPTION);
        
        if(Decision == 0){
        EliminarThisUsuarios();
        }else {
        }
        
    }//GEN-LAST:event_EliminarUs_pnlMouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
            
        String nombre = txt_name.getText();
        String contraseña= txt_pass.getText();
        String telefono = txt_tel.getText(); 
        String Admin = txt_admin.getText();
        
        
                if (nombre.equals("") || contraseña.equals("") || telefono .equals("") || Admin.equals("") ){
                    
                    JOptionPane.showMessageDialog(this, "Porfavor, complete todos los campos.");
                } else {
                    
                int id = Integer.parseInt(usuarioString[0]);    
                if(!Admin.equals("MD") && !Admin.equals("NO") && !Admin.equals("SI") ) {
                   JOptionPane.showMessageDialog(this, "Porfavor, elija un valor de administrador válido.");
                }else{
                    
                    Editar_Usuario(nombre, contraseña, telefono, Admin, id);
                    LlenarUsers();
                    
                }
                
                //Primer else
                }
            
    }//GEN-LAST:event_jLabel9MouseClicked

    private void usuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usuariosMouseClicked
      btn_EditarUsuario();
      
    }//GEN-LAST:event_usuariosMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

        this.setVisible(false);
       
        
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
 String nombre = txt_name.getText();
        String contraseña= txt_pass.getText();
        String telefono = txt_tel.getText(); 
        String Admin = txt_admin.getText();
        
        
                if (nombre.equals("") || contraseña.equals("") || telefono .equals("") || Admin.equals("") ){
                    
                    JOptionPane.showMessageDialog(this, "Porfavor, complete todos los campos.");
                } else {
                    
                int id = Integer.parseInt(usuarioString[0]);    
                if(!Admin.equals("MD") && !Admin.equals("NO") && !Admin.equals("SI") ) {
                   JOptionPane.showMessageDialog(this, "Porfavor, elija un valor de administrador válido.");
                }else{
                    
                    Editar_Usuario(nombre, contraseña, telefono, Admin, id);
                    LlenarUsers();
                    
                }
                
                //Primer else
                }        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel13MouseClicked
 int xy, xx;
             private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
                
   
        xx = evt.getX();
        xy = evt.getY();

             }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
   int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);

    }//GEN-LAST:event_formMouseDragged

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EliminarUs;
    private javax.swing.JPanel EliminarUs_pnl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_admin;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_pass;
    private javax.swing.JTextField txt_tel;
    private javax.swing.JTable usuarios;
    // End of variables declaration//GEN-END:variables
}
