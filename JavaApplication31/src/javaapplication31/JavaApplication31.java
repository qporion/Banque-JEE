/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication31;
import java.sql.*;

/**
 *
 * @author faycal
 */
public class JavaApplication31 {
    public static void backUpDatabase(Connection conn)throws SQLException
{
String backupdirectory ="c:/mybackups/";
CallableStatement cs = conn.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)"); 
cs.setString(1, backupdirectory);
cs.execute(); 
cs.close();
System.out.println("base de données sauvegardée dans "+backupdirectory);
}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Connection connexion=null;
        try {
           Class.forName("org.apache.derby.jdbc.ClientDriver");
       }
       catch(ClassNotFoundException e){
           System.out.println("Erreur lors du chargement du driver"+ e);
       }
       String serveur= "//localhost";
       String dbName = "Banque";//sample
       int port = 1527;
       String derbyURL = "jdbc:derby:" + serveur + ":" + port +"/"+ dbName;
       //String derbyURL = "jdbc:derby:" + serveur + ":" + port +"/"+ dbName + ";restoreFrom=c:\\mybackups\\Banque";
       String user = "app";
       String pass = "app";
       try{
       connexion =DriverManager.getConnection(derbyURL, user, pass);
       }
       catch (SQLException e){ System.out.println("Erreur lors de la connexion à la bdd"+ e);}
       try{
        backUpDatabase(connexion);
       }
       catch (SQLException e){ System.out.println("Erreur lors de la sauvegarde de la base"+ e);} 
    }
}
    

