<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Model.Jour"%>
<%@page import="Model.Agence"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<jsp:useBean id="bean" type="Beans.ContentBeans" scope="request"/>
<div class="row">
    <%
        out.print("<h2 class=\"col-sm-12\">Nos agences</h2>");
        for (Map.Entry<Agence, List<Jour>> agences : bean.getAgences().entrySet()) {
            out.print("<div class=\"card border-success col-sm-6\">"
                    + "<div class=\"card-body\">"
                    + "<div class=\"bubble bg-dark text-success m-auto\" data-toggle=\"collapse\" "
                    + "data-target=\"#agence-" + agences.getKey().getIdAgence() + "\" aria-expanded=\"false\" "
                    + "aria-controls=\"agence-" + agences.getKey().getIdAgence() + "\">"
                    + "<i class=\"mdi mdi-home-account\"></i>"
                    + "</div>"
                    + ""
                    + "<h5 class=\"card-title pt-3\">" + agences.getKey().getNom() + "</h5>"
                    + "<p class=\"card-text\"> Téléphone : " + agences.getKey().getTelephone() + "</p>"
                    + "<p class=\"card-text\"> Adresse : " + agences.getKey().getAdresse() + "</p>"
                    + ""
                    + ""
                    + "<div id=\"agence-" + agences.getKey().getIdAgence() + "\" class=\"text-white p-2 container border border-success collapse mt-4 bg-dark\">"
                    + "<div class=\"row\">"
            );
            SimpleDateFormat formater = new SimpleDateFormat("hh:mm");

            boolean[] tabJours = new boolean[7];
            tabJours[0] = false;
            tabJours[1] = false;
            tabJours[2] = false;
            tabJours[3] = false;
            tabJours[4] = false;
            tabJours[5] = false;
            tabJours[6] = false;
            if (agences.getValue() != null && !agences.getValue().isEmpty()) {
                for (Jour jour : agences.getValue()) {
                    switch (jour.getId().getIdJour()) {
                        case 0:
                            out.print("<div class=\"col-sm-12 order-1\"><p>Lundi : ");
                            break;
                        case 1:
                            out.print("<div class=\"col-sm-12 order-2\"><p>Mardi : ");
                            break;
                        case 2:
                            out.print("<div class=\"col-sm-12 order-3\"><p>Mercredi : ");
                            break;
                        case 3:
                            out.print("<div class=\"col-sm-12 order-4\"><p>Jeudi : ");
                            break;
                        case 4:
                            out.print("<div class=\"col-sm-12 order-5\"><p>Vendredi : ");
                            break;
                        case 5:
                            out.print("<div class=\"col-sm-12 order-6\"><p>Samedi : ");
                            break;
                        case 6:
                            out.print("<div class=\"col-sm-12 order-7\"><p>Dimanche : ");
                            break;
                        default:
                            out.print("<div class=\"col-sm-12\">");
                    }

                    if (jour.getId().getIdJour() >= 0 && jour.getId().getIdJour() < tabJours.length) {
                        tabJours[jour.getId().getIdJour()] = true;
                    }

                    if (jour.getDateDebCreneau1() != null) {
                        out.print("<span class=\"text-success\">"
                                + formater.format(jour.getDateDebCreneau1())
                                + " → "
                                + formater.format(jour.getDateFinCreneau1())
                        );

                        if (jour.getDateDebCreneau2() != null) {
                            out.print(
                                    ", " + formater.format(jour.getDateDebCreneau2())
                                    + " → "
                                    + formater.format(jour.getDateFinCreneau2())
                            );
                        }
                    } else {
                        out.print("<span class=\"text-danger\">Fermé");
                    }

                    out.print("</span></p></div>");
                }

                for (int i = 0; i < 7; i++) {
                    if (!tabJours[i]) {
                        switch (i) {
                            case 0:
                                out.print("<div class=\"col-sm-12 order-1\"><p>Lundi : ");
                                break;
                            case 1:
                                out.print("<div class=\"col-sm-12 order-2\"><p>Mardi : ");
                                break;
                            case 2:
                                out.print("<div class=\"col-sm-12 order-3\"><p>Mercredi : ");
                                break;
                            case 3:
                                out.print("<div class=\"col-sm-12 order-4\"><p>Jeudi : ");
                                break;
                            case 4:
                                out.print("<div class=\"col-sm-12 order-5\"><p>Vendredi : ");
                                break;
                            case 5:
                                out.print("<div class=\"col-sm-12 order-6\"><p>Samedi : ");
                                break;
                            case 6:
                                out.print("<div class=\"col-sm-12 order-7\"><p>Dimanche : ");
                                break;
                        }
                        out.print("<span class=\"text-danger\">Fermé</span>");
                        out.print("</p></div>");
                    }
                }

            } else {
                out.print("<div class=\"col-sm-12 order-1\"><p>Horaires d'ouverture inconnus</p></div>");
            }
            out.print("</div>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
            );
        }
    %>
</div>