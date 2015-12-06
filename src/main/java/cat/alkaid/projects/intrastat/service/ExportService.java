package cat.alkaid.projects.intrastat.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;

import cat.alkaid.projects.intrastat.model.Factura;

/**
 * Created by xavier on 21/07/15.
 */

@Stateless
public class ExportService {

    final String TITLE = "TECNOPLUS";
    final String INTRASTAT_TYPE = "EXPORT";

    final int COLUMN_PAIS = 0; 				//Estado miembro de procedencia/destino(A2)
    final int COLUMN_PROVINCIA = 1; 		//Provincia de destino/origen(N2)
    final int COLUMN_ENTREGA = 2;			//Condiciones de entrega(A3)
    final int COLUMN_TRANSACCION = 3;		//Naturaleza de la Transacción(N2)
    final int COLUMN_TRANSPORTE = 4;		//Modalidad de Transporte(N1)
    final int COLUMN_PUERTO = 5;			//Puerto/Aeropuerto de Carga o Descarga(N4)
    final int COLUMN_NOMENCLATURA = 6;		//Código de las mercancias(N8)
    final int COLUMN_PAIS_ORIGEN = 7;		//País de Origen(A2)
    final int COLUMN_REG_ESTADISTICO = 8;	//Régimen estadístico(N1)
    final int COLUMN_MASA_NETA = 9;			//Masa Neta en Kg.(N12 ó N12,3)
    final int COLUMN_UNIDADES_SUP = 10;		//Unidades Suplementarias(N12 ó N12,3)
    final int COLUMN_IMP_FACTURADO = 11;	//Importe Facturado(N13,2)
    final int COLUMN_VAL_ESTADISTICO = 12;	//Valor Estadístico(N13,2)
    
    //Ex: FR;31;FOB;11;3;;85182190;CN;1;115;162;15,37;15,37
    //Ex: DE;28;CIF;11;1;0811;85182190;US;1;2459;1982;4589,46;4589,46
    //Ex: IT;12;FOB;11;3;;02012030;;1;800;;987,00;890,45 

    final String columns[] = {"PAIS","PROVINCIA","ENTREGA","TRANSACCION","TRANSPORTE","PUERTO","NOMENCLATURA","PAIS ORIGEN",
            "REG.ESTADISTICO","MASA NETA","UNIDADES SUP.","IMP.FACTURADO","VAL.ESTADISTICO"};

    final int widths[] = {2500,2500,5500,1500,4500,2500,
            2500,2500,2500,2500,2500,3000,2500};


    DataFormat format;

    @EJB
    FacturaService facturaService;

    @PersistenceContext
    private EntityManager em;

    ArrayList<Cell[]> totalCells = new ArrayList<Cell[]>();

    public StreamingOutput to_csv(String authId, Long idPeriodo){

    	//List<Factura> facturas = facturaService.findByPeriodo(authId, idPeriodo);
        List<Factura> facturas = facturaService.findAll();

        final List<String> items = new ArrayList<String>();

        for (Factura fact : facturas) {
            items.addAll(fact.toCSV(';','\n'));
        }

        StreamingOutput streamout = new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
            	Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            	for (String item : items) {
					writer.write(item);
				}
            	writer.flush();
            	writer.close();
            }
        };

        return streamout;
    }


}
