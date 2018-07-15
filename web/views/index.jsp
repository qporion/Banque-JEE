<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Banque</title>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="css/materialdesignicons.min.css" media="all"   />
        <link rel="stylesheet" type="text/css" href="css/styles.css" />
        <script src="js/jquery-3.3.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/scripts.js"></script>
    </head>
    <body>
        <div class="h-100">
            <nav class="navbar navbar-dark bg-dark pb-0 px-0">
              <div class="row w-100 ">
                <div class="col-3 col-sm-2 pb-0 pr-0 h-auto">
                    <button class="navbar-toggler mb-2 mx-auto d-block" type="button">
                      <span class="navbar-toggler-icon"></span>
                    </button>
                </div>
                <div class="col-5 col-sm-7 navbar-title p-0">
                    
                </div>
                
                <div class="col-4 col-sm-3 navbar-title pb-0 text-right pr-3 pt-2">
                    <jsp:useBean id="connected" type="Boolean" scope="session"/>
                    <%
                        if (connected) {
                            out.print("<a id=\"btn-connexion\" class=\"p-1\" href=\"" + request.getContextPath() + "/LogOut\" >Deconnexion</a>");
                        } else {
                            out.print("<a id=\"btn-connexion\" class=\"p-1\" href=\"" + request.getContextPath() + "/LogIn\" >Connexion</a>");
                        }
                    %>
                    
                </div>
                  
              </div>
            </nav>
            
            <div class="row h-100">
                <div id="navbar" class="col-3 col-sm-2 bg-dark collapse show">
                    <jsp:useBean id="bean" type="Beans.ContentBeans" scope="request"/>
                    <% 
                        for( Map.Entry<String, String> nav : bean.getNav().entrySet()) {
                            out.print("<div class=\"navbar-item\">"
                                + "<a href=\""+nav.getValue()+"\">"+nav.getKey()+"</a>"
                                + "</div>"
                            );
                         }
                    %>
                </div> 


                <div id="content" class="col-9 col-sm-10">
                    <%
                        if (bean.getErr() != "" && bean.getErr() != null) {
                            %><%@include file="/views/alertErreur.jsp" %><%
                        } else if (bean.getVal()!= "" && bean.getVal() != null) {
                            %><%@include file="/views/alertValide.jsp" %><%
                        }
                        %><jsp:include page="${bean.file}"></jsp:include><%
                    %>
                    
                </div>
            </div>
        </div>
    </body>
    <footer>
        <div class="bg-dark h-25">
            Footer
        </div>
    </footer>
</html>

