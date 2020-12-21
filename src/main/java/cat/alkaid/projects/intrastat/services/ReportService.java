package cat.alkaid.projects.intrastat.services;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
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
public class ReportService {
    final String NUMBER_FORMAT = "#,##0.00_);[Red](#,##0.00)";
    final String TITLE = "TECNOPLUS";
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
    final int COLUMN_TOT_VALOR_EST = 12;
    final String[] columns;
    final int[] widths;
    DataFormat format;

    @Autowired
    FacturaService facturaService;
    
    public ReportService() {
        this.columns = new String[] { "CODIGO", "FACTURA", "DESCRIPCI\u00d3N", "PAIS", "PROVEEDOR", "PESO", "T.PESO", "IMPORTE", "T.IMP", "UNID", "T.UNID", "VALOR EST.", "T.V.EST" };
        this.widths = new int[] { 2500, 2500, 5500, 1500, 4500, 2500, 2500, 2500, 2500, 2500, 2500, 2500, 3000 };
    }
    
    public StreamingOutput Basic(final List<Long> selected, final String flujo) {
        final List<Factura> facturas = new ArrayList<Factura>();
        for (final Long id : selected) {
            facturas.add(this.facturaService.findById(id));
        }
        return this.process(flujo, facturas);
    }
    
    public StreamingOutput Basic(final Account account, final Long idPeriodo, final String flujo) {
        final List<Factura> facturas = (List<Factura>)this.facturaService.findByState(account, flujo, "N");
        return this.process(flujo, facturas);
    }
    
