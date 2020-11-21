package cat.alkaid.projects.intrastat.auth;

import cat.alkaid.projects.intrastat.models.Account;

public interface IAuthenticationFacade {
    Account getAuthentication();    
}
