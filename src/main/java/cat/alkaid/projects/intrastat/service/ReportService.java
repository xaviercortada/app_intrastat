package cat.alkaid.projects.intrastat.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

import cat.alkaid.projects.intrastat.model.Factura;
import cat.alkaid.projects.intrastat.model.Material;
import cat.alkaid.projects.intrastat.model.MaterialDto;

/**
 * Created by xavier on 21/07/15.
 */

@Stateless
public class ReportService {
    final String NUMBER_FORMAT = "#,##0.00_);[Red](#,##0.00)";

    final String TITLE = "TECNOPLUS";
    final String INTRASTAT_TYPE = "EXPORT";

    final int COLUMN_CODIGO = 0;
    final int COLUMN_FACTURA = 1;
    final int COLUMN_DESC = 2;
    final int COLUMN_PAIS = 3;
    final int COLUMN_PROVEEDOR = 4;
    final int COLUMN_PESO = 5;
    final int COLUMN_TOT_PESO = 6;
    final int COLUMN_IMP = 7;
    final int COLUMN_TOT_IMP = 8;
    final int COLUMN_UNIDADES = 9;
    final int COLUMN_TOT_UNIDADES = 10;
    final int COLUMN_VALOR_EST = 11;

    final String columns[] = {"CODIGO","FACTURA","DESCRIPCIÓN","PAIS","PROVEEDOR","PESO","T.PESO","IMPORTE",
            "T.IMP","UNID","T.UNID","VALOR EST."};

    final int widths[] = {2500,2500,5500,1500,4500,2500,
            2500,2500,2500,2500,2500,3000};


    DataFormat format;

    @EJB
    FacturaService facturaService;

    @PersistenceContext
    private EntityManager em;


