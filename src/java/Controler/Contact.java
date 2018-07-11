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
 * @author Quentin
 */
@WebServlet(name = "Contact", urlPatterns = {"/Contact"})
public class Contact extends AbstractServlet {

    private void buildPage(ContentBeans bean, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Client client = this.getClient(request, response);

        if (client != null) {
            String requete = "SELECT {c.*}, {cc.*}, {com.*}, {con.*}, {a.*}, {m.*} FROM Client c"
                    + " JOIN Compteclient cc ON c.id_client = cc.client_id "
                    + "JOIN Compte com ON cc.compte_id = com.id_compte "
                    + "JOIN Conseiller con ON con.id_conseiller = com.conseiller_id "
                    + "JOIN Agence a ON a.id_agence = con.agence_id "
                    + "JOIN Message m ON (con.id_conseiller = m.conseiller_id AND c.id_client = m.client_id) "
                    + "WHERE c.id_client = " + client.getIdClient();

            Map<String, Class> entities = new LinkedHashMap<>();
            entities.put("c", Client.class);
            entities.put("cc", Compteclient.class);
            entities.put("com", Compte.class);
            entities.put("con", Conseiller.class);
            entities.put("a", Agence.class);
            entities.put("m", Message.class);

            QueryHelper qh = new QueryHelper();

            List<Object[]> results = qh.executeQuery(requete, entities);

            Map<Conseiller, Agence> conseillers = new LinkedHashMap<>();
            Map<Conseiller, List<Message>> messages = new LinkedHashMap<>();
            for (Object[] result : results) {
                Conseiller conseiller = (Conseiller) result[3];
                Agence agence = (Agence) result[4];
                Message message = (Message) result[5];

                if (!conseillers.containsKey(conseiller) && conseiller != null) {
                    conseillers.put(conseiller, agence);
                }
                
                if (!messages.containsKey(conseiller) && messages != null) {
                    messages.put(conseiller, new LinkedList<>());
                }
                
                if (!messages.get(conseiller).contains(message)) {
                    messages.get(conseiller).add(message);
                }
            }

            if (conseillers.size() < 1) {
                bean.setErr("Auncun conseiller n'a été trouvé pour vos comptes.");
            }
            bean.setConseillers(conseillers);
            bean.setMessages(messages);

            this.buildBeans(request, "contact", bean);
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
        Client client = this.getClient(request, response);

        if (client != null) {
            if (request.getParameter("contenu") == null
                    || request.getParameter("contenu").length() == 0) {
                bean.setErr("Un contenu vide ne peut pas être envoyé.");
            } else {
                String idConseiller = request.getParameter("conseiller");

                String requete = "insert into message(client_id, conseiller_id, contenu) values ("
                        + client.getIdClient() + ", "
                        + Integer.parseInt(idConseiller) + ", "
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
