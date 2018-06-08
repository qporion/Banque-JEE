/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author p1712620
 */
@WebServlet(name = "Accueil", urlPatterns = {"/Accueil"})
public class Accueil extends HttpServlet {

    private Statement db;
    
     @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement du driver : " + e.getMessage());
        }
        
        String serveur= "localhost";//localhost dans notre cas
        String dbName = "Banque";//sample
        int port = 1527;
        String derbyURL = "jdbc:derby:" + serveur + ":" + port + "/"+ dbName;
        
        String user = "root";
        String pass = "";
        
        try {
            Connection connexion = DriverManager.getConnection(derbyURL, user, pass);
            this.db = connexion.createStatement();
        } catch(SQLException e) {
            System.out.println("Erreur lors de la connexion à la base : " + e.getMessage());
        }
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Accueil</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Accueil at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
