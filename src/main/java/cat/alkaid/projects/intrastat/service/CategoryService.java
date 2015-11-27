package cat.alkaid.projects.intrastat.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cat.alkaid.projects.intrastat.model.Category;

@Stateless
public class CategoryService {
	
    @PersistenceContext
    private EntityManager em;

    public Category findById(Long id){
        return em.find(Category.class, id);
    }

    public List<Category> findAll(){
        TypedQuery<Category>query = em.createQuery("SELECT p FROM Category p",Category.class);
        return query.getResultList();
    }

    public Category findByCodigo(String codigo){
        TypedQuery<Category>query = em.createQuery("SELECT p FROM Category p WHERE p.codigo = ?0",Category.class);
        query.setParameter(0, codigo);
        try{
        	return query.getSingleResult();
        }catch(NoResultException nre){
        	return null;
        }
    }

    public boolean create(Category item){
        em.persist(item);
        return true;
    }

    public boolean update(Category item){
        Category target = findById(item.getId());
        if(target != null) {
            target.setName(item.getName());
            target.setCodigo(item.getCodigo());
            target.setDescripcion(item.getDescripcion());
            target.setModificationDate(new Date(Calendar.getInstance().getTimeInMillis()));

            em.merge(target);
            return true;
        }
        return false;
    }

    public boolean delete(Long id){
        Category target = findById(id);
        if(target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }

    public void addCategory(Category item) {
        em.persist(item);
    }


	

}
