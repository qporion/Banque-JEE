/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Beans.ContentBeans;
import Orm.DatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

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
        Map<String,String> nav = new TreeMap<>();
        nav.put("Accueil", request.getContextPath() + "/Accueil");
        nav.put("Agences", "#");
        
        if (request.getSession().getAttribute("client") != null) {
            nav.put("Mes comptes", request.getContextPath() + "/MesComptes");
            nav.put("Contacts", "#");
            nav.put("Transaction", request.getContextPath() + "/Transaction");
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
    
}
