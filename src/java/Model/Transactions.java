package Model;
// Generated 10 juil. 2018 23:06:08 by Hibernate Tools 4.3.1



/**
 * Transactions generated by hbm2java
 */
public class Transactions  implements java.io.Serializable {


     private int idTransaction;
     private Integer comptecreditId;
     private Integer comptedebiteId;
     private double montant;
     private String etat;

    public Transactions() {
    }

	
    public Transactions(int idTransaction, double montant) {
        this.idTransaction = idTransaction;
        this.montant = montant;
    }
    public Transactions(Integer comptecreditId, Integer comptedebiteId, double montant, String etat) {
       this.idTransaction = idTransaction;
       this.comptecreditId = comptecreditId;
       this.comptedebiteId = comptedebiteId;
       this.montant = montant;
       this.etat = etat;
    }
   
    public int getIdTransaction() {
        return this.idTransaction;
    }
    
    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }
    public Integer getComptecreditId() {
        return this.comptecreditId;
    }
    
    public void setComptecreditId(Integer comptecreditId) {
        this.comptecreditId = comptecreditId;
    }
    public Integer getComptedebiteId() {
        return this.comptedebiteId;
    }
    
    public void setComptedebiteId(Integer comptedebiteId) {
        this.comptedebiteId = comptedebiteId;
    }
    public double getMontant() {
        return this.montant;
    }
    
    public void setMontant(double montant) {
        this.montant = montant;
    }
    public String getEtat() {
        return this.etat;
    }
    
    public void setEtat(String etat) {
        this.etat = etat;
    }




}

