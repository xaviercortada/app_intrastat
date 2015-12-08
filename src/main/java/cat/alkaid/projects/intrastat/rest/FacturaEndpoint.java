	package cat.alkaid.projects.intrastat.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import cat.alkaid.projects.intrastat.model.Factura;
import cat.alkaid.projects.intrastat.service.FacturaService;

@RequestScoped
@Path("/facturas")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class FacturaEndpoint {
	@Inject
    private FacturaService service;
	
	@POST
	public Response create(final Factura factura) {
		try{
			service.create(factura);
		}catch(Throwable e){
			e.printStackTrace();
		}
		//here we use Category#getId(), assuming that it provides the identifier to retrieve the created Category resource.
		return Response.ok(factura).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Factura factura = service.findById(id);
		
		if (factura == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(factura).build();
	}

	@GET
	public List<Factura> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the facturas 
		final List<Factura> facturas = service.findAll();
		return facturas;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final Factura factura) {
		try{
			service.update(factura);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		try{
			service.delete(id);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{idFactura:[0-9][0-9]*}/materiales/{idMaterial:[0-9][0-9]*}")
	public Response deleteMaterialById(@PathParam("idFactura") final Long idFactura,
			@PathParam("idMaterial") final Long idMaterial) {
		try{
			service.removeMaterial(idFactura, idMaterial);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.noContent().build();
	}
}
