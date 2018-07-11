package Controler;

import Beans.ContentBeans;
import Model.Agence;
import Model.Jour;
import Orm.QueryHelper;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "Agences", urlPatterns = {"/Agences"})
public class Agences extends AbstractServlet{
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
        
                String requete = "SELECT {a.*}, {j.*} FROM Agence a "
                + "LEFT JOIN Jour j ON j.agence_id = a.id_agence";

        Map<String, Class> entities = new LinkedHashMap<>();
        entities.put("a", Agence.class);
        entities.put("j", Jour.class);

        
        QueryHelper qh = new QueryHelper();
        List<Object[]> results = qh.executeQuery(requete, entities);

        Map<Agence, List<Jour>> agences = new LinkedHashMap<>();

       for (Object[] result : results) {
            Agence agence = (Agence) result[0];
            Jour jour = (Jour) result[1];

            if (!agences.containsKey(agence)) {
                agences.put(agence, new ArrayList<>());
            }
            
            if(jour != null) {
                agences.get(agence).add(jour);
            }
        }
        
        if (agences.isEmpty()) {
            bean.setErr("Aucune agence trouvée");
        } else {
            bean.setAgences(agences);
        }
        
        this.buildBeans(request, "agences", bean);
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
