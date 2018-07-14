<%@page import="Model.Client"%>
<%@page import="Model.Compte"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<jsp:useBean id="bean" type="Beans.ContentBeans" scope="request"/>
<div class="form-row m-4">
    <label for="decouvert">N'afficher que les comptes en découvert</label>
    <input id="decouvert" class="form-check ml-3 mt-2" id="decouvert" type="checkbox" name="decouvert" />
</div>
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">Numéro du compte</th>
      <th scope="col">Titulaires</th>
      <th scope="col">Solde</th>
      <th scope="col">Visualiser</th>
    </tr>
  </thead>
  <tbody>
    <%
        for(Map.Entry<Compte, List<Client>> compte : bean.getComptesClient().entrySet()) {
            
            String classRow = "";
            if (compte.getKey().getSolde() < 0) {
                classRow = "bg-danger text-white";
            }
            
            out.print("<tr class=\""+classRow+"\">"
                    + "<th scope=\"row\">"+compte.getKey().getIdCompte()+"</th>"
                    + "<td>");
            
            for(Client client : compte.getValue()) {
                out.print("<p>"+client.getNom()+" "+client.getPrenom()+"</p>");
            }
            
            out.print("</td>"
                    + "<td>"+compte.getKey().getSolde()+" €</td>"
                    + "<td>"
                    + "<form method=\"POST\" >"
                    + "<input type=\"hidden\" name=\"compte\" value=\""+compte.getKey().getIdCompte()+"\"/>"        
                    + "<div class=\"bubble-sm bg-dark text-success valid-form\"><i class=\"mdi mdi-eye\"></i></div>"
                    + "</form>"
                    + "</td>"
                    + "</tr>");
        }
    %>
    
    
  </tbody>
</table>
