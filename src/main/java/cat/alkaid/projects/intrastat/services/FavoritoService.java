package cat.alkaid.projects.intrastat.services;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cat.alkaid.projects.intrastat.models.Favorito;

public class FavoritoService {
	
    @PersistenceContext
    private EntityManager em;
    
    public boolean create(Favorito favorito){
		try{
			em.persist(favorito); 
		}catch(Throwable e){
			e.printStackTrace();
		}
        return true;
    }
    
    public Favorito findByCodigo(String codigo){
        Favorito item =  em.find(Favorito.class, codigo);
        return item;
    }


    public  List<Favorito> findAll(){
        TypedQuery<Favorito>query = em.createQuery("SELECT p FROM Favorito p",Favorito.class);
        try{
        	return query.getResultList();
        }catch(NoResultException nre){
        	return null;
        }
    }
    
    public boolean delete(String codigo){
        Favorito target = findByCodigo(codigo);
        if(target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }



}