    private StreamingOutput process(final String flujo, final List<Factura> facturas) {
        final List<Cell[]> totalCells = new ArrayList<Cell[]>();
        final HSSFWorkbook wb = new HSSFWorkbook();
        this.format = (DataFormat)wb.createDataFormat();
        final HSSFSheet sheet = wb.createSheet("Tecnoplus");
        sheet.setDefaultRowHeight((short)265);
        this.fillTitle(sheet, flujo);
        this.fillHeaders(sheet);
        final List<MaterialDto> materials = new ArrayList<MaterialDto>();
        for (final Factura fact : facturas) {
            for (final Material mat : fact.getMateriales()) {
                final MaterialDto dto = new MaterialDto(fact, mat);
                materials.add(dto);
            }
        }
        Collections.sort(materials);
        int i = 7;
        int r0 = 0;
        String reportKey = null;
        String tmpKey = null;
        for (final MaterialDto material : materials) {
            tmpKey = material.getKey();
            if (reportKey == null || !reportKey.equals(tmpKey)) {
                if (r0 > 0) {
                    totalCells.add(this.writeSubtotal(sheet, r0, i - 1));
                    ++i;
                }
                this.writeCategory(sheet, i++, material);
                reportKey = tmpKey;
                r0 = i;
            }
            this.writeItem(sheet, i++, material);
        }
        totalCells.add(this.writeSubtotal(sheet, r0, i - 1));
        i += 2;
        this.writeTotal(sheet, totalCells, i);

        // final StreamingOutput streamout = (StreamingOutput)new StreamingOutput(this, wb);

        StreamingOutput streamout = new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                wb.write(outputStream);
            }
        };

        return streamout;
    }
    
    private Cell[] writeSubtotal(final HSSFSheet sheet, final int initRow, final int finalRow) {
        final Cell[] cells = new Cell[4];
        final CellStyle cellStyle = (CellStyle)sheet.getWorkbook().createCellStyle();
        final Font font = (Font)sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        cellStyle.setFont(font);
        cellStyle.setDataFormat(this.format.getFormat("#,##0.00_);[Red](#,##0.00)"));
        final Row row = (Row)sheet.createRow(finalRow + 1);
        char c = (char)((char)'A' + '\u0005');
        Cell cell = row.createCell(6);
        cell.setCellStyle(cellStyle);
        String formula = String.format("SUM(%c%02d:%c%02d)", c, initRow + 1, c, finalRow + 1);
        cell.setCellFormula(formula);
        cells[0] = cell;
        c = (char)((char)'A' + '\u0007');
        cell = row.createCell(8);
        cell.setCellStyle(cellStyle);
        formula = String.format("SUM(%c%02d:%c%02d)", c, initRow + 1, c, finalRow + 1);
        cell.setCellFormula(formula);
        cells[1] = cell;
        c = (char)((char)'A' + '\t');
        cell = row.createCell(10);
        cell.setCellStyle(cellStyle);
        formula = String.format("SUM(%c%02d:%c%02d)", c, initRow + 1, c, finalRow + 1);
        cell.setCellFormula(formula);
        cells[2] = cell;
        c = (char)((char)'A' + '\u000b');
        cell = row.createCell(12);
        cell.setCellStyle(cellStyle);
        formula = String.format("SUM(%c%02d:%c%02d)", c, initRow + 1, c, finalRow + 1);
        cell.setCellFormula(formula);
        cells[3] = cell;
        return cells;
    }
    
    private void writeTotal(final HSSFSheet sheet, final List<Cell[]> totalCells, final int i) {
        final CellStyle cellStyle = (CellStyle)sheet.getWorkbook().createCellStyle();
        final Font font = (Font)sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        BorderStyle thin = BorderStyle.THIN;
        cellStyle.setBorderTop(thin);
        cellStyle.setBorderBottom(thin);
        cellStyle.setFont(font);
        cellStyle.setDataFormat(this.format.getFormat("#,##0.00_);[Red](#,##0.00)"));
        final Row row = (Row)sheet.createRow(i);
        final Cell[] resum = new Cell[4];
        final char[] columns = new char[4];
        columns[0] = (char)((char)'A' + '\u0005');
        (resum[0] = row.createCell(6)).setCellStyle(cellStyle);
        columns[1] = (char)((char)'A' + '\u0007');
        (resum[1] = row.createCell(8)).setCellStyle(cellStyle);
        columns[2] = (char)((char)'A' + '\t');
        (resum[2] = row.createCell(10)).setCellStyle(cellStyle);
        columns[3] = (char)((char)'A' + '\u000b');
        (resum[3] = row.createCell(12)).setCellStyle(cellStyle);
        final StringBuilder[] s = { new StringBuilder(), new StringBuilder(), new StringBuilder(), new StringBuilder() };
        for (final Cell[] cells : totalCells) {
            char c = (char)('A' + cells[0].getColumnIndex());
            s[0].append(String.format("%c%02d,", c, cells[0].getRowIndex() + 1));
            c = (char)('A' + cells[1].getColumnIndex());
            s[1].append(String.format("%c%02d,", c, cells[1].getRowIndex() + 1));
            c = (char)('A' + cells[2].getColumnIndex());
            s[2].append(String.format("%c%02d,", c, cells[2].getRowIndex() + 1));
            c = (char)('A' + cells[3].getColumnIndex());
            s[3].append(String.format("%c%02d,", c, cells[3].getRowIndex() + 1));
        }
        for (int col = 0; col < resum.length; ++col) {
            final String formula = String.format("SUM(%s)", s[col].toString());
            resum[col].setCellFormula(formula);
        }
    }
    
    private void writeItem(final HSSFSheet sheet, final int i, final MaterialDto material) {
        final Row row = (Row)sheet.createRow(i);
        Cell cell = row.createCell(1);
        this.fillCellWithValue(cell, material.getCodFactura());
        cell = row.createCell(2);
        this.fillCellWithValue(cell, material.getEntrega());
        cell = row.createCell(4);
        this.fillCellWithValue(cell, material.getProveedor());
        cell = row.createCell(3);
        this.fillCellWithValue(cell, material.getSiglas());
        cell = row.createCell(5);
        this.fillCellWithValue(cell, material.getPeso());
        cell = row.createCell(7);
        this.fillCellWithValue(cell, material.getPrice());
        cell = row.createCell(9);
        this.fillCellWithValue(cell, material.getUnidades());
        cell = row.createCell(11);
        this.fillCellWithValue(cell, material.getVestadistico());
    }
    
    private void writeCategory(final HSSFSheet sheet, final int i, final MaterialDto mat) {
        final Row row = (Row)sheet.createRow(i);
        Cell cell = row.createCell(0);
        this.fillCellWithValue(cell, mat.getCodCategory());
        cell = row.createCell(2);
        this.fillCellWithValue(cell, mat.getNameCategory());
    }
    
    private void fillCellWithValue(final Cell cell, final Object value) {
        if (value != null) {
            if (value instanceof String) {
                cell.setCellValue((String)value);
            }
            else if (value instanceof Integer) {
                cell.setCellValue((double)(int)value);
            }
            else if (value instanceof Float) {
                final CellStyle style = cell.getCellStyle();
                style.setDataFormat(this.format.getFormat("#,##0.00_);[Red](#,##0.00)"));
                cell.setCellValue((double)(float)value);
            }
            else if (value instanceof BigDecimal) {
                final CellStyle style = cell.getCellStyle();
                style.setDataFormat(this.format.getFormat("#,##0.00_);[Red](#,##0.00)"));
                final BigDecimal x = (BigDecimal)value;
                cell.setCellValue(x.doubleValue());
            }
            else {
                cell.setCellValue("");
            }
        }
        else {
            cell.setCellValue("");
        }
    }
    
    private void fillTitle(final HSSFSheet sheet, final String flujo) {
        final Row row = (Row)sheet.createRow(2);
        row.setHeight((short)350);
        final CellStyle cellStyle = (CellStyle)sheet.getWorkbook().createCellStyle();
        final Font font = (Font)sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        cellStyle.setFont(font);
        Cell cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        this.fillCellWithValue(cell, "TECNOPLUS");
        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        if (flujo.equalsIgnoreCase("I")) {
            this.fillCellWithValue(cell, "IMPORT");
        }
        else {
            this.fillCellWithValue(cell, "EXPORT");
        }
    }
    
    private void fillHeaders(final HSSFSheet sheet) {
        final Row row = (Row)sheet.createRow(4);
        row.setHeight((short)350);
        final CellStyle cellStyle = (CellStyle)sheet.getWorkbook().createCellStyle();
        final Font font = (Font)sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        cellStyle.setFont(font);
        for (int i = 0; i < this.columns.length; ++i) {
            sheet.setDefaultColumnStyle(i, cellStyle);
            sheet.setColumnWidth(i, this.widths[i]);
        }
        final CellStyle headerStyle = (CellStyle)sheet.getWorkbook().createCellStyle();
        final Font headerFont = (Font)sheet.getWorkbook().createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short)10);
        headerStyle.setFont(headerFont);
        for (int j = 0; j < this.columns.length; ++j) {
            final Cell cell = row.createCell(j);
            cell.setCellStyle(headerStyle);
            this.fillCellWithValue(cell, this.columns[j]);
        }
    }
}
