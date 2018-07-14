<%-- 
    Document   : contactConseiller
    Created on : 14 juil. 2018, 12:00:43
    Author     : Mapotofu
--%>
<%@page import="Model.Message"%>
<%@page import="Model.Agence"%>
<%@page import="java.util.Map"%>
<%@page import="Model.Client"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<jsp:useBean id="bean" type="Beans.ContentBeans" scope="request"/>
<div>
    <%
        if (bean.getClient().size() > 1) {
            out.print("<h2>Vos Clients</h2>");
        } else {
            out.print("<h2>Votre Client</h2>");
        }
        
        for(Map.Entry<Client, Agence> client : bean.getClient().entrySet()) {
            out.print("<div class=\"row mt-3\">"
                    + "<div class=\"bubble bg-dark text-success\" data-toggle=\"collapse\" "
                    + "data-target=\"#message-"+client.getKey().getIdClient()+"\" aria-expanded=\"false\" "
                    + "aria-controls=\"message-"+client.getKey().getIdClient()+"\">"
                    + "<i class=\"mdi mdi-account-outline\"></i>"
                    + "</div>"
                    + "<div class=\"col-sm-7\">"
                    + "<p>" + client.getKey().getNom() + " " + client.getKey().getPrenom() + "</p>"
                    + "<p>" + "Agence : " + client.getValue().getNom() + "</p>"
                    + "<p>" + "Téléphone : " + client.getKey().getTelephone() + "</p>"
                    + "</div>"
                    + ""
                    + "<div id=\"message-"+client.getKey().getIdClient()+"\" class=\"message-float pt-3 col-sm-12 mt-3 collapse\">"
            );
            
            out.print("<div class=\"container mb-3 \">");
            out.print("<div class=\"row\">");
            
            List<Message> messages = bean.getMessagesConseiller().get(client);

            for(Map.Entry<Client, List<Message>> clients : bean.getMessagesConseiller().entrySet()) {
                    if (clients.getKey().getIdClient() == client.getKey().getIdClient()) {
                        messages = clients.getValue();
                        break;
                    }
                }

                if(messages != null){
                    int idxStop = messages.size()-5;
                    if(messages.size() < 5) {
                        idxStop = 0;
                }

                for(int i=idxStop; i < messages.size(); i++) {
                    Message message = messages.get(i);
                    String classMessage = "text-right text-info";
                    if (message.getConseillerMsg()) {
                        classMessage = "text-left text-success";
                    }

                    out.print("<p class=\"message-text bg-dark border-bottom col-sm-12 "+classMessage+"\">"+message.getContenu()+"</p>");
                }
            }
            out.print("</div>");
            out.print("</div>");
            out.print("<form method=\"POST\" id=\"message\">"
                    + "<div class=\"form-group\">"
                    + "<textarea form =\"message\" name=\"contenu\" class=\"form-control col-sm-12\" rows=\"10\" placeholder=\"Saisissez ici le contenu à envoyer à votre client.\"></textarea>"
                    + "</div>"
                    + "<input type=\"hidden\" name=\"client\" value=\""+client.getKey().getIdClient()+"\" />"
                    + "<div class=\"form-group\">"
                    + "<input type=\"submit\" class=\"btn btn-success m-auto\" />"
                    + "</div>"
                    + "</form>"
                    + "</div>"
                    + "</div>"
            );
        }
    %>
</div>
