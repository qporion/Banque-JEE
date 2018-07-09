package Beans;

import Model.Compte;
import Model.Transactions;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Quentin 
 */
public class ContentBeans implements Serializable {
    private String file;
    private Map<String, String> nav;
    private String err = "";
    private Map<Compte, List<Transactions>> transactions = null;

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

    public Map<Compte, List<Transactions>> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<Compte, List<Transactions>> transactions) {
        this.transactions = transactions;
    }
    
}
