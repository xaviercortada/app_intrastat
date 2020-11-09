package cat.alkaid.projects.intrastat.services;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cat.alkaid.projects.intrastat.models.Periodo;

public class PeriodoService {
    @PersistenceContext
    private EntityManager em;

    public Periodo findById(Long id){
        return em.find(Periodo.class, id);
    }

    public Periodo findByMontAndYear(int month, int year){
        TypedQuery<Periodo> query = em.createQuery("SELECT p FROM Periodo p WHERE p.month = ?0 and p.year = ?1",Periodo.class);
        query.setParameter(0, month);
        query.setParameter(1, year);
        List<Periodo> list = query.getResultList();
        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public List<Periodo> findAll(){
        TypedQuery<Periodo>query = em.createQuery("SELECT p FROM Periodo p",Periodo.class);
        return query.getResultList();
    }

    public boolean create(Periodo item){
        em.persist(item);
        return true;
    }

    public boolean update(Periodo item){
        Periodo target = findById(item.getId());
        if(target != null) {
            target.setMonth(item.getMonth());
            target.setYear(item.getYear());

            em.merge(target);
            return true;
        }
        return false;
    }

    public boolean delete(Long id){
        Periodo target = findById(id);
        if(target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }

}
