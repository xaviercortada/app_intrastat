package cat.alkaid.projects.intrastat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.services.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountEndpoint {
	
	@Autowired
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

	@GetMapping("/{id:[0-9][0-9]*}")
	public ResponseEntity<Account> findById(@PathVariable("id") final Long id) {
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
