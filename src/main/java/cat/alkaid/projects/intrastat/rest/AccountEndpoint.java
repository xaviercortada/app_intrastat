package cat.alkaid.projects.intrastat.rest;

import cat.alkaid.projects.intrastat.auth.AuthPasswordElement;
import javax.annotation.security.PermitAll;
import cat.alkaid.projects.intrastat.auth.AuthAccessElement;
import cat.alkaid.projects.intrastat.auth.AuthLoginElement;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import cat.alkaid.projects.intrastat.auth.AdminRole;
import java.util.List;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import cat.alkaid.projects.intrastat.auth.AuthenticatedUser;
import cat.alkaid.projects.intrastat.model.Account;
import cat.alkaid.projects.intrastat.service.AuthService;
import javax.inject.Inject;
import cat.alkaid.projects.intrastat.service.AccountService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Path("/accounts")
@Produces({ "application/json" })
@Consumes({ "application/json" })
public class AccountEndpoint
{
    @Inject
    AccountService service;
    @Inject
    AuthService authService;
    @Inject
    @AuthenticatedUser
    private Account authenticatedAccount;
    
    @POST
    public Response create(final Account account) {
        try {
            this.service.create(account);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return Response.ok((Object)account).build();
    }
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Long id) {
        final Account account = this.service.findById(id);
        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok((Object)account).build();
    }
    
    @AdminRole
    @GET
    public List<Account> listAll(@QueryParam("start") final Integer startPosition, @QueryParam("max") final Integer maxResult) {
        final List<Account> accounts = (List<Account>)this.service.findAll();
        return accounts;
    }
    
    @PUT
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") final Long id, final Account account) {
        this.service.update(account);
        return Response.noContent().build();
    }
    
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Long id) {
        this.service.delete(id);
        return Response.noContent().build();
    }
    
    @PermitAll
    @POST
    @Path("/signin")
    public Response signin(final AuthLoginElement loginElement) {
        try {
            final AuthAccessElement accesssElement = this.authService.Login(loginElement);
            return Response.ok((Object)accesssElement).build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("/changePassword")
    public Response changePassword(final AuthPasswordElement passwordElement) {
        try {
            if (passwordElement.getOld_password().equals(this.authenticatedAccount.getPlainPwd())) {
                this.service.changePassword(this.authenticatedAccount.getId(), passwordElement);
                return Response.ok((Object)passwordElement).build();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Response.serverError().build();
    }
    
    @GET
    @Path("/userName/{texto}")
    public List<Account> getByUserName(@PathParam("texto") final String texto) {
        final List<Account> accounts = (List<Account>)this.service.findByUsername(texto);
        return accounts;
    }
    
    @DELETE
    @Path("/companies/{idCompany:[0-9][0-9]*}")
    public Response deleteMaterialById(@PathParam("idCompany") final Long idCompany) {
        try {
            this.service.removeCompany(this.authenticatedAccount.getId(), idCompany);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }
}

