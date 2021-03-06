package Model;
// Generated 11 juil. 2018 22:30:34 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Jour generated by hbm2java
 */
public class Jour  implements java.io.Serializable {


     private JourId id;
     private Date dateDebCreneau1;
     private Date dateFinCreneau1;
     private Date dateDebCreneau2;
     private Date dateFinCreneau2;

    public Jour() {
    }

    public Jour(JourId id, Date dateDebCreneau1, Date dateFinCreneau1, Date dateDebCreneau2, Date dateFinCreneau2) {
       this.id = id;
       this.dateDebCreneau1 = dateDebCreneau1;
       this.dateFinCreneau1 = dateFinCreneau1;
       this.dateDebCreneau2 = dateDebCreneau2;
       this.dateFinCreneau2 = dateFinCreneau2;
    }
   
    public JourId getId() {
        return this.id;
    }
    
    public void setId(JourId id) {
        this.id = id;
    }
    public Date getDateDebCreneau1() {
        return this.dateDebCreneau1;
    }
    
    public void setDateDebCreneau1(Date dateDebCreneau1) {
        this.dateDebCreneau1 = dateDebCreneau1;
    }
    public Date getDateFinCreneau1() {
        return this.dateFinCreneau1;
    }
    
    public void setDateFinCreneau1(Date dateFinCreneau1) {
        this.dateFinCreneau1 = dateFinCreneau1;
    }
    public Date getDateDebCreneau2() {
        return this.dateDebCreneau2;
    }
    
    public void setDateDebCreneau2(Date dateDebCreneau2) {
        this.dateDebCreneau2 = dateDebCreneau2;
    }
    public Date getDateFinCreneau2() {
        return this.dateFinCreneau2;
    }
    
    public void setDateFinCreneau2(Date dateFinCreneau2) {
        this.dateFinCreneau2 = dateFinCreneau2;
    }




}


