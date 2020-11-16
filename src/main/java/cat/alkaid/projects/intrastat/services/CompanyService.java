package cat.alkaid.projects.intrastat.services;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import java.util.List;
import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.models.Company;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

@Service
public class CompanyService
{
    @PersistenceContext
    private EntityManager em;
    
    public Company findById(final Long id) {
        return (Company)this.em.find((Class)Company.class, (Object)id);
    }
    
    public List<Company> findAll(final Account account) {
        final TypedQuery<Company> query = (TypedQuery<Company>)this.em.createQuery("SELECT p FROM Company p  WHERE p.account.id = ?0 ORDER BY p.name", (Class)Company.class);
        query.setParameter(0, (Object)account.getId());
        return (List<Company>)query.getResultList();
    }
    
    public List<Company> findAll() {
        final TypedQuery<Company> query = (TypedQuery<Company>)this.em.createQuery("SELECT p FROM Company p ORDER BY p.name", (Class)Company.class);
        return (List<Company>)query.getResultList();
    }
    
    public Company findByCodigo(final String codigo) {
        final TypedQuery<Company> query = (TypedQuery<Company>)this.em.createQuery("SELECT p FROM Company p WHERE p.codigo = ?0 ORDER BY p.codigo", (Class)Company.class);
        query.setParameter(0, (Object)codigo);
        try {
            return (Company)query.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
    }
    
    public boolean create(final Account account, final Company item) {
        final Account myAccount = (Account)this.em.find((Class)Account.class, (Object)account.getId());
        item.setAccount(myAccount);
        this.em.persist((Object)item);
        return true;
    }
    
    public boolean update(final Company item) {
        this.em.merge((Object)item);
        return true;
    }
    
    public boolean delete(final Long id) {
        final Company target = this.findById(id);
        if (target != null) {
            this.em.remove((Object)target);
            return true;
        }
        return false;
    }
    
    public void addCompany(final Company item) {
        this.em.persist((Object)item);
    }
}