package cat.alkaid.projects.intrastat.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthLoginElement implements Serializable{
    private String username;
    private String password;

    public AuthLoginElement(){}

    public AuthLoginElement(String username, String password){
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
