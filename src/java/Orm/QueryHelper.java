/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Orm;

import Model.Client;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author p1712620
 */
public class QueryHelper implements Serializable{

    Session session = null;
    
    public QueryHelper() {
        this.session = HibernateUtil.createSessionFactory().getCurrentSession();
    }
    
    public List<Object[]> executeQuery(String query, Map<String, Class> entities) {
        List<Object[]> objectList = new ArrayList<>();

        try {
            Transaction tx = this.session.beginTransaction();
            SQLQuery sqlQuery = this.session.createSQLQuery(query);
            for (Map.Entry<String, Class> entity : entities.entrySet()) {
                sqlQuery.addEntity(entity.getKey(), entity.getValue());
            }

            objectList = sqlQuery.list();  
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectList;
    }
    
    public List<Object> executeSingleQuery(String query, String key, Class entity) {
        List<Object> objectList = new ArrayList<>();

        try {
            Transaction tx = this.session.beginTransaction();
            SQLQuery sqlQuery = this.session.createSQLQuery(query);
            sqlQuery.addEntity(key, entity);

            objectList = sqlQuery.list();  
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectList;
    }
    
    public boolean updateOneObject(String query) {
        Transaction tx = this.session.beginTransaction();
        try {
            SQLQuery sqlQuery = this.session.createSQLQuery(query);
 
            int nbresult = sqlQuery.executeUpdate();
            
            if (nbresult != 1) {
               tx.rollback();
               return false;
            } else {
               tx.commit();
               return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        
        return false;
    }
}