    public StreamingOutput Basic(String authId, Long idPeriodo){
        List<Cell[]> totalCells = new ArrayList<Cell[]>();

        final HSSFWorkbook wb = new HSSFWorkbook();

        format = wb.createDataFormat();

        HSSFSheet sheet = wb.createSheet("Tecnoplus");
        sheet.setDefaultRowHeight((short) 265);

        fillTitle(sheet);

        fillHeaders(sheet);

        //List<Factura> facturas = facturaService.findByPeriodo(authId, idPeriodo);
        List<Factura> facturas = facturaService.findAll();
        List<MaterialDto> materials = new ArrayList<MaterialDto>();

        for(Factura fact : facturas){
            for(Material mat : fact.getMateriales()) {
                MaterialDto dto = new MaterialDto(fact, mat);
                materials.add(dto);
            }
        }

        Collections.sort(materials);


        int i = 7;
        int r0 = 0;
        String reportKey = null;
        String tmpKey = null;
        Row row = null;
        for (MaterialDto material : materials) {
            tmpKey = material.getKey();

            if (reportKey == null || (!reportKey.equals(tmpKey))) {
                if (r0 > 0) {
                	totalCells.add(writeSubtotal(sheet, r0, i - 1));
                    i++;
                }
                writeCategory(sheet, i++, material);
                reportKey = tmpKey;
                r0 = i;
            }
            writeItem(sheet, i++, material);

        }

        totalCells.add(writeSubtotal(sheet, r0, i-1));


        i+=2;
        writeTotal(sheet, totalCells, i);

        StreamingOutput streamout = new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                wb.write(outputStream);
            }
        };

        return streamout;
    }

    private Cell[] writeSubtotal(HSSFSheet sheet, int initRow, int finalRow) {
        Cell[] cells = new Cell[4];

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);
        cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT));

        Row row = sheet.createRow(finalRow+1);

        char c = (char)(Character.valueOf('A')+COLUMN_PESO);
        Cell cell = row.createCell(COLUMN_TOT_PESO);
        cell.setCellStyle(cellStyle);
        String formula = String.format("SUM(%c%02d:%c%02d)", c, initRow+1, c, finalRow+1);
        cell.setCellFormula(formula);
        cells[0] = cell;

        c = (char)(Character.valueOf('A')+COLUMN_IMP);
        cell = row.createCell(COLUMN_TOT_IMP);
        cell.setCellStyle(cellStyle);
        formula = String.format("SUM(%c%02d:%c%02d)", c, initRow+1, c, finalRow+1);
        cell.setCellFormula(formula);
        cells[1] = cell;

        c = (char)(Character.valueOf('A')+COLUMN_UNIDADES);
        cell = row.createCell(COLUMN_TOT_UNIDADES);
        cell.setCellStyle(cellStyle);
        formula = String.format("SUM(%c%02d:%c%02d)", c, initRow+1, c, finalRow+1);
        cell.setCellFormula(formula);
        cells[2] = cell;

        c = (char)(Character.valueOf('A')+COLUMN_TOT_IMP);
        cell = row.createCell(COLUMN_VALOR_EST);
        cell.setCellStyle(cellStyle);
        formula = String.format("%c%02d-%c%02d*3/100", c, row.getRowNum()+1, c, row.getRowNum()+1);
        cell.setCellFormula(formula);
        cells[3] = cell;

        return cells;
    }

    private void writeTotal(HSSFSheet sheet, List<Cell[]> totalCells, int i) {
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderBottom((short) 2);
        cellStyle.setFont(font);
        cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT));

        Row row = sheet.createRow(i);

        Cell resum[] = new Cell[4];
        char columns[] = new char[4];

        columns[0] = (char)(Character.valueOf('A')+COLUMN_PESO);
        resum[0] = row.createCell(COLUMN_TOT_PESO);
        resum[0].setCellStyle(cellStyle);

        columns[1] = (char)(Character.valueOf('A')+COLUMN_IMP);
        resum[1] = row.createCell(COLUMN_TOT_IMP);
        resum[1].setCellStyle(cellStyle);

        columns[2] = (char)(Character.valueOf('A')+COLUMN_UNIDADES);
        resum[2] = row.createCell(COLUMN_TOT_UNIDADES);
        resum[2].setCellStyle(cellStyle);

        columns[3] = (char)(Character.valueOf('A')+COLUMN_TOT_IMP);
        resum[3] = row.createCell(COLUMN_VALOR_EST);
        resum[3].setCellStyle(cellStyle);


        StringBuilder[] s = new StringBuilder[4];
        s[0] = new StringBuilder();
        s[1] = new StringBuilder();
        s[2] = new StringBuilder();
        s[3] = new StringBuilder();

        for(Cell[] cells : totalCells){
            char c = (char)(Character.valueOf('A')+cells[0].getColumnIndex());
            s[0].append(String.format("%c%02d,", c,  cells[0].getRowIndex()+1));

            c = (char)(Character.valueOf('A')+cells[1].getColumnIndex());
            s[1].append(String.format("%c%02d,", c,  cells[1].getRowIndex()+1));

            c = (char)(Character.valueOf('A')+cells[2].getColumnIndex());
            s[2].append(String.format("%c%02d,", c,  cells[2].getRowIndex()+1));

            c = (char)(Character.valueOf('A')+cells[3].getColumnIndex());
            s[3].append(String.format("%c%02d,", c, cells[3].getRowIndex() + 1));
        }

        for(int col=0; col<resum.length; col++) {
            String formula = String.format("SUM(%s)", s[col].toString());
            resum[col].setCellFormula(formula);
        }
    }

    private void writeItem(HSSFSheet sheet, int i, MaterialDto material){
        Row row = sheet.createRow(i);

        Cell cell = row.createCell(COLUMN_FACTURA);
        fillCellWithValue(cell, material.getCodFactura());

        cell = row.createCell(COLUMN_DESC);
        fillCellWithValue(cell, material.getEntrega());

        cell = row.createCell(COLUMN_PROVEEDOR);
        fillCellWithValue(cell, material.getProveedor());

        cell = row.createCell(COLUMN_PAIS);
        fillCellWithValue(cell, material.getSiglas());

        cell = row.createCell(COLUMN_PESO);
        fillCellWithValue(cell, material.getPeso());

        cell = row.createCell(COLUMN_IMP);
        fillCellWithValue(cell, material.getPrice());

        cell = row.createCell(COLUMN_UNIDADES);
        fillCellWithValue(cell, material.getUnidades());
    }

    private void writeCategory(HSSFSheet sheet, int i, MaterialDto mat){
        Row row = sheet.createRow(i);

        Cell cell = row.createCell(COLUMN_CODIGO);
        fillCellWithValue(cell, mat.getCodCategory());

        cell = row.createCell(COLUMN_DESC);
        fillCellWithValue(cell, mat.getNameCategory());
    }

    private void fillCellWithValue(Cell cell, Object value){
        if(value != null){
            if(value instanceof String)
                cell.setCellValue((String)value);
            else if(value instanceof Integer)
                cell.setCellValue((Integer)value);
            else if(value instanceof Float) {
                CellStyle style = cell.getCellStyle();
                style.setDataFormat(format.getFormat(NUMBER_FORMAT));
                cell.setCellValue((Float) value);
            }
            else
                cell.setCellValue("");

        }else{
            cell.setCellValue("");

        }
    }

    private void fillTitle(HSSFSheet sheet) {
        Row row = sheet.createRow(2);
        row.setHeight((short)350);

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);

        Cell cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        fillCellWithValue(cell, TITLE);

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        fillCellWithValue(cell, INTRASTAT_TYPE);

    }

    private void fillHeaders(HSSFSheet sheet){
        Row row = sheet.createRow(4);
        row.setHeight((short)350);

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontHeightInPoints((short) 10);
        cellStyle.setFont(font);

        for(int i=0;i<columns.length; i++) {
            sheet.setDefaultColumnStyle(i, cellStyle);
            sheet.setColumnWidth(i, widths[i]);
        }

        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
        Font headerFont = sheet.getWorkbook().createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short) 10);
        headerStyle.setFont(headerFont);

        for(int i=0;i<columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            fillCellWithValue(cell, columns[i]);
        }
    }

}
