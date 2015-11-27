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

import cat.alkaid.projects.intrastat.model.Category;
import cat.alkaid.projects.intrastat.service.CategoryService;

@RequestScoped
@Path("/categories")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class CategoryEndpoint {
	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private CategoryService service;
	
	@POST
	public Response create(final Category category) {
		try{
			service.create(category);
		}catch(Throwable e){
			e.printStackTrace();
		}					

		return Response.ok(category).build();
//		return Response
//				.created(UriBuilder.fromResource(CategoryEndpoint.class).path(String.valueOf(category.getId())).build())
//				.build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		
		Category category = service.findById(id);
		
		if (category == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(category).build();
	}

	@GET
	public List<Category> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		try{
			final List<Category> categories = service.findAll();
			return categories;
		}catch(Throwable e){
			e.printStackTrace();
		}			
		return null;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final Category category) {
		try{
			service.update(category);
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
