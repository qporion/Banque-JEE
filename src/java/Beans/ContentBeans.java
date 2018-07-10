package Beans;

import Model.Compte;
import Model.Transactions;
import java.io.Serializable;
import java.util.ArrayList;
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
    
}
