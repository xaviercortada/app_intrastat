package cat.alkaid.projects.intrastat.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.Proveedor;

@Service
public class ProveedorService {
	
    @PersistenceContext
    private EntityManager em;

    public Proveedor findById(Long id){
        return em.find(Proveedor.class, id);
    }

    public List<Proveedor> findAll(){
        TypedQuery<Proveedor>query = em.createQuery("SELECT p FROM Proveedor p",Proveedor.class);
        return query.getResultList();
    }

    public Proveedor findByCodigo(String codigo){
        TypedQuery<Proveedor>query = em.createQuery("SELECT p FROM Proveedor p WHERE p.codigo = ?0",Proveedor.class);
        query.setParameter(0, codigo);
        try{
        	return query.getSingleResult();
        }catch(NoResultException nre){
        	return null;
        }
    }

    public boolean create(Proveedor item){
        em.persist(item);
        return true;
    }

    public boolean update(Proveedor item){
        em.merge(item);
        return true;
    }

    public boolean delete(Long id){
        Proveedor target = findById(id);
        if(target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }

    public void addProveedor(Proveedor item) {
        em.persist(item);
    }


	

}
