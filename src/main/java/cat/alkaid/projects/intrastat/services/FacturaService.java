package cat.alkaid.projects.intrastat.services;

import java.util.Calendar;
import cat.alkaid.projects.intrastat.models.Company;
import java.util.Iterator;
import java.util.Set;
import cat.alkaid.projects.intrastat.models.Material;
import java.util.Date;
import cat.alkaid.projects.intrastat.models.Periodo;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.models.Factura;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

@Service
public class FacturaService
{
    @PersistenceContext
    private EntityManager em;
    @Autowired
    MaterialService materialService;
    @Autowired
    NomenclatureService nomenclatureService;
    @Autowired
    AccountService accountService;
    @Autowired
    PeriodoService periodoService;
    @Autowired
    CompanyService companyService;
    
    public Factura findById(final Long id) {
        final Factura item = (Factura)this.em.find((Class)Factura.class, (Object)id);
        return item;
    }
    
    public List<Factura> findAll(final Account account) {
        final TypedQuery<Factura> query = (TypedQuery<Factura>)this.em.createQuery("SELECT p FROM Factura p WHERE p.account.id = ?0 and p.company.id = ?1", (Class)Factura.class);
        query.setParameter(0, (Object)account.getId());
        query.setParameter(1, (Object)account.getActiveCompany());
        return (List<Factura>)query.getResultList();
    }
    
    public List<Factura> findByUser(final Account account) {
        final TypedQuery<Factura> query = (TypedQuery<Factura>)this.em.createQuery("SELECT p FROM Factura p WHERE p.account.id = ?0", (Class)Factura.class);
        query.setParameter(0, (Object)account.getId());
        return (List<Factura>)query.getResultList();
    }
    
    public List<Factura> findByPeriodo(final Account account, final Long idPer) {
        final Periodo per = this.periodoService.findById(idPer);
        final TypedQuery<Factura> query = (TypedQuery<Factura>)this.em.createQuery("SELECT p FROM Factura p WHERE p.account.id = ?0 and p.company.id = ?1 and Year(p.fecha) = ?2 and Month(p.fecha) = ?3 and p.account.id = ?4", (Class)Factura.class);
        query.setParameter(0, (Object)account.getId());
        query.setParameter(1, (Object)account.getActiveCompany());
        query.setParameter(2, (Object)per.getYear());
        query.setParameter(3, (Object)per.getMonth());
        query.setParameter(4, (Object)account.getId());
        return (List<Factura>)query.getResultList();
    }
    
    public List<Factura> findByIntervalo(final Account account, final String flujo, final String present, final Date fechaIni, final Date fechaFin) {
        String s = this.checkPresent(present);
        s = String.valueOf(s) + this.checkFlujo(flujo);
        final TypedQuery<Factura> query = (TypedQuery<Factura>)this.em.createQuery("SELECT p FROM Factura p WHERE fecha >= ?0 and fecha < ?1 and p.account.id = ?2" + s, (Class)Factura.class);
        query.setParameter(0, (Object)fechaIni);
        query.setParameter(1, (Object)fechaFin);
        query.setParameter(2, (Object)account.getId());
        //query.setParameter(3, (Object)account.getActiveCompany());
        return (List<Factura>)query.getResultList();
    }
    
    public List<Factura> findByProveedor(final Account account, final String flujo, final String present, final Long idProveedor) {
        String s = this.checkPresent(present);
        s = String.valueOf(s) + this.checkFlujo(flujo);
        //final TypedQuery<Factura> query = (TypedQuery<Factura>)this.em.createQuery("SELECT p FROM Factura p WHERE p.proveedor.id = ?0 and p.account.id = ?1 and p.company.id = ?2" + s, (Class)Factura.class);
        final TypedQuery<Factura> query = (TypedQuery<Factura>)this.em.createQuery("SELECT p FROM Factura p WHERE p.proveedor.id = ?0 and p.account.id = ?1" + s, (Class)Factura.class);
        query.setParameter(0, (Object)idProveedor);
        query.setParameter(1, (Object)account.getId());
        //query.setParameter(2, (Object)account.getActiveCompany());
        return (List<Factura>)query.getResultList();
    }
    
    public boolean create(final Account account, final Factura factura) {
        try {
            final Set<Material> materiales = (Set<Material>)factura.getMateriales();
            for (final Material mat : materiales) {
                // mat.setFactura(factura);
            }
            final Account myAccount = (Account)this.em.find((Class)Account.class, (Object)account.getId());
            if (myAccount != null) {
                factura.setAccount(myAccount);
            }
            if (account.getActiveCompany() != null) {
                final Company company = this.companyService.findById(account.getActiveCompany());
                factura.setCompany(company);
            }
            this.em.persist((Object)factura);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean update(final Factura factura) {
        final Set<Material> materiales = (Set<Material>)factura.getMateriales();
        for (final Material mat : materiales) {
            // mat.setFactura(factura);
        }
        this.em.merge((Object)factura);
        return false;
    }
    
    public boolean delete(final Long id) {
        final Factura target = this.findById(id);
        if (target != null) {
            this.em.remove((Object)target);
            return true;
        }
        return false;
    }
    
    public void removeMaterial(final Long idFactura, final Long idMaterial) {
        final Factura factura = this.findById(idFactura);
        final Material material = this.materialService.findById(idMaterial);
        factura.getMateriales().remove(material);
        this.em.merge((Object)factura);
        this.em.remove((Object)material);
    }
    
    public List<Factura> findPendientes() {
        final TypedQuery<Factura> query = (TypedQuery<Factura>)this.em.createQuery("SELECT p FROM Factura p WHERE p.presentado IS NULL", (Class)Factura.class);
        return (List<Factura>)query.getResultList();
    }
    
    public List<Factura> findByCodigo(final Account account, final String codigo) {
        final TypedQuery<Factura> query = (TypedQuery<Factura>)this.em.createQuery("SELECT p FROM Factura p WHERE p.account.id = ?0 and p.company.id = ?1 and p.codigo = ?2", (Class)Factura.class);
        query.setParameter(0, (Object)account.getId());
        query.setParameter(1, (Object)account.getActiveCompany());
        query.setParameter(2, (Object)codigo);
        return (List<Factura>)query.getResultList();
    }
    
    public List<Factura> findByState(final Account account, final String flujo, final String present) {
        String s = this.checkPresent(present);
        s = String.valueOf(s) + this.checkFlujo(flujo);
        final TypedQuery<Factura> query = (TypedQuery<Factura>)this.em.createQuery("SELECT p FROM Factura p WHERE p.account.id = ?0 and p.company.id = ?1 " + s, (Class)Factura.class);
        query.setParameter(0, (Object)account.getId());
        query.setParameter(1, (Object)account.getActiveCompany());
        return (List<Factura>)query.getResultList();
    }
    
    private String checkPresent(final String present) {
        String s = "";
        if (present.equalsIgnoreCase("S")) {
            s = " and presentado IS NOT NULL";
        }
        else if (present.equalsIgnoreCase("N")) {
            s = " and presentado IS NULL";
        }
        return s;
    }
    
    private String checkFlujo(final String flujo) {
        String s = "";
        if (!flujo.equalsIgnoreCase("X")) {
            s = String.valueOf(s) + " and flujo = '" + flujo + "'";
        }
        return s;
    }
    
    public void updateState(final List<Long> selected, final Boolean presentado) {
        for (final Long id : selected) {
            final Factura factura = this.findById(id);
            if (presentado) {
                factura.setPresentado(Calendar.getInstance().getTime());
            }
            else {
                factura.setPresentado((Date)null);
            }
            this.em.merge((Object)factura);
        }
    }
}
