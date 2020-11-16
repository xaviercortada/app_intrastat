package cat.alkaid.projects.intrastat.auth;

import org.springframework.security.core.Authentication;

import cat.alkaid.projects.intrastat.models.Account;

public interface IAuthenticationFacade {
    Account getAuthentication();    
}
