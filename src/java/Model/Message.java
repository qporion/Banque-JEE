package Model;
// Generated 10 juil. 2018 23:06:08 by Hibernate Tools 4.3.1



/**
 * Message generated by hbm2java
 */
public class Message  implements java.io.Serializable {


     private int idMessage;
     private Integer clientId;
     private Integer conseillerId;
     private String contenu;

    public Message() {
    }

	
    public Message(int idMessage) {
        this.idMessage = idMessage;
    }
    public Message(int idMessage, Integer clientId, Integer conseillerId, String contenu) {
       this.idMessage = idMessage;
       this.clientId = clientId;
       this.conseillerId = conseillerId;
       this.contenu = contenu;
    }
   
    public int getIdMessage() {
        return this.idMessage;
    }
    
    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }
    public Integer getClientId() {
        return this.clientId;
    }
    
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
    public Integer getConseillerId() {
        return this.conseillerId;
    }
    
    public void setConseillerId(Integer conseillerId) {
        this.conseillerId = conseillerId;
    }
    public String getContenu() {
        return this.contenu;
    }
    
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }




}

