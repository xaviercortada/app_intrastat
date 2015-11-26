package cat.alkaid.projects.intrastat.rest;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import cat.alkaid.projects.intrastat.model.Category;
import cat.alkaid.projects.intrastat.model.Factura;
import cat.alkaid.projects.intrastat.model.Material;

@RequestScoped
@Path("/facturas")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class FacturaEndpoint {
	@PersistenceContext
	private EntityManager em;

	@Resource
    private UserTransaction utx;
	
	@POST
	public Response create(final Factura factura) {
		try{
			utx.begin();
			Set<Material> materiales = factura.getMateriales();
			for(Material mat : materiales)
			{
				mat.setFactura(factura);
				//em.persist(mat);
			}
			em.persist(factura); 
			utx.commit();
		}catch(Throwable e){
			try{
				utx.rollback();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		//here we use Category#getId(), assuming that it provides the identifier to retrieve the created Category resource.
		return Response.ok(factura).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Factura factura = em.find(Factura.class, id);
		
		if (factura == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(factura).build();
	}

	@GET
	public List<Factura> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the facturas 
        TypedQuery<Factura>query = em.createQuery("SELECT p FROM Factura p",Factura.class);
		final List<Factura> facturas = query.getResultList();
		return facturas;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final Factura factura) {
		try{
			utx.begin();
			Set<Material> materiales = factura.getMateriales();
			for(Material mat : materiales)
			{
				mat.setFactura(factura);
				//em.persist(mat);
			}
			em.merge(factura);
			utx.commit();
		}catch(Throwable e){
			try{
				utx.rollback();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		try{
			utx.begin();
			Factura factura = em.find(Factura.class, id);
			em.remove(factura);
			utx.commit();
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.noContent().build();
	}

}
