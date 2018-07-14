<%@page import="Model.Client"%>
<%@page import="Model.Compte"%>
<%@page import="Model.Transactions"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<jsp:useBean id="bean" type="Beans.ContentBeans" scope="request"/>
<%
    for(Map.Entry<Compte, List<Transactions>> compte : bean.getTransactions().entrySet()) {
        out.print("<h3>Compte n°"+compte.getKey().getIdCompte()+"</h3>");
        String select = "<select data-idx=\"__INDEX__\" class=\"not-same-value custom-select\" name=\"clients[__INDEX__]\">";
        
        for (Client client : bean.getClients()) {
            select += "<option value=\""+client.getIdClient()+"\">"+client.getNom()+" "+client.getPrenom()+"</option>";
        }
        select += "</select>";        
        List<Client> clients = null;
        for(Map.Entry<Compte, List<Client>> compteClient : bean.getComptesClient().entrySet()) {
            if (compteClient.getKey().getIdCompte() == compte.getKey().getIdCompte()) {
                clients = compteClient.getValue();
                break;
            }
        }
        
        out.print("<form method=\"POST\">"
                + "<div class=\"form-row\"><label>Solde</label><input type=\"number\" class=\"form-control\" name=\"solde\" value=\""+compte.getKey().getSolde()+"\"/></div>"
                + "<div class=\"form-row\"><label>Découvert autorisé</label><input type=\"number\" class=\"form-control\" name=\"decouvert\" value=\""+compte.getKey().getDecouvertautorise()+"\"/></div>"
                + "<input type=\"hidden\" name=\"clients[]\" />"
                + "<div class=\"form-row\"><label>Clients</label><div id=\"clients\" class=\"col-sm-12 row\">");
        
        int idx = 0;
        for(Client client : clients) {
            out.print("<select data-idx=\""+idx+"\" disabled class=\"custom-select not-same-value\" name=\"clients["+(idx++)+"]\">");
            out.print("<option selected=\"selected\" value=\""+client.getIdClient()+"\">"+client.getNom()+" "+client.getPrenom()+"</option>");
            out.print("</select>");        
        }
        
        out.print("<div class=\"collapse\" id=\"protoype-select\" data-target=\"#clients\" >"+select+"</div>"
                + "</div>"
                + "<div data-prototype=\"#protoype-select\" class=\"mx-auto mt-2 bubble-sm bg-dark text-success\"/><i class=\"mdi mdi-plus\"></i></div></div>"
                + "</form>");
    }
%>


    