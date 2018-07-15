<%@page import="Model.Client"%>
<%@page import="Model.Compte"%>
<%@page import="Model.Transactions"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<jsp:useBean id="bean" type="Beans.ContentBeans" scope="request"/>
<%
    if (bean.getTransactions() == null) {
        out.print("<h3>Création de compte</h3>");
        String select = "<select data-idx=\"__INDEX__\" class=\"col-sm-11 not-same-value custom-select\" name=\"clients[__INDEX__]\">";

        for (Client client : bean.getClients()) {
            select += "<option value=\"" + client.getIdClient() + "\">" + client.getNom() + " " + client.getPrenom() + "</option>";
        }
        select += "</select>";
        select += "<div class=\"col-sm-1\" ><div data-idx-remove=\"__INDEX__\" class=\"mx-auto mt-2 bubble-sm bg-dark text-danger\"/><i class=\"mdi mdi-minus\"></i></div></div>";

        out.print("<form method=\"POST\">"
                + "<div class=\"collapse\" id=\"protoype-select\" data-target=\"#clients\" >" + select + "</div>"
                + "<div class=\"form-row\"><label>Solde</label><input type=\"number\" class=\"form-control\" name=\"solde\" value=\"\"/></div>"
                + "<div class=\"form-row\"><label>Découvert autorisé</label><input type=\"number\" class=\"form-control\" name=\"decouvert\" value=\"\"/></div>"
                + "<div class=\"form-row\"><label>Clients</label><div id=\"clients\" class=\"col-sm-12 row\">"
                + "</div>"
                + "<div data-prototype=\"#protoype-select\" class=\"mx-auto mt-2 bubble-sm bg-dark text-success\"/><i class=\"mdi mdi-plus\"></i></div></div>"
                + "<input type=\"submit\" class=\"btn btn-success\"/>"
                + "<input type=\"submit\" name=\"supprimer\" class=\"btn btn-danger\" value=\"Supprimer\"/>"
                + "</form>");
    } else {

        for (Map.Entry<Compte, List<Transactions>> compte : bean.getTransactions().entrySet()) {
            out.print("<h3>Compte n°" + compte.getKey().getIdCompte() + "</h3>");
            String select = "<select data-idx=\"__INDEX__\" class=\"col-sm-11 not-same-value custom-select\" name=\"clients[__INDEX__]\">";

            for (Client client : bean.getClients()) {
                select += "<option value=\"" + client.getIdClient() + "\">" + client.getNom() + " " + client.getPrenom() + "</option>";
            }
            select += "</select>";
            select += "<div class=\"col-sm-1\" ><div data-idx-remove=\"__INDEX__\" class=\"mx-auto mt-2 bubble-sm bg-dark text-danger\"/><i class=\"mdi mdi-minus\"></i></div></div>";

            List<Client> clients = null;
            for (Map.Entry<Compte, List<Client>> compteClient : bean.getComptesClient().entrySet()) {
                if (compteClient.getKey().getIdCompte() == compte.getKey().getIdCompte()) {
                    clients = compteClient.getValue();
                    break;
                }
            }

            out.print("<form method=\"POST\">"
                    + "<input type=\"hidden\" name=\"compte\" value=\"" + compte.getKey().getIdCompte() + "\"/>"
                    + "<div class=\"collapse\" id=\"protoype-select\" data-target=\"#clients\" >" + select + "</div>"
                    + "<div class=\"form-row\"><label>Solde</label><input type=\"number\" class=\"form-control\" name=\"solde\" value=\"" + compte.getKey().getSolde() + "\"/></div>"
                    + "<div class=\"form-row\"><label>Découvert autorisé</label><input type=\"number\" class=\"form-control\" name=\"decouvert\" value=\"" + compte.getKey().getDecouvertautorise() + "\"/></div>"
                    + "<div class=\"form-row\"><label>Clients</label><div id=\"clients\" class=\"col-sm-12 row\">");

            int idx = 0;
            for (Client client : clients) {
                out.print("<select data-idx=\"" + idx + "\" class=\"col-sm-11 custom-select not-same-value\" name=\"clients[" + idx + "]\">");
                out.print("<option selected=\"selected\" value=\"" + client.getIdClient() + "\">" + client.getNom() + " " + client.getPrenom() + "</option>");
                out.print("</select>");
                out.print("<div class=\"col-sm-1\" ><div data-idx-remove=\"" + idx + "\" class=\"mx-auto mt-2 bubble-sm bg-dark text-danger\"/><i class=\"mdi mdi-minus\"></i></div></div>");
                idx++;
            }

            out.print(""
                    + "</div>"
                    + "<div data-prototype=\"#protoype-select\" class=\"mx-auto mt-2 bubble-sm bg-dark text-success\"/><i class=\"mdi mdi-plus\"></i></div></div>"
                    + "<input type=\"submit\" class=\"btn btn-success\"/>"
                    + "<input type=\"submit\" name=\"supprimer\" class=\"btn btn-danger\" value=\"Supprimer\"/>"
                    + "</form>");
        }
    }
%>


