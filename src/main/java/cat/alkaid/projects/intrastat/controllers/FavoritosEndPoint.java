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

import cat.alkaid.projects.intrastat.models.Favorito;
import cat.alkaid.projects.intrastat.services.FavoritoService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("favoritos")
public class FavoritosEndPoint {
	
	@Autowired
	FavoritoService service;

	@PostMapping("")
	public Favorito create(final Favorito favorito) {
		try{
			service.create(favorito);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return favorito;
	}

	@PutMapping("/{code:[0-9][0-9]*}")
	public Favorito update(@PathVariable("code") String code,@RequestBody Favorito favorito) {
		try{
			Favorito item = service.findByCodigo(favorito.getCode());
			if (item == null) {
				service.create(favorito);
			}
		}catch(Throwable e){
			e.printStackTrace();
		}
		return favorito;
	}

	@GetMapping("/{codigo:[0-9][0-9]*}")
	public Favorito findById(@PathVariable("codigo") final String codigo) {
		Favorito favorito = service.findByCodigo(codigo);
		if (favorito == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return favorito;
	}

	@GetMapping("")
	public List<Favorito> listAll(@RequestParam(value = "start", required = false) Integer startPosition,
			@RequestParam(value = "max", required = false) Integer maxResult) {
		final List<Favorito> favoritos = service.findAll();
		return favoritos;
	}

	@DeleteMapping("/{codigo:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteByCodigo(@PathVariable("codigo") String codigo) {
		try{
			service.delete(codigo);
		}catch(Throwable e){
			e.printStackTrace();
		}
	}

}
