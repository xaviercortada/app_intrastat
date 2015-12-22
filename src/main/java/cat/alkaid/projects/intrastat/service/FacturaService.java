package cat.alkaid.projects.intrastat.service;

import java.util.Date;
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
    MaterialService materialService;

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

    public List<Factura> findByIntervalo(String authId, String present, Date fechaIni, Date fechaFin){
    	String s = "";
    	if(present.equalsIgnoreCase("S")){
    		s = " and presentado IS NOT NULL";
    	}else if (present.equalsIgnoreCase("N")) {
    		s = " and presentado IS NULL";			
		}
        TypedQuery<Factura> query = em.createQuery("SELECT p FROM Factura p WHERE fecha >= ?0 and fecha < ?1"+s,Factura.class);
        query.setParameter(0, fechaIni);
        query.setParameter(1, fechaFin);
        return query.getResultList();
    }

    public List<Factura> findByFlujoIntervalo(String authId, String flujo, String present, Date fechaIni, Date fechaFin){
    	String s = "";
    	if(present.equalsIgnoreCase("S")){
    		s = " and presentado IS NOT NULL";
    	}else if (present.equalsIgnoreCase("N")) {
    		s = " and presentado IS NULL";			
		}
        TypedQuery<Factura> query = em.createQuery("SELECT p FROM Factura p WHERE fecha >= ?0 and fecha < ?1 and p.flujo = ?2"+s,Factura.class);
        query.setParameter(0, fechaIni);
        query.setParameter(1, fechaFin);
        query.setParameter(2, flujo);
        return query.getResultList();
    }

    public List<Factura> findByFlujoProveedor(String authId, String flujo, String present, Long idProveedor){
    	String s = "";
    	if(present.equalsIgnoreCase("S")){
    		s = " and presentado IS NOT NULL";
    	}else if (present.equalsIgnoreCase("N")) {
    		s = " and presentado IS NULL";			
		}

        TypedQuery<Factura> query = em.createQuery("SELECT p FROM Factura p WHERE p.proveedor.id = ?0 and p.flujo = ?1"+s,Factura.class);
        query.setParameter(0, idProveedor);
        query.setParameter(1, flujo);
        return query.getResultList();
    }

    public List<Factura> findByProveedor(String authId, String present, Long idProveedor){
    	String s = "";
    	if(present.equalsIgnoreCase("S")){
    		s = " and presentado IS NOT NULL";
    	}else if (present.equalsIgnoreCase("N")) {
    		s = " and presentado IS NULL";			
		}

        TypedQuery<Factura> query = em.createQuery("SELECT p FROM Factura p WHERE p.proveedor.id = ?0"+s,Factura.class);
        query.setParameter(0, idProveedor);
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

    public void removeMaterial(Long idFactura, Long idMaterial) {
    	Factura factura = findById(idFactura);
    	Material material = materialService.findById(idMaterial);
        factura.getMateriales().remove(material);
        em.merge(factura);
        em.remove(material);
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

	public List<Factura> findPendientes() {
        TypedQuery<Factura> query = em.createQuery("SELECT p FROM Factura p WHERE p.presentado IS NULL", Factura.class);
        return query.getResultList();
	}

	public List<Factura> findByCodigo(String codigo) {
        TypedQuery<Factura> query = em.createQuery("SELECT p FROM Factura p WHERE p.codigo = ?0", Factura.class);
        query.setParameter(0, codigo);
        return query.getResultList();
	}

}
