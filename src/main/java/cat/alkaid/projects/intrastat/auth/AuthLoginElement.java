
package cat.alkaid.projects.intrastat.auth;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class AuthLoginElement implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = -1790823417847168587L;
    private String username;
    private String password;
    
    public AuthLoginElement() {
    }
    
    public AuthLoginElement(final String username, final String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
}

