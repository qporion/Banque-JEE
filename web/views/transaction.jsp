<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.Compte"%>
<%@page import="Model.Transactions"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<div class="mt-5">
    <form class="row" method="POST">
        <jsp:useBean id="bean" type="Beans.ContentBeans" scope="request"/>
        <div class="form-group col-sm-5">
            <select name="debite" class="form-control">        
                <%
                    for (Map.Entry<Compte, List<Transactions>> entrySet : bean.getTransactions().entrySet()) {
                        out.print("<option value=\""+entrySet.getKey().getIdCompte()+"\">"
                                + "Compte Débité numéro : : "+entrySet.getKey().getIdCompte()
                                + "</option>");
                    }
                %>  
            </select>
        </div>
            <span class="col-sm-2 text-center">→</span>
        <div class="form-group col-sm-5">
            <select name="credite" class="form-control">
                <%
                    for (Compte compte : bean.getComptes()) {
                        out.print("<option value=\""+compte.getIdCompte()+"\">"
                                + "Compte Crédité numéro : "+compte.getIdCompte()
                                + "</option>");
                    }
                %>  
            </select>
        </div>
            
        <div class="form-group col-sm-12">
            <input type="number" class="form-control" min="0" name="montant" step="0.01" placeholder="Montant de la transaction"/>
        </div>
        <div class="form-group pl-3">
            <input type="submit" class="btn btn-success m-auto"/>
        </div>
    </form>
</div>