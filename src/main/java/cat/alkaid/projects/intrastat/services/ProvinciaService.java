package cat.alkaid.projects.intrastat.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.Provincia;

@Service
public class ProvinciaService {
	
    @PersistenceContext
    private EntityManager em;

    public Provincia findById(long codigo){
        return em.find(Provincia.class, codigo);
    }

    public List<Provincia> findAll(){
        TypedQuery<Provincia>query = em.createQuery("SELECT p FROM Provincia p",Provincia.class);
        return query.getResultList();
    }

    public Provincia findByCodigo(String codigo){
        TypedQuery<Provincia>query = em.createQuery("SELECT p FROM Provincia p WHERE p.codigo = ?0",Provincia.class);
        query.setParameter(0, codigo);
        try{
        	return query.getSingleResult();
        }catch(NoResultException nre){
        	return null;
        }
    }

    public boolean create(Provincia item){
        em.persist(item);
        return true;
    }

    public boolean update(Provincia item){
        em.merge(item);
        return true;
    }

    public boolean delete(Long id){
        Provincia target = findById(id);
        if(target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }

    public void addProvincia(Provincia item) {
        em.persist(item);
    }


	

}
