/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Beans.ContentBeans;
import Model.Client;
import Orm.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quentin
 */
public abstract class AbstractServlet extends HttpServlet{
    
    protected DatabaseConnection database;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        this.database = DatabaseConnection.getInstance();
    }
    
    protected void buildBeans(HttpServletRequest request, String file, ContentBeans bean) {
        Map<String,String> nav = new LinkedHashMap<>();
        nav.put("<i class=\"mdi mdi-home\"></i> Accueil", request.getContextPath() + "/Accueil");
        nav.put("<i class=\"mdi mdi-home-account\"></i> Agences", request.getContextPath() + "/Agences");
        
        if (request.getSession().getAttribute("client") != null) {
            nav.put("<i class=\"mdi mdi-contacts\"></i> Contacts", request.getContextPath() + "/Contact");
            nav.put("<i class=\"mdi mdi-comment-text-multiple-outline\"></i> Mes comptes", request.getContextPath() + "/MesComptes");
            nav.put("<i class=\"mdi mdi-swap-horizontal\"></i> Transaction", request.getContextPath() + "/Transaction");
            request.getSession().setAttribute("connected", true);
        } else {
            request.getSession().setAttribute("connected", false);
        }
        
        if (bean == null) {
            bean = new ContentBeans();
        }
        
        bean.setNav(nav);
        bean.setFile(file);
        request.setAttribute("bean", bean);
    }
    
    public Client getClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("client") == null) {
            response.sendRedirect( request.getContextPath() + "/LogIn");
            return null;
        } else {
            return ((Client) request.getSession().getAttribute("client"));
        }
    }
}
