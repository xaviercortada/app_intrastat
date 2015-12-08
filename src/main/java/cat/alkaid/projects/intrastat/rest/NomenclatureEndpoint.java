package cat.alkaid.projects.intrastat.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cat.alkaid.projects.intrastat.model.Nomenclature;
import cat.alkaid.projects.intrastat.service.NomenclatureService;

@RequestScoped
@Path("/nomenclatures")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class NomenclatureEndpoint {
	@Inject
	NomenclatureService service;

	@GET
	@Path("/{codigo:[0-9][0-9]*}")
	public Response findByCode(@PathParam("codigo") final String codigo) {
		Nomenclature nomenclature = service.findByCodigo(codigo);
		if (nomenclature == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(nomenclature).build();
	}
	
	@GET
	@Path("/level/{level:[0-9][0-9]*}")
	public List<Nomenclature> findByLevel(@PathParam("level") final int level) {
		try{
			final List<Nomenclature> nomenclatures = service.findByLevel(level);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
		}			
		return null;
	}

	@GET
	@Path("/capitulos/{codigo:[0-9][0-9]*}")
	public List<Nomenclature> findCapitulosByLevel(@PathParam("codigo") final String codigo) {
		try{
			final List<Nomenclature> nomenclatures = service.findCapitulosBySeccion(codigo);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
		}			
		return null;
	}

	@GET
	@Path("/capitulo/{codigo:[0-9][0-9]*}/items/{texto}")
	public List<Nomenclature> findItemsByText(@PathParam("codigo") final String codigo, @PathParam("texto") final String texto) {
		try{
			String code = codigo.substring(0, 2);
			final List<Nomenclature> nomenclatures = service.findItemsByText(code, texto);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
		}			
		return null;
	}

	@GET
	@Path("/search/texto")
	//public List<Nomenclature> findItemsByText(@QueryParam("texto") final String texto,
	public Response findItemsByText(@QueryParam("texto") final String texto,
			@QueryParam("current_page") final int currentPage,
			@QueryParam("total_pages") final int totalPages,
			@QueryParam("total_entries") final int totalEntries,
			@QueryParam("page_size") final int pageSize) {
		try{
			final Long total = service.countItems(texto);
			final List<Nomenclature> nomenclatures = service.findItems(texto, currentPage * pageSize, pageSize);
			return Response.ok(nomenclatures).header("X-total", total).build();
		}catch(Throwable e){
			e.printStackTrace();
		}			
		return null;
	}

	@GET
	@Path("/search/codigo")
	public Response findItemsByCodigo(@QueryParam("codigo") final String codigo,
			@QueryParam("current_page") final int currentPage,
			@QueryParam("total_pages") final int totalPages,
			@QueryParam("total_entries") final int totalEntries,
			@QueryParam("page_size") final int pageSize) {
		try{
			final Long total = service.countItemsByCodigo(codigo);
			final List<Nomenclature> nomenclatures = service.findItemsByCodigo(codigo, currentPage * pageSize, pageSize);
			return Response.ok(nomenclatures).header("X-total", total).build();
		}catch(Throwable e){
			e.printStackTrace();
		}			
		return null;
	}
}
