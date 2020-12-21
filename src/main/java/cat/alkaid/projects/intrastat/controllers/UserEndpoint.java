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

import cat.alkaid.projects.intrastat.models.User;
import cat.alkaid.projects.intrastat.services.UserService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("users")
public class UserEndpoint {
	
	@Autowired
	UserService service;

	@PostMapping("")
	public User create(final User user) {
		try{
			service.create(user);
		}catch(Throwable e){
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return user;
	}

	@GetMapping("/{id:[0-9][0-9]*}")
	public User findById(@PathVariable("id") final Long id) {
		User user = service.findById(id);
		if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return user;
	}

	@GetMapping("")
	public List<User> listAll(@RequestParam("start") Integer startPosition,
			@RequestParam("max") final Integer maxResult) {
		final List<User> users = service.findAll();
		return users;
	}

	@PutMapping("/{id:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable("id") Long id, @RequestBody User user) {
		try{
			service.update(user);
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
