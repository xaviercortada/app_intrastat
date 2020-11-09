package cat.alkaid.projects.intrastat.controllers;

import java.io.IOException;
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

import javax.ws.rs.core.UriBuilder;

import cat.alkaid.projects.intrastat.models.User;
import cat.alkaid.projects.intrastat.services.UserService;

@Path("/users")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class UserEndpoint {
	
	@Autowired
	UserService service;

	@POST
	public Response create(final User user) {
		try{
			service.create(user);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.ok(user).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		User user = service.findById(id);
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(user).build();
	}

	@GET
	public List<User> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		final List<User> users = service.findAll();
		return users;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final User user) {
		try{
			service.update(user);
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
