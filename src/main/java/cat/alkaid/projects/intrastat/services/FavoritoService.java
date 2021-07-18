package cat.alkaid.projects.intrastat.services;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.models.Favorito;
import cat.alkaid.projects.intrastat.models.Nomenclature;

@Repository
@Transactional
public class FavoritoService {
	
    @PersistenceContext
    private EntityManager em;
    
    public boolean create(final Account account, String codigo){
		try{
            Favorito item = new Favorito();
            item.setAccount(account);
            item.setCode(codigo);
			em.persist(item); 
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

    

	public List<Nomenclature> findItemsByAccount(final Account account) {
		try {
			TypedQuery<Nomenclature> query = em.createQuery(
					"SELECT p FROM Nomenclature p, Favorito f WHERE p.code = f.code and f.account.id = ?0", // and f.company.id = ?1",
					Nomenclature.class);
                    query.setParameter(0, (Object) account.getId());
                    // query.setParameter(1, (Object) account.getActiveCompany());
            

			return query.getResultList();

		} catch (NoResultException nre) {
			return null;
		}
	}

}
