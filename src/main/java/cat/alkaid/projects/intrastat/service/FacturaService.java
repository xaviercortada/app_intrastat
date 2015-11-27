package cat.alkaid.projects.intrastat.service;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import cat.alkaid.projects.intrastat.model.Account;
import cat.alkaid.projects.intrastat.model.Factura;
import cat.alkaid.projects.intrastat.model.Material;
import cat.alkaid.projects.intrastat.model.Periodo;

@Stateless
public class FacturaService {
    @PersistenceContext
    private EntityManager em;

    @EJB
    AccountService accountService;

    @EJB
    PeriodoService periodoService;

    public Factura findById(Long id){
        Factura item =  em.find(Factura.class, id);
        return item;
    }

    public List<Factura> findAll(){
        TypedQuery<Factura> query = em.createQuery("SELECT p FROM Factura p", Factura.class);
        return query.getResultList();
    }

    public List<Factura> findByUser(String authId){
        Account account =  accountService.findByUsername(authId);

        TypedQuery<Factura> query = em.createQuery("SELECT p FROM Factura p WHERE p.account.id = ?0", Factura.class);
        query.setParameter(0, account.getId());
        return query.getResultList();
    }

    public List<Factura> findByPeriodo(String authId, Long idPer){
        Account account =  accountService.findByUsername(authId);

        Periodo per = periodoService.findById(idPer);
        TypedQuery<Factura> query = em.createQuery("SELECT p FROM Factura p WHERE Year(p.fecha) = ?0 and Month(p.fecha) = ?1 and p.account.id = ?2",Factura.class);
        query.setParameter(0, per.getYear());
        query.setParameter(1, per.getMonth());
        query.setParameter(2, account.getId());
        return query.getResultList();
    }


    public boolean create(Factura factura){
		try{
			Set<Material> materiales = factura.getMateriales();
			for(Material mat : materiales)
			{
				mat.setFactura(factura);
				//em.persist(mat);
			}
			em.persist(factura); 
		}catch(Throwable e){
			e.printStackTrace();
		}
        return true;
    }

    public boolean update(Factura factura){
		Set<Material> materiales = factura.getMateriales();
		for(Material mat : materiales)
		{
			mat.setFactura(factura);
			//em.persist(mat);
		}
		em.merge(factura);
        return false;
    }

    public boolean delete(Long id){
        Factura target = findById(id);
        if(target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }

    public void removeMaterial(Factura factura, Material material) {
        factura.getMateriales().remove(material);
        em.merge(factura);
    }

    public boolean create(String authId, Factura dto) {
        Account account =  accountService.findByUsername(authId);
        if(account != null){
            dto.setAccount(account);
            create(dto);
            return true;

        }
        return false;
    }

}
