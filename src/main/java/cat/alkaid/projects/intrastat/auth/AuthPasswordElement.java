package cat.alkaid.projects.intrastat.auth;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class AuthPasswordElement implements Serializable
{
    private static final long serialVersionUID = 8966318271610368678L;
    private String old_password;
    private String new_password;
    private String confirm_password;
    
    public AuthPasswordElement() {
    }
    
    public AuthPasswordElement(final String old_password, final String new_password, final String confirm_password) {
        this.setOld_password(old_password);
        this.setNew_password(new_password);
        this.setConfirm_password(confirm_password);
    }
    
    public String getOld_password() {
        return this.old_password;
    }
    
    public void setOld_password(final String old_password) {
        this.old_password = old_password;
    }
    
    public String getNew_password() {
        return this.new_password;
    }
    
    public void setNew_password(final String new_password) {
        this.new_password = new_password;
    }
    
    public String getConfirm_password() {
        return this.confirm_password;
    }
    
    public void setConfirm_password(final String confirm_password) {
        this.confirm_password = confirm_password;
    }
}

