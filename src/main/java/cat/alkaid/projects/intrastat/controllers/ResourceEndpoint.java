package cat.alkaid.projects.intrastat.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

import cat.alkaid.projects.intrastat.models.Pais;
import cat.alkaid.projects.intrastat.models.Provincia;
import cat.alkaid.projects.intrastat.models.Transaccion;
import cat.alkaid.projects.intrastat.models.Transporte;
import cat.alkaid.projects.intrastat.services.ProvinciaService;

@Path("/resources")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class ResourceEndpoint {
	@Autowired
    private ProvinciaService provinciaService;
	
	@Autowired
	private EntityManager em;

	@GET
	@Path("/provincias")
	public List<Provincia> listAllProvincia() {
		final List<Provincia> Provincias = provinciaService.findAll();
		return Provincias;
	}

	@GET
	@Path("/paises")
	public List<Pais> listAllPais() {
		TypedQuery<Pais> query = em.createQuery("SELECT p FROM Pais p", Pais.class); 
		final List<Pais> paises = query.getResultList();
		return paises;
	}
	
	@GET
	@Path("/transportes")
	public List<Transporte> listAllTransporte() {
		TypedQuery<Transporte> query = em.createQuery("SELECT p FROM Transporte p", Transporte.class); 
		final List<Transporte> transportes = query.getResultList();
		return transportes;
	}

	@GET
	@Path("/transacciones")
	public List<Transaccion> listAllTransaccion() {
		TypedQuery<Transaccion> query = em.createQuery("SELECT p FROM Transaccion p", Transaccion.class); 
		final List<Transaccion> items = query.getResultList();
		return items;
	}

}
