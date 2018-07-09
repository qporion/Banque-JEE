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
import Model.Transactions;
import Orm.QueryHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quentin
 */
@WebServlet(name = "MesComptes", urlPatterns = {"/MesComptes"})
public class MesComptes extends AbstractServlet {
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
        ContentBeans bean = new ContentBeans();
        Client client = ((Client) request.getSession().getAttribute("client"));
        
        String requete = "SELECT {c.*}, {cc.*}, {co.*}, {t.*} FROM Client c"
                + " JOIN Compteclient cc ON c.id_client = cc.client_id "
                + "JOIN Compte co ON cc.compte_id = co.id_compte "
                + "JOIN Transactions t ON t.comptecredit_id = co.id_compte OR t.comptedebite_id = co.id_compte "
                + "WHERE c.id_client = " + client.getIdClient()
                ;

        Map <String, Class> entities = new LinkedHashMap<>();
        entities.put("c", Client.class);
        entities.put("cc", Compteclient.class);
        entities.put("co", Compte.class);
        entities.put("t", Transactions.class);
        
        QueryHelper qh = new QueryHelper();
        List<Object[]> results = qh.executeQuery(requete, entities);
        
        Map<Compte, List<Transactions>> transactions = new LinkedHashMap<>();
        for( Object[] result : results) {
            Compte compte = (Compte) result[2];
            Transactions transaction = (Transactions) result[3];
            
            if (!transactions.containsKey(compte)) {
                transactions.put(compte, new ArrayList<>());
            }
            
            transactions.get(compte).add(transaction);
        } 
         
        if( transactions.size() == 0) {
            bean.setErr("Aucune transaction effectuée pour ce compte");
        } else {
            bean.setTransactions(transactions);
        }
        
        this.buildBeans(request, "mescomptes", bean);
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
        this.doGet(request, response);
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
