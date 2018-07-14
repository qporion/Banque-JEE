package Beans;

import Model.Agence;
import Model.Client;
import Model.Compte;
import Model.Conseiller;
import Model.Jour;
import Model.Message;
import Model.Transactions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Quentin 
 */
public class ContentBeans implements Serializable {
    private String file;
    private Map<String, String> nav;
    private String err = "", val = "";
    private Map<Compte, List<Transactions>> transactions = null;
    private List<Compte> comptes = new ArrayList<>();
    private Map<Conseiller, Agence> conseillers = new LinkedHashMap<>();
    private Map<Conseiller, List<Message>> messages = new LinkedHashMap<>();
    private Map<Agence, List<Jour>> agences = new LinkedHashMap<>();
    private Map<Compte, List<Client>> comptesClient = new LinkedHashMap<>();
    private List<Client> clients = new ArrayList<>();

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file+".jsp";
    }

    public Map<String, String> getNav() {
        return nav;
    }

    public void setNav(Map<String, String> nav) {
        this.nav = nav;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Map<Compte, List<Transactions>> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<Compte, List<Transactions>> transactions) {
        this.transactions = transactions;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }

    public Map<Conseiller, Agence> getConseillers() {
        return conseillers;
    }

    public void setConseillers(Map<Conseiller, Agence> conseillers) {
        this.conseillers = conseillers;
    }

    public Map<Conseiller, List<Message>> getMessages() {
        return messages;
    }

    public void setMessages(Map<Conseiller, List<Message>> messages) {
        this.messages = messages;
    }

    public Map<Agence, List<Jour>> getAgences() {
        return agences;
    }

    public void setAgences(Map<Agence, List<Jour>> agences) {
        this.agences = agences;
    }

    public Map<Compte, List<Client>> getComptesClient() {
        return comptesClient;
    }

    public void setComptesClient(Map<Compte, List<Client>> comptesClient) {
        this.comptesClient = comptesClient;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
    
}
