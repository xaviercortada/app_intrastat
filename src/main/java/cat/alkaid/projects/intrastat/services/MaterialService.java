package cat.alkaid.projects.intrastat.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.Material;

@Service
public class MaterialService {
	
    @PersistenceContext
    private EntityManager em;

    public Material findById(Long id){
        return em.find(Material.class, id);
    }

    public List<Material> findAll(){
        TypedQuery<Material>query = em.createQuery("SELECT p FROM Material p",Material.class);
        return query.getResultList();
    }

    public Material findByCodigo(String codigo){
        TypedQuery<Material>query = em.createQuery("SELECT p FROM Material p WHERE p.codigo = ?0",Material.class);
        query.setParameter(0, codigo);
        try{
        	return query.getSingleResult();
        }catch(NoResultException nre){
        	return null;
        }
    }

    public boolean create(Material item){
        em.persist(item);
        return true;
    }

    public boolean update(Material item){
        em.merge(item);
        return true;
    }

    public boolean delete(Long id){
        Material target = findById(id);
        if(target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }

    public void addMaterial(Material item) {
        em.persist(item);
    }


	

}
