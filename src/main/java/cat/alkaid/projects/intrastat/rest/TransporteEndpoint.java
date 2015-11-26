package cat.alkaid.projects.intrastat.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cat.alkaid.projects.intrastat.model.Transporte;

@RequestScoped
@Path("/transportes")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class TransporteEndpoint {
	@Inject
	private EntityManager em;

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Transporte transporte = em.find(Transporte.class, id);
		if (transporte == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(transporte).build();
	}

	@GET
	public List<Transporte> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		TypedQuery<Transporte> query = em.createQuery("SELECT p FROM Transporte p", Transporte.class); 
		final List<Transporte> transportes = query.getResultList();
		return transportes;
	}

}
