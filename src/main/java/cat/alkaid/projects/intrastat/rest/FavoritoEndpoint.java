package cat.alkaid.projects.intrastat.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
import cat.alkaid.projects.intrastat.model.Favorito;
import cat.alkaid.projects.intrastat.service.FavoritoService;

@RequestScoped
@Path("/favoritos")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class FavoritoEndpoint {
	
	@Inject
	FavoritoService service;

	@POST
	public Response create(final Favorito favorito) {
		try{
			service.create(favorito);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.ok(favorito).build();
	}

	@PUT
	@Path("/{code:[0-9][0-9]*}")
	public Response update(@PathParam("code") String code, final Favorito favorito) {
		try{
			Favorito item = service.findByCodigo(favorito.getCode());
			if (item == null) {
				service.create(favorito);
			}
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.ok(favorito).build();
	}

	@GET
	@Path("/{codigo:[0-9][0-9]*}")
	public Response findById(@PathParam("codigo") final String codigo) {
		Favorito favorito = service.findByCodigo(codigo);
		if (favorito == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(favorito).build();
	}

	@GET
	public List<Favorito> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		final List<Favorito> favoritos = service.findAll();
		return favoritos;
	}

	@DELETE
	@Path("/{codigo:[0-9][0-9]*}")
	public Response deleteByCodigo(@PathParam("codigo") final String codigo) {
		try{
			service.delete(codigo);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.noContent().build();
	}

}
