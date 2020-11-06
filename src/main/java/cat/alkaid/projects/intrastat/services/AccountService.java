package cat.alkaid.projects.intrastat.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

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
        TypedQuery<Account> query = em.createQuery("SELECT p FROM Account p WHERE p.username = ?0 ", Account.class);
        query.setParameter(0, username);
        List<Account> list = query.getResultList();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
        return em.findByUserName(userName);
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
