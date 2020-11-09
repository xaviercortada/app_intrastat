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
public class ProveedorServiceImpl implements ProveedorService {
	
    @PersistenceContext
    private EntityManager em;

    @Override
    public Proveedor findById(Long id){
        return em.find(Proveedor.class, id);
    }

    @Override
    public List<Proveedor> findAll(){
        TypedQuery<Proveedor>query = em.createQuery("SELECT p FROM Proveedor p",Proveedor.class);
        return query.getResultList();
    }

    @Override
    public Proveedor findByCodigo(String codigo){
        TypedQuery<Proveedor>query = em.createQuery("SELECT p FROM Proveedor p WHERE p.codigo = ?0",Proveedor.class);
        query.setParameter(0, codigo);
        try{
        	return query.getSingleResult();
        }catch(NoResultException nre){
        	return null;
        }
    }

    @Override
    public boolean create(Proveedor item){
        em.persist(item);
        return true;
    }

    @Override
    public boolean update(Proveedor item){
        em.merge(item);
        return true;
    }

    @Override
    public boolean delete(Long id){
        Proveedor target = findById(id);
        if(target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }

    @Override
    public void addProveedor(Proveedor item) {
        em.persist(item);
    }


	

}
