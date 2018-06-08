/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Orm;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author p1712620
 */
public class QueryHelper<T> {
    Session session = null;

    public QueryHelper() {
        this.session = HibernateUtil.createSessionFactory().getCurrentSession();
    }
    
   public List<T> executeQuery(String query){
        List<T> objectList = null;
        
       try {
           Transaction tx = session.beginTransaction();
           Query q = session.createSQLQuery(query);
           objectList = (List<T>) q.list();

       } catch (Exception e) {
           e.printStackTrace();
       }

       return objectList;
   }   
}
