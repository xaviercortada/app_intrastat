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
import javax.ws.rs.core.UriBuilder;

import cat.alkaid.projects.intrastat.model.Account;
import cat.alkaid.projects.intrastat.service.AccountService;

@RequestScoped
@Path("/accounts")
@Produces({ "application/json" })
@Consumes({ "application/json" })
public class AccountEndpoint {
	
	@Inject
	AccountService service;

	@POST
	public Response create(final Account account) {
		try{
			service.create(account);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.ok(account).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Account account = service.findById(id);
		if (account == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(account).build();
	}

	@GET
	public List<Account> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		final List<Account> accounts = service.findAll();
		return accounts;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final Account account) {
		service.update(account);
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		service.delete(id);
		return Response.noContent().build();
	}

}
