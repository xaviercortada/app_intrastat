package cat.alkaid.projects.intrastat.controllers;

import java.util.List;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.Proveedor;
import cat.alkaid.projects.intrastat.services.ProveedorService;

@Component
@Path("/proveedores")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ProveedorEndpoint {
	@Autowired
    private ProveedorService service;
	

	@POST
	public Response create(final Proveedor proveedor) {
		try{
			service.create(proveedor); 
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.ok(proveedor).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Proveedor proveedor = service.findById(id);
		if (proveedor == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(proveedor).build();
	}

	@GET
	public List<Proveedor> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		final List<Proveedor> proveedors = service.findAll();
		return proveedors;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final Proveedor proveedor) {
		try{
			service.update(proveedor);
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

}
