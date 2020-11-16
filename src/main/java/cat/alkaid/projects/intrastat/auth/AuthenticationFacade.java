package cat.alkaid.projects.intrastat.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.services.AccountService;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    @Autowired
    private AccountService accountService;

    @Override
    public Account getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Account> accounts = accountService.findByUsername(authentication.getName());
        if(accounts.size() > 0){
            return accounts.get(0);
        }

        return null;
    }    
}
