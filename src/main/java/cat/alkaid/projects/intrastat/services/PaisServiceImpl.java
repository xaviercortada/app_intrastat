package cat.alkaid.projects.intrastat.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.Pais;

@Service
public class PaisServiceImpl implements PaisService {
	
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Pais> findAll(){
        TypedQuery<Pais>query = em.createQuery("SELECT p FROM Pais p",Pais.class);
        return query.getResultList();
    }	

}
