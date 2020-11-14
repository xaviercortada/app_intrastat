
package cat.alkaid.projects.intrastat.rest;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javax.ws.rs.GET;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import cat.alkaid.projects.intrastat.auth.AuthenticatedUser;
import javax.inject.Inject;
import cat.alkaid.projects.intrastat.model.Account;
import cat.alkaid.projects.intrastat.service.ExportService;
import javax.ejb.EJB;
import cat.alkaid.projects.intrastat.service.ReportService;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@Path("report")
public class ReportEndpoint
{
    @EJB
    private ReportService reportService;
    @EJB
    private ExportService exportService;
    @Inject
    @AuthenticatedUser
    private Account authenticatedAccount;
    
    @GET
    @Path("/excel/{periodo:[0-9][0-9]*}/flujo/{flujo}")
    public Response toExcel(@PathParam("periodo") final Long id_perido, @PathParam("flujo") final String flujo) {
        final StreamingOutput streamout = this.reportService.Basic(this.authenticatedAccount, id_perido, flujo);
        final Response.ResponseBuilder response = Response.ok((Object)streamout, "application/vnd.oasis.opendocument.spreadsheet");
        response.header("content-disposition", (Object)"attachment; filename=\"Report-intrastat\".xls");
        return response.build();
    }
    
    @GET
    @Path("/excel/selection/{text}/flujo/{flujo}")
    public Response toExcel(@PathParam("text") final String items, @PathParam("flujo") final String flujo) {
        final List<String> numbers = Arrays.asList(items.split(","));
        final List<Long> selected = new ArrayList<Long>();
        for (final String x : numbers) {
            selected.add(Long.parseLong(x));
        }
        final StreamingOutput streamout = this.reportService.Basic((List)selected, flujo);
        final Response.ResponseBuilder response = Response.ok((Object)streamout, "application/vnd.oasis.opendocument.spreadsheet");
        response.header("content-disposition", (Object)"attachment; filename=\"Report-intrastat\".xls");
        return response.build();
    }
    
    @GET
    @Path("/csv/selection/{text}/flujo/{flujo}")
    public Response toCSV(@PathParam("text") final String items, @PathParam("flujo") final String flujo) {
        final List<String> numbers = Arrays.asList(items.split(","));
        final List<Long> selected = new ArrayList<Long>();
        for (final String x : numbers) {
            selected.add(Long.parseLong(x));
        }
        final StreamingOutput streamout = this.exportService.to_csv(this.authenticatedAccount, (List)selected, flujo);
        final Response.ResponseBuilder response = Response.ok((Object)streamout, "application/vnd.oasis.opendocument.spreadsheet");
        response.header("content-disposition", (Object)"attachment; filename=\"Report-intrastat\".csv");
        return response.build();
    }
    
    @GET
    @Path("/csv/{periodo:[0-9][0-9]*}/flujo/{flujo}")
    public Response toCSV(@PathParam("periodo") final Long id_perido, @PathParam("flujo") final String flujo) {
        final StreamingOutput streamout = this.exportService.to_csv(this.authenticatedAccount, id_perido, flujo);
        final Response.ResponseBuilder response = Response.ok((Object)streamout, "application/vnd.oasis.opendocument.spreadsheet");
        response.header("content-disposition", (Object)"attachment; filename=\"Report-intrastat\".csv");
        return response.build();
    }
}

