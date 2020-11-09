package cat.alkaid.projects.intrastat.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import cat.alkaid.projects.intrastat.models.Factura;
import cat.alkaid.projects.intrastat.services.FacturaService;

@Path("/facturas")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class FacturaEndpoint {
	@Autowired
    private FacturaService service;
	
	@POST
	public Response create(final Factura factura) {
		try{
			service.create(factura);
		}catch(Throwable e){
			e.printStackTrace();
		}
		//here we use Category#getId(), assuming that it provides the identifier to retrieve the created Category resource.
		return Response.ok(factura).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Factura factura = service.findById(id);
		
		if (factura == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(factura).build();
	}

	@GET
	@Path("/codigo/{codigo:[0-9][0-9]*}")
	public List<Factura> findByCodigo(@PathParam("codigo") final String codigo) {
		final List<Factura> facturas = service.findByCodigo(codigo);
		return facturas;
	}

	@GET
	public List<Factura> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the facturas 
		final List<Factura> facturas = service.findAll();
		return facturas;
	}

	@GET
	@Path("/proveedor/{id:[0-9][0-9]*}")
	public List<Factura> listByProveedor(@PathParam("id") final Long id,
			@QueryParam("flujo") final String flujo,
			@QueryParam("present") final String present,
			@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {

		List<Factura> facturas;
		if(flujo.equalsIgnoreCase("X")){
			facturas = service.findByProveedor("", present, id);
		}else{
			facturas = service.findByFlujoProveedor("", flujo, present, id);
		}
		return facturas;
	}

	@GET
	@Path("/interval")
	public List<Factura> listByInterval(@QueryParam("fechaIni") final String fechaIni,
			@QueryParam("fechaFin") final String fechaFin,
			@QueryParam("flujo") final String flujo,
			@QueryParam("present") final String present,
			@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<Factura> facturas = new ArrayList<Factura>();
		try {
			Date date1 = formatter.parse(fechaIni);
			Date date2 = formatter.parse(fechaFin);
			if(flujo.equalsIgnoreCase("X")){
				facturas = service.findByIntervalo("", present, date1, date2);
			}else{
				facturas = service.findByFlujoIntervalo("", flujo, present, date1, date2);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return facturas;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final Factura factura) {
		try{
			service.update(factura);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		try{
			service.delete(id);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{idFactura:[0-9][0-9]*}/materiales/{idMaterial:[0-9][0-9]*}")
	public Response deleteMaterialById(@PathParam("idFactura") final Long idFactura,
			@PathParam("idMaterial") final Long idMaterial) {
		try{
			service.removeMaterial(idFactura, idMaterial);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return Response.noContent().build();
	}
}
