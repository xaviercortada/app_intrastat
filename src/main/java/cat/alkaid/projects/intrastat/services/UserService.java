package cat.alkaid.projects.intrastat.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.User;

@Service
public class UserService {
	
    @PersistenceContext
    private EntityManager em;

    public User findById(Long id){
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        TypedQuery<User>query = em.createQuery("SELECT p FROM User p",User.class);
        return query.getResultList();
    }

    public User findByCodigo(String codigo){
        TypedQuery<User>query = em.createQuery("SELECT p FROM User p WHERE p.codigo = ?0",User.class);
        query.setParameter(0, codigo);
        try{
        	return query.getSingleResult();
        }catch(NoResultException nre){
        	return null;
        }
    }

    public boolean create(User item){
        em.persist(item);
        return true;
    }

    public boolean update(User item){
        em.merge(item);
        return true;
    }

    public boolean delete(Long id){
        User target = findById(id);
        if(target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }

    public void addUser(User item) {
        em.persist(item);
    }


	

}
