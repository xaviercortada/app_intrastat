package cat.alkaid.projects.intrastat.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.alkaid.projects.intrastat.auth.IAuthenticationFacade;
import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.services.ExportService;
import cat.alkaid.projects.intrastat.services.ReportService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("report")
public class ReportEndpoint {
    @Autowired
    private ReportService reportService;

    @Autowired
    private ExportService exportService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    private Account authenticatedAccount()
    {
        return authenticationFacade.getAuthentication();
    };

    @GetMapping("/excel/{periodo:[0-9][0-9]*}/flujo/{flujo}")
    public void toExcel(@PathVariable("periodo") Long id_perido, @PathVariable("flujo") String flujo, HttpServletResponse response)
            throws WebApplicationException, IOException {
        StreamingOutput streamout = this.reportService.Basic(this.authenticatedAccount(), id_perido, flujo);

        //response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
        response.setContentType("application/vnd.ms-excel");
        
        response.setHeader("content-disposition", "attachment; filename=\"Report-" + "intrastat" + "\".xls");

        streamout.write(response.getOutputStream());
    }
    
    @GetMapping("/excel/selection/{text}/flujo/{flujo}")
    public void toExcel(@PathVariable("text")  String items, @PathVariable("flujo") String flujo, HttpServletResponse response)
            throws WebApplicationException, IOException {
        final List<String> numbers = Arrays.asList(items.split(","));
        final List<Long> selected = new ArrayList<Long>();
        for (final String x : numbers) {
            selected.add(Long.parseLong(x));
        }
        final StreamingOutput streamout = this.reportService.Basic((List<Long>)selected, flujo);

        //response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment; filename=\"Report-" + "intrastat" + "\".xls");

        streamout.write(response.getOutputStream());
    }
    
    @GetMapping("/csv/selection/{text}/flujo/{flujo}")
    public void toCSV(@PathVariable("text") final String items, @PathVariable("flujo") final String flujo, HttpServletResponse response)
            throws WebApplicationException, IOException {
        final List<String> numbers = Arrays.asList(items.split(","));
        final List<Long> selected = new ArrayList<Long>();
        for (final String x : numbers) {
            selected.add(Long.parseLong(x));
        }
        StreamingOutput streamout = this.exportService.to_csv(this.authenticatedAccount(), (List<Long>)selected, flujo);

        //response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
        response.setContentType("text/csv");
        response.setHeader("content-disposition", "attachment; filename=\"Report-"+"intrastat"+"\".csv");

        streamout.write(response.getOutputStream());

    }
    
    @GetMapping("/csv/{periodo:[0-9][0-9]*}/flujo/{flujo}")
    public void toCSV(@PathVariable("periodo")  Long id_perido, @PathVariable("flujo") final String flujo, HttpServletResponse response)
            throws WebApplicationException, IOException {
        final StreamingOutput streamout = this.exportService.to_csv(this.authenticatedAccount(), id_perido, flujo);

        //response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
        response.setContentType("text/csv");
        response.setHeader("content-disposition", "attachment; filename=\"Report-"+"intrastat"+"\".csv");

        streamout.write(response.getOutputStream());
    }
}
