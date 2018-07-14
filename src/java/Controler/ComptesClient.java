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
import Model.Conseiller;
import Model.Transactions;
import Orm.QueryHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
@WebServlet(name = "ComptesClient", urlPatterns = {"/ComptesClient"})
public class ComptesClient extends AbstractServlet {

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
        Conseiller conseiller = this.getConseiller(request, response);
        if (conseiller != null) {
            String requete = "SELECT {c.*}, {co.*}, {cc.*}, {cl.*} FROM Conseiller c "
                    + "JOIN Compte co ON c.id_conseiller = co.conseiller_id "
                    + "JOIN Compteclient cc ON cc.compte_id = co.id_compte "
                    + "JOIN Client cl ON cl.id_client = cc.client_id "
                    + "WHERE c.id_conseiller = " + conseiller.getIdConseiller();

            Map<String, Class> entities = new LinkedHashMap<>();
            entities.put("c", Conseiller.class);
            entities.put("co", Compte.class);
            entities.put("cc", Compteclient.class);
            entities.put("cl", Client.class);

            QueryHelper qh = new QueryHelper();
            List<Object[]> results = qh.executeQuery(requete, entities);

            Map<Compte, List<Client>> comptes = new LinkedHashMap<>();

            for (Object[] result : results) {
                Compte compte = (Compte) result[1];
                Client client = (Client) result[3];

                if (!comptes.containsKey(compte) && compte != null) {
                    comptes.put(compte, new ArrayList<>());
                }

                comptes.get(compte).add(0, client);
            }

            if (comptes.isEmpty()) {
                bean.setErr("Aucun compte retrouvé");
            } else {
                bean.setComptesClient(comptes);
            }

            this.buildBeans(request, "comptesclient", bean);
            this.getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
        }
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
        ContentBeans bean = new ContentBeans();
        Conseiller conseiller = this.getConseiller(request, response);
        if (conseiller != null) {
            String requete = "SELECT {c.*}, {co.*}, {cc.*}, {cl.*}, {t.*} FROM Conseiller c "
                    + "JOIN Compte co ON c.id_conseiller = co.conseiller_id "
                    + "JOIN Compteclient cc ON cc.compte_id = co.id_compte "
                    + "JOIN Client cl ON cl.id_client = cc.client_id "
                    + "LEFT JOIN Transactions t ON (t.comptecredit_id = co.id_compte OR t.comptedebite_id = co.id_compte) "
                    + "WHERE c.id_conseiller = " + conseiller.getIdConseiller();

            if (!request.getParameter("compte").isEmpty()) {
                requete += " AND co.id_compte = "+request.getParameter("compte");
            }
            
            Map<String, Class> entities = new LinkedHashMap<>();
            entities.put("c", Conseiller.class);
            entities.put("co", Compte.class);
            entities.put("cc", Compteclient.class);
            entities.put("cl", Client.class);
            entities.put("t", Transactions.class);

            QueryHelper qh = new QueryHelper();
            List<Object[]> results = qh.executeQuery(requete, entities);

            Map<Compte, List<Client>> comptes = new LinkedHashMap<>();
            Map<Compte, List<Transactions>> transactions = new LinkedHashMap<>();

            for (Object[] result : results) {
                Compte compte = (Compte) result[1];
                Client client = (Client) result[3];
                Transactions transaction = (Transactions) result[4];

                if (!comptes.containsKey(compte) && compte != null) {
                    comptes.put(compte, new ArrayList<>());
                }

                if (!comptes.get(compte).contains(client)) {
                    comptes.get(compte).add(0, client);
                }
                
                if (!transactions.containsKey(compte) && compte != null) {
                    transactions.put(compte, new ArrayList<>());
                }

                if(transaction != null) {
                    transactions.get(compte).add(0, transaction);
                }
            }

            if (comptes.isEmpty()) {
                bean.setErr("Aucun compte retrouvé");
            } else {
                bean.setComptesClient(comptes);
                bean.setTransactions(transactions);
            }
            
             requete = "SELECT {c.*} FROM Client c ";

            qh = new QueryHelper();
            List<Object> result = qh.executeSingleQuery(requete, "c", Client.class);

            List<Client> clients = new LinkedList<>();

            for (Object client : result) {
                clients.add((Client) client);
            }
            
            bean.setClients(clients);
            
            this.buildBeans(request, "visucompte", bean);
            this.getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
        }
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
