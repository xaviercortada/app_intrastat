package cat.alkaid.projects.intrastat.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.repositories.AccountRepository;

@Service("accountService")
public class AccountService {
    @Autowired
    private AccountRepository em;

    public Account findById(Long id) {
        return em.findById(id).orElse(null);
    }

    public List<Account> findAll() {
        return em.findAll();
    }

    public Account findByUsername(String username) {
        return em.findByUserName(username);
    }

    public Account create(Account account) {
        return em.save(account);
    }

    public Account update(Account item) {
        return em.save(item);
    }

    public boolean delete(Long id) {
        Account target = em.findById(id).orElse(null);
        if (target != null) {
            em.delete(target);
            return true;
        }

        return false;
    }

    public boolean changePassword(Long id, String password) {
        Account target = findById(id);
        if (target != null) {
            em.changePassword(password);

            em.save(target);
            return true;
        }
        return false;

    }

    public Object findByLoginName(String email) {
        return null;
    }

    public Account activateAccount(String activationCode) {
        Account target = em.findByActivationCode(activationCode);
                
        if (target != null) {
            Date date = new Date();
            target.setActivated(date);
            return em.save(target);
        } else {
            return null;
        }
    }

}
