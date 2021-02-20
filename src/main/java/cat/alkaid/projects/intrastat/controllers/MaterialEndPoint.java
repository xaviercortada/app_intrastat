package cat.alkaid.projects.intrastat.controllers;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cat.alkaid.projects.intrastat.models.Material;
import cat.alkaid.projects.intrastat.services.MaterialService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("materiales")
public class MaterialEndPoint
{
    @Autowired
    private MaterialService service;

/*     @Inject
    @AuthenticatedUser
    private Account authenticatedAccount;
 */    
    @PostMapping("")
    public Material create(@RequestBody Material material) {
        try {
            this.service.create(material);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return material;
    }

    @GetMapping("/new")
    public Material empty() {
        return new Material();
    }
    
    @GetMapping("/{id:[0-9][0-9]*}")
    public Material findById(@PathVariable("id") Long id) {
        final Material Material = this.service.findById(id);
        if (Material == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return Material;
    }
    
    @PutMapping("/{id:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, @RequestBody Material Material) {
        try {
            this.service.update(Material);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @DeleteMapping("/{id:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        try {
            this.service.delete(id);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

