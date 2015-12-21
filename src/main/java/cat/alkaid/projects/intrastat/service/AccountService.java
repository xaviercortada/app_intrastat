package cat.alkaid.projects.intrastat.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cat.alkaid.projects.intrastat.model.Account;
import cat.alkaid.projects.intrastat.model.User;

@Stateless
public class AccountService {
    @PersistenceContext
    private EntityManager em;

    public Account findById(Long id){
        return em.find(Account.class, id);
    }

    public List<Account> findAll(){
        TypedQuery<Account>query = em.createQuery("SELECT p FROM Account p",Account.class);
        return query.getResultList();
    }

    public Account findByUsername(String username){
        TypedQuery<Account>query = em.createQuery("SELECT p FROM Account p WHERE p.username = ?0 ",Account.class);
        query.setParameter(0, username);
        List<Account> list = query.getResultList();
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public boolean create(Account account){
    	try{
    		User user = account.getUser();
    		user.setAccount(account);
    		em.persist(user);
	        em.persist(account);
    	}catch(Throwable e){
			e.printStackTrace();    		
    	}
    	return false;
    }

    public boolean update(Account item){
        em.merge(item);
        return false;
    }

    public boolean delete(Long id){
        Account target = findById(id);
        if(target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }

    public boolean changePassword(Long id, String password){
        Account target = findById(id);
        if(target != null) {
            target.changePassword(password);

            em.merge(target);
            return true;
        }
        return false;

    }


    public Object findByLoginName(String email) {
        return null;
    }

    public Account activateAccount(String activationCode) {
        TypedQuery<Account>query = em.createQuery("SELECT p FROM Account p WHERE p.activationCode = ?0 ",Account.class);
        query.setParameter(0, activationCode);
        List<Account> list = query.getResultList();
        if(list.size() > 0){
            Account account = list.get(0);
            Date date = new Date();
            account.setActivated(date);
            em.merge(account);
            return account;
        }else{
            return null;
        }
    }


}
