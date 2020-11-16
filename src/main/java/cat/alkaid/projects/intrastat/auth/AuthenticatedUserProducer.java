package cat.alkaid.projects.intrastat.auth;

import java.util.List;
import javax.ws.rs.Produces;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.services.AccountService;


@Service
public class AuthenticatedUserProducer
{
    @Autowired
    private AccountService service;
    private Account authenticatedAccount;
    
    @Produces
    @RequestScoped
    @AuthenticatedUser
    public Account getAuthenticatedAccount() {
        return this.authenticatedAccount;
    }
    
    public void handleAuthenticationEvent(@AuthenticatedUser final AuthenticatedEvent authEvent) {
        final List<Account> accounts = (List<Account>)this.service.findByUsername(authEvent.getAuthId());
        this.authenticatedAccount = accounts.get(0);
        if (authEvent.getCompanyId() != null) {
            this.authenticatedAccount.setActiveCompany(authEvent.getCompanyId());
        }
    }
}

