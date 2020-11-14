package cat.alkaid.projects.intrastat.auth;

import java.util.List;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import cat.alkaid.projects.intrastat.model.Account;
import javax.inject.Inject;
import cat.alkaid.projects.intrastat.service.AccountService;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AuthenticatedUserProducer
{
    @Inject
    private AccountService service;
    private Account authenticatedAccount;
    
    @Produces
    @RequestScoped
    @AuthenticatedUser
    public Account getAuthenticatedAccount() {
        return this.authenticatedAccount;
    }
    
    public void handleAuthenticationEvent(@Observes @AuthenticatedUser final AuthenticatedEvent authEvent) {
        final List<Account> accounts = (List<Account>)this.service.findByUsername(authEvent.getAuthId());
        this.authenticatedAccount = accounts.get(0);
        if (authEvent.getCompanyId() != null) {
            this.authenticatedAccount.setActiveCompany(authEvent.getCompanyId());
        }
    }
}

