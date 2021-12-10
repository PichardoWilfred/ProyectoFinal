
package Principal;

//import com.sun.awt.AWTUtilities;
//import java.awt.Shape;
//import java.awt.geom.RoundRectangle2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;  
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;

import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Principal extends javax.swing.JFrame {
    //Conexion para la base de Datos 
    Connection connDBC = null;
    PreparedStatement pst = null;
    ResultSet resultado = null;
    //Objeto  
    Conexionar dbc = new Conexionar();
    
//TableModels para los metodos correctos    
    DefaultTableModel TMproductos = new DefaultTableModel();
    DefaultTableModel TMusuarios = new DefaultTableModel();
    
    
 //HasMap para los parametros del reporte   

    
//Objeto de  la clase de Login para el comprobador del Admin
Login adminObj = new Login();
public static String  adminP;


 //Comprobar admin

//  public static String  adminP1;
        String getAdminP1(){
        return this.adminP;
        }       
    
//Array y el metodo con datos que se llenan automaticamente para la ventana de actualizar
    public static String[] updateData = new String[5];
    String[] getUpdate(){
        return this.updateData;
    }
    
//Total que se enviara al Jframe del total_confirmacion
    public static int totalFinal = 0;
    int getTotalFinal(){
    return this.totalFinal;
    }
    
//Para verificar las cantidades adecuadas.    
boolean otroProd;
    
//Esto es el si o no cuando queremos eliminar un producto.
int Decision;
    

     //Hacer este frame no visible
       public void Desaparecer() {
        this.setVisible(false);
        new Login().setVisible(true);
         
        }
                                                  //Arrays para totales y factura
                                                  
                                                        //ID
                                                        public static String[] idfactura;

                                                        String[] getIdfactura (){
                                                        return this.idfactura;
                                                        }

                                                       //Cantidad
                                                        public static String[] cantfactura;

                                                         String[] getCantFactura(){
                                                        return this.cantfactura;
                                                        }

                                                       //filas
                                                        public static int filas = 0;
                                                        int getFilas(){
                                                        return this.filas;
                                                        }

                                                        //Nombres
                                                        public static String[] nombre;
                                                        String[] getNombre(){
                                                        return this.nombre;
                                                        }

                                                        //Importes
                                                        public static String[] importe;
                                                        String[] getimporte(){
                                                        return this.importe;
            }
            
         //Comprobador
           int longitudArr;
           int result;
           ArrayList<Integer> cantidadOg;
          String  cantidadAF[];
          boolean esMasGrande;
          String idProd[];

    //Constructor this is where Magic happens
    public Principal()  {
        initComponents();
        //Estar centrado
        this.setLocationRelativeTo(this);
       getRootPane().setBorder(BorderFactory.createLineBorder(Color.gray));
       this.setResizable(false);
       
        adminP = adminObj.getAdmin();
        
        
        //comprobar si el usuario es de rango admin para ver si...
        switch (adminP) {
            case "NO":
               //Esto quita practicamente todas las funciones a excepcion de las de Facturar...
                btn_EditarProducto.setVisible(false);
                jPanel5.setVisible(false);
                
                btn_EliminarProducto3.setVisible(false);
                 jPanel4.setVisible(false);
                
                btn_Añadir.setVisible(false);
                jPanel8.setVisible(false);
                
                break;
            case "MD":
                //Este casi no da permisos...
                btn_EliminarProducto3.setVisible(false);
                 jPanel4.setVisible(false);
                break;
                
            default:
                break;
        } 
       
        //Aqui Asignamos el DefaultTableModel de la tabla productos
        productos.setModel(TMproductos);
        //Las contruimos y las llenamos
        LlenarTabla();
        otroProd = false;
    }

    
    //Buscar cantidades de los productos osea 
     public void BuscarCantidades(int id){
            try {           connDBC = dbc.conectar();
                            String SQLselect = "SELECT cantidad FROM productos WHERE id = ?";
                            pst = connDBC.prepareStatement(SQLselect);
                            pst.setInt(1,id);
                            resultado = pst.executeQuery();
                            
                                    while( resultado.next() ){
                                    result = resultado.getInt("cantidad");
                                    cantidadOg.add(result);        
 }
                                connDBC.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                        }
     }
     
    //Restricción para los JOP Input 
       boolean HayLetras(String evt) {
            boolean validar = false;
            String expression = "([a-zA-Z]+)|(['()+,\\-.=]+)";
            if(evt.matches(expression)){
               validar = true;
           }
            return validar;
       }
    
   //Esto es muy obvio y se usa en to lo lao.
   public void Advertencia(){
    JOptionPane.showMessageDialog(this, "Porfavor, elija un producto");
   }
    
   
   //Metodo que actualizar la tabla de productos
    public void ActualizarProductos() {
        TMproductos.setColumnCount(0);
        TMproductos.setRowCount(0);
        productos.revalidate();
    }
    
   public void ActualizarUsuarios() {
        TMusuarios.setColumnCount(0);
        TMproductos.setRowCount(0);
        productos.revalidate();
    }
    
    //Metodo que elimina el producto Seleccionado
    public void EliminarProducto(int id){
    
        String SQL = "DELETE FROM productos WHERE id = ?";
        try {
            connDBC = dbc.conectar();
            pst = connDBC.prepareStatement(SQL);
            pst.setInt(1, id);
            pst.executeUpdate();
            
            connDBC.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //Este es el metodo que se encarga de llenar la tabla desde
    public void LlenarTabla() {
        ActualizarProductos();
        TMproductos.addColumn("ID");
        TMproductos.addColumn("Nombre");
        TMproductos.addColumn("Marca");
        TMproductos.addColumn("Cantidad");        
        TMproductos.addColumn("Precio");
      
        try {
             connDBC = dbc.conectar();
            String SQLselect = "SELECT id, nombre, descripcion, cantidad, precio FROM public.productos ORDER BY id";
            pst = connDBC.prepareStatement(SQLselect);
            
            resultado = pst.executeQuery();
            
            while( resultado.next() ){
                String productos[] = new String[5];
                productos[0] = resultado.getString("id");
                productos[1] = resultado.getString("nombre");
                productos[2] = resultado.getString("descripcion");
                productos[3] = resultado.getString("cantidad");
                productos[4] = resultado.getString("precio");

                    TMproductos.addRow(productos);
                    
                   connDBC.close();
            }
             
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
//Reasignando los tamaños de las tablas y esas cosas
      //THE MAXIMUN SIZE IN TOTAL HAS TO BE    324   4:53 19/05/2019
productos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
productos.getColumnModel().getColumn(0).setPreferredWidth(27);
productos.getColumnModel().getColumn(1).setPreferredWidth(85);
productos.getColumnModel().getColumn(2).setPreferredWidth(210);
productos.getColumnModel().getColumn(4).setPreferredWidth(81);


    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popUp_1 = new javax.swing.JPopupMenu();
        popUp_2 = new javax.swing.JPopupMenu();
        btn_quitar = new javax.swing.JMenuItem();
        btn_Vaciar = new javax.swing.JMenuItem();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        btn_EliminarProducto1 = new javax.swing.JButton();
        btn_EliminarProducto2 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Label_productosReporte = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btn_EliminarProducto3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btn_EditarProducto = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btn_AñadirFac = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        btn_Añadir = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        btn_Actualizar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        Minimizar = new javax.swing.JButton();

        popUp_1.setBackground(new java.awt.Color(153, 153, 153));

        btn_quitar.setText("Remover producto");
        btn_quitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quitarActionPerformed(evt);
            }
        });
        popUp_2.add(btn_quitar);

        btn_Vaciar.setText("Vaciar tabla");
        btn_Vaciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_VaciarActionPerformed(evt);
            }
        });
        popUp_2.add(btn_Vaciar);

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        btn_EliminarProducto1.setBorder(null);
        btn_EliminarProducto1.setContentAreaFilled(false);
        btn_EliminarProducto1.setFocusPainted(false);
        btn_EliminarProducto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarProducto1ActionPerformed(evt);
            }
        });

        btn_EliminarProducto2.setBorder(null);
        btn_EliminarProducto2.setContentAreaFilled(false);
        btn_EliminarProducto2.setFocusPainted(false);
        btn_EliminarProducto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarProducto2ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        setResizable(false);
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

        jPanel13.setBackground(new java.awt.Color(218, 223, 225));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel1.setToolTipText("");

        Label_productosReporte.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        Label_productosReporte.setForeground(new java.awt.Color(102, 102, 102));
        Label_productosReporte.setText("Inventario");
        Label_productosReporte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Label_productosReporteMouseClicked(evt);
            }
        });
        Label_productosReporte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Label_productosReporteKeyTyped(evt);
            }
        });

        productos.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Marca", "Cantidad", "Precio"
            }
        ));
        productos.setGridColor(new java.awt.Color(255, 255, 255));
        productos.setSelectionBackground(new java.awt.Color(153, 153, 153));
        jScrollPane1.setViewportView(productos);

        jPanel4.setBackground(new java.awt.Color(241, 169, 160));

        btn_EliminarProducto3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/EliminarProducto.png"))); // NOI18N
        btn_EliminarProducto3.setToolTipText("Eliminar producto");
        btn_EliminarProducto3.setBorder(null);
        btn_EliminarProducto3.setContentAreaFilled(false);
        btn_EliminarProducto3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_EliminarProducto3.setFocusPainted(false);
        btn_EliminarProducto3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EliminarProducto3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_EliminarProducto3, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_EliminarProducto3, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(254, 241, 96));

        btn_EditarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/Editar.png"))); // NOI18N
        btn_EditarProducto.setToolTipText("Editar producto");
        btn_EditarProducto.setBorder(null);
        btn_EditarProducto.setContentAreaFilled(false);
        btn_EditarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_EditarProducto.setFocusPainted(false);
        btn_EditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_EditarProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_EditarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(123, 239, 178));

        btn_AñadirFac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/FacturaAdd.png"))); // NOI18N
        btn_AñadirFac.setToolTipText("Facturar producto");
        btn_AñadirFac.setBorder(null);
        btn_AñadirFac.setContentAreaFilled(false);
        btn_AñadirFac.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_AñadirFac.setFocusPainted(false);
        btn_AñadirFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AñadirFacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_AñadirFac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_AñadirFac, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(102, 204, 153));

        btn_Añadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/NuevoProducto.png"))); // NOI18N
        btn_Añadir.setToolTipText("Añadir producto");
        btn_Añadir.setBorder(null);
        btn_Añadir.setContentAreaFilled(false);
        btn_Añadir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_Añadir.setFocusPainted(false);
        btn_Añadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AñadirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_Añadir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btn_Añadir))
        );

        jPanel9.setBackground(new java.awt.Color(255, 236, 139));

        btn_Actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/Actualizar.png"))); // NOI18N
        btn_Actualizar.setToolTipText("Actualizar tabla");
        btn_Actualizar.setBorder(null);
        btn_Actualizar.setBorderPainted(false);
        btn_Actualizar.setContentAreaFilled(false);
        btn_Actualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_Actualizar.setFocusPainted(false);
        btn_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btn_Actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(btn_Actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/Logout.png"))); // NOI18N
        jLabel2.setToolTipText("Cerrar sesión");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setText("Ver usuarios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Label_productosReporte)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(383, 383, 383)
                                .addComponent(jButton1)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Label_productosReporte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(315, 315, 315)
                                        .addComponent(jLabel1)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(63, 63, 63)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/Cerrar.png"))); // NOI18N
        Salir.setToolTipText("Salir");
        Salir.setBorder(null);
        Salir.setBorderPainted(false);
        Salir.setContentAreaFilled(false);
        Salir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Salir.setFocusPainted(false);
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        Minimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconos/Minimizar.png"))); // NOI18N
        Minimizar.setToolTipText("Minimizar");
        Minimizar.setBorder(null);
        Minimizar.setBorderPainted(false);
        Minimizar.setContentAreaFilled(false);
        Minimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Minimizar.setFocusPainted(false);
        Minimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MinimizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(0, 524, Short.MAX_VALUE)
                .addComponent(Minimizar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 626, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Minimizar)
                    .addComponent(Salir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    //allow movement
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

    
    private void btn_VaciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_VaciarActionPerformed
       
    }//GEN-LAST:event_btn_VaciarActionPerformed

    //Quitar producto de la factura
    private void btn_quitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quitarActionPerformed

   

    }//GEN-LAST:event_btn_quitarActionPerformed

    private void btn_EliminarProducto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarProducto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_EliminarProducto1ActionPerformed

    private void btn_EliminarProducto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarProducto2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_EliminarProducto2ActionPerformed

    private void MinimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MinimizarActionPerformed
        this.setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_MinimizarActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_SalirActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        adminP = null;
        Desaparecer();

    }//GEN-LAST:event_jLabel2MouseClicked

    private void btn_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ActualizarActionPerformed
        LlenarTabla();
    }//GEN-LAST:event_btn_ActualizarActionPerformed

    private void btn_AñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AñadirActionPerformed
        try {
            new Añadir_Producto().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_AñadirActionPerformed

    private void btn_AñadirFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AñadirFacActionPerformed

    }//GEN-LAST:event_btn_AñadirFacActionPerformed

    private void btn_EditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditarProductoActionPerformed
        if (productos.getSelectedRow() >= 0){
            for(int i = 0; i < 5; i++ ){
                updateData[i] = (String) productos.getValueAt(productos.getSelectedRow(), i );
            }
            new Actualizar_Producto().setVisible(true);
        } else {
            Advertencia();
        }
    }//GEN-LAST:event_btn_EditarProductoActionPerformed

    private void btn_EliminarProducto3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EliminarProducto3ActionPerformed
        if (productos.getSelectedRow() >= 0) {
            Decision =  JOptionPane.showConfirmDialog(null, "¿Está seguro/a de que quiere eliminar este producto?", "...",
                JOptionPane.YES_NO_OPTION);
            if(Decision == 0){
                updateData[0] = (String) productos.getValueAt(productos.getSelectedRow(), 0);
                EliminarProducto( Integer.parseInt(updateData[0]) );
                JOptionPane.showMessageDialog(null, "Producto eliminado");
            }
        }else if(Decision == 1) {

        } else {
            Advertencia();
        }

        LlenarTabla();
    }//GEN-LAST:event_btn_EliminarProducto3ActionPerformed

    //Java pls
    private void Label_productosReporteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Label_productosReporteKeyTyped

    }//GEN-LAST:event_Label_productosReporteKeyTyped

    //Metodo para que me cree un reporte de los Usuarios
    private void Label_productosReporteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Label_productosReporteMouseClicked

    }//GEN-LAST:event_Label_productosReporteMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Usuarios().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
    String text;
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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Label_productosReporte;
    private javax.swing.JButton Minimizar;
    private javax.swing.JButton Salir;
    private javax.swing.JButton btn_Actualizar;
    private javax.swing.JButton btn_Añadir;
    private javax.swing.JButton btn_AñadirFac;
    private javax.swing.JButton btn_EditarProducto;
    private javax.swing.JButton btn_EliminarProducto1;
    private javax.swing.JButton btn_EliminarProducto2;
    private javax.swing.JButton btn_EliminarProducto3;
    private javax.swing.JMenuItem btn_Vaciar;
    private javax.swing.JMenuItem btn_quitar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu popUp_1;
    private javax.swing.JPopupMenu popUp_2;
    private javax.swing.JTable productos;
    // End of variables declaration//GEN-END:variables
}
