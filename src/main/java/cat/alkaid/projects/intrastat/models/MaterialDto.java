package cat.alkaid.projects.intrastat.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.math.BigDecimal;

public class MaterialDto implements Comparable<MaterialDto>
{
    final String NUMBER_FORMAT = "#0,00";
    private String flujo;
    private String codFactura;
    private String codCategory;
    private String nameCategory;
    private String siglas;
    private int codProvincia;
    private String codPais;
    private String codPaisOrigen;
    private String proveedor;
    private String entrega;
    private Integer peso;
    private String importe;
    private Integer unidades;
    private String name;
    private int codTransaccion;
    private String codTransporte;
    private int codRegimen;
    private BigDecimal price;
    private BigDecimal vestadistico;
    
    public MaterialDto(final Factura factura, final Material material) {
        this.setCodCategory((String) material.getNomenclature().getCodeCN8());
        this.setCodFactura(factura.getCodigo());
        this.setEntrega(material.getEntrega());
        this.setImporte(material.getImporte());
        this.setPrice(material.getPrice());
        this.setNameCategory(material.getNomenclature().getDescription());
        this.setPeso(material.getPeso());
        this.setUnidades(material.getUnidades());
        this.setSiglas(factura.getPais().getSigla());
        this.setEntrega(factura.getEntrega());
        this.setProveedor(factura.getProveedor().getName());
        this.setCodProvincia(factura.getProvincia().getCodigo());
        this.setCodRegimen(factura.getRegimen());
        this.setCodTransaccion(factura.getTransaccion());
        this.setCodTransporte(factura.getTransporte().getCodigo());
        this.setVestadistico(material.getVestadistico());
        this.setCodPaisOrigen("");
        if (factura.getOrigen() != null) {
            this.setCodPaisOrigen(String.format("%02d", factura.getOrigen().getCodigo()));
        }
        this.setCodPais("");
        if (factura.getPais() != null) {
            this.setCodPais(String.format("%02d", factura.getPais().getCodigo()));
        }
        this.setCodPaisOrigen("");
        if (factura.getOrigen() != null) {
            this.setCodPaisOrigen(String.format("%02d", factura.getOrigen().getCodigo()));
        }
    }
    
    public String getCodFactura() {
        return this.codFactura;
    }
    
    public void setCodFactura(final String codFactura) {
        this.codFactura = codFactura;
    }
    
    public String getCodCategory() {
        return this.codCategory;
    }
    
    public void setCodCategory(final String codCategory) {
        this.codCategory = codCategory;
    }
    
    public String getNameCategory() {
        return this.nameCategory;
    }
    
    public void setNameCategory(final String nameCategory) {
        this.nameCategory = nameCategory;
    }
    
    public String getSiglas() {
        return this.siglas;
    }
    
    public void setSiglas(final String siglas) {
        this.siglas = siglas;
    }
    
    public String getProveedor() {
        return this.proveedor;
    }
    
    public void setProveedor(final String proveedor) {
        this.proveedor = proveedor;
    }
    
    public String getEntrega() {
        return this.entrega;
    }
    
    public void setEntrega(final String entrega) {
        this.entrega = entrega;
    }
    
    public Integer getPeso() {
        return this.peso;
    }
    
    public void setPeso(final Integer peso) {
        this.peso = peso;
    }
    
    public String getImporte() {
        return this.importe;
    }
    
    public void setImporte(final String importe) {
        this.importe = importe;
    }
    
    public void setPrice(final BigDecimal price2) {
        this.price = price2;
    }
    
    public BigDecimal getPrice() {
        return this.price;
    }
    
    public Integer getUnidades() {
        return this.unidades;
    }
    
    public void setUnidades(final Integer unidades) {
        this.unidades = unidades;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getCodPais() {
        return this.codPais;
    }
    
    public void setCodPais(final String codPais) {
        this.codPais = codPais;
    }
    
    public BigDecimal getVestadistico() {
        return this.vestadistico;
    }
    
    public void setVestadistico(final BigDecimal vestadistico2) {
        this.vestadistico = vestadistico2;
    }
    
    public String getKey() {
        return String.format("%s%s%s", this.getCodCategory(), this.getEntrega(), this.getCodPais());
    }
    
    public String toCSV(final char delimeter, final char endRecord) {
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);
        final DecimalFormat decFormat = (DecimalFormat)nf;
        decFormat.setGroupingUsed(false);
        decFormat.setMinimumFractionDigits(2);
        final String line = String.format(Locale.FRANCE, "%2s%1c%02d%1c%3s%1c%2d%1c%d%1c%1c%8s%1c%s%1c%1d%1c%d%1c%d%1c%s%1c%s%c", this.siglas, delimeter, this.codProvincia, delimeter, this.entrega, delimeter, this.codTransaccion, delimeter, Integer.parseInt(this.codTransporte), delimeter, delimeter, this.codCategory, delimeter, this.codPaisOrigen, delimeter, this.codRegimen, delimeter, this.getPeso(), delimeter, this.unidades, delimeter, decFormat.format(this.price), delimeter, decFormat.format(this.vestadistico), endRecord);
        return line;
    }
    
   
    public String getCodTransporte() {
        return this.codTransporte;
    }
    
    public void setCodTransporte(final String codTransporte) {
        this.codTransporte = codTransporte;
    }
    
    public int getCodTransaccion() {
        return this.codTransaccion;
    }
    
    public void setCodTransaccion(final int codTransaccion) {
        this.codTransaccion = codTransaccion;
    }
    
    public int getCodRegimen() {
        return this.codRegimen;
    }
    
    public void setCodRegimen(final int codRegimen) {
        this.codRegimen = codRegimen;
    }
    
    public String getCodPaisOrigen() {
        return this.codPaisOrigen;
    }
    
    public void setCodPaisOrigen(final String codPaisOrigen) {
        this.codPaisOrigen = codPaisOrigen;
    }
    
    public String getFlujo() {
        return this.flujo;
    }
    
    public void setFlujo(final String flujo) {
        this.flujo = flujo;
    }
    
    public int getCodProvincia() {
        return this.codProvincia;
    }
    
    public void setCodProvincia(final int codProvincia) {
        this.codProvincia = codProvincia;
    }

    @Override
    public int compareTo(MaterialDto o) {
        return this.getKey().compareTo(o.getKey());
   }
}