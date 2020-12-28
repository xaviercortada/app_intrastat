package cat.alkaid.projects.intrastat.auth;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class AuthAccessElement implements Serializable {
    private static final long serialVersionUID = 8966318271610368678L;
    public static final String PARAM_AUTH_ID = "auth-id";
    public static final String PARAM_COMPANY_ID = "company-id";
    public static final String PARAM_AUTH_TOKEN = "auth-token";
    private String authId;
    private String authToken;
    private String refreshToken;
    private String authPermission;
    private Long accountId;
    private Long companyId;

    public AuthAccessElement(String token, String refreshToken) {
        this.authToken = token;
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public AuthAccessElement(final String authId, final Long id) {
        this.authId = authId;
        this.accountId = id;
    }
    
    public AuthAccessElement(final String authId, final Long id, final Long companyId) {
        this.authId = authId;
        this.accountId = id;
        this.companyId = companyId;
    }
    
    public String getAuthId() {
        return this.authId;
    }
    
    public void setAuthId(final String authId) {
        this.authId = authId;
    }
    
    public String getAuthToken() {
        return this.authToken;
    }
    
    public void setAuthToken(final String authToken) {
        this.authToken = authToken;
    }
    
    public String getAuthPermission() {
        return this.authPermission;
    }
    
    public void setAuthPermission(final String authPermission) {
        this.authPermission = authPermission;
    }
    
    public Long getAccountId() {
        return this.accountId;
    }
    
    public void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }
    
    public Long getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }
}
