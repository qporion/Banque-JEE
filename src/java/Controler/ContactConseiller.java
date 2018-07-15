/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Beans.ContentBeans;
import Model.Agence;
import Model.Client;
import Model.Compte;
import Model.Compteclient;
import Model.Conseiller;
import Model.Message;
import Model.Transactions;
import Orm.QueryHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author Mapotofu
 */
@WebServlet(name = "ContactConseiller", urlPatterns = {"/ContactConseiller"})
public class ContactConseiller extends AbstractServlet {
    
     private void buildPage(ContentBeans bean, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Conseiller conseiller = this.getConseiller(request, response);

        if (conseiller != null) {
            String requete = "SELECT {c.*}, {com.*}, {cc.*}, {cl.*}, {a.*}, {m.*} "
                            +"FROM Conseiller c "
                            +"JOIN Compte com ON com.CONSEILLER_ID = c.id_conseiller "
                            +"JOIN Compteclient cc ON com.id_compte = cc.compte_id "
                            +"JOIN Client cl ON cl.id_client = cc.client_id "
                            +"JOIN Agence a ON a.id_agence = c.agence_id "
                            +"LEFT JOIN Message m ON (c.id_conseiller = m.conseiller_id AND cl.id_client = m.client_id) "
                            +"WHERE c.id_conseiller = " + conseiller.getIdConseiller();

            Map<String, Class> entities = new LinkedHashMap<>();
            entities.put("c", Conseiller.class);
            entities.put("com", Compte.class);
            entities.put("cc", Compteclient.class);
            entities.put("cl", Client.class);
            entities.put("a", Agence.class);
            entities.put("m", Message.class);

            QueryHelper qh = new QueryHelper();

            List<Object[]> results = qh.executeQuery(requete, entities);

            Map<Client, Agence> clients = new LinkedHashMap<>();
            Map<Client, List<Message>> messages = new LinkedHashMap<>();
            for (Object[] result : results) {
                Client client = (Client) result[3];
                Agence agence = (Agence) result[4];
                Message message = (Message) result[5];

                if (!clients.containsKey(client) && client != null) {
                    clients.put(client, agence);
                }
                
                if (!messages.containsKey(client) && messages != null) {
                    messages.put(client, new LinkedList<>());
                }
                
                if (!messages.get(client).contains(message) && message != null) {
                    messages.get(client).add(message);
                }
            }

            if (clients.size() < 1) {
                bean.setErr("Auncun Client n'a été trouvé pour vos comptes.");
            }
            bean.setClientsAgence(clients);
            bean.setMessagesConseiller(messages);

            this.buildBeans(request, "contactConseiller", bean);
            this.getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
        }
    }
     
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
        this.buildPage(bean, request, response);
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
            if (request.getParameter("contenu") == null
                    || request.getParameter("contenu").length() == 0) {
                bean.setErr("Un contenu vide ne peut pas être envoyé.");
            } else {
                String idClient = request.getParameter("client");

                String requete = "insert into message(client_id, conseiller_id, contenu) values ("
                        + Integer.parseInt(idClient) + ", "
                        + conseiller.getIdConseiller() + ", "
                        + "'" + request.getParameter("contenu") + "')";

                QueryHelper qh = new QueryHelper();

                if (qh.updateOneObject(requete)) {
                    bean.setVal("Votre message a bien été envoyé.");
                } else {
                    bean.setErr("Votre message a échoué, veuillez réessayer plus tard.");
                }
            }
            this.buildPage(bean, request, response);
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
