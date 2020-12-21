package cat.alkaid.projects.intrastat.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.Account;
import cat.alkaid.projects.intrastat.models.Factura;
import cat.alkaid.projects.intrastat.models.Material;
import cat.alkaid.projects.intrastat.models.MaterialDto;

/**
 * Created by xavier on 21/07/15.
 */
@Service
public class ExportService
{
    final String TITLE = "TECNOPLUS";
    final String INTRASTAT_TYPE = "EXPORT";
    final int COLUMN_PAIS = 0;
    final int COLUMN_PROVINCIA = 1;
    final int COLUMN_ENTREGA = 2;
    final int COLUMN_TRANSACCION = 3;
    final int COLUMN_TRANSPORTE = 4;
    final int COLUMN_PUERTO = 5;
    final int COLUMN_NOMENCLATURA = 6;
    final int COLUMN_PAIS_ORIGEN = 7;
    final int COLUMN_REG_ESTADISTICO = 8;
    final int COLUMN_MASA_NETA = 9;
    final int COLUMN_UNIDADES_SUP = 10;
    final int COLUMN_IMP_FACTURADO = 11;
    final int COLUMN_VAL_ESTADISTICO = 12;
    final String[] columns;
    final int[] widths;
    DataFormat format;

    @Autowired
    FacturaService facturaService;
    @Autowired
    PeriodoService periodoService;
    
    public ExportService() {
        this.columns = new String[] { "PAIS", "PROVINCIA", "ENTREGA", "TRANSACCION", "TRANSPORTE", "PUERTO", "NOMENCLATURA", "PAIS ORIGEN", "REG.ESTADISTICO", "MASA NETA", "UNIDADES SUP.", "IMP.FACTURADO", "VAL.ESTADISTICO" };
        this.widths = new int[] { 2500, 2500, 5500, 1500, 4500, 2500, 2500, 2500, 2500, 2500, 2500, 3000, 2500 };
    }
    
    public StreamingOutput to_csv(final Account account, final List<Long> selected, final String flujo) {
        final List<Factura> facturas = new ArrayList<Factura>();
        for (final Long id : selected) {
            facturas.add(this.facturaService.findById(id));
        }
        return this.process(facturas, flujo);
    }
    
    public StreamingOutput to_csv(final Account account, final Long idPeriodo, final String flujo) {
        final List<Factura> facturas = (List<Factura>)this.facturaService.findByState(account, flujo, "N");
        return this.process(facturas, flujo);
    }
    
    private StreamingOutput process(final List<Factura> facturas, final String flujo) {
        final List<String> items = new ArrayList<String>();
        final List<MaterialDto> materials = new ArrayList<MaterialDto>();
        final HashMap<String, MaterialDto> partidas = new HashMap<String, MaterialDto>();
        for (final Factura fact : facturas) {
            for (final Material mat : fact.getMateriales()) {
                final MaterialDto dto = new MaterialDto(fact, mat);
                materials.add(dto);
            }
        }
        Collections.sort(materials);
        String key = null;
        for (final MaterialDto material : materials) {
            key = material.getKey();
            if (partidas.containsKey(key)) {
                final MaterialDto dto2 = partidas.get(key);
                final int peso = dto2.getPeso() + material.getPeso();
                final BigDecimal price = dto2.getPrice().add(material.getPrice());
                final BigDecimal vestadistico = dto2.getVestadistico().add(material.getVestadistico());
                final int unidades = dto2.getUnidades() + material.getUnidades();
                dto2.setPeso(Integer.valueOf(peso));
                dto2.setPrice(price);
                dto2.setVestadistico(vestadistico);
                dto2.setUnidades(Integer.valueOf(unidades));
                partidas.put(key, dto2);
            }
            else {
                partidas.put(key, material);
            }
        }
        for (final MaterialDto dto3 : partidas.values()) {
            items.add(dto3.toCSV(';', '\n'));
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
