package Model;
// Generated 8 juil. 2018 20:33:07 by Hibernate Tools 4.3.1



/**
 * Compteclient generated by hbm2java
 */
public class Compteclient  implements java.io.Serializable {


     private long id;
     private Integer idclient;
     private Integer idcompte;

    public Compteclient() {
    }

	
    public Compteclient(long id) {
        this.id = id;
    }
    public Compteclient(long id, Integer idclient, Integer idcompte) {
       this.id = id;
       this.idclient = idclient;
       this.idcompte = idcompte;
    }
   
    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public Integer getIdclient() {
        return this.idclient;
    }
    
    public void setIdclient(Integer idclient) {
        this.idclient = idclient;
    }
    public Integer getIdcompte() {
        return this.idcompte;
    }
    
    public void setIdcompte(Integer idcompte) {
        this.idcompte = idcompte;
    }




}


