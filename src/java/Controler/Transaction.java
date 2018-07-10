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
@WebServlet(name = "Transaction", urlPatterns = {"/Transaction"})
public class Transaction extends AbstractServlet {

    private void buildPage(ContentBeans bean, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Client client = ((Client) request.getSession().getAttribute("client"));

        String requete = "SELECT {c.*}, {cc.*}, {co.*} FROM Client c "
                + "JOIN Compteclient cc ON c.id_client = cc.client_id "
                + "JOIN Compte co ON cc.compte_id = co.id_compte "
                + "WHERE c.id_client = " + client.getIdClient();

        Map<String, Class> entities = new LinkedHashMap<>();
        entities.put("c", Client.class);
        entities.put("cc", Compteclient.class);
        entities.put("co", Compte.class);

        QueryHelper qh = new QueryHelper();
        List<Object[]> results = qh.executeQuery(requete, entities);

        Map<Compte, List<Transactions>> transactions = new LinkedHashMap<>();
        for (Object[] result : results) {
            Compte compte = (Compte) result[2];

            if (!transactions.containsKey(compte)) {
                transactions.put(compte, new ArrayList<>());
            }
        }

        requete = "SELECT {c.*} FROM Compte c ";

        qh = new QueryHelper();
        List<Object> result = qh.executeSingleQuery(requete, "c", Compte.class);

        List<Compte> comptes = new LinkedList<>();

        for (Object compte : result) {
            comptes.add((Compte) compte);
        }

        if (transactions.size() == 0) {
            bean.setErr("Aucun compte trouvé");
        } else {
            bean.setTransactions(transactions);
            bean.setComptes(comptes);
        }

        this.buildBeans(request, "transaction", bean);
        this.getServletContext().getRequestDispatcher("/views/index.jsp").forward(request, response);
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

        if (request.getParameter("credite") == null) {
            bean.setErr("Le compte crédité doit être sélectionné.");
        }
        if (request.getParameter("debite") == null) {
            bean.setErr("Le compte débité doit être sélectionné.");
        }
        if (request.getParameter("montant") == null || Double.parseDouble(request.getParameter("montant")) <= 0) {
            bean.setErr("Le compte montant doit être sélectionné et supérieur à 0.");
        }

        if (request.getParameter("credite").equals(request.getParameter("debite"))) {
            bean.setErr("Le compte débité et le compte crédité doivent être différents.");
        }

        if (bean.getErr().length() == 0) {
            String requete = "SELECT {c.*} FROM Compte c "
                    + "WHERE c.id_compte = " + Integer.parseInt(request.getParameter("debite"));

            QueryHelper qh = new QueryHelper();
            List<Object> result = qh.executeSingleQuery(requete, "c", Compte.class);

            Compte compteDebite = (Compte) result.get(0);

            requete = "SELECT {c.*} FROM Compte c "
                    + "WHERE c.id_compte = " + Integer.parseInt(request.getParameter("credite"));

            qh = new QueryHelper();
            result = qh.executeSingleQuery(requete, "c", Compte.class);

            Compte compteCredite = (Compte) result.get(0);

            if ((compteDebite.getSolde() + compteDebite.getDecouvertautorise()) < Double.parseDouble(request.getParameter("montant"))) {
                bean.setErr("Transaction refusée, le solde du compte débité n'est pas assez élevé.");
                requete = "insert into transactions(comptecredit_id, comptedebite_id, montant, etat) values ("
                        + Integer.parseInt(request.getParameter("credite")) + ", "
                        + Integer.parseInt(request.getParameter("debite")) + ", "
                        + request.getParameter("montant") + ", "
                        + "'REF'" + ")";

                qh = new QueryHelper();
                qh.updateOneObject(requete);
            } else {
                requete = "update compte set solde = "
                        + (compteCredite.getSolde() + Double.parseDouble(request.getParameter("montant"))) + " "
                        + "WHERE id_compte = " + compteCredite.getIdCompte();

                qh = new QueryHelper();
                if (!qh.updateOneObject(requete)) {
                    bean.setErr("Erreur de créditage.");
                } else {
                    requete = "update compte set solde = "
                            + (compteDebite.getSolde() - Double.parseDouble(request.getParameter("montant"))) + " "
                            + "WHERE id_compte = " + compteDebite.getIdCompte();

                    qh = new QueryHelper();
                    if (!qh.updateOneObject(requete)) {
                        bean.setErr("Erreur de débitage.");
                    } else {
                        requete = "insert into transactions(comptecredit_id, comptedebite_id, montant, etat) values ("
                                + Integer.parseInt(request.getParameter("credite")) + ", "
                                + Integer.parseInt(request.getParameter("debite")) + ", "
                                + request.getParameter("montant") + ", "
                                + "'ATT'" + ")";

                        qh = new QueryHelper();
                        if (qh.updateOneObject(requete)) {
                            bean.setVal("Votre transaction a été effectuée.");
                        } else {
                            bean.setErr("Votre transaction a échoué, veuillez réessayer plus tard.");
                        }
                    }
                }
            }
        }

        this.buildPage(bean, request, response);
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
