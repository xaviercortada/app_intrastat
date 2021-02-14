package cat.alkaid.projects.intrastat.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.alkaid.projects.intrastat.models.Pais;
import cat.alkaid.projects.intrastat.models.Provincia;
import cat.alkaid.projects.intrastat.models.Transaccion;
import cat.alkaid.projects.intrastat.models.Transporte;
import cat.alkaid.projects.intrastat.services.ProvinciaService;

@CrossOrigin(origins = "http://localhost:8081")
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

	@GetMapping("/provincias/{codigo:[0-9][0-9]*}")
	public Provincia getProvincia(@PathVariable("codigo") final Integer codigo) {
		TypedQuery<Provincia> query = em.createQuery("SELECT p FROM Provincia p where codigo = ?0", Provincia.class); 
        query.setParameter(0, (Object) codigo);
		return query.getSingleResult();
	}

	@GetMapping("/paises")
	public List<Pais> listAllPais() {
		TypedQuery<Pais> query = em.createQuery("SELECT p FROM Pais p order by p.name", Pais.class); 
		final List<Pais> paises = query.getResultList();
		return paises;
	}
	
	@GetMapping("/paises/{codigo:[0-9][0-9]*}")
	public Pais getPais(@PathVariable("codigo") final Integer codigo) {
		TypedQuery<Pais> query = em.createQuery("SELECT p FROM Pais p where codigo = ?0", Pais.class); 
        query.setParameter(0, (Object) codigo);
		return query.getSingleResult();
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
