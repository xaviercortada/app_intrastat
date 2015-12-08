package cat.alkaid.projects.intrastat.model;

import org.apache.poi.ss.formula.functions.Replace;

/**
 * Created by xavier on 27/09/15.
 */

public class MaterialDto implements Comparable {

    private String codFactura;

    private String codCategory;

    private String nameCategory;

    private String siglas;

    private int codPais;

    private String proveedor;

    private String entrega;

    private Integer peso;

    private String importe;

    private Integer unidades;

    private String name;
    
    private Float vestadistico;

    public MaterialDto(Factura factura, Material material){
        setCodCategory(material.getNomenclature().getCodeCN8());
        setCodFactura(factura.getCodigo());
        setEntrega(material.getEntrega());
        setImporte(material.getImporte());
        setNameCategory(material.getNomenclature().getDescription());
        setPeso(material.getPeso());
        setUnidades(material.getUnidades());
        setSiglas(factura.getPais().getSigla());
        setCodPais(factura.getPais().getCodigo());
        setEntrega(factura.getEntrega());
        setProveedor(factura.getProveedor().getName());
    }

    public String getCodFactura() {
        return codFactura;
    }

    public void setCodFactura(String codFactura) {
        this.codFactura = codFactura;
    }

    public String getCodCategory() {
        return codCategory;
    }

    public void setCodCategory(String codCategory) {
        this.codCategory = codCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
    	
        this.importe = importe;
    }

    public Float getPrice() {
    	try{
    		String p = this.importe.replace(".", "");
    		p = p.replace(",", ".");
    		return Float.parseFloat(p);
    	}catch(Exception e){
    		return (float) 0;
    	}
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCodPais() {
        return codPais;
    }

    public void setCodPais(int codPais) {
        this.codPais = codPais;
    }

    public Float getVestadistico() {
		return vestadistico;
	}

	public void setVestadistico(Float vestadistico) {
		this.vestadistico = vestadistico;
	}

	public String getKey(){
        return String.format("%s%s%02d", getCodCategory(),
                getEntrega(), getCodPais());
    }

    @Override
    public int compareTo(Object o) {
        MaterialDto dto = (MaterialDto)o;
        return this.getKey().compareTo(dto.getKey());
    }
}
