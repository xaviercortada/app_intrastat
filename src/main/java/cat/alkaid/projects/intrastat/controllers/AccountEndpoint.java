package cat.alkaid.projects.intrastat.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
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
import org.springframework.web.servlet.ModelAndView;

import cat.alkaid.projects.intrastat.auth.AuthAccessElement;
import cat.alkaid.projects.intrastat.auth.AuthLoginElement;
import cat.alkaid.projects.intrastat.auth.AuthPasswordElement;
import cat.alkaid.projects.intrastat.auth.JwtTokenFactory;
import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.services.AccountService;
import io.jsonwebtoken.impl.DefaultClaims;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("accounts")
// @Produces({ "application/json" })
// @Consumes({ "application/json" })
public class AccountEndpoint {
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";

    @Autowired
    AccountService service;

    @Autowired
    JwtTokenFactory tokenFactory;

    @PostMapping("/")
    public Account create(final Account account) {
        try {
            this.service.create(account);
        } catch (Throwable e) {
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
        final List<Account> accounts = (List<Account>) this.service.findAll();
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
    public ModelAndView signin(ModelMap model) {
       model.addAttribute("attribute", "forwardWithForwardPrefix");
       return new ModelAndView("forward:/auth/login", model);

/*         List<Account> accounts = this.service.findByUsername(ele.getUsername());
        if (accounts.size() == 1) {
            Account account = accounts.get(0);
            // String token = getJWTToken(account.getId().toString(),
            // account.getUserName());
            String token = tokenFactory.doGenerateToken(account);
            account.setToken(token);
            this.service.update(account);

            final AuthAccessElement accessElement = new AuthAccessElement(account.getUsername(), account.getId());
            accessElement.setAuthToken(token);

            return accessElement;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
 */    }

    @PostMapping("/token")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        return ""; //tokenFactory.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
    }

    @PostMapping("/changePassword")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(final AuthPasswordElement passwordElement) {
        try {
            /*
             * if (passwordElement.getOld_password().equals(this.authenticatedAccount.
             * getPlainPwd())) {
             * this.service.changePassword(this.authenticatedAccount.getId(),
             * passwordElement); }
             */ } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/userName/{texto}")
    public List<Account> getByUserName(@PathVariable(name = "texto") final String texto) {
        final List<Account> accounts = (List<Account>) this.service.findByUsername(texto);
        return accounts;
    }

    @DeleteMapping("/companies/{idCompany:[0-9][0-9]*}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterialById(@PathVariable(name = "Company") final Long idCompany) {
        try {
            // this.service.removeCompany(this.authenticatedAccount.getId(), idCompany);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
}
