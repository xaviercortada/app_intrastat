package cat.alkaid.projects.intrastat.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import cat.alkaid.projects.intrastat.auth.AuthenticatedUser;
import cat.alkaid.projects.intrastat.auth.IAuthenticationFacade;
import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.models.Factura;
import cat.alkaid.projects.intrastat.services.FacturaService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("facturas")
public class FacturaEndPoint
{
    @Autowired
    private FacturaService service;
    
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    private Account authenticatedAccount;
    
    @PostMapping("")
    public Factura create(@RequestBody Factura factura) {
        try {
            this.service.create(this.authenticatedAccount, factura);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return factura;
    }
    
    @GetMapping("/{id:[0-9][0-9]*}")
    public Factura findById(@PathVariable("id") final Long id) {
        final Factura factura = this.service.findById(id);
        if (factura == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return factura;
    }
    
    @GetMapping("/codigo/{codigo:[0-9][0-9]*}")
    public List<Factura> findByCodigo(@PathVariable("codigo") final String codigo) {
        final List<Factura> facturas = (List<Factura>)this.service.findByCodigo(this.authenticatedAccount, codigo);
        return facturas;
    }
    
    @GetMapping("")
    public List<Factura> listAll(@RequestParam("start") Integer startPosition, @RequestParam(value = "max", required = false) Integer maxResult) {
        final List<Factura> facturas = (List<Factura>)this.service.findAll(this.authenticatedAccount);
        return facturas;
    }
    
    @GetMapping("/state")
    public List<Factura> listByState(@RequestParam("flujo") String flujo, @RequestParam("present") String present, 
        @RequestParam(value = "start", required = false) Integer startPosition, @RequestParam(value = "max", required = false) Integer maxResult) {

        final List<Factura> facturas = (List<Factura>)this.service.findByState(this.authenticatedAccount, flujo, present);
        return facturas;
    }
    
    @GetMapping("/proveedor/{id:[0-9][0-9]*}")
    public List<Factura> listByProveedor(@PathVariable("id") final Long id, @RequestParam(value = "flujo", required = false) String flujo, 
        @RequestParam(value = "present", required = false) String present, @RequestParam(value = "start", required = false) Integer startPosition, 
        @RequestParam(value = "max", required = false) Integer maxResult) {
        
        this.authenticatedAccount = authenticationFacade.getAuthentication();

        final List<Factura> facturas = (List<Factura>)this.service.findByProveedor(this.authenticatedAccount, flujo, present, id);
        return facturas;
    }
    
    @GetMapping("/interval")
    public List<Factura> listByInterval(@RequestParam("fechaIni") final String fechaIni, @RequestParam("fechaFin") final String fechaFin, 
        @RequestParam("flujo") final String flujo, @RequestParam("present") final String present, @RequestParam("start") final Integer startPosition, 
        @RequestParam("max") final Integer maxResult) {

        final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<Factura> facturas = new ArrayList<Factura>();
        try {
            final Date date1 = formatter.parse(fechaIni);
            final Date date2 = formatter.parse(fechaFin);
            facturas = (List<Factura>)this.service.findByIntervalo(this.authenticatedAccount, flujo, present, date1, date2);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return facturas;
    }
    
    @PutMapping("/{id:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") final Long id, @RequestBody Factura factura) {
        try {
            this.service.update(factura);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @PutMapping("/state/{text}/presentado/{presentado}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateState(@PathVariable("text") final String items, @PathVariable("presentado") final String presentado) {
        try {
            final List<String> numbers = Arrays.asList(items.split(","));
            final List<Long> selected = new ArrayList<Long>();
            for (final String x : numbers) {
                selected.add(Long.parseLong(x));
            }
            final Boolean bPresentado = presentado.equals("S");
            this.service.updateState((List)selected, bPresentado);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @DeleteMapping("/{id:[0-9][0-9]*}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable final Long id) {
        try {
            this.service.delete(id);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    @DeleteMapping("/{idFactura:[0-9][0-9]*}/materiales/{idMaterial:[0-9][0-9]*}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaterialById(@PathVariable("idFactura") final Long idFactura, @PathVariable("idMaterial") final Long idMaterial) {
        try {
            this.service.removeMaterial(idFactura, idMaterial);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
