package cat.alkaid.projects.intrastat.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.SystemException;
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

@RequestScoped
@Path("/categories")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class CategoryEndpoint {
	@PersistenceContext
	private EntityManager em;
	
	@Resource
    private UserTransaction utx;
	
	@POST
	public Response create(final Category category) {
		try{
			utx.begin();
			em.persist(category); 
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
		return Response.ok(category).build();
//		return Response
//				.created(UriBuilder.fromResource(CategoryEndpoint.class).path(String.valueOf(category.getId())).build())
//				.build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Category category = em.find(Category.class, id);
		
		if (category == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(category).build();
	}

	@GET
	public List<Category> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
        TypedQuery<Category>query = em.createQuery("SELECT p FROM Category p",Category.class);
		final List<Category> categories = query.getResultList();
		return categories;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final Category category) {
		try{
			utx.begin();
			em.merge(category);
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
			Category category = em.find(Category.class, id);
			em.remove(category);
			utx.commit();
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.noContent().build();
	}

}
