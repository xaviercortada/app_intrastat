package cat.alkaid.projects.intrastat.rest;

import javax.ws.rs.DELETE;
import java.util.Iterator;
import java.util.Arrays;
import javax.ws.rs.PUT;
import java.util.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import javax.ws.rs.QueryParam;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import cat.alkaid.projects.intrastat.model.Factura;
import cat.alkaid.projects.intrastat.auth.AuthenticatedUser;
import cat.alkaid.projects.intrastat.model.Account;
import javax.inject.Inject;
import cat.alkaid.projects.intrastat.service.FacturaService;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Path("/facturas")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class FacturaEndpoint
{
    @Inject
    private FacturaService service;
    @Inject
    @AuthenticatedUser
    private Account authenticatedAccount;
    
    @POST
    public Response create(final Factura factura) {
        try {
            this.service.create(this.authenticatedAccount, factura);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return Response.ok((Object)factura).build();
    }
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Long id) {
        final Factura factura = this.service.findById(id);
        if (factura == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok((Object)factura).build();
    }
    
    @GET
    @Path("/codigo/{codigo:[0-9][0-9]*}")
    public List<Factura> findByCodigo(@PathParam("codigo") final String codigo) {
        final List<Factura> facturas = (List<Factura>)this.service.findByCodigo(this.authenticatedAccount, codigo);
        return facturas;
    }
    
    @GET
    public List<Factura> listAll(@QueryParam("start") final Integer startPosition, @QueryParam("max") final Integer maxResult) {
        final List<Factura> facturas = (List<Factura>)this.service.findAll(this.authenticatedAccount);
        return facturas;
    }
    
    @GET
    @Path("/state")
    public List<Factura> listByState(@QueryParam("flujo") final String flujo, @QueryParam("present") final String present, @QueryParam("start") final Integer startPosition, @QueryParam("max") final Integer maxResult) {
        final List<Factura> facturas = (List<Factura>)this.service.findByState(this.authenticatedAccount, flujo, present);
        return facturas;
    }
    
    @GET
    @Path("/proveedor/{id:[0-9][0-9]*}")
    public List<Factura> listByProveedor(@PathParam("id") final Long id, @QueryParam("flujo") final String flujo, @QueryParam("present") final String present, @QueryParam("start") final Integer startPosition, @QueryParam("max") final Integer maxResult) {
        final List<Factura> facturas = (List<Factura>)this.service.findByProveedor(this.authenticatedAccount, flujo, present, id);
        return facturas;
    }
    
    @GET
    @Path("/interval")
    public List<Factura> listByInterval(@QueryParam("fechaIni") final String fechaIni, @QueryParam("fechaFin") final String fechaFin, @QueryParam("flujo") final String flujo, @QueryParam("present") final String present, @QueryParam("start") final Integer startPosition, @QueryParam("max") final Integer maxResult) {
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
    
    @PUT
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") final Long id, final Factura factura) {
        try {
            this.service.update(factura);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }
    
    @PUT
    @Path("/state/{text}/presentado/{presentado}")
    public Response updateState(@PathParam("text") final String items, @PathParam("presentado") final String presentado) {
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
        return Response.noContent().build();
    }
    
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
    
    @DELETE
    @Path("/{idFactura:[0-9][0-9]*}/materiales/{idMaterial:[0-9][0-9]*}")
    public Response deleteMaterialById(@PathParam("idFactura") final Long idFactura, @PathParam("idMaterial") final Long idMaterial) {
        try {
            this.service.removeMaterial(idFactura, idMaterial);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }
}

