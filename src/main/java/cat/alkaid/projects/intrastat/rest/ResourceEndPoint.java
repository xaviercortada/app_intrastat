package cat.alkaid.projects.intrastat.rest;

import cat.alkaid.projects.intrastat.model.Transaccion;
import cat.alkaid.projects.intrastat.model.Transporte;
import javax.persistence.TypedQuery;
import cat.alkaid.projects.intrastat.model.Pais;
import javax.ws.rs.GET;
import cat.alkaid.projects.intrastat.model.Provincia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.inject.Inject;
import cat.alkaid.projects.intrastat.service.ProvinciaService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Path("/resources")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ResourceEndpoint
{
    @Inject
    private ProvinciaService provinciaService;
    @Inject
    private EntityManager em;
    
    @GET
    @Path("/provincias")
    public List<Provincia> listAllProvincia() {
        final List<Provincia> Provincias = (List<Provincia>)this.provinciaService.findAll();
        return Provincias;
    }
    
    @GET
    @Path("/paises")
    public List<Pais> listAllPais() {
        final TypedQuery<Pais> query = (TypedQuery<Pais>)this.em.createQuery("SELECT p FROM Pais p ORDER BY p.name", (Class)Pais.class);
        final List<Pais> paises = (List<Pais>)query.getResultList();
        return paises;
    }
    
    @GET
    @Path("/transportes")
    public List<Transporte> listAllTransporte() {
        final TypedQuery<Transporte> query = (TypedQuery<Transporte>)this.em.createQuery("SELECT p FROM Transporte p", (Class)Transporte.class);
        final List<Transporte> transportes = (List<Transporte>)query.getResultList();
        return transportes;
    }
    
    @GET
    @Path("/transacciones")
    public List<Transaccion> listAllTransaccion() {
        final TypedQuery<Transaccion> query = (TypedQuery<Transaccion>)this.em.createQuery("SELECT p FROM Transaccion p", (Class)Transaccion.class);
        final List<Transaccion> items = (List<Transaccion>)query.getResultList();
        return items;
    }
}

