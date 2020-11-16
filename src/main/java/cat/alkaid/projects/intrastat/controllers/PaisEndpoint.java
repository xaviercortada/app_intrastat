package cat.alkaid.projects.intrastat.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cat.alkaid.projects.intrastat.models.Pais;
import cat.alkaid.projects.intrastat.services.PaisService;

@Component
@Path("/pais")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class PaisEndpoint {
	@Autowired
    private PaisService service;
	

	@GET
	public List<Pais> listAll() {
		final List<Pais> proveedors = service.findAll();
		return proveedors;
	}

}
