package cat.alkaid.projects.intrastat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cat.alkaid.projects.intrastat.models.Proveedor;
import cat.alkaid.projects.intrastat.services.ProveedorService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("proveedores")
//@Produces({ "application/xml", "application/json" })
//@Consumes({ "application/xml", "application/json" })
public class ProveedorEndpoint {
	@Autowired
    private ProveedorService service;
	

	@PostMapping("")
	public Proveedor create(@RequestBody Proveedor proveedor) {
		try{
			service.create(proveedor); 
		}catch(Throwable e){
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return proveedor;
	}

	@GetMapping("/{id:[0-9][0-9]*}")
	public Proveedor findById(@PathVariable("id") final Long id) {
		Proveedor proveedor = service.findById(id);
		if (proveedor == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return proveedor;
	}

	@GetMapping("")
	public List<Proveedor> listAll(@RequestParam(value = "start", required = false) final Integer startPosition,
			@RequestParam(value = "max", required = false) final Integer maxResult) {
		final List<Proveedor> proveedors = service.findAll();
		return proveedors;
	}

	@PutMapping("/{id:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable(name = "id") Long id, final Proveedor proveedor) {
		try{
			service.update(proveedor);
		}catch(Throwable e){
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable("id") final Long id) {
		try{
			service.delete(id);
		}catch(Throwable e){
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

}
