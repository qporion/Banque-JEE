package Model;
// Generated 11 juil. 2018 22:30:34 by Hibernate Tools 4.3.1



/**
 * Compte generated by hbm2java
 */
public class Compte  implements java.io.Serializable {


     private int idCompte;
     private Integer solde;
     private Integer decouvertautorise;
     private Integer conseillerId;

    public Compte() {
    }

	
    public Compte(int idCompte) {
        this.idCompte = idCompte;
    }
    public Compte(int idCompte, Integer solde, Integer decouvertautorise, Integer conseillerId) {
       this.idCompte = idCompte;
       this.solde = solde;
       this.decouvertautorise = decouvertautorise;
       this.conseillerId = conseillerId;
    }
   
    public int getIdCompte() {
        return this.idCompte;
    }
    
    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }
    public Integer getSolde() {
        return this.solde;
    }
    
    public void setSolde(Integer solde) {
        this.solde = solde;
    }
    public Integer getDecouvertautorise() {
        return this.decouvertautorise;
    }
    
    public void setDecouvertautorise(Integer decouvertautorise) {
        this.decouvertautorise = decouvertautorise;
    }
    public Integer getConseillerId() {
        return this.conseillerId;
    }
    
    public void setConseillerId(Integer conseillerId) {
        this.conseillerId = conseillerId;
    }




}


