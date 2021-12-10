
package Principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexionar {
    Connection Conn = null;
    String url = "jdbc:postgresql://localhost:5432/Sunflower";
    String usr = "postgres";
    String pass = "taller";
    
    public Connection conectar() throws SQLException {
           
        try {
             Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
             Logger.getLogger(Conexionar.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        try {
            
             Conn = DriverManager.getConnection(url, usr, pass);
      
        } catch (SQLException ex) {
             Logger.getLogger(Conexionar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Conn;
    }
    
    public static void main(String[] args) throws SQLException {
        Conexionar connDatabase = new Conexionar();
       connDatabase.conectar();
    }
}
    