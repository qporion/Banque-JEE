/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Beans.ContentBeans;
import Model.Client;
import Model.Compte;
import Model.Compteclient;
import Orm.QueryHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quentin
 */
@WebServlet(name = "LogIn", urlPatterns = {"/LogIn"})
public class LogIn extends AbstractServlet {

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
        this.buildBeans(request, "login", null);                   
        this.getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
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
        
        String encryptedPass = this.database.encryptedMessage(request.getParameter("password"));
        
        String requete = "SELECT {c.*}, {cc.*}, {co.*} FROM Client c"
                + " JOIN Compteclient cc ON c.id_client = cc.client_id "
                + "JOIN Compte co ON cc.compte_id = co.id_compte "
                + "WHERE c.password = '" + encryptedPass + "' "
                + "AND co.id_compte = " + request.getParameter("compte")
                ;

        Map <String, Class> entities = new LinkedHashMap<>();
        entities.put("c", Client.class);
        entities.put("cc", Compteclient.class);
        entities.put("co", Compte.class);
        
        QueryHelper qh = new QueryHelper();
        
        List<Object[]> results = qh.executeQuery(requete, entities);
        
        List<Client> clients = new ArrayList<>();
        for( Object[] result : results) {
            clients.add((Client) result[0]); 
        }
        
        if( clients == null || clients.size() != 1) {
            System.err.println("Identification impossible"); //@TODO
        } else {
            request.getSession().setAttribute("client", clients.get(0));
        }
        
        this.buildBeans(request, "accueil", null);                   
        this.getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
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
