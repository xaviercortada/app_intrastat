package cat.alkaid.projects.intrastat.controllers;

import javax.ws.rs.DELETE;
import javax.annotation.security.DenyAll;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cat.alkaid.projects.intrastat.models.Material;
import cat.alkaid.projects.intrastat.auth.AuthenticatedUser;
import cat.alkaid.projects.intrastat.models.Account;
import javax.inject.Inject;
import cat.alkaid.projects.intrastat.services.MaterialService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

@Component
@Path("/materiales")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class MaterialEndPoint
{
    @Autowired
    private MaterialService service;

/*     @Inject
    @AuthenticatedUser
    private Account authenticatedAccount;
 */    
    @POST
    public Response create(final Material material) {
        try {
            this.service.create(material);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return Response.ok((Object)material).build();
    }
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Long id) {
        final Material Material = this.service.findById(id);
        if (Material == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok((Object)Material).build();
    }
    
    @PUT
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") final Long id, final Material Material) {
        try {
            this.service.update(Material);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }
    
    @DenyAll
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Long id) {
        try {
            this.service.delete(id);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }
}

