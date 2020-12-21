package cat.alkaid.projects.intrastat.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.alkaid.projects.intrastat.models.Pais;
import cat.alkaid.projects.intrastat.models.Provincia;
import cat.alkaid.projects.intrastat.models.Transaccion;
import cat.alkaid.projects.intrastat.models.Transporte;
import cat.alkaid.projects.intrastat.services.ProvinciaService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("resources")
public class ResourceEndpoint {
	@Autowired
    private ProvinciaService provinciaService;
	
	@Autowired
	private EntityManager em;

	@GetMapping("/provincias")
	public List<Provincia> listAllProvincia() {
		final List<Provincia> Provincias = provinciaService.findAll();
		return Provincias;
	}

	@GetMapping("/paises")
	public List<Pais> listAllPais() {
		TypedQuery<Pais> query = em.createQuery("SELECT p FROM Pais p", Pais.class); 
		final List<Pais> paises = query.getResultList();
		return paises;
	}
	
	@GetMapping("/transportes")
	public List<Transporte> listAllTransporte() {
		TypedQuery<Transporte> query = em.createQuery("SELECT p FROM Transporte p", Transporte.class); 
		final List<Transporte> transportes = query.getResultList();
		return transportes;
	}

	@GetMapping("/transacciones")
	public List<Transaccion> listAllTransaccion() {
		TypedQuery<Transaccion> query = em.createQuery("SELECT p FROM Transaccion p", Transaccion.class); 
		final List<Transaccion> items = query.getResultList();
		return items;
	}

}
