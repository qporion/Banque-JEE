<%@page import="Model.Message"%>
<%@page import="Model.Agence"%>
<%@page import="java.util.Map"%>
<%@page import="Model.Conseiller"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<jsp:useBean id="bean" type="Beans.ContentBeans" scope="request"/>
<div>
    <%
        if (bean.getConseillers().size() > 1) {
            out.print("<h2>Vos conseillers</h2>");
        } else {
            out.print("<h2>Votre conseiller</h2>");
        }
        
        for(Map.Entry<Conseiller, Agence> conseiller : bean.getConseillers().entrySet()) {
            out.print("<div class=\"row mt-3\">"
                    + "<div class=\"bubble bg-dark text-success\" data-toggle=\"collapse\" "
                    + "data-target=\"#message-"+conseiller.getKey().getIdConseiller()+"\" aria-expanded=\"false\" "
                    + "aria-controls=\"message-"+conseiller.getKey().getIdConseiller()+"\">"
                    + "<i class=\"mdi mdi-account-outline\"></i>"
                    + "</div>"
                    + "<div class=\"col-sm-7\">"
                    + "<p>" + conseiller.getKey().getNom() + " " + conseiller.getKey().getPrenom() + "</p>"
                    + "<p>" + "Agence : " + conseiller.getValue().getNom() + "</p>"
                    + "<p>" + "Téléphone : " + conseiller.getKey().getTelephone() + "</p>"
                    + "</div>"
                    + ""
                    + "<div id=\"message-"+conseiller.getKey().getIdConseiller()+"\" class=\"message-float pt-3 col-sm-12 mt-3 collapse\">"
            );
            
            out.print("<div class=\"container mb-3 \">");
            out.print("<div class=\"row\">");
            
            List<Message> messages = bean.getMessages().get(conseiller);

            for(Map.Entry<Conseiller, List<Message>> message : bean.getMessages().entrySet()) {
                    if (message.getKey().getIdConseiller() == conseiller.getKey().getIdConseiller()) {
                        messages = message.getValue();
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
                    + "<textarea form =\"message\" name=\"contenu\" class=\"form-control col-sm-12\" rows=\"10\" placeholder=\"Saisissez ici le contenu à envoyer à votre conseiller.\"></textarea>"
                    + "</div>"
                    + "<input type=\"hidden\" name=\"conseiller\" value=\""+conseiller.getKey().getIdConseiller()+"\" />"
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
