package cat.alkaid.projects.intrastat.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import cat.alkaid.projects.intrastat.service.ReportService;

@RequestScoped
@Path("report")
public class ReportEndpoint {
    @EJB
    private ReportService service;

    @GET
	@Path("/{periodo:[0-9][0-9]*}")
    //@Secured
    //public Response excel(@HeaderParam(AuthAccessElement.PARAM_AUTH_ID) String authId, @HeaderParam("per-id") String id_periodo) {
    public Response excel(@PathParam("periodo") final Long id_perido) {
        //Long idPer = Long.parseLong(id_periodo);

        StreamingOutput streamout = service.Basic("x", id_perido);

        //Response.ResponseBuilder response = Response.ok(streamout, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        Response.ResponseBuilder response = Response.ok(streamout, "application/vnd.oasis.opendocument.spreadsheet");
        response.header("content-disposition", "attachment; filename=\"Report-"+"intrastat"+"\".xls");

        return response.build();
    }


}
