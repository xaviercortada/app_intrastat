package cat.alkaid.projects.intrastat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cat.alkaid.projects.intrastat.models.AuxDto;
import cat.alkaid.projects.intrastat.models.Nomenclature;
import cat.alkaid.projects.intrastat.services.NomenclatureService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("nomenclatures")
public class NomenclatureEndpoint {
	@Autowired
	NomenclatureService service;

	@GetMapping("/{codigo:[0-9][0-9]*}")
	public Nomenclature findById(@PathVariable("codigo") String codigo) {
		Nomenclature nomenclature = service.findById(codigo);
		if (nomenclature == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return nomenclature;
	}
	
	@GetMapping("/level/{level:[0-9][0-9]*}")
	public List<Nomenclature> findByLevel(@PathVariable("level")int level) {
		try{
			final List<Nomenclature> nomenclatures = service.findByLevel(level);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}

	@GetMapping("/section/{codigo:[0-9][0-9]*}")
	public List<Nomenclature> findCapitulosByLevel(@PathVariable("codigo") String codigo) {
		try{
			final List<Nomenclature> nomenclatures = service.findCapitulosBySection(codigo);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}

	@GetMapping("/search/codigo/{codigo:[0-9][0-9]*}")
	public List<Nomenclature> findItemsByCodigo(@PathVariable("codigo") String codigo) {
		try{
			// String code = codigo.substring(0, 2);
			final List<Nomenclature> nomenclatures = service.findItemsByCodigo(codigo);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}

	@GetMapping("/capitulo/{codigo:[0-9][0-9]*}")
	public List<Nomenclature> findItemsByCapitulo(@PathVariable("codigo") String codigo) {
		try{
			// String code = codigo.substring(0, 2);
			final List<Nomenclature> nomenclatures = service.findItemsBySection(codigo);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}


	@GetMapping("/capitulo/{codigo:[0-9][0-9]*}/text/{text}")
	public List<Nomenclature> findItemsByCapituloText(@PathVariable("codigo") String codigo, 
			@PathVariable("text") final String text) {
		try{
			// String code = codigo.substring(0, 2);
			final List<Nomenclature> nomenclatures = service.findItemsBySection(codigo, text);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}

	@GetMapping("/section/{section}")
	public List<Nomenclature> findItemsBySection(@PathVariable(name = "section", required = true) final String section) {
		try{
			final List<Nomenclature> nomenclatures = service.findCapitulosBySection(section);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}


	@GetMapping("/search/{section}/{text}")
	public List<Nomenclature> findItemsByName(@PathVariable(name = "section", required = true) final String section,
		@PathVariable(name = "text", required = true) 
		String text) {
		try{
			final List<Nomenclature> nomenclatures = service.searchByText(section, text);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}

	@GetMapping("/codigo/{texto}")
	public List<AuxDto> findItemsByCodigoText(@PathVariable("texto") final String texto) {
		try{
			// final Long total = service.countItems(texto);
			final List<AuxDto> nomenclatures = service.searchByCodigo(texto);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}

	@GetMapping("/id/{codigo}")
	public AuxDto findItemById(@PathVariable("codigo") final String codigo) {
		try{
			// final Long total = service.countItems(texto);
			final AuxDto nomenclatures = service.searchById(codigo);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}

	@GetMapping("/search_/texto")
	//public List<Nomenclature> findItemsByText(@QueryParam("texto") final String texto,
	public List<Nomenclature> findItemsByText(@RequestParam("texto") final String texto,
			@RequestParam("current_page") final int currentPage,
			@RequestParam("total_pages") final int totalPages,
			@RequestParam("total_entries") final int totalEntries,
			@RequestParam("page_size") final int pageSize) {
		try{
			// final Long total = service.countItems(texto);
			final List<Nomenclature> nomenclatures = service.findItems(texto, currentPage * pageSize, pageSize);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}

	@GetMapping("/search_/codigo")
	public List<Nomenclature> findItemsByCodigo(@RequestParam("codigo") final String codigo,
			@RequestParam("current_page") final int currentPage,
			@RequestParam("total_pages") final int totalPages,
			@RequestParam("total_entries") final int totalEntries,
			@RequestParam("page_size") final int pageSize) {
		try{
			// final Long total = service.countItemsByCodigo(codigo);
			final List<Nomenclature> nomenclatures = service.findItemsByCodigo(codigo, currentPage * pageSize, pageSize);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}

	@GetMapping("/detail/{codigo:[0-9][0-9]*}")
	public List<Nomenclature> findDetailItemsByCodigo(@PathVariable("codigo") String codigo){
		try{
			// final Long total = service.countItemsByCodigo(codigo);
			final List<Nomenclature> nomenclatures = service.findDetailItemsByCodigo(codigo);
			return nomenclatures;
		}catch(Throwable e){
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}			
	}
}
