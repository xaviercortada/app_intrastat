package cat.alkaid.projects.intrastat.auth;

public class AuthenticatedEvent
{
    private String authId;
    private Long companyId;
    
    public AuthenticatedEvent(final String authId, final Long companyId) {
        this.authId = authId;
        this.companyId = companyId;
    }
    
    public String getAuthId() {
        return this.authId;
    }
    
    public void setAuthId(final String authId) {
        this.authId = authId;
    }
    
    public Long getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }
}

