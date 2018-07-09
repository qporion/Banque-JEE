package Orm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Quentin
 */
public class DatabaseConnection {

    private Statement statement;
     
    private DatabaseConnection()
    {
        /*try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement du driver : " + e.getMessage());
        }
        
        String serveur= "//localhost";//localhost dans notre cas
        String dbName = "Banque";//sample
        int port = 1527;
        String derbyURL = "jdbc:derby:" + serveur + ":" + port + "/"+ dbName;
        
        String user = "root";
        String pass = "root";
        try {
            Connection connexion = DriverManager.getConnection(derbyURL, user, pass);
            this.statement = connexion.createStatement();
        } catch(SQLException e) {
            System.out.println("Erreur lors de la connexion à la base : " + e.getMessage());
        }*/
    }
 
    private static DatabaseConnection INSTANCE = new DatabaseConnection();
     
    public static DatabaseConnection getInstance()
    {   
        return INSTANCE;
    }

    public Statement getStatement() {
        return statement;
    }
    
    public String encryptedMessage(String pass) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] encodedhash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(encodedhash);
    }
	
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
