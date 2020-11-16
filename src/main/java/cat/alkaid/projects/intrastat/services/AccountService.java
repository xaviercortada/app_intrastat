package cat.alkaid.projects.intrastat.services;

import java.util.Date;
import cat.alkaid.projects.intrastat.auth.AuthPasswordElement;
import java.util.Set;
import cat.alkaid.projects.intrastat.models.Company;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import cat.alkaid.projects.intrastat.models.Account;
import javax.persistence.EntityManager;

@Service
public class AccountService
{
    @Autowired
    private EntityManager em;
    
    public Account findById(final Long id) {
        return (Account)this.em.find((Class)Account.class, (Object)id);
    }
    
    public List<Account> findAll() {
        final TypedQuery<Account> query = (TypedQuery<Account>)this.em.createQuery("SELECT p FROM Account p", (Class)Account.class);
        return (List<Account>)query.getResultList();
    }
    
    public List<Account> findByUsername(final String username) {
        final TypedQuery<Account> query = (TypedQuery<Account>)this.em.createQuery("SELECT p FROM Account p WHERE p.userName = ?0 ", (Class)Account.class);
        query.setParameter(0, (Object)username);
        return (List<Account>)query.getResultList();
    }
    
    public boolean create(final Account account) {
        try {
            this.em.persist((Object)account);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Transactional
    public boolean update(final Account account) {
        final Set<Company> companies = account.getCompanies();
        for (final Company item : companies) {
            item.setAccount(account);
        }
        this.em.merge((Object)account);
        return false;
    }
    
    public boolean delete(final Long id) {
        final Account target = this.findById(id);
        if (target != null) {
            this.em.remove((Object)target);
            return true;
        }
        return false;
    }
    
    public boolean changePassword(final Long id, final AuthPasswordElement passwordElement) {
        final Account target = this.findById(id);
        if (target != null) {
            target.changePassword(passwordElement.getNew_password());
            this.em.merge((Object)target);
            return true;
        }
        return false;
    }
    
    public Object findByLoginName(final String email) {
        return null;
    }
    
    public Account activateAccount(final String activationCode) {
        final TypedQuery<Account> query = (TypedQuery<Account>)this.em.createQuery("SELECT p FROM Account p WHERE p.activationCode = ?0 ", (Class)Account.class);
        query.setParameter(0, (Object)activationCode);
        final List<Account> list = (List<Account>)query.getResultList();
        if (list.size() > 0) {
            final Account account = list.get(0);
            final Date date = new Date();
            account.setActivated(date);
            this.em.merge((Object)account);
            return account;
        }
        return null;
    }
    
    public void removeCompany(final Long id, final Long idCompany) {
        final Account account = this.findById(id);
        Company selected = null;
        for (final Company item : account.getCompanies()) {
            final long itemId = item.getId();
            if (itemId == idCompany) {
                selected = item;
                break;
            }
        }
        if (selected != null) {
            account.getCompanies().remove(selected);
            this.em.merge((Object)account);
            this.em.remove((Object)selected);
        }
    }
}