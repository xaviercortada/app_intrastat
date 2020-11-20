package cat.alkaid.projects.intrastat.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/excel/{periodo:[0-9][0-9]*}")
    public void toExcel(@PathVariable("periodo") Long id_perido,  HttpServletResponse response)
            throws WebApplicationException, IOException {

        StreamingOutput streamout = reportService.Basic("x", id_perido);

        // Response.ResponseBuilder response = Response.ok(streamout,
        // "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
        response.setHeader("content-disposition", "attachment; filename=\"Report-" + "intrastat" + "\".xls");

        streamout.write(response.getOutputStream());
    }

    @GetMapping("/csv/{periodo:[0-9][0-9]*}")
    public void toCSV(@PathVariable("periodo") Long id_perido, HttpServletResponse response)
            throws WebApplicationException, IOException {

        StreamingOutput streamout = exportService.to_csv("x", id_perido);

        //Response.ResponseBuilder response = Response.ok(streamout, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
        response.setHeader("content-disposition", "attachment; filename=\"Report-"+"intrastat"+"\".csv");

        streamout.write(response.getOutputStream());
    }
}
