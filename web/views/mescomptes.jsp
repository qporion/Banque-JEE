<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="Model.Compte"%>
<%@page import="java.util.List"%>
<%@page import="Model.Transactions"%>
<jsp:useBean id="bean" type="Beans.ContentBeans" scope="request"/>
<div id="accordion">
    <%
        for (Map.Entry<Compte, List<Transactions>> entrySet : bean.getTransactions().entrySet()) {

            out.print("<div class=\"card mt-4\">"
                    + "<div class=\"card-header p-0\" id=\"header-compte-"+entrySet.getKey().getIdCompte()+"\">"
                    + "<h5 class=\"mb-0\">"
                    + "<div class=\"row p-3\" data-toggle=\"collapse\" data-target=\"#body-compte-"+entrySet.getKey().getIdCompte()+"\" aria-expanded=\"false\" "
                    + "aria-controls=\"body-compte-"+entrySet.getKey().getIdCompte()+"\">"
                    + "<div class=\"col-sm-6\">Compte n°"+entrySet.getKey().getIdCompte()+"</div>"
                    + "<div class=\"col-sm-6 text-right\">"+entrySet.getKey().getSolde()+" euros</div>"
                    + "</div>"
                    + "</h5>"
                    + "</div>"
                    + ""
                    + "<div id=\"body-compte-"+entrySet.getKey().getIdCompte()+"\" class=\"collapse\" aria-labelledby=\"header-compte-"+entrySet.getKey().getIdCompte()+"\" data-parent=\"#accordion\">"
                    + "<div class=\"card-body\">"
                    + "<table class=\"table table-striped\">"
                    + "<thead>"
                    + "<tr>"
                    + "<th scope=\"col\">Numéro de la transaction</th>"
                    + "<th scope=\"col\">Compte</th>"
                    + "<th scope=\"col\">Montant</th>"
                    + "<th scope=\"col\">Etat</th>"
                    + "</tr>"
                    + "</thead>"
                    + "<tbody>"
            );
            for (Transactions t : entrySet.getValue()) {
                out.print("<tr>"
                        + "<th scope=\"row\">" + t.getIdTransaction() + "</th>"
                        + "<td>" + ((entrySet.getKey().getIdCompte() == t.getComptecreditId()) ? t.getComptedebiteId() : t.getComptecreditId()) + "</td>"
                        + "<td class=\"" + ((entrySet.getKey().getIdCompte() == t.getComptecreditId()) ? "text-success" : "text-danger") + "\">"
                        + ((entrySet.getKey().getIdCompte() == t.getComptecreditId()) ? "" : "-") + t.getMontant() + "</td>"
                        );
                if (t.getEtat().equals("ATT")) {
                    out.print("<td class=\"text-warning\">En attente de validation</td>");
                } else  if (t.getEtat().equals("VAL")) {
                    out.print("<td class=\"text-success\">Validée</td>");
                } else {
                    out.print("<td class=\"text-danger\">Refusée</td>");
                }
                out.print("</tr>");
            }
            out.print("</tbody></table></div></div></div>");
        }
    %>
</div>