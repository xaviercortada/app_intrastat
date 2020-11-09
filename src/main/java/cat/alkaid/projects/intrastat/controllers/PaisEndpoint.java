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

import cat.alkaid.projects.intrastat.models.Pais;
import cat.alkaid.projects.intrastat.models.Proveedor;
import cat.alkaid.projects.intrastat.services.PaisService;
import cat.alkaid.projects.intrastat.services.ProveedorService;

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
