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
            String requete = "SELECT {c.*}, {co.*} FROM Conseiller c "
                    + "JOIN Compte co ON c.id_conseiller = co.conseiller_id "
                    + "WHERE c.id_conseiller = " + conseiller.getIdConseiller()
                    + " AND co.id_compte = " + request.getParameter("compte");

            Map<String, Class> entities = new LinkedHashMap<>();
            entities.put("c", Conseiller.class);
            entities.put("co", Compte.class);

            QueryHelper qh = new QueryHelper();
            List<Object[]> results = qh.executeQuery(requete, entities);

            List<Compte> comptes = new LinkedList<>();

            for (Object[] result : results) {
                Compte compte = (Compte) result[1];
                comptes.add(0, compte);

            }

            if (comptes.isEmpty()) {
                bean.setErr("Aucun compte retrouvé");
            } else {
                bean.setComptes(comptes);
            }
            
            this.buildBeans(request, "comptesclient", bean);
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
