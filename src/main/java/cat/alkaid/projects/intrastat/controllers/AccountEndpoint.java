package cat.alkaid.projects.intrastat.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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

import cat.alkaid.projects.intrastat.auth.AuthAccessElement;
import cat.alkaid.projects.intrastat.auth.AuthLoginElement;
import cat.alkaid.projects.intrastat.auth.AuthPasswordElement;
import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.services.AccountService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("accounts")
//@Produces({ "application/json" })
//@Consumes({ "application/json" })
public class AccountEndpoint
{
    @Autowired
	AccountService service;
	
  
	@PostMapping("/")
	public Account create(final Account account) {
        try {
            this.service.create(account);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return account;
    }
    
    @GetMapping("/{id:[0-9][0-9]*}")
    public Account findById(@PathVariable Long id) {
        final Account account = this.service.findById(id);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return account;
    }
    
    public List<Account> listAll(@RequestParam("start") Integer startPosition, @RequestParam("max") Integer maxResult) {
        final List<Account> accounts = (List<Account>)this.service.findAll();
        return accounts;
    }
    
	@PutMapping("/{id:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") Long id, Account account) {
        this.service.update(account);
    }
    
    @DeleteMapping("/{id:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "id") Long id) {
        this.service.delete(id);
    }
    
    @PostMapping("/signin")
    public AuthAccessElement signin(@RequestBody AuthLoginElement ele) {
        List<Account> accounts = this.service.findByUsername(ele.getUsername());
        if(accounts.size() == 1) {
            Account account = accounts.get(0);
            String token = getJWTToken(account.getId().toString(), account.getUserName());
            account.setToken(token);
            this.service.update(account);

            final AuthAccessElement accessElement = new AuthAccessElement(account.getUserName(), account.getId());
            accessElement.setAuthToken(token);

            return accessElement;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    
	@PostMapping("/changePassword")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(final AuthPasswordElement passwordElement) {
        try {
/*             if (passwordElement.getOld_password().equals(this.authenticatedAccount.getPlainPwd())) {
                this.service.changePassword(this.authenticatedAccount.getId(), passwordElement);
            }
 */        }
        catch (Exception e) {
            e.printStackTrace();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/userName/{texto}")
    public List<Account> getByUserName(@PathVariable(name = "texto") final String texto) {
        final List<Account> accounts = (List<Account>)this.service.findByUsername(texto);
        return accounts;
    }
    
    @DeleteMapping("/companies/{idCompany:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterialById(@PathVariable(name = "Company") final Long idCompany) {
        try {
            // this.service.removeCompany(this.authenticatedAccount.getId(), idCompany);
        }
        catch (Throwable e) {
            e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getJWTToken(String id, String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId(id)
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 6000000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return token;
	}
}

